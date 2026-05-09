package org.example.biludlejning.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.example.biludlejning.models.Damage;
import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.repositories.BusinessRepository;
import org.example.biludlejning.repositories.DamageRepository;
import org.example.biludlejning.repositories.RentalAgreementRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController
{
    private final BusinessRepository businessRepository;
    private final DamageRepository damageRepository;
    private final RentalAgreementRepository rentalAgreementRepository;

    public PageController(BusinessRepository businessRepository,
                          DamageRepository damageRepository,
                          RentalAgreementRepository rentalAgreementRepository)
    {
        this.businessRepository = businessRepository;
        this.damageRepository = damageRepository;
        this.rentalAgreementRepository = rentalAgreementRepository;
    }

    @GetMapping("/")
    public String showLoginPage()
    {
        return "login";
    }

    @GetMapping("/home")
    public String showHome(Model model)
    {
        return showBusinessDevelopment(model);
    }

    @GetMapping("/damage-registration")
    public String showDamageRegistration(Model model)
    {
        addSidebarState(model, "repairs", "DAMAGE");
        return "damage-registration";
    }

    @GetMapping("/damage-list")
    public String showDamageList(Model model)
    {
        addSidebarState(model, "repairs", "DAMAGE");
        model.addAttribute("damages", damageRepository.getAllDamages());
        return "damage-list";
    }

    @PostMapping("/damage-registration")
    public String submitDamageRegistration(@RequestParam int rentalId,
                                           @RequestParam String description,
                                           @RequestParam BigDecimal price,
                                           @RequestParam(required = false) LocalDate createdAt)
    {
        try
        {
            // Validate that rental agreement exists
            RentalAgreement rentalAgreement = rentalAgreementRepository.getRentalAgreementByRentalId(rentalId);
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
            damageRepository.createDamage(damage);
            System.out.println("Damage registered successfully");
        }
        catch (Exception e)
        {
            System.out.println("Error registering damage: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/damage-list";
    }

    @GetMapping("/damage-edit")
    public String showDamageEdit(@RequestParam int damageId, Model model)
    {
        Damage damage = damageRepository.getDamageById(damageId);
        if (damage != null)
        {
            model.addAttribute("damage", damage);
            addSidebarState(model, "repairs", "DAMAGE");
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
            Damage damage = damageRepository.getDamageById(damageId);
            if (damage != null)
            {
                damage.setRentalId(rentalId);
                damage.setDescription(description);
                damage.setPrice(price);
                damageRepository.updateDamage(damage);
            }
        }
        return "redirect:/damage-list";
    }

    @GetMapping("/damage-delete")
    public String deleteDamage(@RequestParam int damageId)
    {
        damageRepository.deleteDamage(damageId);
        return "redirect:/damage-list";
    }

    @GetMapping("/rental-agreement")
    public String showRentalAgreementPage(Model model)
    {
        model.addAttribute("rentalAgreements", rentalAgreementRepository.getAllRentalAgreements());
        addSidebarState(model, "registration", "REGISTRATION");
        return "rental-agreement";
    }

    @GetMapping("/business-development")
    public String showBusinessDevelopment(Model model)
    {
        int activeRentalCount = businessRepository.getActiveRentalCount();
        int availableCarCount = businessRepository.getAvailableCarCount();
        int totalCarCount = businessRepository.getTotalCarCount();
        BigDecimal monthlyRevenue = businessRepository.getTotalActiveRentalPrice();
        BigDecimal totalDamageCost = businessRepository.getTotalDamageCost();
        int openDamageCount = damageRepository.getAllDamages().size();

        int leasedCarCount = activeRentalCount;
        int inRepairCount = Math.min(openDamageCount, Math.max(totalCarCount - availableCarCount - leasedCarCount, 0));
        int readyForSaleCount = Math.max(totalCarCount - availableCarCount - leasedCarCount - inRepairCount, 0);
        BigDecimal totalFleetValue = monthlyRevenue.multiply(BigDecimal.valueOf(12));

        List<RentalAgreement> activeAgreements = rentalAgreementRepository.getAllRentalAgreements()
                .stream()
                .filter(agreement -> "active".equalsIgnoreCase(agreement.getStatus()))
                .toList();

        model.addAttribute("activeRentalCount", activeRentalCount);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("totalFleetValue", totalFleetValue);
        model.addAttribute("openDamageCount", openDamageCount);
        model.addAttribute("totalDamageCost", totalDamageCost);
        model.addAttribute("availableCarCount", availableCarCount);
        model.addAttribute("inRepairCount", inRepairCount);
        model.addAttribute("leasedCarCount", leasedCarCount);
        model.addAttribute("readyForSaleCount", readyForSaleCount);
        model.addAttribute("activeAgreements", activeAgreements);
        addSidebarState(model, "business", "BUSINESS");
        return "business-development";
    }

    @PostMapping("/rental-agreement")
    public String submitRentalAgreement(@RequestParam(required = false) Integer rentalId,
                                        @RequestParam(required = false) Integer carId,
                                        @RequestParam(required = false) Integer customerId,
                                        @RequestParam(required = false) String startDate,
                                        @RequestParam(required = false) String endDate,
                                        @RequestParam(required = false) BigDecimal price,
                                        @RequestParam(required = false) String status)
    {
        if (carId != null && customerId != null && startDate != null && endDate != null && price != null && status != null)
        {
            try
            {
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);
                RentalAgreement rentalAgreement = new RentalAgreement(carId, customerId, start, end, price, status);
                if (rentalId != null && rentalId > 0)
                {
                    rentalAgreement.setRentalId(rentalId);
                }
                rentalAgreementRepository.createRentalAgreement(rentalAgreement);
                System.out.println("Rental agreement created successfully");
            }
            catch (Exception e)
            {
                System.out.println("Error creating rental agreement: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return "redirect:/rental-agreement";
    }

    private void addSidebarState(Model model, String activeMenu, String activeRole)
    {
        model.addAttribute("activeMenu", activeMenu);
        model.addAttribute("activeRole", activeRole);
    }
}