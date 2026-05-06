package org.example.biludlejning.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalAgreement
{
    private int rentalId;
    private int carId;
    private int customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private String status;


    //With rentalId, intented for retrieving a rentalAgreement object from the database
    public RentalAgreement(int rentalId, int carId, int customerId, LocalDate startDate,
                           LocalDate endDate, BigDecimal price, String status)
    {
        this.rentalId = rentalId;
        this.carId = carId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
    }

    //Without rentalId, intented for creating a rentalAgreement object
    //rentalId will be auto incremented in the database
    public RentalAgreement(int carId, int customerId, LocalDate startDate, LocalDate endDate,
                           BigDecimal price, String status)
    {
        this.carId = carId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
    }

    public int getRentalId()
    {
        return rentalId;
    }

    public int getCarId()
    {
        return carId;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public String getStatus()
    {
        return status;
    }

    public void setRentalId(int rentalId)
    {
        this.rentalId = rentalId;
    }

    public void setCarId(int carId)
    {
        this.carId = carId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
