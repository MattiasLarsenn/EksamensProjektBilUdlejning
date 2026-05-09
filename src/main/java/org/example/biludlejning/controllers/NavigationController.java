package org.example.biludlejning.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController
{
    @GetMapping("/")
    public String showLoginPage()
    {
        return "login";
    }

    @GetMapping("/home")
    public String showHome()
    {
        return "redirect:/business-development";
    }
}
