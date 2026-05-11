package org.example.biludlejning.repositories.repositoryInterfaces;

import org.example.biludlejning.models.Customer;

import java.util.List;

public interface ICustomerRepository
{
    void createCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(int customerId);

    List<Customer> getAllCustomers();

    boolean customerExists(int customerId);
}
