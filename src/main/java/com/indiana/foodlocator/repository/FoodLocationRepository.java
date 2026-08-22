package com.indiana.foodlocator.repository;

import com.indiana.foodlocator.entity.FoodLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodLocationRepository extends JpaRepository<FoodLocation, Long>  {
    
    List<FoodLocation> findByActiveTrue();

    List<FoodLocation> findByCityIgnoreCaseAndActiveTrue(String city);

    List<FoodLocation> findByZipCodeAndActiveTrue(String zipCode);

    List<FoodLocation> findByCategoryIgnoreCaseAndActiveTrue(String category);

    List<FoodLocation> findByCityIgnoreCaseAndCategoryIgnoreCaseAndActiveTrue(
            String city, String category);

    List<FoodLocation> findByZipCodeAndCategoryIgnoreCaseAndActiveTrue(
            String zipCode, String category);
}
