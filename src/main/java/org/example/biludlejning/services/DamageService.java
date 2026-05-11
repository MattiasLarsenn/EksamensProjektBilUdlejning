package org.example.biludlejning.services;

import java.math.BigDecimal;
import java.util.List;

import org.example.biludlejning.exceptions.InvalidDescriptionException;
import org.example.biludlejning.exceptions.InvalidPriceException;
import org.example.biludlejning.models.Damage;
import org.example.biludlejning.repositories.DamageRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.IDamageRepository;
import org.example.biludlejning.validation.DescriptionValidation;
import org.example.biludlejning.validation.PriceValidation;
import org.springframework.stereotype.Service;


@Service
public class DamageService
{
    private final IDamageRepository damageRepository;

    public DamageService(IDamageRepository damageRepository)
    {
        this.damageRepository = damageRepository;
    }

    public void createDamage(Damage damage)
    {
        if (damage == null)
        {
            throw new IllegalArgumentException("Damage cannot be null");
        }

        if (damage.getRentalId() <= 0)
        {
            throw new IllegalArgumentException("Rental id must be greater than 0");
        }

        if (!DescriptionValidation.isDescriptionValid(damage.getDescription()))
        {
            throw new InvalidDescriptionException("Invalid damage description");
        }

        if (!PriceValidation.isPriceValid(damage.getPrice()))
        {
            throw new InvalidPriceException("Invalid price for damage");
        }

        damageRepository.createDamage(damage);
    }

    public Damage getDamageById(int damageId)
    {
        if (damageId <= 0)
        {
            throw new IllegalArgumentException("Damage id must be greater than 0");
        }

        return damageRepository.getDamageById(damageId);
    }

    public List<Damage> getAllDamages()
    {
        return damageRepository.getAllDamages();
    }

    public List<Damage> getAllDamagesByRentalId(int rentalId)
    {
        if (rentalId <= 0)
        {
            throw new IllegalArgumentException("Rental id must be greater than 0");
        }

        return damageRepository.getAllDamagesByRentalId(rentalId);
    }

    public BigDecimal getTotalDamagePriceByRentalId(int rentalId)
    {
        if (rentalId <= 0)
        {
            throw new IllegalArgumentException("Rental id must be greater than 0");
        }

        return damageRepository.getTotalDamagePriceByRentalId(rentalId);
    }
    public void updateDamage(Damage damage)
    {
        if (damage == null)
        {
            throw new IllegalArgumentException("Damage cannot be null");
        }

        if (damage.getRentalId() <= 0)
        {
            throw new IllegalArgumentException("Rental id must be greater than 0");
        }

        if (!DescriptionValidation.isDescriptionValid(damage.getDescription()))
        {
            throw new InvalidDescriptionException("Invalid damage description");
        }

        if (!PriceValidation.isPriceValid(damage.getPrice()))
        {
            throw new InvalidPriceException("Invalid price for damage");
        }

        damageRepository.updateDamage(damage);
    }

    public void deleteDamage(int damageId)
    {
        if (damageId <= 0)
        {
            throw new IllegalArgumentException("Damage id must be greater than 0");
        }

        damageRepository.deleteDamage(damageId);
    }
}
