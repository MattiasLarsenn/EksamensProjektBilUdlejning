package org.example.biludlejning.repositories;

import org.example.biludlejning.repositories.repositoryInterfaces.IBusinessRepository;
import org.example.biludlejning.utility.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusinessRepository implements IBusinessRepository
{
    private final ConnectionManager conn;

    public BusinessRepository(ConnectionManager connection)
    {
        this.conn = connection;
    }

    public int getActiveRentalCount()
    {
        String sql = "SELECT COUNT(*) FROM rentalAgreement WHERE status = ?";
        int count = 0;

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setString(1, "active");
            ResultSet rs = ps.executeQuery();

           if (rs.next())
           {
               count = rs.getInt(1);
           }
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving active rental amount: " + e.getMessage());
        }
        return count;
    }

    public int getAvailableCarCount()
    {
        String sql = "SELECT COUNT(*) FROM car WHERE status = ?";
        int count = 0;

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setString(1, "available");
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving available car amount: " + e.getMessage());
        }
        return count;
    }

    public int getTotalCarCount()
    {
        String sql = "SELECT COUNT(*) FROM car";
        int count = 0;

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving total car amount: " + e.getMessage());
        }
        return count;
    }

    public BigDecimal getTotalActiveRentalPrice()
    {
        String sql = "SELECT SUM(price) FROM rentalAgreement WHERE status = ?";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setString(1, "active");
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                BigDecimal total = rs.getBigDecimal(1);

                if (total == null)
                {
                    return BigDecimal.ZERO;
                }

                return total;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving total active rental price: " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getTotalDamageCost()
    {
        String sql = "SELECT SUM(price) FROM damage";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                BigDecimal total = rs.getBigDecimal(1);

                if (total == null)
                {
                    return BigDecimal.ZERO;
                }

                return total;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving total damage cost: " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }
}
