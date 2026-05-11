package org.example.biludlejning.serviceTest;


import org.example.biludlejning.exceptions.InvalidDescriptionException;
import org.example.biludlejning.exceptions.InvalidPriceException;
import org.example.biludlejning.models.Damage;
import org.example.biludlejning.repositories.repositoryInterfaces.IDamageRepository;
import org.example.biludlejning.services.DamageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DamageServiceTest
{
    private IDamageRepository mockRepository;
    private DamageService service;

    @BeforeEach
    void setUp()
    {
        mockRepository = mock(IDamageRepository.class);
        service = new DamageService(mockRepository);
    }

    @Test
    void shouldCreateDamage()
    {
        Damage damage = new Damage(
                1,
                "Ødelagt spejl",
                new BigDecimal("1500")
        );

        service.createDamage(damage);

        verify(mockRepository)
                .createDamage(damage);
    }

    @Test
    void shouldThrowExceptionWhenDamageIsNull()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.createDamage(null));
    }

    @Test
    void shouldThrowExceptionWhenRentalIdIsLessThanOne()
    {
        Damage damage = new Damage(
                0,
                "Ødelagt spejl",
                new BigDecimal("1500")
        );

        assertThrows(IllegalArgumentException.class, () ->
                service.createDamage(damage));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsInvalid()
    {
        Damage damage = new Damage(
                1,
                " ",
                new BigDecimal("1500")
        );
        assertThrows(InvalidDescriptionException.class, () ->
                service.createDamage(damage));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsInvalid()
    {
        Damage damage = new Damage(
                1,
                "Ødelagt spejl",
                new BigDecimal("-100")
        );

        assertThrows(InvalidPriceException.class, () ->
                service.createDamage(damage));
    }

    @Test
    void shouldReturnDamageById()
    {
        Damage damage = new Damage(
                1,
                1,
                "Ødelagt spejl",
                new BigDecimal("1500"),
                LocalDate.now()
        );

        when(mockRepository.getDamageById(1))
                .thenReturn(damage);

        Damage result = service.getDamageById(1);

        assertNotNull(result);
        assertEquals(1, result.getDamageId());
    }

    @Test
    void shouldThrowExceptionWhenDamageIdIsLessThanOne()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.getDamageById(0));
    }

    @Test
    void shouldReturnAlleDamages()
    {
        when(mockRepository.getAllDamages())
                .thenReturn(new ArrayList<>());

        List<Damage> result = service.getAllDamages();

        assertNotNull(result);
    }

    @Test
    void shouldReturnAllDamagesByRentalId()
    {
        when(mockRepository.getAllDamagesByRentalId(1))
                .thenReturn(new ArrayList<>());

        List<Damage> result = service.getAllDamagesByRentalId(1);

        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionWhenRentalIdIsLessThanOneInGetAllDamagesByRentalId()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.getAllDamagesByRentalId(0));
    }

    @Test
    void shouldReturnTotalDamagePriceByRentalId()
     {
         when(mockRepository.getTotalDamagePriceByRentalId(1))
                 .thenReturn(new BigDecimal("3000"));

         BigDecimal result = service.getTotalDamagePriceByRentalId(1);

         assertEquals(new BigDecimal("3000"), result);
     }

     @Test
     void shouldThrowExceptionWhenRentalIdIsLessThanOneInGetTotalDamagePriceById()
     {
         assertThrows(IllegalArgumentException.class, () ->
                 service.getTotalDamagePriceByRentalId(0));
     }

    @Test
    void shouldUpdateDamage()
    {
        Damage damage = new Damage(
                1,
                "Ødelagt spejl",
                new BigDecimal("1500")
        );

        service.updateDamage(damage);

        verify(mockRepository, times(1))
                .updateDamage(damage);
    }

    @Test
    void shouldThrowExceptionWhenDamageIsNullInUpdateDamage()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.updateDamage(null));
    }

    @Test
    void shouldThrowExceptionWhenRentalIdIsLessThanOneInUpdateDamage()
    {
        Damage damage = new Damage(
                0,
                "Ødelagt spejl",
                new BigDecimal("1500")
        );

        assertThrows(IllegalArgumentException.class, () ->
                service.updateDamage(damage));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsInvalidInUpdateDamage()
    {
        Damage damage = new Damage(
                1,
                " ",
                new BigDecimal("1500")
        );
        assertThrows(InvalidDescriptionException.class, () ->
                service.updateDamage(damage));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsInvalidInUpdateDamage()
    {
        Damage damage = new Damage(
                1,
                "Ødelagt spejl",
                new BigDecimal("-100")
        );

        assertThrows(InvalidPriceException.class, () ->
                service.updateDamage(damage));
    }

    @Test
    void shouldDeleteDamage()
    {
        service.deleteDamage(1);

        verify(mockRepository)
                .deleteDamage(1);
    }

    @Test
    void shouldThrowExceptionWhenDamageIdIsLessThanOneInDeleteDamage()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.deleteDamage(0));
    }
}
