package org.example.biludlejning.repositories;

import org.example.biludlejning.models.Customer;
import org.example.biludlejning.repositories.repositoryInterfaces.ICustomerRepository;
import org.example.biludlejning.utility.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class CustomerRepository implements ICustomerRepository
{
    private final ConnectionManager conn;

    public CustomerRepository(ConnectionManager conn)
    {
        this.conn = conn;
    }

    public void createCustomer(Customer customer)
    {
        String sql = "INSERT INTO customer (name, email, phone) VALUES (?, ?, ?)";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error while inserting customer to the database: " + e.getMessage());
        }
    }
}
