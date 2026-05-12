package org.example.biludlejning.repositories.repositoryInterfaces;

import java.math.BigDecimal;

public interface IBusinessRepository
{
    int getActiveRentalCount();

    int getAvailableCarCount();

    int getTotalCarCount();

    BigDecimal getTotalActiveRentalPrice();

    BigDecimal getTotalDamageCost();

    boolean carExists(int carId);

    boolean isCarRented(int carId);

    void updateCarStatus(int carId, String status);
}
