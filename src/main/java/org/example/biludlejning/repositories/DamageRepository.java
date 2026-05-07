package org.example.biludlejning.repositories;

import org.example.biludlejning.models.Damage;
import org.example.biludlejning.repositories.repositoryInterfaces.IDamageRepository;
import org.example.biludlejning.utility.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DamageRepository implements IDamageRepository
{

    private final ConnectionManager conn;

    public DamageRepository(ConnectionManager connection)
    {
        this.conn = connection;
    }

    public void createDamage(Damage damage)
    {
        String sql = "INSERT INTO damage (rental_id, description, price) VALUES (?, ?, ?)";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, damage.getRentalId());
            ps.setString(2, damage.getDescription());
            ps.setBigDecimal(3, damage.getPrice());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error while inserting damage into database: " + e.getMessage());
        }
    }

    public Damage getDamageById(int damageId)
    {
        String sql = "SELECT * FROM damage WHERE damage_id = ?";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, damageId);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return new Damage(
                        rs.getInt("damage_id"),
                        rs.getInt("rental_id"),
                        rs.getString("description"),
                        rs.getBigDecimal("price"),
                        rs.getDate("created_at").toLocalDate()
                );
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving damage by id: " + e.getMessage());
        }
        return null;
    }

    public List<Damage> getAllDamages()
    {
        String sql = "SELECT * FROM damage";
        List<Damage> damages = new ArrayList<>();

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                damages.add(new Damage(
                        rs.getInt("damage_id"),
                        rs.getInt("rental_id"),
                        rs.getString("description"),
                        rs.getBigDecimal("price"),
                        rs.getDate("created_at").toLocalDate()
                ));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving damages: " + e.getMessage());
        }
        return damages;
    }

    public List<Damage> getAllDamagesByRentalId(int rentalId)
    {
        String sql = "SELECT * FROM damage WHERE rental_id = ?";
        List<Damage> damages = new ArrayList<>();

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, rentalId);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                damages.add(new Damage(
                        rs.getInt("damage_id"),
                        rs.getInt("rental_id"),
                        rs.getString("description"),
                        rs.getBigDecimal("price"),
                        rs.getDate("created_at").toLocalDate()
                ));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while retrieving damages by rental id: " + e.getMessage());
        }
        return damages;
    }

    public BigDecimal getTotalDamagePriceByRentalId(int rentalId)
    {
        String sql = "SELECT SUM(price) FROM damage WHERE rental_id = ?";

        try (Connection database = conn.getConnection();
             PreparedStatement ps = database.prepareStatement(sql))
        {
            ps.setInt(1, rentalId);
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
            System.out.println("Error while retrieving damage price by rental id: " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }
}
