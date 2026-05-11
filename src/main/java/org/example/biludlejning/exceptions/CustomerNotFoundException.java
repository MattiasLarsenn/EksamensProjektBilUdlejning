package org.example.biludlejning.exceptions;

public class CustomerNotFoundException extends RuntimeException
{
    public CustomerNotFoundException(String message)
    {
        super(message);
    }
}
