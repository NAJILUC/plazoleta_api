
package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IRestaurantRepository;
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
class RestaurantJpaAdapterTest {

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private RestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    private Restaurant restaurant;
    private RestaurantEntity restaurantEntity;
    private RestaurantEntity savedRestaurantEntity;
    private Restaurant expectedRestaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(
                null,
                "Restaurante 123",
                "123456789",
                "Calle 10",
                "3001234567",
                "https://logo.com/image.png",
                2L
        );

        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName(restaurant.getName());
        restaurantEntity.setNit(restaurant.getNit());
        restaurantEntity.setAddress(restaurant.getAddress());
        restaurantEntity.setPhoneNumber(restaurant.getPhoneNumber());
        restaurantEntity.setUrlLogo(restaurant.getUrlLogo());
        restaurantEntity.setOwnerId(restaurant.getOwnerId());

        savedRestaurantEntity = new RestaurantEntity();
        savedRestaurantEntity.setId(10L);
        savedRestaurantEntity.setName(restaurant.getName());
        savedRestaurantEntity.setNit(restaurant.getNit());
        savedRestaurantEntity.setAddress(restaurant.getAddress());
        savedRestaurantEntity.setPhoneNumber(restaurant.getPhoneNumber());
        savedRestaurantEntity.setUrlLogo(restaurant.getUrlLogo());
        savedRestaurantEntity.setOwnerId(restaurant.getOwnerId());

        expectedRestaurant = new Restaurant(
                10L,
                restaurant.getName(),
                restaurant.getNit(),
                restaurant.getAddress(),
                restaurant.getPhoneNumber(),
                restaurant.getUrlLogo(),
                restaurant.getOwnerId()
        );
    }

    @Test
    void shouldSaveRestaurantSuccessfully() {
        // Arrange
        when(restaurantEntityMapper.toEntity(restaurant))
                .thenReturn(restaurantEntity);

        when(restaurantRepository.save(restaurantEntity))
                .thenReturn(savedRestaurantEntity);

        when(restaurantEntityMapper.toRestaurant(savedRestaurantEntity))
                .thenReturn(expectedRestaurant);

        // Act
        Restaurant result = restaurantJpaAdapter.saveRestaurant(restaurant);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(restaurant.getName(), result.getName());
        assertEquals(restaurant.getOwnerId(), result.getOwnerId());

        verify(restaurantEntityMapper).toEntity(restaurant);
        verify(restaurantRepository).save(restaurantEntity);
        verify(restaurantEntityMapper).toRestaurant(savedRestaurantEntity);
    }
}
