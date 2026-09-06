package com.indiana.foodlocator.config;

import com.indiana.foodlocator.entity.FoodLocation;
import com.indiana.foodlocator.repository.FoodLocationRepository;
import com.indiana.foodlocator.entity.User;
import com.indiana.foodlocator.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${app.admin.password}")
    private String adminPassword;

    private final FoodLocationRepository foodLocationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(FoodLocationRepository foodLocationRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.foodLocationRepository = foodLocationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (foodLocationRepository.count() == 0) {

            FoodLocation location1 = new FoodLocation();
            location1.setName("Downtown Community Food Pantry");
            location1.setCategory("Food Pantry");
            location1.setAddress("123 Main Street");
            location1.setCity("Indianapolis");
            location1.setState("IN");
            location1.setZipCode("46204");
            location1.setPhone("317-555-0101");
            location1.setWebsite("https://example.org");
            location1.setHours("Monday-Friday 9:00 AM-5:00 PM");
            location1.setRequirements("Photo ID recommended.");
            location1.setLatitude(new BigDecimal("39.7684000"));
            location1.setLongitude(new BigDecimal("-86.1581000"));
            location1.setActive(true);

            FoodLocation location2 = new FoodLocation();
            location2.setName("Eastside Community Food Center");
            location2.setCategory("Food Pantry");
            location2.setAddress("456 Community Avenue");
            location2.setCity("Indianapolis");
            location2.setState("IN");
            location2.setZipCode("46218");
            location2.setPhone("317-555-0102");
            location2.setHours("Tuesday-Saturday 10:00 AM-4:00 PM");
            location2.setRequirements("Open to Indiana residents.");
            location2.setLatitude(new BigDecimal("39.8100000"));
            location2.setLongitude(new BigDecimal("-86.1000000"));
            location2.setActive(true);

            foodLocationRepository.save(location1);
            foodLocationRepository.save(location2);

            System.out.println("Sample food locations added.");
        }


        if (!userRepository.existsByEmail("admin@indianafoodlocator.com")) {
            User admin = new User();

            admin.setFirstName("Indiana");
            admin.setLastName("Admin");
            admin.setEmail("admin@indianafoodlocator.com");

            admin.setPassword(passwordEncoder.encode(adminPassword));

            admin.setRole("ADMIN");

            userRepository.save(admin);

            System.out.println("Admin user created.");
        }
    }
}
