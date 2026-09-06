package com.indiana.foodlocator.controller;

import com.indiana.foodlocator.entity.FoodLocation;
import com.indiana.foodlocator.service.FoodLocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FoodLocationService foodLocationService;

    public AdminController(FoodLocationService foodLocationService) {
        this.foodLocationService = foodLocationService;
    }

    @GetMapping("/locations")
    public String showAdminLocations(Model model) {

        model.addAttribute(
                "locations",
                foodLocationService.getAllLocations());

        return "admin/locations";
    }

    @GetMapping("/locations/new")
    public String showAddLocationForm(Model model) {

        model.addAttribute(
                "foodLocation",
                new FoodLocation());

        return "admin/location-form";
    }

    @PostMapping("/locations")
    public String saveLocation(
            @ModelAttribute FoodLocation foodLocation) {

        foodLocationService.saveLocation(foodLocation);

        return "redirect:/admin/locations";
    }

    @GetMapping("/locations/{id}/edit")
    public String showEditLocationForm(
            @PathVariable Long id,
            Model model) {

        FoodLocation foodLocation = foodLocationService.getLocationByIdOrThrow(id);

        model.addAttribute("foodLocation", foodLocation);

        return "admin/location-form";
    }

    @PostMapping("/locations/{id}")
    public String updateLocation(
            @PathVariable Long id,
            @ModelAttribute FoodLocation foodLocation) {

        foodLocation.setId(id);

        foodLocationService.saveLocation(foodLocation);

        return "redirect:/admin/locations";
    }

    @PostMapping("/locations/{id}/deactivate")
    public String deactivateLocation(
            @PathVariable Long id) {

        foodLocationService.deactivateLocation(id);

        return "redirect:/admin/locations";
    }
}
