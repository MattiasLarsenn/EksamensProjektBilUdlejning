package org.example.biludlejning.services;

import org.example.biludlejning.exceptions.InvalidEmailException;
import org.example.biludlejning.exceptions.InvalidNameException;
import org.example.biludlejning.exceptions.InvalidPhoneNumberException;
import org.example.biludlejning.models.Customer;
import org.example.biludlejning.repositories.repositoryInterfaces.ICustomerRepository;
import org.example.biludlejning.validation.EmailValidation;
import org.example.biludlejning.validation.NameValidation;
import org.example.biludlejning.validation.PhoneNumberValidation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService
{
    private final ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer)
    {
        if (customer == null)
        {
            throw new IllegalArgumentException("Fejl ved oprettelse af kunde");
        }

        if (!NameValidation.isNameValid(customer.getName()))
        {
            throw new InvalidNameException("Ugyldigt navn");
        }

        if (!EmailValidation.isEmailValid(customer.getEmail()))
        {
            throw new InvalidEmailException("Ugyldig email");
        }

        if (!PhoneNumberValidation.isPhoneNumberValid(customer.getPhone()))
        {
            throw new InvalidPhoneNumberException("Ugyldigt telefonnummer");
        }
        customerRepository.createCustomer(customer);
    }

    public void updateCustomer(Customer customer)
    {
        if (customer == null)
        {
            throw new IllegalArgumentException("Fejl ved opdatering af kundeoplysninger");
        }

        if (!NameValidation.isNameValid(customer.getName()))
        {
            throw new InvalidNameException("Ugyldigt navn");
        }

        if (!EmailValidation.isEmailValid(customer.getEmail()))
        {
            throw new InvalidEmailException("Ugyldig email");
        }

        if (!PhoneNumberValidation.isPhoneNumberValid(customer.getPhone()))
        {
            throw new InvalidPhoneNumberException("Ugyldigt telefonnummer");
        }
        customerRepository.updateCustomer(customer);
    }
    public Customer getCustomerById(int customerId)
    {
        return customerRepository.getCustomerById(customerId);
    }

    public List<Customer> getAllCustomers()
    {
        return customerRepository.getAllCustomers();
    }
}
