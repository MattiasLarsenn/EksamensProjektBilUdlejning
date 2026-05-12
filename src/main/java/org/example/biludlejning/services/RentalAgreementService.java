package org.example.biludlejning.services;

import java.util.List;

import org.example.biludlejning.exceptions.CarNotFoundException;
import org.example.biludlejning.exceptions.CustomerNotFoundException;
import org.example.biludlejning.exceptions.InvalidPriceException;
import org.example.biludlejning.exceptions.InvalidRentalDateException;
import org.example.biludlejning.exceptions.InvalidRentalStatusException;
import org.example.biludlejning.exceptions.RentalAgreementNotFoundException;
import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.repositories.repositoryInterfaces.IBusinessRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.ICustomerRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.IRentalAgreementRepository;
import org.example.biludlejning.validation.PriceValidation;
import org.example.biludlejning.validation.RentalDateValidation;
import org.example.biludlejning.validation.RentalStatusValidation;
import org.springframework.stereotype.Service;

@Service
public class RentalAgreementService
{
    private final IRentalAgreementRepository rentalAgreementRepository;
    private final IBusinessRepository businessRepository;
    private final ICustomerRepository customerRepository;

    public RentalAgreementService(IRentalAgreementRepository rentalAgreementRepository,
                                  IBusinessRepository businessRepository,
                                  ICustomerRepository customerRepository)
    {
        this.rentalAgreementRepository = rentalAgreementRepository;
        this.businessRepository = businessRepository;
        this.customerRepository = customerRepository;
    }

    public RentalAgreement getRentalAgreementByRentalId(int rentalId)
    {
        RentalAgreement rentalAgreement = rentalAgreementRepository.getRentalAgreementByRentalId(rentalId);

        if (rentalAgreement == null)
        {
            throw new RentalAgreementNotFoundException("Kunne ikke finde lejeaftale med id: " + rentalId);
        }

        return rentalAgreement;
    }

    public void createRentalAgreement(RentalAgreement rentalAgreement)
    {
        if (rentalAgreement == null)
        {
            throw new IllegalArgumentException("Fejl ved oprettelse af lejeaftale");
        }

        if (!businessRepository.carExists(rentalAgreement.getCarId()))
        {
            throw new CarNotFoundException("Bil med id: " + rentalAgreement.getCarId() + " eksisterer ikke");
        }

        if (businessRepository.isCarRented(rentalAgreement.getCarId()))
        {
            throw new IllegalArgumentException("Bil med id: " + rentalAgreement.getCarId() + " er allerede udlejet");
        }

        if (!customerRepository.customerExists(rentalAgreement.getCustomerId()))
        {
            throw new CustomerNotFoundException("Kunde med id: " + rentalAgreement.getCustomerId() + " eksisterer ikke");
        }

        if (!PriceValidation.isPriceValid(rentalAgreement.getPrice()))
        {
            throw new InvalidPriceException("Ugyldig pris for lejeaftale");
        }

        if (!RentalDateValidation.validateDate(rentalAgreement.getStartDate(), rentalAgreement.getEndDate()))
        {
            throw new InvalidRentalDateException("Ugyldig dato for lejeaftale");
        }
        if (!RentalStatusValidation.isStatusValid(rentalAgreement.getStatus()))
        {
            throw new InvalidRentalStatusException("Ugyldig status for lejeaftale");
        }


        rentalAgreementRepository.createRentalAgreement(rentalAgreement);
    }

    public List<RentalAgreement> getAllRentalAgreements()
    {
        return rentalAgreementRepository.getAllRentalAgreements();
    }

    public boolean isRentalAgreementActive(int rentalId)
    {
        if (rentalAgreementRepository.getRentalAgreementByRentalId(rentalId) == null)
        {
            throw new RentalAgreementNotFoundException("Kunne ikke finde lejeaftale med id: " + rentalId);
        }

        return rentalAgreementRepository.isRentalAgreementActive(rentalId);
    }


}
