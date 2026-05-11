package org.example.biludlejning.exceptions;

public class RentalAgreementNotFoundException extends RuntimeException
{
    public RentalAgreementNotFoundException(String message)
    {
        super(message);
    }
}
