package org.example.biludlejning.repositories;

import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.repositories.repositoryInterfaces.IRentalAgreementRepository;
import org.example.biludlejning.utility.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RentalAgreementRepository implements IRentalAgreementRepository
{

    private final ConnectionManager conn;

    public RentalAgreementRepository(ConnectionManager connection)
    {
        this.conn = connection;
    }


    public RentalAgreement getRentalAgreementByRentalId(int rentalId)
    {
        String sql = "SELECT * FROM rentalAgreement WHERE rental_id = ?";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, rentalId);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return new RentalAgreement(
                        rs.getInt("rental_id"),
                        rs.getInt("car_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getBigDecimal("price"),
                        rs.getString("status")
                );
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error retrieving rental agreement: " + e.getMessage());
        }
        return null;
    }

    public void createRentalAgreement(RentalAgreement rentalAgreement)
    {
        String sql = "INSERT INTO rentalAgreement (car_id, customer_id, start_date, end_date, price, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, rentalAgreement.getCarId());
            ps.setInt(2, rentalAgreement.getCustomerId());
            ps.setDate(3, Date.valueOf(rentalAgreement.getStartDate()));
            ps.setDate(4, Date.valueOf(rentalAgreement.getEndDate()));
            ps.setBigDecimal(5, rentalAgreement.getPrice());
            ps.setString(6, rentalAgreement.getStatus());
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rental agreement created successfully. Rows affected: " + rowsAffected);
        }
        catch(SQLException e)
        {
            System.out.println("Error creating rental agreement: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<RentalAgreement> getAllRentalAgreements()
    {
        String sql = "SELECT * FROM rentalAgreement";
        List<RentalAgreement> rentalAgreements = new ArrayList<>();

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                rentalAgreements.add(new RentalAgreement(
                        rs.getInt("rental_id"),
                        rs.getInt("car_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getBigDecimal("price"),
                        rs.getString("status")
                ));
            }
            System.out.println("Retrieved " + rentalAgreements.size() + " rental agreements from database");
        }

        catch(SQLException e)
        {
            System.out.println("Error retrieving all rental agreements: " + e.getMessage());
            e.printStackTrace();
        }
        return rentalAgreements;
    }


    public boolean isRentalAgreementActive(int rentalId)
    {
        String sql = "SELECT status FROM rentalAgreement WHERE rental_id = ?";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, rentalId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                String status = rs.getString("status");

                if (status.equalsIgnoreCase("aktiv"))
                {
                    return true;
                }
            }
        }

        catch(SQLException e)
        {
            System.out.println("Error retrieving status from rental agreement: " + e.getMessage());
        }
        return false;
    }
}
