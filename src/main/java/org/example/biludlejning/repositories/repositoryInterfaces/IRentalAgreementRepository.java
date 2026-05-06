package org.example.biludlejning.repositories.repositoryInterfaces;

import org.example.biludlejning.models.RentalAgreement;

import java.util.List;

public interface IRentalAgreementRepository
{
    RentalAgreement getRentalAgreementByRentalId(int rentalId);

    void createRentalAgreement(RentalAgreement rentalAgreement);

    List<RentalAgreement> getAllRentalAgreements();

    boolean isCarRented(int carId);


}
