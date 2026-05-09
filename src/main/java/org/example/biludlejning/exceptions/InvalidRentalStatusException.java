package org.example.biludlejning.exceptions;

public class InvalidRentalStatusException extends RuntimeException
{
    public InvalidRentalStatusException(String message)
    {
        super(message);
    }
}
