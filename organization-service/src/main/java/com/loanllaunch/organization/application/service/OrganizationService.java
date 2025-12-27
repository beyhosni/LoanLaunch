package com.loanllaunch.organization.application.service;

import com.loanllaunch.common.exception.BusinessException;
import com.loanllaunch.common.exception.ResourceNotFoundException;
import com.loanllaunch.events.organization.OrganizationCreatedEvent;
import com.loanllaunch.events.organization.OrganizationUpdatedEvent;
import com.loanllaunch.organization.adapter.in.rest.dto.CreateOrganizationRequest;
import com.loanllaunch.organization.adapter.in.rest.dto.OrganizationResponse;
import com.loanllaunch.organization.adapter.in.rest.dto.UpdateOrganizationRequest;
import com.loanllaunch.organization.adapter.in.rest.mapper.OrganizationMapper;
import com.loanllaunch.organization.adapter.out.kafka.OrganizationEventPublisher;
import com.loanllaunch.organization.adapter.out.persistence.OrganizationRepository;
import com.loanllaunch.organization.domain.model.Organization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service for managing organizations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final OrganizationEventPublisher eventPublisher;

    @Transactional
    public OrganizationResponse createOrganization(CreateOrganizationRequest request) {
        log.info("Creating organization with name: {}", request.getName());

        // Check if tax ID already exists
        if (organizationRepository.existsByTaxId(request.getTaxId())) {
            throw new BusinessException("Organization with tax ID " + request.getTaxId() + " already exists", "DUPLICATE_TAX_ID");
        }

        // Create organization
        Organization organization = organizationMapper.toEntity(request);
        Organization savedOrganization = organizationRepository.save(organization);

        // Publish event
        OrganizationCreatedEvent event = new OrganizationCreatedEvent(
                savedOrganization.getId(),
                new OrganizationCreatedEvent.OrganizationCreatedPayload(
                        savedOrganization.getId(),
                        savedOrganization.getName(),
                        savedOrganization.getLegalName(),
                        savedOrganization.getTaxId(),
                        savedOrganization.getIndustry(),
                        savedOrganization.getStatus().name()
                )
        );
        eventPublisher.publishOrganizationCreated(event);

        log.info("Organization created successfully with ID: {}", savedOrganization.getId());
        return organizationMapper.toResponse(savedOrganization);
    }

    @Transactional(readOnly = true)
    public OrganizationResponse getOrganizationById(UUID id) {
        log.info("Fetching organization with ID: {}", id);
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", id.toString()));
        return organizationMapper.toResponse(organization);
    }

    @Transactional(readOnly = true)
    public Page<OrganizationResponse> getAllOrganizations(Pageable pageable) {
        log.info("Fetching all organizations with pagination: {}", pageable);
        return organizationRepository.findAll(pageable)
                .map(organizationMapper::toResponse);
    }

    @Transactional
    public OrganizationResponse updateOrganization(UUID id, UpdateOrganizationRequest request) {
        log.info("Updating organization with ID: {}", id);

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", id.toString()));

        organizationMapper.updateEntityFromRequest(request, organization);
        Organization updatedOrganization = organizationRepository.save(organization);

        // Publish event
        OrganizationUpdatedEvent event = new OrganizationUpdatedEvent(
                updatedOrganization.getId(),
                new OrganizationUpdatedEvent.OrganizationUpdatedPayload(
                        updatedOrganization.getId(),
                        updatedOrganization.getName(),
                        updatedOrganization.getStatus().name()
                )
        );
        eventPublisher.publishOrganizationUpdated(event);

        log.info("Organization updated successfully with ID: {}", updatedOrganization.getId());
        return organizationMapper.toResponse(updatedOrganization);
    }

    @Transactional
    public void deleteOrganization(UUID id) {
        log.info("Deleting organization with ID: {}", id);

        if (!organizationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Organization", id.toString());
        }

        organizationRepository.deleteById(id);
        log.info("Organization deleted successfully with ID: {}", id);
    }
}
