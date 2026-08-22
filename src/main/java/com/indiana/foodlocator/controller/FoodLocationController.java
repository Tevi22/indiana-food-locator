package com.indiana.foodlocator.controller;

import com.indiana.foodlocator.entity.FoodLocation;
import com.indiana.foodlocator.service.FoodLocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

import java.util.List;

@Controller
public class FoodLocationController {

    private final FoodLocationService foodLocationService;

    public FoodLocationController(FoodLocationService foodLocationService) {
        this.foodLocationService = foodLocationService;
    }

    @GetMapping("/locations")
    public String showLocations(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category,
            Model model) {

        List<FoodLocation> locations;

        boolean hasLocation = location != null && !location.isBlank();

        boolean hasCategory = category != null && !category.isBlank();

        if (!hasLocation && !hasCategory) {

            locations = foodLocationService.getAllActiveLocations();

        } else if (hasLocation &&
                location.matches("\\d{5}") &&
                hasCategory) {

            locations = foodLocationService
                    .getLocationsByZipCodeAndCategory(
                            location, category);

        } else if (hasLocation && hasCategory) {

            locations = foodLocationService
                    .getLocationsByCityAndCategory(
                            location, category);

        } else if (hasLocation &&
                location.matches("\\d{5}")) {

            locations = foodLocationService
                    .getLocationsByZipCode(location);

        } else if (hasLocation) {

            locations = foodLocationService
                    .getLocationsByCity(location);

        } else {

            locations = foodLocationService
                    .getLocationsByCategory(category);
        }

        model.addAttribute("locations", locations);
        model.addAttribute("searchLocation", location);
        model.addAttribute("selectedCategory", category);

        return "locations";
    }

    @GetMapping("/locations/{id}")
    public String showLocationDetails(
            @PathVariable Long id,
            Model model) {

        Optional<FoodLocation> location = foodLocationService.getLocationById(id);

        if (location.isEmpty()) {
            return "redirect:/locations";
        }

        model.addAttribute("location", location.get());

        return "location-details";
    }
}
