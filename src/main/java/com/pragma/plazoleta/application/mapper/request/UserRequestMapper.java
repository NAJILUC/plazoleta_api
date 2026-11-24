package com.pragma.plazoleta.application.mapper.request;

import com.pragma.plazoleta.application.dto.request.CreateUserRequest;
import com.pragma.plazoleta.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserRequestMapper {

    User toUser(CreateUserRequest createUserRequest);

}
