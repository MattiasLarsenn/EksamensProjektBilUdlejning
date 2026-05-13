package org.example.biludlejning.controllers;

import org.example.biludlejning.models.Customer;
import org.example.biludlejning.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController
{
    private final CustomerService customerService;
    private final SidebarModelHelper sidebarModelHelper;

    public CustomerController(CustomerService customerService, SidebarModelHelper sidebarModelHelper)
    {
        this.customerService = customerService;
        this.sidebarModelHelper = sidebarModelHelper;
    }

    @GetMapping("/customer-registration")
    public String showCustomerRegistration(Model model)
    {
        sidebarModelHelper.addSidebarState(model, "customers", "CUSTOMER");
        return "customer-registration";
    }

    @PostMapping("/customer-registration")
    public String submitCustomerRegistration(@RequestParam String name,
                                             @RequestParam String email,
                                             @RequestParam String phone)
    {
        Customer customer = new Customer(0, name, email, phone);
        customerService.createCustomer(customer);
        System.out.println("Customer registered successfully");

        return "redirect:/customer-list";
    }
    @GetMapping("/customer-list")
    public String showCustomerList(Model model)
    {
        sidebarModelHelper.addSidebarState(model, "customers", "CUSTOMER");
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer-list";
    }
    @PostMapping("/customer-edit")
    public String submitCustomerEdit(@RequestParam int customerId,
                                     @RequestParam String name,
                                     @RequestParam String email,
                                     @RequestParam String phone)
    {
        Customer customer = new Customer(customerId, name, email, phone);
        customerService.updateCustomer(customer);
        System.out.println("Customer updated successfully");

        return "redirect:/customer-list";
    }

}