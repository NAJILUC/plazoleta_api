package com.pragma.plazoleta.application.mapper.response;

import com.pragma.plazoleta.application.dto.response.RoleResponse;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserResponseMapper {

    RoleResponseMapper INSTANCE = Mappers.getMapper(RoleResponseMapper.class);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "roleResponse.name", source = "role.name")
    @Mapping(target = "roleResponse.id", source = "role.id")
    UserResponse toUserResponse(User user, Role role);

    default List<UserResponse> toUserResponseList(List<User> userList, List<Role> roleList) {
        return userList.stream()
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setId(user.getId());
                    userResponse.setFirstName(user.getFirstName());
                    userResponse.setLastName(user.getLastName());
                    userResponse.setIdentificationNumber(user.getIdentificationNumber());
                    userResponse.setPhoneNumber(user.getPhoneNumber());
                    userResponse.setBirthDate(user.getBirthDate());
                    userResponse.setEmail(user.getEmail());
                    RoleResponse role = INSTANCE.toRoleResponse(roleList.stream().filter(r -> r.getId().equals(user.getRoleId())).findFirst().orElse(null));
                    userResponse.setRoleResponse(role);
                    return userResponse;
                })
                .toList();
    }
}
