package org.example.biludlejning.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.services.BusinessService;
import org.example.biludlejning.services.DamageService;
import org.example.biludlejning.services.RentalAgreementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessController
{
    private final BusinessService businessService;
    private final DamageService damageService;
    private final RentalAgreementService rentalAgreementService;
    private final SidebarModelHelper sidebarModelHelper;

    public BusinessController(BusinessService businessService,
                              DamageService damageService,
                              RentalAgreementService rentalAgreementService,
                              SidebarModelHelper sidebarModelHelper)
    {
        this.businessService = businessService;
        this.damageService = damageService;
        this.rentalAgreementService = rentalAgreementService;
        this.sidebarModelHelper = sidebarModelHelper;
    }

    @GetMapping("/business-development")
    public String showBusinessDevelopment(Model model)
    {
        int activeRentalCount = businessService.getActiveRentalCount();
        int availableCarCount = businessService.getAvailableCarCount();
        int totalCarCount = businessService.getTotalCarCount();
        BigDecimal monthlyRevenue = businessService.getTotalActiveRentalPrice();
        BigDecimal totalDamageCost = businessService.getTotalDamageCost();
        int openDamageCount = damageService.getAllDamages().size();

        int leasedCarCount = activeRentalCount;
        int inRepairCount = Math.min(openDamageCount, Math.max(totalCarCount - availableCarCount - leasedCarCount, 0));
        int readyForSaleCount = Math.max(totalCarCount - availableCarCount - leasedCarCount - inRepairCount, 0);
        BigDecimal totalFleetValue = monthlyRevenue.multiply(BigDecimal.valueOf(12));

        List<RentalAgreement> activeAgreements = rentalAgreementService.getAllRentalAgreements()
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
        sidebarModelHelper.addSidebarState(model, "business", "BUSINESS");
        return "business-development";
    }
}
