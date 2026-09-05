package com.indiana.foodlocator;

import com.indiana.foodlocator.entity.User;
import com.indiana.foodlocator.repository.UserRepository;
import com.indiana.foodlocator.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(
                userRepository,
                passwordEncoder);
    }

    // Test 1. Test the createUser method to ensure that it correctly saves a new
    // user with an encoded password.

    @Test
    void registerUserCreatesUserWithEncodedPasswordAndUserRole() {

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setPassword("Password123");

        when(userRepository.existsByEmail("test@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("Password123"))
                .thenReturn("encodedPassword");

        when(userRepository.save(user))
                .thenReturn(user);

        User result = userService.registerUser(user);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("USER", result.getRole());
        assertEquals("test@example.com", result.getEmail());

        verify(userRepository, times(1))
                .existsByEmail("test@example.com");

        verify(passwordEncoder, times(1))
                .encode("Password123");

        verify(userRepository, times(1))
                .save(user);
    }

    // Test 2. Reject registration if the email is already in use.
    // This test ensures that the service throws an exception when trying to
    // register a user with an email that already exists in the database.
    // It verifies that the userRepository's existsByEmail method is called and that
    // the save method is never called.

    @Test
    void registerUserThrowsExceptionWhenEmailAlreadyExists() {

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setPassword("Password123");

        when(userRepository.existsByEmail("test@example.com"))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(user));

        assertNotNull(exception.getMessage());

        verify(userRepository, times(1))
                .existsByEmail("test@example.com");

        verify(passwordEncoder, never())
                .encode(anyString());

        verify(userRepository, never())
                .save(any(User.class));
    }

    // Test 3. Service should assign the default role "USER" to a new user upon
    // registration. This test checks that the role is set correctly when a new user
    // is created.
    // It verifies that the userRepository's save method is called and that the role
    // is set to "USER".

    @Test
    void registerUserForcesRoleToUser() {

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setPassword("Password123");
        user.setRole("ADMIN");

        when(userRepository.existsByEmail("test@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("Password123"))
                .thenReturn("encodedPassword");

        when(userRepository.save(user))
                .thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals("USER", result.getRole());

        verify(userRepository, times(1))
                .save(user);
    }
}
