
package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.exception.UserNotOwnerException;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(
                1L,
                "Restaurante 123",
                "123456789",
                "Calle 10",
                "3001234567",
                "https://logo.com/image.png",
                2L
        );
    }

    @Test
    void shouldSaveRestaurantWhenUserIsOwner() {
        // Arrange
        Restaurant savedRestaurant = new Restaurant(
                10L,
                restaurant.getName(),
                restaurant.getNit(),
                restaurant.getAddress(),
                "3001234567",
                "https://logo.com/image.png",
                2L
        );

        when(restaurantPersistencePort.saveRestaurant(restaurant))
                .thenReturn(savedRestaurant);

        // Act
        Restaurant result = restaurantUseCase.saveRestaurant(
                restaurant, "PROPIETARIO"
        );

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(restaurant.getName(), result.getName());

        verify(restaurantPersistencePort, times(1))
                .saveRestaurant(restaurant);
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotOwner() {
        // Arrange
        String roleName = "ADMINISTRADOR";

        // Act & Assert
        assertThrows(
                UserNotOwnerException.class,
                () -> restaurantUseCase.saveRestaurant(restaurant, roleName)
        );

        verify(restaurantPersistencePort, never())
                .saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenRoleIsDifferentFromOwner() {
        // Arrange
        String roleName = "CLIENTE";

        // Act & Assert
        assertThrows(
                UserNotOwnerException.class,
                () -> restaurantUseCase.saveRestaurant(restaurant, roleName)
        );

        verify(restaurantPersistencePort, never())
                .saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenRoleIsNull() {
        // Act & Assert
        assertThrows(
                UserNotOwnerException.class,
                () -> restaurantUseCase.saveRestaurant(restaurant, null)
        );

        verify(restaurantPersistencePort, never())
                .saveRestaurant(any());
    }
}
