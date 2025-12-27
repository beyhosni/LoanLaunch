package com.loanllaunch.auth.application.service;

import com.loanllaunch.auth.adapter.in.rest.dto.AuthResponse;
import com.loanllaunch.auth.adapter.in.rest.dto.LoginRequest;
import com.loanllaunch.auth.adapter.in.rest.dto.RegisterRequest;
import com.loanllaunch.auth.adapter.out.persistence.RefreshTokenRepository;
import com.loanllaunch.auth.adapter.out.persistence.UserRepository;
import com.loanllaunch.auth.application.security.JwtService;
import com.loanllaunch.auth.domain.model.RefreshToken;
import com.loanllaunch.auth.domain.model.User;
import com.loanllaunch.common.exception.BusinessException;
import com.loanllaunch.common.exception.ResourceNotFoundException;
import com.loanllaunch.events.config.KafkaTopics;
import com.loanllaunch.events.user.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Authentication service for user registration and login.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering user with email: {}", request.getEmail());

        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("User with email " + request.getEmail() + " already exists", "USER_ALREADY_EXISTS");
        }

        // Validate role
        User.UserRole role;
        try {
            role = User.UserRole.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid role: " + request.getRole(), "INVALID_ROLE");
        }

        // Create user
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .organizationId(request.getOrganizationId())
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);

        // Generate tokens
        String accessToken = jwtService.generateToken(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole().name(),
                savedUser.getOrganizationId()
        );
        String refreshToken = jwtService.generateRefreshToken(savedUser.getId(), savedUser.getEmail());

        // Save refresh token
        saveRefreshToken(savedUser.getId(), refreshToken);

        // Publish event
        UserRegisteredEvent event = new UserRegisteredEvent(
                savedUser.getId(),
                new UserRegisteredEvent.UserRegisteredPayload(
                        savedUser.getId(),
                        savedUser.getEmail(),
                        savedUser.getRole().name(),
                        savedUser.getOrganizationId()
                )
        );
        kafkaTemplate.send(KafkaTopics.USER_EVENTS, savedUser.getId().toString(), event);

        log.info("User registered successfully with ID: {}", savedUser.getId());

        return buildAuthResponse(savedUser, accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("User login attempt: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Invalid email or password", "INVALID_CREDENTIALS"));

        if (!user.getEnabled()) {
            throw new BusinessException("User account is disabled", "ACCOUNT_DISABLED");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BusinessException("Invalid email or password", "INVALID_CREDENTIALS");
        }

        // Generate tokens
        String accessToken = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getOrganizationId()
        );
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getEmail());

        // Delete old refresh tokens and save new one
        refreshTokenRepository.deleteByUserId(user.getId());
        saveRefreshToken(user.getId(), refreshToken);

        log.info("User logged in successfully: {}", user.getEmail());

        return buildAuthResponse(user, accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        log.info("Refreshing access token");

        if (!jwtService.validateToken(refreshToken)) {
            throw new BusinessException("Invalid or expired refresh token", "INVALID_REFRESH_TOKEN");
        }

        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new BusinessException("Refresh token not found", "REFRESH_TOKEN_NOT_FOUND"));

        if (storedToken.isExpired()) {
            refreshTokenRepository.delete(storedToken);
            throw new BusinessException("Refresh token expired", "REFRESH_TOKEN_EXPIRED");
        }

        User user = userRepository.findById(storedToken.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", storedToken.getUserId().toString()));

        // Generate new access token
        String accessToken = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getOrganizationId()
        );

        log.info("Access token refreshed for user: {}", user.getEmail());

        return buildAuthResponse(user, accessToken, refreshToken);
    }

    @Transactional
    public void logout(UUID userId) {
        log.info("Logging out user: {}", userId);
        refreshTokenRepository.deleteByUserId(userId);
    }

    private void saveRefreshToken(UUID userId, String token) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(token)
                .expiresAt(Instant.now().plusMillis(refreshExpiration))
                .build();
        refreshTokenRepository.save(refreshToken);
    }

    private AuthResponse buildAuthResponse(User user, String accessToken, String refreshToken) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtExpiration / 1000) // Convert to seconds
                .user(AuthResponse.UserInfo.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .organizationId(user.getOrganizationId())
                        .createdAt(user.getCreatedAt())
                        .build())
                .build();
    }
}
