package org.example.biludlejning.controllers;

import org.example.biludlejning.utility.ConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class TestController
{
    private final ConnectionManager connectionManager;

    public TestController(ConnectionManager connectionManager)
    {
        this.connectionManager = connectionManager;
    }

    @GetMapping("/db-test")
    public String testDB()
    {
        try (Connection conn = connectionManager.getConnection())
        {
            return "Connected successfully to the database";
        }
        catch (SQLException e)
        {
            return "Failed to connect to the database: " + e.getMessage();
        }
    }
    @Controller
    public class DamageRegistrationController {

        @GetMapping("/damage-registration")
        public String showDamageRegistration() {
            return "damage-registration";
        }

        @PostMapping("/damage-registration")
        public String submitDamageRegistration(@RequestParam String carModel,
                                               @RequestParam String licensePlate,
                                               @RequestParam String damageDescription,
                                               @RequestParam MultipartFile[] damagePhotos) {

            System.out.println("Car Model: " + carModel);
            System.out.println("License Plate: " + licensePlate);
            System.out.println("Damage Description: " + damageDescription);
            for (MultipartFile photo : damagePhotos) {
                System.out.println("Photo: " + photo.getOriginalFilename());
            }

            return "redirect:/damage-registration";
        }
    }
    }
