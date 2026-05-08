package org.example.biludlejning.services;

import org.example.biludlejning.models.Damage;
import org.example.biludlejning.repositories.DamageRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class DamageService
{
    private final DamageRepository damageRepository;

    public DamageService(DamageRepository damageRepository)
    {
        this.damageRepository = damageRepository;
    }

    public void createDamage(Damage damage)
    {
        damageRepository.createDamage(damage);
    }

    public Damage getDamageById(int damageId)
    {
        return damageRepository.getDamageById(damageId);
    }

    public List<Damage> getAllDamages()
    {
        return damageRepository.getAllDamages();
    }

    public List<Damage> getAllDamagesByRentalId(int rentalId)
    {
        return damageRepository.getAllDamagesByRentalId(rentalId);
    }

    public BigDecimal getTotalDamagePriceByRentalId(int rentalId)
    {
        return damageRepository.getTotalDamagePriceByRentalId(rentalId);
    }
}
