package org.example.biludlejning.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Damage
{
    private int damageId;
    private int rentalId;
    private String description;
    private BigDecimal price;
    private LocalDate createdAt;


    //With damageId, intented for retrieving a damage object from the database
    public Damage(int damageId, int rentalId, String description, BigDecimal price, LocalDate createdAt)
    {
        this.damageId = damageId;
        this.rentalId = rentalId;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
    }

    //Without damageId, intented for creating a damage object, damageId will be auto incremented in the database
    public Damage(int rentalId, String description, BigDecimal price, LocalDate createdAt)
    {
        this.rentalId = rentalId;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
    }

    public int getDamageId()
    {
        return damageId;
    }

    public int getRentalId()
    {
        return rentalId;
    }

    public String getDescription()
    {
        return description;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public LocalDate getCreatedAt()
    {
        return createdAt;
    }

    public void setDamageId(int damageId)
    {
        this.damageId = damageId;
    }

    public void setRentalId(int rentalId)
    {
        this.rentalId = rentalId;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public void setCreatedAt(LocalDate createdAt)
    {
        this.createdAt = createdAt;
    }
}
