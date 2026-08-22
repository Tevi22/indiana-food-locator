package com.indiana.foodlocator.repository;

import com.indiana.foodlocator.entity.Favorite;
import com.indiana.foodlocator.entity.FoodLocation;
import com.indiana.foodlocator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository
        extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser(User user);

    Optional<Favorite> findByUserAndFoodLocation(
            User user,
            FoodLocation foodLocation
    );

    boolean existsByUserAndFoodLocation(
            User user,
            FoodLocation foodLocation
    );
}
