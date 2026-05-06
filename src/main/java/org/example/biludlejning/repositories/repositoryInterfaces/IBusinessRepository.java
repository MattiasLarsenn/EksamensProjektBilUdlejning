package org.example.biludlejning.repositories.repositoryInterfaces;

import java.math.BigDecimal;

public interface IBusinessRepository
{
    int getActiveRentalCount();

    int getAvailableCarCount();

    int getTotalCarAmount();

    BigDecimal getTotalActiveRentalPrice();

    BigDecimal getTotalDamageCost();

}
