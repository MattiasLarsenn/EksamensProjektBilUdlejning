package org.example.biludlejning.controllers;

import org.example.biludlejning.models.Damage;
import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.services.DamageService;
import org.example.biludlejning.services.RentalAgreementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class DamageController
{
    private final DamageService damageService;
    private final RentalAgreementService rentalAgreementService;
    private final SidebarModelHelper sidebarModelHelper;

    public DamageController(DamageService damageService,
                            RentalAgreementService rentalAgreementService,
                            SidebarModelHelper sidebarModelHelper)
    {
        this.damageService = damageService;
        this.rentalAgreementService = rentalAgreementService;
        this.sidebarModelHelper = sidebarModelHelper;
    }

    @GetMapping("/damage-registration")
    public String showDamageRegistration(Model model)
    {
        sidebarModelHelper.addSidebarState(model, "repairs", "DAMAGE");
        return "damage-registration";
    }

    @PostMapping("/damage-registration")
    public String submitDamageRegistration(@RequestParam int rentalId,
                                           @RequestParam String description,
                                           @RequestParam BigDecimal price,
                                           @RequestParam(required = false) LocalDate createdAt)
    {
        try
        {
            RentalAgreement rentalAgreement = rentalAgreementService.getRentalAgreementByRentalId(rentalId);
            if (rentalAgreement == null)
            {
                System.out.println("Error: Rental agreement with ID " + rentalId + " does not exist");
                return "redirect:/damage-registration";
            }

            Damage damage = new Damage(rentalId, description, price);
            if (createdAt != null)
            {
                damage.setCreatedAt(createdAt);
            }
            else
            {
                damage.setCreatedAt(LocalDate.now());
            }
            damageService.createDamage(damage);
            System.out.println("Damage registered successfully");
        }
        catch (Exception e)
        {
            System.out.println("Error registering damage: " + e.getMessage());
        }

        return "redirect:/damage-list";
    }

    @GetMapping("/damage-list")
    public String showDamageList(Model model)
    {
        sidebarModelHelper.addSidebarState(model, "repairs", "DAMAGE");
        model.addAttribute("damages", damageService.getAllDamages());
        return "damage-list";
    }

    @GetMapping("/damage-edit")
    public String showDamageEdit(@RequestParam int damageId, Model model)
    {
        Damage damage = damageService.getDamageById(damageId);
        if (damage != null)
        {
            model.addAttribute("damage", damage);
            sidebarModelHelper.addSidebarState(model, "repairs", "DAMAGE");
            return "damage-edit";
        }
        return "redirect:/damage-list";
    }

    @PostMapping("/damage-edit")
    public String submitDamageEdit(@RequestParam(required = false) Integer damageId,
                                   @RequestParam(required = false) Integer rentalId,
                                   @RequestParam(required = false) String description,
                                   @RequestParam(required = false) BigDecimal price)
    {
        if (damageId != null && damageId > 0 && rentalId != null && rentalId > 0 && description != null && price != null)
        {
            Damage damage = damageService.getDamageById(damageId);
            if (damage != null)
            {
                damage.setRentalId(rentalId);
                damage.setDescription(description);
                damage.setPrice(price);
                damageService.updateDamage(damage);
            }
        }
        return "redirect:/damage-list";
    }

    @GetMapping("/damage-delete")
    public String deleteDamage(@RequestParam int damageId)
    {
        damageService.deleteDamage(damageId);
        return "redirect:/damage-list";
    }
}
