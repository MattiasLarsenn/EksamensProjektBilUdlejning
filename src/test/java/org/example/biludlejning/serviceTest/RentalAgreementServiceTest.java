package org.example.biludlejning.serviceTest;


import org.example.biludlejning.exceptions.*;
import org.example.biludlejning.models.RentalAgreement;
import org.example.biludlejning.repositories.repositoryInterfaces.IBusinessRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.ICustomerRepository;
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
    void shouldThrowExceptionWhenRentalIdDoesntExist()
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

        assertThrows(RentalAgreementNotFoundException.class, () ->
                service.getRentalAgreementByRentalId(2));
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
    void shouldThrowExceptionWhenCustomerIdDoesntExist()
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
        when(mockCustomerRepository.customerExists(1)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () ->
                service.createRentalAgreement(rentalAgreement));
    }

    @Test
    void shouldThrowExceptionWhenCarIdDoesntExist()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                new BigDecimal("5000"),
                "aktiv"
        );

        when(mockBusinessRepository.carExists(1)).thenReturn(false);

        assertThrows(CarNotFoundException.class, () ->
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

        when(mockCustomerRepository.customerExists(1)).thenReturn(true);
        when(mockBusinessRepository.carExists(1)).thenReturn(true);

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

        when(mockCustomerRepository.customerExists(1)).thenReturn(true);
        when(mockBusinessRepository.carExists(1)).thenReturn(true);

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

        when(mockCustomerRepository.customerExists(1)).thenReturn(true);
        when(mockBusinessRepository.carExists(1)).thenReturn(true);

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
    void shouldReturnTrueWhenRentalAgreementIsActive()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                new BigDecimal("-5000"),
                "aktiv"
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);

        when(mockRentalAgreementRepository.isRentalAgreementActive(1))
                .thenReturn(true);

        boolean result = service.isRentalAgreementActive(1);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenRentalAgreementIsNotActve()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                new BigDecimal("-5000"),
                "aktiv"
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);

        when(mockRentalAgreementRepository.isRentalAgreementActive(1))
                .thenReturn(false);

        boolean result = service.isRentalAgreementActive(1);

        assertFalse(result);
    }

    @Test
    void shouldThrowExceptionWhenCarIsAlreadyRented()
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
        when(mockBusinessRepository.isCarRented(1)).thenReturn(true);

        assertThrows(CarAlreadyRentedException.class, () ->
                service.createRentalAgreement(rentalAgreement));
    }

    @Test
    void shouldThrowExceptionWhenRentalIdDoesntExistInIsRentalAgreementActive()
    {
        RentalAgreement rentalAgreement = new RentalAgreement(
                1,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                new BigDecimal("5000"),
                "aktiv"
        );

        when(mockRentalAgreementRepository.getRentalAgreementByRentalId(1)).thenReturn(rentalAgreement);

        assertThrows(RentalAgreementNotFoundException.class,() ->
                service.isRentalAgreementActive(2));
    }
}
