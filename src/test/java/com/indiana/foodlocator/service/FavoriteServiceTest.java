package com.indiana.foodlocator.service;

import com.indiana.foodlocator.entity.Favorite;
import com.indiana.foodlocator.entity.FoodLocation;
import com.indiana.foodlocator.entity.User;
import com.indiana.foodlocator.repository.FavoriteRepository;
import com.indiana.foodlocator.repository.FoodLocationRepository;
import com.indiana.foodlocator.repository.UserRepository;
import com.indiana.foodlocator.service.FavoriteService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FoodLocationRepository foodLocationRepository;

    private FavoriteService favoriteService;

    @BeforeEach
    void setUp() {
        favoriteService = new FavoriteService(
                favoriteRepository,
                userRepository,
                foodLocationRepository);
    }

    // Test 1. Test retrieving favorites for a user, ensuring that the correct list
    // of favorites is returned.

    @Test
    void getFavoritesForUserReturnsUsersFavorites() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        FoodLocation location = new FoodLocation();
        location.setId(1L);
        location.setName("Indianapolis Community Pantry");

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setFoodLocation(location);

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(java.util.Optional.of(user));

        when(favoriteRepository.findByUser(user))
                .thenReturn(List.of(favorite));

        List<Favorite> results = favoriteService.getFavoritesForUser("test@example.com");

        assertEquals(1, results.size());
        assertEquals(
                "Indianapolis Community Pantry",
                results.get(0).getFoodLocation().getName());

        verify(userRepository, times(1))
                .findByEmail("test@example.com");

        verify(favoriteRepository, times(1))
                .findByUser(user);
    }

    // Test 2. Test adding a favorite for a user, ensuring that the favorite is saved

    @Test
    void addFavoriteSavesFavoriteWhenNotAlreadySaved() {

        User user = new User();
        user.setEmail("test@example.com");

        FoodLocation location = new FoodLocation();
        location.setName("Indianapolis Community Pantry");

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(java.util.Optional.of(user));

        when(foodLocationRepository.findById(1L))
                .thenReturn(java.util.Optional.of(location));

        when(favoriteRepository
                .existsByUserAndFoodLocation(user, location))
                .thenReturn(false);

        favoriteService.addFavorite(
                "test@example.com",
                1L);

        verify(userRepository, times(1))
                .findByEmail("test@example.com");

        verify(foodLocationRepository, times(1))
                .findById(1L);

        verify(favoriteRepository, times(1))
                .existsByUserAndFoodLocation(user, location);

        verify(favoriteRepository, times(1))
                .save(any(Favorite.class));
    }

    // Test 3. Test to prevent duplicate favorites from being saved for a user. This
    // test checks that if a favorite already exists for a user and a food location,
    // the service does not save a duplicate favorite.

    @Test
    void addFavoriteDoesNotSaveDuplicateFavorite() {

        User user = new User();
        user.setEmail("test@example.com");

        FoodLocation location = new FoodLocation();
        location.setName("Indianapolis Community Pantry");

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(java.util.Optional.of(user));

        when(foodLocationRepository.findById(1L))
                .thenReturn(java.util.Optional.of(location));

        when(favoriteRepository
                .existsByUserAndFoodLocation(user, location))
                .thenReturn(true);

        favoriteService.addFavorite(
                "test@example.com",
                1L);

        verify(userRepository, times(1))
                .findByEmail("test@example.com");

        verify(foodLocationRepository, times(1))
                .findById(1L);

        verify(favoriteRepository, times(1))
                .existsByUserAndFoodLocation(user, location);

        verify(favoriteRepository, never())
                .save(any(Favorite.class));
    }

    // Test 4. Test removing a favorite for a user, ensuring that the favorite is
    // removed from the repository.

    @Test
    void removeFavoriteDeletesExistingFavorite() {

        User user = new User();
        user.setEmail("test@example.com");

        FoodLocation location = new FoodLocation();
        location.setName("Indianapolis Community Pantry");

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setFoodLocation(location);

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(java.util.Optional.of(user));

        when(foodLocationRepository.findById(1L))
                .thenReturn(java.util.Optional.of(location));

        when(favoriteRepository
                .findByUserAndFoodLocation(user, location))
                .thenReturn(java.util.Optional.of(favorite));

        favoriteService.removeFavorite(
                "test@example.com",
                1L);

        verify(userRepository, times(1))
                .findByEmail("test@example.com");

        verify(foodLocationRepository, times(1))
                .findById(1L);

        verify(favoriteRepository, times(1))
                .findByUserAndFoodLocation(user, location);

        verify(favoriteRepository, times(1))
                .delete(favorite);
    }
}
