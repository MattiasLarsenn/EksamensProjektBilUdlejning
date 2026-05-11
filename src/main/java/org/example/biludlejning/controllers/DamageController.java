package org.example.biludlejning.controllers;

import java.math.BigDecimal;

import org.example.biludlejning.models.Damage;
import org.example.biludlejning.services.DamageService;
import org.example.biludlejning.services.RentalAgreementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DamageController
{
    private final DamageService damageService;
    private final SidebarModelHelper sidebarModelHelper;

    public DamageController(DamageService damageService,
                            RentalAgreementService rentalAgreementService,
                            SidebarModelHelper sidebarModelHelper)
    {
        this.damageService = damageService;
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
                                           @RequestParam BigDecimal price)
    {
        Damage damage = new Damage(rentalId, description, price);

        damageService.createDamage(damage);
        System.out.println("Damage registered successfully");

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

        model.addAttribute("damage", damage);
        sidebarModelHelper.addSidebarState(model, "repairs", "DAMAGE");

        return "damage-edit";
    }

    @PostMapping("/damage-edit")
    public String submitDamageEdit(@RequestParam(required = false) Integer damageId,
                                   @RequestParam(required = false) Integer rentalId,
                                   @RequestParam(required = false) String description,
                                   @RequestParam(required = false) BigDecimal price)
    {
        Damage damage = damageService.getDamageById(damageId);

        damage.setRentalId(rentalId);
        damage.setDescription(description);
        damage.setPrice(price);
        damageService.updateDamage(damage);

        return "redirect:/damage-list";
    }

    @GetMapping("/damage-delete")
    public String deleteDamage(@RequestParam int damageId)
    {
        damageService.deleteDamage(damageId);
        return "redirect:/damage-list";
    }
}
