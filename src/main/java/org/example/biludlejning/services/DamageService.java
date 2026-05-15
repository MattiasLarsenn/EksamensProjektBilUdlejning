package org.example.biludlejning.services;

import java.math.BigDecimal;
import java.util.List;

import org.example.biludlejning.exceptions.DamageNotFoundException;
import org.example.biludlejning.exceptions.InvalidDescriptionException;
import org.example.biludlejning.exceptions.InvalidPriceException;
import org.example.biludlejning.exceptions.RentalAgreementNotFoundException;
import org.example.biludlejning.models.Damage;
import org.example.biludlejning.repositories.repositoryInterfaces.IDamageRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.IRentalAgreementRepository;
import org.example.biludlejning.validation.DescriptionValidation;
import org.example.biludlejning.validation.PriceValidation;
import org.springframework.stereotype.Service;


@Service
public class DamageService
{
    private final IDamageRepository damageRepository;
    private final IRentalAgreementRepository rentalAgreementRepository;

    public DamageService(IDamageRepository damageRepository, IRentalAgreementRepository rentalAgreementRepository)
    {
        this.damageRepository = damageRepository;
        this.rentalAgreementRepository = rentalAgreementRepository;
    }

    public void createDamage(Damage damage)
    {
        if (damage == null)
        {
            throw new IllegalArgumentException("Fejl ved oprettelse af skade");
        }

        if (rentalAgreementRepository.getRentalAgreementByRentalId(damage.getRentalId()) == null)
        {
            throw new RentalAgreementNotFoundException("Kunne ikke finde lejeaftale med id: " + damage.getRentalId());
        }

        if (!DescriptionValidation.isDescriptionValid(damage.getDescription()))
        {
            throw new InvalidDescriptionException("Ugyldig beskrivelse af skade");
        }

        if (!PriceValidation.isPriceValid(damage.getPrice()))
        {
            throw new InvalidPriceException("Ugyldig pris på skade");
        }

        damageRepository.createDamage(damage);
    }

    public Damage getDamageById(int damageId)
    {
        Damage damage = damageRepository.getDamageById(damageId);

        if (damage == null)
        {
            throw new DamageNotFoundException("Ingen skade fundet med nummer: " + damageId);
        }

        return damage;
    }

    public List<Damage> getAllDamages()
    {
        return damageRepository.getAllDamages();
    }

    public List<Damage> getAllDamagesByRentalId(int rentalId)
    {
        if (rentalAgreementRepository.getRentalAgreementByRentalId(rentalId) == null)
        {
            throw new RentalAgreementNotFoundException("Kunne ikke finde lejeaftale med id: " + rentalId);
        }

        return damageRepository.getAllDamagesByRentalId(rentalId);
    }

    public BigDecimal getTotalDamagePriceByRentalId(int rentalId)
    {
        if (rentalAgreementRepository.getRentalAgreementByRentalId(rentalId) == null)
        {
            throw new RentalAgreementNotFoundException("Kunne ikke finde lejeaftale med id: " + rentalId);
        }

        return damageRepository.getTotalDamagePriceByRentalId(rentalId);
    }
    public void updateDamage(Damage damage)
    {
        if (damage == null)
        {
            throw new IllegalArgumentException("Fejl ved opdatering af skade");
        }

        if (rentalAgreementRepository.getRentalAgreementByRentalId(damage.getRentalId()) == null)
        {
            throw new RentalAgreementNotFoundException("Kunne ikke finde lejeaftale med id: " + damage.getRentalId());
        }

        if (!DescriptionValidation.isDescriptionValid(damage.getDescription()))
        {
            throw new InvalidDescriptionException("Ugyldig beskrivelse af skade");
        }

        if (!PriceValidation.isPriceValid(damage.getPrice()))
        {
            throw new InvalidPriceException("Ugyldig pris på skade");
        }

        damageRepository.updateDamage(damage);
    }

    public void deleteDamage(int damageId)
    {
        if (damageRepository.getDamageById(damageId) == null)
        {
            throw new DamageNotFoundException("Kunne ikke finde skade med id: " + damageId);
        }

        damageRepository.deleteDamage(damageId);
    }
}
