package org.example.biludlejning.models;

public class Car
{
    private int carId;
    private String vinNumber;
    private int doorAmount;
    private String brand;
    private String model;
    private String status;

    public Car(int carId, String vinNumber, int doorAmount, String brand, String model, String status)
    {
        this.carId = carId;
        this.vinNumber = vinNumber;
        this.doorAmount = doorAmount;
        this.brand = brand;
        this.model = model;
        this.status = status;
    }

    public int getCarId()
    {
        return carId;
    }

    public String getVinNumber()
    {
        return vinNumber;
    }

    public int getDoorAmount()
    {
        return doorAmount;
    }

    public String getBrand()
    {
        return brand;
    }

    public String getModel()
    {
        return model;
    }

    public String getStatus()
    {
        return status;
    }

    public void setCarId(int carId)
    {
        this.carId = carId;
    }

    public void setVinNumber(String vinNumber)
    {
        this.vinNumber = vinNumber;
    }

    public void setDoorAmount(int doorAmount)
    {
        this.doorAmount = doorAmount;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
