package org.example.biludlejning.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController
{
    @GetMapping("/")
    public String showLoginPage()
    {
        return "login";
    }

    @GetMapping("/damage-registration")
    public String showDamageRegistration()
    {
        return "damage-registration";
    }

    @PostMapping("/damage-registration")
    public String submitDamageRegistration(@RequestParam int rentalId,
                                           @RequestParam String description,
                                           @RequestParam BigDecimal price,
                                           @RequestParam LocalDate createdAt)
    {
        System.out.println("Rental ID: " + rentalId);
        System.out.println("Description: " + description);
        System.out.println("Price: " + price);
        System.out.println("Created at: " + createdAt);

        return "redirect:/damage-registration";
    }
}