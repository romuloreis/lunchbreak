package br.com.lunchbreak;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.lunchbreak.lunchbreak.persistence.model.Restaurant;
import br.com.lunchbreak.lunchbreak.persistence.repository.RestaurantRepository;
import br.com.lunchbreak.lunchbreak.service.RestaurantService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RestaurantServiceTests {
     /**
     * Autowire in the service we want to test
     */
    @Autowired
    private RestaurantService restaurantService;

    /**
     * Create a mock implementation of the WidgetRepository
     */
    @MockBean
    private RestaurantRepository restaurantRepository;

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        // Setup our mock repository
        Restaurant restaurant = new  Restaurant(1, "Silva Lanches", "Lanches e buffet");
        doReturn(restaurant).when(restaurantRepository).findById(1l);

        // Execute the service call
        Restaurant returnedRestaurant = restaurantService.loadById(1l);

        // Assert the response
        //Assertions.assertTrue(returnedRestaurant.isPresent(), "restaurant was not found");
        Assertions.assertSame(returnedRestaurant, restaurant, "The restaurant returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        // Setup our mock repository
        doReturn(Optional.empty()).when(restaurantRepository).findById(1l);

        // Execute the service call
        Restaurant returnedRestaurant = restaurantService.loadById(1l);

        // Assert the response
        Assertions.assertFalse(returnedRestaurant != null, "restaurant should not be found");
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        // Setup our mock reepository
        Restaurant restaurant1 = new  Restaurant(1, "Silva Lanches", "Lanches e buffet");
        Restaurant restaurant2 = new  Restaurant(2, "Dias Restaurant", "Apenas buffet");
        doReturn(Arrays.asList(restaurant1, restaurant2)).when(restaurantRepository).findAll();

        // Execute the service call
        List<Restaurant> resturants = restaurantService.loadAll();

        // Assert the response
        Assertions.assertEquals(2, resturants.size(), "findAll should return 2 restaurants");
    }

    @Test
    @DisplayName("Test save widget")
    void testSave() {
        // Setup our mock repository
        // Setup our mock repository
        Restaurant restaurant = new  Restaurant(1, "Silva Lanches", "Lanches e buffet");
        doReturn(restaurant).when(restaurantRepository).save(any());

        // Execute the service call
        Restaurant returnedRestaurant = restaurantService.loadById(1l);

        // Assert the response
        Assertions.assertNotNull(returnedRestaurant, "The saved widget should not be null");
    }
}
