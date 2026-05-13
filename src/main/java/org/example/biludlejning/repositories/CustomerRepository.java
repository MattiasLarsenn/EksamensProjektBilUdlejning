package org.example.biludlejning.repositories;

import org.example.biludlejning.models.Customer;
import org.example.biludlejning.repositories.repositoryInterfaces.ICustomerRepository;
import org.example.biludlejning.utility.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void updateCustomer(Customer customer)
    {
        String sql = "UPDATE customer SET name = ?, email = ?, phone = ? WHERE customer_id = ?";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setInt(4, customer.getCustomerId());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error while updating customer: " + e.getMessage());
        }
    }

    public void deleteCustomer(int customerId)
    {
        String sql = "DELETE FROM customer WHERE customer_id = ?";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, customerId);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error while deleting customer: " + e.getMessage());
        }
    }

    public Customer getCustomerById(int customerId)
    {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving customer: " + e.getMessage());
        }
        return null;
    }

        public List<Customer> getAllCustomers()
    {
        String sql = "SELECT * FROM customer";
        List<Customer> customers = new ArrayList<>();

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                customers.add(new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving list of customer: " + e.getMessage());
        }
        return customers;
    }

    public boolean customerExists(int customerId)
    {
        String sql = "SELECT COUNT(*) FROM customer WHERE customer_id = ?";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return rs.getInt(1) > 0;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while checking if customer exists: " + e.getMessage());
        }
        return false;
    }

    public String getCustomerNameByRentalId(int rentalId)
    {
        String sql = "SELECT customer.name FROM rentalAgreement JOIN customer ON rentalAgreement.customer_id = customer.customer_id WHERE rentalAgreement.rental_id = ?";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, rentalId);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return rs.getString("name");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error retrieving customer name by rental id");
        }
        return null;
    }

}
