package org.example.biludlejning.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.example.biludlejning.models.Damage;
import org.example.biludlejning.repositories.DamageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController
{
    private final DamageRepository damageRepository;

    public PageController(DamageRepository damageRepository)
    {
        this.damageRepository = damageRepository;
    }

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

    @GetMapping("/damage-list")
    public String showDamageList(Model model)
    {
        model.addAttribute("damages", damageRepository.getAllDamages());
        return "damage-list";
    }

    @PostMapping("/damage-registration")
    public String submitDamageRegistration(@RequestParam int rentalId,
                                           @RequestParam String description,
                                           @RequestParam BigDecimal price,
                                           @RequestParam LocalDate createdAt)
    {
        Damage damage = new Damage(rentalId, description, price);
        damage.setCreatedAt(createdAt);
        damageRepository.createDamage(damage);

        return "redirect:/damage-list";
    }
}