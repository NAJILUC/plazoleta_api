
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IRoleServicePort roleServicePort;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private RestaurantRequestMapper restaurantRequestMapper;

    @Mock
    private RestaurantResponseMapper restaurantResponseMapper;

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    private CreateRestaurantRequest request;
    private User user;
    private Role role;
    private Restaurant restaurant;
    private Restaurant savedRestaurant;
    private RestaurantResponse restaurantResponse;

    @BeforeEach
    void setUp() {
        request = new CreateRestaurantRequest();
        request.setName("Restaurante 123");
        request.setNit("123456789");
        request.setAddress("Calle 10");
        request.setPhoneNumber("3001234567");
        request.setUrlLogo("https://logo.com/image.png");
        request.setOwnerId(2L);

        user = new User(
                2L,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                1L
        );

        role = new Role(
                1L,
                "PROPIETARIO"
        );
        role.setId(1L);
        role.setName("PROPIETARIO");

        restaurant = new Restaurant(
                null,
                request.getName(),
                request.getNit(),
                request.getAddress(),
                request.getPhoneNumber(),
                request.getUrlLogo(),
                user.getId()
        );
        restaurant.setName(request.getName());
        restaurant.setNit(request.getNit());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhoneNumber(request.getPhoneNumber());
        restaurant.setUrlLogo(request.getUrlLogo());

        savedRestaurant = new Restaurant(
                10L,
                request.getName(),
                request.getNit(),
                request.getAddress(),
                request.getPhoneNumber(),
                request.getUrlLogo(),
                user.getId()
        );

        restaurantResponse = new RestaurantResponse();
        restaurantResponse.setId(10L);
        restaurantResponse.setName(request.getName());
    }

    @Test
    void shouldSaveRestaurantSuccessfully() {
        // Arrange
        when(userServicePort.getUserById(2L)).thenReturn(user);
        when(roleServicePort.getRoleById(1L)).thenReturn(role);
        when(restaurantRequestMapper.toRestaurant(request)).thenReturn(restaurant);
        when(restaurantServicePort.saveRestaurant(restaurant, "PROPIETARIO"))
                .thenReturn(savedRestaurant);
        when(restaurantResponseMapper.toRestaurantResponse(
                savedRestaurant, user, role
        )).thenReturn(restaurantResponse);

        // Act
        RestaurantResponse result = restaurantHandler.saveRestaurant(request);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Restaurante 123", result.getName());

        verify(userServicePort).getUserById(2L);
        verify(roleServicePort).getRoleById(1L);
        verify(restaurantRequestMapper).toRestaurant(request);

        assertEquals(user.getId(), restaurant.getOwnerId());

        verify(restaurantServicePort)
                .saveRestaurant(restaurant, "PROPIETARIO");

        verify(restaurantResponseMapper)
                .toRestaurantResponse(savedRestaurant, user, role);
    }
}
