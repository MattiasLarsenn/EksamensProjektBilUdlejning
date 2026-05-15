package org.example.biludlejning.exceptions;

public class CarAlreadyRentedException extends RuntimeException
{
    public CarAlreadyRentedException(String message)
    {
        super(message);
    }
}
