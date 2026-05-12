package org.example.biludlejning.serviceTest;


import org.example.biludlejning.exceptions.DamageNotFoundException;
import org.example.biludlejning.exceptions.InvalidDescriptionException;
import org.example.biludlejning.exceptions.InvalidPriceException;
import org.example.biludlejning.exceptions.RentalAgreementNotFoundException;
import org.example.biludlejning.models.Damage;
import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.repositories.repositoryInterfaces.IDamageRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.IRentalAgreementRepository;
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
    private IDamageRepository mockDamageRepository;
    private IRentalAgreementRepository mockRentalAgreementRepository;
    private DamageService service;

    @BeforeEach
    void setUp()
    {
        mockRentalAgreementRepository = mock(IRentalAgreementRepository.class);
        mockDamageRepository = mock(IDamageRepository.class);
        service = new DamageService(mockDamageRepository, mockRentalAgreementRepository);
    }

    @Test
    void shouldCreateDamage()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                new BigDecimal("2000"), "Aktiv"
        );

        Damage damage = new Damage(
                1,
                "Ødelagt spejl",
                new BigDecimal("1500")
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);

        service.createDamage(damage);

        verify(mockDamageRepository)
                .createDamage(damage);
    }

    @Test
    void shouldThrowExceptionWhenDamageIsNull()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.createDamage(null));
    }

    @Test
    void shouldThrowExceptionWhenRentalIdDoesntExist()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                new BigDecimal("2000"), "Aktiv"
        );

        Damage damage = new Damage(
                2,
                "Ødelagt spejl",
                new BigDecimal("1500")
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);


        assertThrows(RentalAgreementNotFoundException.class, () ->
                service.createDamage(damage));
    }

    @Test
    void shouldNotThrowExceptionWhenRentalIdExist()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                new BigDecimal("2000"), "Aktiv"
        );

        Damage damage = new Damage(
                1,
                "Ødelagt spejl",
                new BigDecimal("1500")
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);


        assertDoesNotThrow(() ->
                service.createDamage(damage));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsInvalid()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                new BigDecimal("2000"), "Aktiv"
        );

        Damage damage = new Damage(
                1,
                " ",
                new BigDecimal("1500")
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);

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

        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                new BigDecimal("2000"), "Aktiv"
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);


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

        when(mockDamageRepository.getDamageById(1))
                .thenReturn(damage);

        Damage result = service.getDamageById(1);

        assertNotNull(result);
        assertEquals(1, result.getDamageId());
    }

    @Test
    void shouldThrowExceptionWhenDamageIdDoesntExist()
    {
        Damage damage = new Damage(
                1,
                1,
                "Total Smadret",
                new BigDecimal("15000"),
                LocalDate.now()
        );

        when(mockDamageRepository.getDamageById(1)).thenReturn(damage);

        assertThrows(DamageNotFoundException.class, () ->
                service.getDamageById(2));
    }

    @Test
    void shouldReturnAllDamages()
    {
        when(mockDamageRepository.getAllDamages())
                .thenReturn(new ArrayList<>());

        List<Damage> result = service.getAllDamages();

        assertNotNull(result);
    }

    @Test
    void shouldReturnAllDamagesByRentalId()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                new BigDecimal("3000"),
                "aktiv"
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);

        when(mockDamageRepository.getAllDamagesByRentalId(1))
                .thenReturn(new ArrayList<>());

        List<Damage> result = service.getAllDamagesByRentalId(1);

        assertNotNull(result);
    }

    @Test
    void shouldReturnTotalDamagePriceByRentalId()
     {
         RentalAgreement rentalAgreement = new RentalAgreement(
                 1,
                 1,
                 1,
                 LocalDate.now(),
                 LocalDate.now().plusDays(7),
                 new BigDecimal("3000"),
                 "aktiv"
         );

         when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);

         when(mockDamageRepository.getTotalDamagePriceByRentalId(1))
                 .thenReturn(new BigDecimal("3000"));

         BigDecimal result = service.getTotalDamagePriceByRentalId(1);

         assertEquals(new BigDecimal("3000"), result);
     }

     @Test
     void shouldThrowExceptionWhenRentalIdDoesntExistInGetTotalPriceByRentalId()
     {
         assertThrows(RentalAgreementNotFoundException.class, () ->
                 service.getTotalDamagePriceByRentalId(1));
     }

    @Test
    void shouldUpdateDamage()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                new BigDecimal("3000"),
                "aktiv"
        );

        Damage damage = new Damage(
                1,
                "Ødelagt spejl",
                new BigDecimal("1500")
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);

        service.updateDamage(damage);

        verify(mockDamageRepository, times(1))
                .updateDamage(damage);
    }

    @Test
    void shouldThrowExceptionWhenDamageIsNullInUpdateDamage()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.updateDamage(null));
    }

    @Test
    void shouldThrowExceptionWhenRentalIdDoesntExistInUpdateDamage()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                new BigDecimal("3000"),
                "aktiv"
        );

        Damage damage = new Damage(
                2,
                "Ødelagt spejl",
                new BigDecimal("1500")
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);


        assertThrows(RentalAgreementNotFoundException.class, () ->
                service.updateDamage(damage));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsInvalidInUpdateDamage()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                new BigDecimal("3000"),
                "aktiv"
        );

        Damage damage = new Damage(
                1,
                " ",
                new BigDecimal("1500")
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);

        assertThrows(InvalidDescriptionException.class, () ->
                service.updateDamage(damage));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsInvalidInUpdateDamage()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                new BigDecimal("3000"),
                "aktiv"
        );

        Damage damage = new Damage(
                1,
                "Ødelagt spejl",
                new BigDecimal("-100")
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);


        assertThrows(InvalidPriceException.class, () ->
                service.updateDamage(damage));
    }

    @Test
    void shouldDeleteDamage()
    {
        Damage damage = new Damage(
                1,
                "Ødelagt spejl",
                new BigDecimal("-100")
        );

        when(mockDamageRepository.getDamageById(1)).thenReturn(damage);

        service.deleteDamage(1);

        verify(mockDamageRepository)
                .deleteDamage(1);
    }

    @Test
    void shouldThrowExceptionWhenDamageIdDoesntExistInDeleteDamage()
    {
        Damage damage = new Damage(
                1,
                "Ødelagt spejl",
                new BigDecimal("1500")
        );

        when(mockDamageRepository.getDamageById(1)).thenReturn(damage);

        assertThrows(DamageNotFoundException.class, () ->
                service.deleteDamage(2));
    }
}
