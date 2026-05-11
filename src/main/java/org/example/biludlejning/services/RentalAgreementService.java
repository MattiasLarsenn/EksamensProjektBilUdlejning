package org.example.biludlejning.services;

import java.util.List;

import org.example.biludlejning.exceptions.InvalidPriceException;
import org.example.biludlejning.exceptions.InvalidRentalDateException;
import org.example.biludlejning.exceptions.InvalidRentalStatusException;
import org.example.biludlejning.exceptions.RentalAgreementNotFoundException;
import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.repositories.BusinessRepository;
import org.example.biludlejning.repositories.CustomerRepository;
import org.example.biludlejning.repositories.RentalAgreementRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.IRentalAgreementRepository;
import org.example.biludlejning.validation.PriceValidation;
import org.example.biludlejning.validation.RentalStatusValidation;
import org.springframework.stereotype.Service;

@Service
public class RentalAgreementService
{
    private final RentalAgreementRepository rentalAgreementRepository;
    private final BusinessRepository businessRepository;
    private final CustomerRepository customerRepository;

    public RentalAgreementService(RentalAgreementRepository rentalAgreementRepository,
                                  BusinessRepository businessRepository,
                                  CustomerRepository customerRepository)
    {
        this.rentalAgreementRepository = rentalAgreementRepository;
        this.businessRepository = businessRepository;
        this.customerRepository = customerRepository;
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

        // Check if car exists
        if (!businessRepository.carExists(rentalAgreement.getCarId()))
        {
            throw new IllegalArgumentException("Car with ID " + rentalAgreement.getCarId() + " does not exist");
        }

        // Check if customer exists
        if (!customerRepository.customerExists(rentalAgreement.getCustomerId()))
        {
            throw new IllegalArgumentException("Customer with ID " + rentalAgreement.getCustomerId() + " does not exist");
        }

        // Check if car is already rented
        if (businessRepository.isCarRented(rentalAgreement.getCarId()))
        {
            throw new IllegalArgumentException("Car with ID " + rentalAgreement.getCarId() + " is already rented");
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
