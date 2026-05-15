package org.example.biludlejning.services;

import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.repositories.repositoryInterfaces.IBusinessRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.IRentalAgreementRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BusinessService
{
    private final IBusinessRepository businessRepository;
    private final IRentalAgreementRepository rentalAgreementRepository;

    public BusinessService(IBusinessRepository businessRepository, IRentalAgreementRepository rentalAgreementRepository)
    {
        this.businessRepository = businessRepository;
        this.rentalAgreementRepository = rentalAgreementRepository;
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

    public List<RentalAgreement> getAllActiveRentalAgreements()
    {
        return rentalAgreementRepository.getAllActiveRentalAgreements();
    }

    public String getBrandAndModelByRentalId(int rentalId)
    {
        String brandAndModel = businessRepository.getBrandAndModelByRentalId(rentalId);

        if (brandAndModel == null)
        {
            throw new NoSuchElementException("Kunne ikke finde bilinformation for lejeaftale med id: " + rentalId);
        }

        return brandAndModel;
    }

}
