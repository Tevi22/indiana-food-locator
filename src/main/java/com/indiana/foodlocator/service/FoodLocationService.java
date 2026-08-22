package com.indiana.foodlocator.service;

import com.indiana.foodlocator.entity.FoodLocation;
import com.indiana.foodlocator.repository.FoodLocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodLocationService {

    private final FoodLocationRepository foodLocationRepository;

    public FoodLocationService(FoodLocationRepository foodLocationRepository) {
        this.foodLocationRepository = foodLocationRepository;
    }

    public List<FoodLocation> getAllActiveLocations() {
        return foodLocationRepository.findByActiveTrue();
    }

    public List<FoodLocation> getAllLocations() {
        return foodLocationRepository.findAll();
    }

    public List<FoodLocation> getLocationsByCity(String city) {
        return foodLocationRepository.findByCityIgnoreCaseAndActiveTrue(city);
    }

    public List<FoodLocation> getLocationsByZipCode(String zipCode) {
        return foodLocationRepository.findByZipCodeAndActiveTrue(zipCode);
    }

    public List<FoodLocation> getLocationsByCategory(String category) {
        return foodLocationRepository.findByCategoryIgnoreCaseAndActiveTrue(category);
    }

    public Optional<FoodLocation> getLocationById(Long id) {
        return foodLocationRepository.findById(id);
    }

    public FoodLocation saveLocation(FoodLocation foodLocation) {
        return foodLocationRepository.save(foodLocation);
    }

    public void deleteLocation(Long id) {
        foodLocationRepository.deleteById(id);
    }

    public List<FoodLocation> getLocationsByCityAndCategory(
            String city, String category) {

        return foodLocationRepository
                .findByCityIgnoreCaseAndCategoryIgnoreCaseAndActiveTrue(
                        city, category);
    }

    public List<FoodLocation> getLocationsByZipCodeAndCategory(
            String zipCode, String category) {

        return foodLocationRepository
                .findByZipCodeAndCategoryIgnoreCaseAndActiveTrue(
                        zipCode, category);
    }

    public FoodLocation getLocationByIdOrThrow(Long id) {
        return foodLocationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid food location ID: " + id));
    }

    public void deactivateLocation(Long id) {

        FoodLocation location = getLocationByIdOrThrow(id);

        location.setActive(false);

        foodLocationRepository.save(location);
    }
}
