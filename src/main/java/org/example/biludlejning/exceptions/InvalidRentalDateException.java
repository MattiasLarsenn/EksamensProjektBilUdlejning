package org.example.biludlejning.exceptions;

public class InvalidRentalDateException extends RuntimeException
{
    public InvalidRentalDateException(String message)
    {
        super(message);
    }
}
