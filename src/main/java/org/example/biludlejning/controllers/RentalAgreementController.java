package org.example.biludlejning.controllers;

import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.services.RentalAgreementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class RentalAgreementController
{
    private final RentalAgreementService rentalAgreementService;
    private final SidebarModelHelper sidebarModelHelper;

    public RentalAgreementController(RentalAgreementService rentalAgreementService,
                                     SidebarModelHelper sidebarModelHelper)
    {
        this.rentalAgreementService = rentalAgreementService;
        this.sidebarModelHelper = sidebarModelHelper;
    }

    @GetMapping("/rental-agreement")
    public String showRentalAgreementPage(Model model)
    {
        model.addAttribute("rentalAgreements", rentalAgreementService.getAllRentalAgreements());
        sidebarModelHelper.addSidebarState(model, "registration", "REGISTRATION");
        return "rental-agreement";
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
                rentalAgreementService.createRentalAgreement(rentalAgreement);
                System.out.println("Rental agreement created successfully");
            }
            catch (Exception e)
            {
                System.out.println("Error creating rental agreement: " + e.getMessage());
            }
        }
        return "redirect:/rental-agreement";
    }
}
