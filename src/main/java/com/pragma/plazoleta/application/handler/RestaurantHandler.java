package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.response.RestaurantResponse;
import com.pragma.plazoleta.application.mapper.request.RestaurantRequestMapper;
import com.pragma.plazoleta.application.mapper.response.RestaurantResponseMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {


    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final RestaurantRequestMapper restaurantRequestMapper;
    private final RestaurantResponseMapper restaurantResponseMapper;

    @Override
    public RestaurantResponse saveRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        User user = userServicePort.getUserById(createRestaurantRequest.getOwnerId());
        Role role = roleServicePort.getRoleById(user.getRoleId());
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(createRestaurantRequest);
        restaurant.setOwnerId(user.getId());
        restaurant = restaurantServicePort.saveRestaurant(restaurant, role.getName());
        return restaurantResponseMapper.toRestaurantResponse(restaurant, user, role);
    }
}
