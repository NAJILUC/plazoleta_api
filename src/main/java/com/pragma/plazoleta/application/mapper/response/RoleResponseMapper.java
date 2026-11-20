package com.pragma.plazoleta.application.mapper.response;

import com.pragma.plazoleta.application.dto.response.RoleResponse;
import com.pragma.plazoleta.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RoleResponseMapper {

    RoleResponse toRoleResponse(Role role);
}
