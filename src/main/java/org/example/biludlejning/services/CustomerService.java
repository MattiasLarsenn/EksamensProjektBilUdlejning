package org.example.biludlejning.services;

import org.example.biludlejning.exceptions.InvalidEmailException;
import org.example.biludlejning.exceptions.InvalidNameException;
import org.example.biludlejning.exceptions.InvalidPhoneNumberException;
import org.example.biludlejning.models.Customer;
import org.example.biludlejning.repositories.CustomerRepository;
import org.example.biludlejning.validation.EmailValidation;
import org.example.biludlejning.validation.NameValidation;
import org.example.biludlejning.validation.PhoneNumberValidation;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (customer == null)
        {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (!NameValidation.isNameValid(customer.getName()))
        {
            throw new InvalidNameException("Invalid Name");
        }

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

    public void updateCustomer(Customer customer)
    {
        if (customer == null)
        {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (!NameValidation.isNameValid(customer.getName()))
        {
            throw new InvalidNameException("Invalid Name");
        }

        if (!EmailValidation.isEmailValid(customer.getEmail()))
        {
            throw new InvalidEmailException("Invalid Email");
        }

        if (!PhoneNumberValidation.isPhoneNumberValid(customer.getPhone()))
        {
            throw new InvalidPhoneNumberException("Invalid Phone Number");
        }
        customerRepository.updateCustomer(customer);
    }

    public void deleteCustomer(int customerId)
    {
        if (customerId <= 0)
        {
            throw new IllegalArgumentException("Customer id must be greater than 0");
        }

        customerRepository.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers()
    {
        return customerRepository.getAllCustomers();
    }
}
