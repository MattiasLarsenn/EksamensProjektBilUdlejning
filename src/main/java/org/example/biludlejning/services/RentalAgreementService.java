package org.example.biludlejning.services;

import java.util.List;

import org.example.biludlejning.exceptions.InvalidPriceException;
import org.example.biludlejning.exceptions.InvalidRentalDateException;
import org.example.biludlejning.exceptions.InvalidRentalStatusException;
import org.example.biludlejning.exceptions.RentalAgreementNotFoundException;
import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.repositories.RentalAgreementRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.IRentalAgreementRepository;
import org.example.biludlejning.validation.PriceValidation;
import org.example.biludlejning.validation.RentalStatusValidation;
import org.springframework.stereotype.Service;

@Service
public class RentalAgreementService
{
    private final IRentalAgreementRepository rentalAgreementRepository;

    public RentalAgreementService(IRentalAgreementRepository rentalAgreementRepository)
    {
        this.rentalAgreementRepository = rentalAgreementRepository;
    }

    public RentalAgreement getRentalAgreementByRentalId(int rentalId)
    {
        if (rentalId <= 0)
        {
            throw new IllegalArgumentException("Rental id must be greater than 0");
        }

        RentalAgreement rentalAgreement = rentalAgreementRepository.getRentalAgreementByRentalId(rentalId);

        if (rentalAgreement == null)
        {
            throw new RentalAgreementNotFoundException("No rental agreement found with rental id: " + rentalId);
        }

        return rentalAgreement;
    }

    public void createRentalAgreement(RentalAgreement rentalAgreement)
    {
        if (rentalAgreement == null)
        {
            throw new IllegalArgumentException("Rental agreement cannot be null");
        }

        if (rentalAgreement.getCustomerId() <= 0)
        {
            throw new IllegalArgumentException("Customer id must be greater than 0");
        }

        if (rentalAgreement.getCarId() <= 0)
        {
            throw new IllegalArgumentException("Car id must be greater than 0");
        }

        if (rentalAgreement.getStartDate() == null
            || rentalAgreement.getEndDate() == null
            || !rentalAgreement.getEndDate().isAfter(rentalAgreement.getStartDate()))
        {
            throw new InvalidRentalDateException("Invalid dates for rental agreement");
        }

        if (!RentalStatusValidation.isStatusValid(rentalAgreement.getStatus()))
        {
            throw new InvalidRentalStatusException("Invalid status for rental agreement");
        }

        if (!PriceValidation.isPriceValid(rentalAgreement.getPrice()))
        {
            throw new InvalidPriceException("Invalid price for rental agreement");
        }

        rentalAgreementRepository.createRentalAgreement(rentalAgreement);
    }

    public List<RentalAgreement> getAllRentalAgreements()
    {
        return rentalAgreementRepository.getAllRentalAgreements();
    }

    public boolean isRentalAgreementActive(int rentalId)
    {
        return rentalAgreementRepository.isRentalAgreementActive(rentalId);
    }

}
