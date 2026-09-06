package com.indiana.foodlocator.service;

import com.indiana.foodlocator.entity.Favorite;
import com.indiana.foodlocator.entity.FoodLocation;
import com.indiana.foodlocator.entity.User;
import com.indiana.foodlocator.repository.FavoriteRepository;
import com.indiana.foodlocator.repository.FoodLocationRepository;
import com.indiana.foodlocator.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final FoodLocationRepository foodLocationRepository;

    public FavoriteService(
            FavoriteRepository favoriteRepository,
            UserRepository userRepository,
            FoodLocationRepository foodLocationRepository) {

        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.foodLocationRepository = foodLocationRepository;
    }

    public List<Favorite> getFavoritesForUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return favoriteRepository.findByUser(user);
    }

    public void addFavorite(String email, Long locationId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        FoodLocation location = foodLocationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Food location not found"));

        if (!favoriteRepository
                .existsByUserAndFoodLocation(user, location)) {

            Favorite favorite = new Favorite();

            favorite.setUser(user);
            favorite.setFoodLocation(location);

            favoriteRepository.save(favorite);
        }
    }

    public void removeFavorite(String email, Long locationId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        FoodLocation location = foodLocationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Food location not found"));

        favoriteRepository
                .findByUserAndFoodLocation(user, location)
                .ifPresent(favoriteRepository::delete);
    }
}
