package com.indiana.foodlocator.controller;

import com.indiana.foodlocator.service.FavoriteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/favorites")
    public String showFavorites(
            Authentication authentication,
            Model model) {

        model.addAttribute(
                "favorites",
                favoriteService.getFavoritesForUser(
                        authentication.getName()));

        return "favorites";
    }

    @PostMapping("/favorites/{locationId}")
    public String addFavorite(
            @PathVariable Long locationId,
            Authentication authentication) {

        favoriteService.addFavorite(
                authentication.getName(),
                locationId);

        return "redirect:/locations/" + locationId;
    }

    @PostMapping("/favorites/{locationId}/remove")
    public String removeFavorite(
            @PathVariable Long locationId,
            Authentication authentication) {

        favoriteService.removeFavorite(
                authentication.getName(),
                locationId);

        return "redirect:/favorites";
    }
}
