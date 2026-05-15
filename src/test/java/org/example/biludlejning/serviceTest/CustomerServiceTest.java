package org.example.biludlejning.serviceTest;


import org.example.biludlejning.exceptions.CustomerNotFoundException;
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
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest
{
    private ICustomerRepository mockCustomerRepository;
    private CustomerService service;

    @BeforeEach
    void setup()
    {
        mockCustomerRepository = mock(ICustomerRepository.class);
        service = new CustomerService(mockCustomerRepository);
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

        verify(mockCustomerRepository)
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

        verify(mockCustomerRepository)
                .updateCustomer(customer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNullInUpdateCustomer()
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
        when(mockCustomerRepository.getAllCustomers())
                .thenReturn(new ArrayList<>());

        List<Customer> result = service.getAllCustomers();

        assertNotNull(result);
    }

    @Test
    void shouldReturnCustomerById()
    {
        Customer customer = new Customer(
                1,
                "Jens Jensen",
                "Jens@gmail.com",
                "22345678"
        );

        when(mockCustomerRepository.getCustomerById(1)).thenReturn(customer);

        Customer result = service.getCustomerById(1);

        assertNotNull(result);
        assertEquals(1, result.getCustomerId());
    }

    @Test
    void shouldThrowExceptionWhenCustomerIdDoesntExist()
    {
        Customer customer = new Customer(
                1,
                "Jens Jensen",
                "Jens@gmail.com",
                "22345678"
        );

        when(mockCustomerRepository.getCustomerById(1)).thenReturn(customer);

        assertThrows(CustomerNotFoundException.class, () ->
                service.getCustomerById(2));
    }

    @Test
    void shouldReturnCustomerNameByRentalId()
    {
        when(mockCustomerRepository.getCustomerNameByRentalId(1)).thenReturn("Hans Jensen");

        assertEquals("Hans Jensen", service.getCustomerNameByRentalId(1));
    }

    @Test
    void shouldThrowExceptionWhenCustomerNameByRentalIdWasntFound()
    {
        when(mockCustomerRepository.getCustomerNameByRentalId(1)).thenReturn("Hans Jensen");

        assertThrows(NoSuchElementException.class, () ->
                service.getCustomerNameByRentalId(2));
    }
}
