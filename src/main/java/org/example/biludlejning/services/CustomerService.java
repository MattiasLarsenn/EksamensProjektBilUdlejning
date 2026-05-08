package org.example.biludlejning.services;

import org.example.biludlejning.exceptions.InvalidEmailException;
import org.example.biludlejning.exceptions.InvalidPhoneNumberException;
import org.example.biludlejning.models.Customer;
import org.example.biludlejning.repositories.CustomerRepository;
import org.example.biludlejning.validation.EmailValidation;
import org.example.biludlejning.validation.PhoneNumberValidation;
import org.springframework.stereotype.Service;

@Service
public class CustomerService
{
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer)
    {
        if (!EmailValidation.isEmailValid(customer.getEmail()))
        {
            throw new InvalidEmailException("Invalid Email");
        }

        if (!PhoneNumberValidation.isPhoneNumberValid(customer.getPhone()))
        {
            throw new InvalidPhoneNumberException("Invalid Phone Number");
        }
        customerRepository.createCustomer(customer);
    }
}
