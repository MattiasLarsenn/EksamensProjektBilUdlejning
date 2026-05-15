package org.example.biludlejning.serviceTest;

import org.example.biludlejning.repositories.repositoryInterfaces.IBusinessRepository;
import org.example.biludlejning.repositories.repositoryInterfaces.IRentalAgreementRepository;
import org.example.biludlejning.services.BusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BusinessServiceTest
{
    private IBusinessRepository mockBusinessRepository;
    private IRentalAgreementRepository mockRentalAgreementRepository;
    private BusinessService service;

    @BeforeEach
    void setup()
    {
        mockBusinessRepository = mock(IBusinessRepository.class);
        mockRentalAgreementRepository = mock(IRentalAgreementRepository.class);
        service = new BusinessService(mockBusinessRepository, mockRentalAgreementRepository);
    }

    @Test
    void shouldReturnBrandAndModelByRentalId()
    {
        when(mockBusinessRepository.getBrandAndModelByRentalId(1)).thenReturn("Volkswagen Golf");

        assertEquals("Volkswagen Golf", service.getBrandAndModelByRentalId(1));
    }

    @Test
    void shouldThrowExceptionWhenBrandAndModelByRentalIdWasntFound()
    {
        when(mockBusinessRepository.getBrandAndModelByRentalId(1)).thenReturn("Volkswagen Golf");

        assertThrows(NoSuchElementException.class, () ->
                service.getBrandAndModelByRentalId(2));
    }
}
