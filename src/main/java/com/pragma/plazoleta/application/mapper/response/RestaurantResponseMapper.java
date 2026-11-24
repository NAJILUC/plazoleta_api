package com.pragma.plazoleta.application.mapper.response;

import com.pragma.plazoleta.application.dto.response.RestaurantResponse;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantResponseMapper {

    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    default RestaurantResponse toRestaurantResponse(Restaurant restaurant, User user, Role role){
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setId(restaurant.getId());
        restaurantResponse.setName(restaurant.getName());
        restaurantResponse.setAddress(restaurant.getAddress());
        restaurantResponse.setPhoneNumber(restaurant.getPhoneNumber());
        restaurantResponse.setNit(restaurant.getNit());
        restaurantResponse.setUrlLogo(restaurant.getUrlLogo());
        restaurantResponse.setOwner(INSTANCE.toUserResponse(user, role));
        return restaurantResponse;
    }
}
