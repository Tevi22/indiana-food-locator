package com.indiana.foodlocator.repository;

import com.indiana.foodlocator.entity.FoodLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.core.annotation.MergedAnnotations.Search;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodLocationRepositoryTest {

    @Autowired
    private FoodLocationRepository foodLocationRepository;

    // Test 1. Test retrieving active food locations, ensuring that only active
    // locations are returned.
    // This test will create two food locations, one active and one inactive, and
    // then call the
    // findByActiveTrue method to verify that only the active location is returned.

    @Test
    void findByActiveTrueReturnsOnlyActiveLocations() {

        FoodLocation activeLocation = new FoodLocation();
        activeLocation.setName("Active Food Pantry");
        activeLocation.setCategory("Food Pantry");
        activeLocation.setAddress("100 Main Street");
        activeLocation.setCity("Indianapolis");
        activeLocation.setState("IN");
        activeLocation.setZipCode("46201");
        activeLocation.setActive(true);

        FoodLocation inactiveLocation = new FoodLocation();
        inactiveLocation.setName("Inactive Food Pantry");
        inactiveLocation.setCategory("Food Pantry");
        inactiveLocation.setAddress("200 Main Street");
        inactiveLocation.setCity("Indianapolis");
        inactiveLocation.setState("IN");
        inactiveLocation.setZipCode("46202");
        inactiveLocation.setActive(false);

        foodLocationRepository.save(activeLocation);
        foodLocationRepository.save(inactiveLocation);

        List<FoodLocation> results = foodLocationRepository.findByActiveTrue();

        boolean containsActiveLocation = results.stream()
                .anyMatch(location -> "Active Food Pantry".equals(location.getName()));

        boolean containsInactiveLocation = results.stream()
                .anyMatch(location -> "Inactive Food Pantry".equals(location.getName()));

        assertEquals(true, containsActiveLocation);
        assertEquals(false, containsInactiveLocation);
    }

    // Test 2: Search by city

    @Test
    void findByCityIgnoreCaseAndActiveTrueReturnsMatchingActiveLocations() {

        FoodLocation location = new FoodLocation();
        location.setName("City Test Pantry");
        location.setCategory("Food Pantry");
        location.setAddress("300 Test Street");
        location.setCity("Indianapolis");
        location.setState("IN");
        location.setZipCode("46203");
        location.setActive(true);

        foodLocationRepository.save(location);

        List<FoodLocation> results = foodLocationRepository
                .findByCityIgnoreCaseAndActiveTrue("indianapolis");

        boolean containsLocation = results.stream()
                .anyMatch(result -> "City Test Pantry".equals(result.getName()));

        assertEquals(true, containsLocation);
    }

    // Test 3: Search by ZIP code

    @Test
    void findByZipCodeAndActiveTrueReturnsMatchingActiveLocations() {

        FoodLocation location = new FoodLocation();
        location.setName("Zip Test Pantry");
        location.setCategory("Food Pantry");
        location.setAddress("400 Test Street");
        location.setCity("Indianapolis");
        location.setState("IN");
        location.setZipCode("46299");
        location.setActive(true);

        foodLocationRepository.save(location);

        List<FoodLocation> results = foodLocationRepository
                .findByZipCodeAndActiveTrue("46299");

        boolean containsLocation = results.stream()
                .anyMatch(result -> "Zip Test Pantry".equals(result.getName()));

        assertEquals(true, containsLocation);
    }

    // Test 4: Search by category

    @Test
    void findByCategoryIgnoreCaseAndActiveTrueReturnsMatchingActiveLocations() {

        FoodLocation location = new FoodLocation();
        location.setName("Category Test Pantry");
        location.setCategory("Community Meal");
        location.setAddress("500 Test Street");
        location.setCity("Indianapolis");
        location.setState("IN");
        location.setZipCode("46298");
        location.setActive(true);

        foodLocationRepository.save(location);

        List<FoodLocation> results = foodLocationRepository
                .findByCategoryIgnoreCaseAndActiveTrue("community meal");

        boolean containsLocation = results.stream()
                .anyMatch(result -> "Category Test Pantry".equals(result.getName()));

        assertEquals(true, containsLocation);
    }

    // Test 6: ZIP + Category

    @Test
    void findByZipCodeAndCategoryReturnsMatchingActiveLocations() {

        FoodLocation location = new FoodLocation();
        location.setName("Zip Category Test");
        location.setCategory("Food Pantry");
        location.setAddress("700 Test Street");
        location.setCity("Indianapolis");
        location.setState("IN");
        location.setZipCode("46297");
        location.setActive(true);

        foodLocationRepository.save(location);

        List<FoodLocation> results = foodLocationRepository
                .findByZipCodeAndCategoryIgnoreCaseAndActiveTrue(
                        "46297", "food pantry");

        boolean containsLocation = results.stream()
                .anyMatch(result -> "Zip Category Test".equals(result.getName()));

        assertEquals(true, containsLocation);
    }
}