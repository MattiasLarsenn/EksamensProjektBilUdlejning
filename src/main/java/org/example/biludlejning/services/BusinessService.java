package org.example.biludlejning.services;


import org.example.biludlejning.repositories.BusinessRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BusinessService
{
    private final BusinessRepository businessRepository;

    public BusinessService(BusinessRepository businessRepository)
    {
        this.businessRepository = businessRepository;
    }

    public int getActiveRentalCount()
    {
        return businessRepository.getActiveRentalCount();
    }

    public int getAvailableCarCount()
    {
        return businessRepository.getAvailableCarCount();
    }

    public int getTotalCarCount()
    {
        return businessRepository.getTotalCarCount();
    }

    public BigDecimal getTotalActiveRentalPrice()
    {
        return businessRepository.getTotalActiveRentalPrice();
    }

    public BigDecimal getTotalDamageCost()
    {
        return businessRepository.getTotalDamageCost();
    }

}
