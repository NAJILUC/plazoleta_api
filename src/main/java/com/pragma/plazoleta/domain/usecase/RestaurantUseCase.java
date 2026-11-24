package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.UserNotOwnerException;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    private static final String ROLE_OWNER = "PROPIETARIO";

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant, String roleName) {
        if (!roleName.equals(ROLE_OWNER)) {
            throw new UserNotOwnerException("Role OWNER expected");
        }
        return restaurantPersistencePort.saveRestaurant(restaurant);
    }
}
