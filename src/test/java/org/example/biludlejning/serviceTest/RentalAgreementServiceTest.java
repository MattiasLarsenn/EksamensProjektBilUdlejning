package org.example.biludlejning.serviceTest;


import org.example.biludlejning.exceptions.InvalidPriceException;
import org.example.biludlejning.exceptions.InvalidRentalDateException;
import org.example.biludlejning.exceptions.InvalidRentalStatusException;
import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.repositories.repositoryInterfaces.IBusinessRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.ICustomerRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.IDamageRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.IRentalAgreementRepository;
import org.example.biludlejning.services.RentalAgreementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RentalAgreementServiceTest
{
    private IRentalAgreementRepository mockRentalAgreementRepository;
    private ICustomerRepository mockCustomerRepository;
    private IBusinessRepository mockBusinessRepository;

    private RentalAgreementService service;

    @BeforeEach
    public void setUp()
    {
        mockRentalAgreementRepository = mock(IRentalAgreementRepository.class);
        mockCustomerRepository = mock(ICustomerRepository.class);
        mockBusinessRepository = mock(IBusinessRepository.class);

        service = new RentalAgreementService(mockRentalAgreementRepository, mockBusinessRepository, mockCustomerRepository);
    }

    @Test
    void shouldReturnRentalAgreementByRentalId()
    {

        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                new BigDecimal("5000"),
                "aktiv"
        );
        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1))
                .thenReturn(rentalAgreement);

        RentalAgreement result = service.getRentalAgreementByRentalId(1);

        assertNotNull(result);
        assertEquals(1, result.getRentalId());
    }

    @Test
    void shouldThrowExceptionWhenRentalIdIsLessThanOne()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.getRentalAgreementByRentalId(0));
    }

    @Test
    void shouldCreateRentalAgreement()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                new BigDecimal("5000"),
                "aktiv"
        );
        when(mockBusinessRepository.carExists(1)).thenReturn(true);
        when(mockCustomerRepository.customerExists(1)).thenReturn(true);

        service.createRentalAgreement(rentalAgreement);

        verify(mockRentalAgreementRepository)
                .createRentalAgreement(rentalAgreement);
    }

    @Test
    void shouldThrowExceptionWhenRentalAgreementIsNull()
    {
        assertThrows(IllegalArgumentException.class, () ->
                service.createRentalAgreement(null));
    }

    @Test
    void shouldThrowEceptionWhenCustomerIdIsLessThanOne()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                0,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                new BigDecimal("5000"),
                "aktiv"
        );

        assertThrows(IllegalArgumentException.class, () ->
                service.createRentalAgreement(rentalAgreement));
    }

    @Test
    void shouldThrowExceptionWhenCarIdIsLessThanOne()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                0,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                new BigDecimal("5000"),
                "aktiv"
        );

        assertThrows(IllegalArgumentException.class, () ->
                service.createRentalAgreement(rentalAgreement));
    }

    @Test
    void shouldThrowExceptionWhenEndDateIsBeforeStartDate()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                LocalDate.now(),
                LocalDate.now().minusDays(5),
                new BigDecimal("5000"),
                "aktiv"
        );

        assertThrows(InvalidRentalDateException.class, () ->
                service.createRentalAgreement(rentalAgreement));
    }

    @Test
    void ShouldThrowExceptionWhenStatusIsInvalid()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                new BigDecimal("5000"),
                "forkertStatus"
        );

        assertThrows(InvalidRentalStatusException.class, () ->
                service.createRentalAgreement(rentalAgreement));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsInvalid()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                new BigDecimal("-5000"),
                "aktiv"
        );

        assertThrows(InvalidPriceException.class, () ->
                service.createRentalAgreement(rentalAgreement));
    }

    @Test
    void shouldReturnAllRentalAgreements()
    {
        when(mockRentalAgreementRepository.getAllRentalAgreements())
                .thenReturn(new ArrayList<>());

        List<RentalAgreement> result = service.getAllRentalAgreements();

        assertNotNull(result);
    }

    @Test
    void shouldReturnTrueWhenRenntalAgreementIsActive()
    {
        when(mockRentalAgreementRepository.isRentalAgreementActive(1))
                .thenReturn(true);

        boolean result = service.isRentalAgreementActive(1);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenRentalAgreementIsNotActve()
    {
        when(mockRentalAgreementRepository.isRentalAgreementActive(1))
                .thenReturn(false);

        boolean result = service.isRentalAgreementActive(1);

        assertFalse(result);
    }
}
