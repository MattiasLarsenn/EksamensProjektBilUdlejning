package org.example.biludlejning.repositories.repositoryInterfaces;

import java.math.BigDecimal;

public interface IBusinessRepository
{
    int getActiveRentalCount();

    int getAvailableCarCount();

    int getTotalCarCount();

    BigDecimal getTotalActiveRentalPrice();

    BigDecimal getTotalDamageCost();

}
