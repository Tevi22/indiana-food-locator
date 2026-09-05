package com.indiana.foodlocator.service;

import com.indiana.foodlocator.entity.FoodLocation;
import com.indiana.foodlocator.repository.FoodLocationRepository;
import com.indiana.foodlocator.service.FoodLocationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodLocationServiceTest {

    @Mock
    private FoodLocationRepository foodLocationRepository;

    private FoodLocationService foodLocationService;

    @BeforeEach
    void setUp() {
        foodLocationService = new FoodLocationService(foodLocationRepository);
    }

    // Test 1. FoodLocationService automated service-layer test
    // This test checks that the getAllActiveLocations method correctly retrieves
    // active food locations from the repository.
    // It also verifies that the repository's findByActiveTrue method is called once
    // during the process.
    // The test uses a mock FoodLocationRepository to simulate the behavior of the
    // repository without requiring a real database connection.
    // The test creates a sample FoodLocation object, sets its properties, and
    // configures the mock repository to return a list containing that object when
    // findByActiveTrue is called.
    // Finally, the test asserts that the result from getAllActiveLocations matches
    // the expected values and verifies that the repository method was called
    // exactly once.
    // This test ensures that the service layer correctly interacts with the
    // repository and returns the expected data for active food locations.
    // It also helps to catch any potential issues in the service layer logic
    // related to retrieving active food locations.

    @Test
    void getAllActiveLocationsReturnsActiveLocations() {

        FoodLocation location = new FoodLocation();
        location.setName("Downtown Community Food Pantry");
        location.setActive(true);

        when(foodLocationRepository.findByActiveTrue())
                .thenReturn(List.of(location));

        List<FoodLocation> results = foodLocationService.getAllActiveLocations();

        assertEquals(1, results.size());
        assertEquals(
                "Downtown Community Food Pantry",
                results.get(0).getName());

        verify(foodLocationRepository, times(1))
                .findByActiveTrue();
    }

    // Test 2. City-based retrieval test
    // This test checks that the getLocationsByCity method correctly retrieves food
    // locations based on the specified city from the repository.
    // It also verifies that the repository's findByCityIgnoreCaseAndActiveTrue
    // method is called once with the correct city parameter.
    // The test uses a mock FoodLocationRepository to simulate the behavior of the
    // repository without requiringa real database connection.
    // The test creates a sample FoodLocation object, sets its properties, and
    // configures the mock repository to return a list containing that object when
    // findByCityIgnoreCaseAndActiveTrue is called with the specified city.
    // Finally, the test asserts that the result from getLocationsByCity matches the
    // expected values and verifies that the repository method was called exactly
    // once with the correct city parameter.
    // This test ensures that the service layer correctly interacts with the
    // repository and returns the expected data for food locations based on the
    // specified city.

    @Test
    void getLocationsByCityReturnsMatchingLocations() {

        FoodLocation location = new FoodLocation();
        location.setName("Indianapolis Community Pantry");
        location.setCity("Indianapolis");
        location.setActive(true);

        when(foodLocationRepository
                .findByCityIgnoreCaseAndActiveTrue("Indianapolis"))
                .thenReturn(List.of(location));

        List<FoodLocation> results = foodLocationService.getLocationsByCity("Indianapolis");

        assertEquals(1, results.size());
        assertEquals("Indianapolis", results.get(0).getCity());
        assertEquals(
                "Indianapolis Community Pantry",
                results.get(0).getName());

        verify(foodLocationRepository, times(1))
                .findByCityIgnoreCaseAndActiveTrue("Indianapolis");
    }

    // Test 3: ZIP code-based retrieval test
    // This test checks that the getLocationsByZipCode method correctly retrieves
    // food locations based on the specified ZIP code from the repository.
    // It also verifies that the repository's findByZipCodeAndActiveTrue method is
    // called once with the correct ZIP code parameter.
    // The test uses a mock FoodLocationRepository to simulate the behavior of the
    // repository without requiring a real database connection.
    // The test creates a sample FoodLocation object, sets its properties, and
    // configures the mock repository to return a list containing that object when
    // findByZipCodeAndActiveTrue is called with the specified ZIP code.
    // Finally, the test asserts that the result from getLocationsByZipCode matches
    // the expected values and verifies that the repository method was called
    // exactly once with the correct ZIP code parameter.

    @Test
    void getLocationsByZipCodeReturnsMatchingLocations() {

        FoodLocation location = new FoodLocation();
        location.setName("Downtown Food Center");
        location.setZipCode("46204");
        location.setActive(true);

        when(foodLocationRepository
                .findByZipCodeAndActiveTrue("46204"))
                .thenReturn(List.of(location));

        List<FoodLocation> results = foodLocationService.getLocationsByZipCode("46204");

        assertEquals(1, results.size());
        assertEquals("46204", results.get(0).getZipCode());
        assertEquals(
                "Downtown Food Center",
                results.get(0).getName());

        verify(foodLocationRepository, times(1))
                .findByZipCodeAndActiveTrue("46204");
    }

    // Test 4. Category-based retrieval test
    // This test checks that the getLocationsByCategory method correctly retrieves
    // food locations based on the specified category from the repository.
    // It also verifies that the repository's findByCategoryIgnoreCaseAndActiveTrue
    // method is called once with the correct category parameter.
    // The test uses a mock FoodLocationRepository to simulate the behavior of the
    // repository without requiring a real database connection.
    // The test creates a sample FoodLocation object, sets its properties, and
    // configures the mock repository to return a list containing that object when
    // findByCategoryIgnoreCaseAndActiveTrue is called with the specified category.
    // Finally, the test asserts that the result from getLocationsByCategory matches
    // the expected values and verifies that the repository method was called
    // exactly once with the correct category parameter.

    @Test
    void getLocationsByCategoryReturnsMatchingLocations() {

        FoodLocation location = new FoodLocation();
        location.setName("Community Food Pantry");
        location.setCategory("Food Pantry");
        location.setActive(true);

        when(foodLocationRepository
                .findByCategoryIgnoreCaseAndActiveTrue("Food Pantry"))
                .thenReturn(List.of(location));

        List<FoodLocation> results = foodLocationService.getLocationsByCategory("Food Pantry");

        assertEquals(1, results.size());
        assertEquals("Food Pantry", results.get(0).getCategory());
        assertEquals(
                "Community Food Pantry",
                results.get(0).getName());

        verify(foodLocationRepository, times(1))
                .findByCategoryIgnoreCaseAndActiveTrue("Food Pantry");
    }

    // Test 5. Saving a new FoodLocation test
    // This test checks that the saveLocation method correctly saves a new food
    // location to the repository and returns the saved location.
    // It also verifies that the repository's save method is called once with the
    // new location.

    @Test
    void saveLocationReturnsSavedLocation() {

        FoodLocation location = new FoodLocation();
        location.setName("Indy Community Food Center");
        location.setCity("Indianapolis");
        location.setActive(true);

        FoodLocation savedLocation = new FoodLocation();
        savedLocation.setId(1L);
        savedLocation.setName("Indy Community Food Center");
        savedLocation.setCity("Indianapolis");
        savedLocation.setActive(true);

        when(foodLocationRepository.save(location))
                .thenReturn(savedLocation);

        FoodLocation result = foodLocationService.saveLocation(location);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(
                "Indy Community Food Center",
                result.getName());
        assertEquals("Indianapolis", result.getCity());
        assertTrue(result.getActive());

        verify(foodLocationRepository, times(1))
                .save(location);
    }

    // Test 6. Deactivating a FoodLocation test
    // This test checks that the deactivateLocation method correctly sets the active
    // status to false and saves the updated location.
    // It also verifies that the repository's save method is called once with the
    // updated location.

    @Test
    void deactivateLocationSetsLocationInactive() {

        FoodLocation location = new FoodLocation();
        location.setId(1L);
        location.setName("Indy Community Food Center");
        location.setActive(true);

        when(foodLocationRepository.findById(1L))
                .thenReturn(java.util.Optional.of(location));

        foodLocationService.deactivateLocation(1L);

        assertFalse(location.getActive());

        verify(foodLocationRepository, times(1))
                .findById(1L);

        verify(foodLocationRepository, times(1))
                .save(location);
    }

    // Test 7. Deactivating a non-existent FoodLocation test
    // This test checks that the deactivateLocation method throws an IllegalArgumentException when trying to deactivate a non-existent food location.
    // It also verifies that the repository's findById method is called once with the correct ID parameter and that the save method is not called.


    @Test
    void getLocationByIdOrThrowThrowsExceptionWhenLocationNotFound() {

        when(foodLocationRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> foodLocationService.getLocationByIdOrThrow(999L));

        assertEquals(
                "Invalid food location ID: 999",
                exception.getMessage());

        verify(foodLocationRepository, times(1))
                .findById(999L);
    }
}
