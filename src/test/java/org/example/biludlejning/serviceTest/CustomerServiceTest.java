package org.example.biludlejning.serviceTest;


import org.example.biludlejning.exceptions.InvalidEmailException;
import org.example.biludlejning.exceptions.InvalidNameException;
import org.example.biludlejning.exceptions.InvalidPhoneNumberException;
import org.example.biludlejning.models.Customer;
import org.example.biludlejning.repositories.repositoryInterfaces.ICustomerRepository;
import org.example.biludlejning.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CustomerServiceTest
{
    private ICustomerRepository mockRepository;
    private CustomerService service;

    @BeforeEach
    void setup()
    {
        mockRepository = mock(ICustomerRepository.class);
        service = new CustomerService(mockRepository);
    }

    @Test
    void shouldCreateCustomer()
    {
        Customer customer = new Customer(
                1,
                "Jens Jensen",
                "Jens@gmail.com",
                "22345678"
        );

        service.createCustomer(customer);

        verify(mockRepository)
                .createCustomer(customer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNUll()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.createCustomer(null));
    }

    @Test
    void shouldThrowExceptionWhenNameIsInvalid()
    {
        Customer customer = new Customer(
                1,
                "",
                "jens@gmail.com",
                "22345678"
        );

        assertThrows(InvalidNameException.class, () ->
                service.createCustomer(customer));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid()
    {
        Customer customer = new Customer(
                1,
                "Karsten Kold",
                "invalidEmail",
                "22345678"
        );

        assertThrows(InvalidEmailException.class, () ->
                service.createCustomer(customer));
    }

    @Test
    void shouldThrowExceptionWhenPhoneNumberIsInvalid()
    {
        Customer customer = new Customer(
                1,
                "Torsten Larsen",
                "valid@gmail.com",
                "12345678"
        );

        assertThrows(InvalidPhoneNumberException.class, () ->
                service.createCustomer(customer));
    }

    @Test
    void shouldUpdateCustomer()
    {
        Customer customer = new Customer(
                1,
                "Jens Jensen",
                "Jens@gmail.com",
                "22345678"
        );

        service.updateCustomer(customer);

        verify(mockRepository)
                .updateCustomer(customer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNUllInUpdateCustomer()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.updateCustomer(null));
    }

    @Test
    void shouldThrowExceptionWhenNameIsInvalidInUpdateCustomer()
    {
        Customer customer = new Customer(
                1,
                "",
                "jens@gmail.com",
                "22345678"
        );

        assertThrows(InvalidNameException.class, () ->
                service.updateCustomer(customer));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalidInUpdateCustomer()
    {
        Customer customer = new Customer(
                1,
                "Karsten Kold",
                "invalidEmail",
                "22345678"
        );

        assertThrows(InvalidEmailException.class, () ->
                service.updateCustomer(customer));
    }

    @Test
    void shouldThrowExceptionWhenPhoneNumberIsInvalidInUpdateCustomer()
    {
        Customer customer = new Customer(
                1,
                "Torsten Larsen",
                "valid@gmail.com",
                "12345678"
        );

        assertThrows(InvalidPhoneNumberException.class, () ->
                service.updateCustomer(customer));
    }

    @Test
    void shouldReturnAllCustomers()
    {
        when(mockRepository.getAllCustomers())
                .thenReturn(new ArrayList<>());

        List<Customer> result = service.getAllCustomers();

        assertNotNull(result);
    }
}
