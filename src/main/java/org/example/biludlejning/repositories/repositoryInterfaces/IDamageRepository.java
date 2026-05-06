package org.example.biludlejning.repositories.repositoryInterfaces;

import org.example.biludlejning.models.Damage;

import java.math.BigDecimal;
import java.util.List;

public interface IDamageRepository
{
    void createDamage(Damage damage);

    Damage getDamageById(int damageId);

    List<Damage> getAllDamages();

    List<Damage> getAllDamagesByRentalId(int rentalId);

    BigDecimal getTotalDamagePriceByRentalId(int rentalId);
}
