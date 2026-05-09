package org.example.biludlejning.services;

import java.util.List;

import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.repositories.RentalAgreementRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalAgreementService
{
    private final RentalAgreementRepository rentalAgreementRepository;

    public RentalAgreementService(RentalAgreementRepository rentalAgreementRepository)
    {
        this.rentalAgreementRepository = rentalAgreementRepository;
    }

    public RentalAgreement getRentalAgreementRepositoryByRentalId(int rentalId)
    {
        return rentalAgreementRepository.getRentalAgreementByRentalId(rentalId);
    }

    public RentalAgreement getRentalAgreementByRentalId(int rentalId)
    {
        return rentalAgreementRepository.getRentalAgreementByRentalId(rentalId);
    }

    public void createRentalAgreement(RentalAgreement rentalAgreement)
    {
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
