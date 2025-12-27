package com.loanllaunch.organization.adapter.in.rest;

import com.loanllaunch.common.dto.ApiResponse;
import com.loanllaunch.common.dto.PageResponse;
import com.loanllaunch.organization.adapter.in.rest.dto.CreateOrganizationRequest;
import com.loanllaunch.organization.adapter.in.rest.dto.OrganizationResponse;
import com.loanllaunch.organization.adapter.in.rest.dto.UpdateOrganizationRequest;
import com.loanllaunch.organization.application.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for organization management.
 */
@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
@Tag(name = "Organizations", description = "Organization management APIs")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    @Operation(summary = "Create a new organization")
    public ResponseEntity<ApiResponse<OrganizationResponse>> createOrganization(
            @Valid @RequestBody CreateOrganizationRequest request) {
        OrganizationResponse response = organizationService.createOrganization(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Organization created successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get organization by ID")
    public ResponseEntity<ApiResponse<OrganizationResponse>> getOrganizationById(@PathVariable UUID id) {
        OrganizationResponse response = organizationService.getOrganizationById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    @Operation(summary = "Get all organizations with pagination")
    public ResponseEntity<PageResponse<OrganizationResponse>> getAllOrganizations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<OrganizationResponse> organizationPage = organizationService.getAllOrganizations(pageable);

        PageResponse<OrganizationResponse> response = PageResponse.of(
                organizationPage.getContent(),
                organizationPage.getNumber(),
                organizationPage.getSize(),
                organizationPage.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an organization")
    public ResponseEntity<ApiResponse<OrganizationResponse>> updateOrganization(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOrganizationRequest request) {
        OrganizationResponse response = organizationService.updateOrganization(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Organization updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an organization")
    public ResponseEntity<ApiResponse<Void>> deleteOrganization(@PathVariable UUID id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Organization deleted successfully"));
    }
}
