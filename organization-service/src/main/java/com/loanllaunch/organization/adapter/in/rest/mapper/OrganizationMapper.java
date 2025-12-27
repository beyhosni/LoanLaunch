package com.loanllaunch.organization.adapter.in.rest.mapper;

import com.loanllaunch.organization.adapter.in.rest.dto.CreateOrganizationRequest;
import com.loanllaunch.organization.adapter.in.rest.dto.OrganizationResponse;
import com.loanllaunch.organization.adapter.in.rest.dto.UpdateOrganizationRequest;
import com.loanllaunch.organization.domain.model.Organization;
import org.mapstruct.*;

/**
 * MapStruct mapper for Organization entity and DTOs.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    Organization toEntity(CreateOrganizationRequest request);

    OrganizationResponse toResponse(Organization organization);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(UpdateOrganizationRequest request, @MappingTarget Organization organization);
}
