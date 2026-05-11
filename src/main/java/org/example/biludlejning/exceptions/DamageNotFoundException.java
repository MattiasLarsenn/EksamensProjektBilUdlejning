package org.example.biludlejning.exceptions;

public class DamageNotFoundException extends RuntimeException
{
    public DamageNotFoundException(String message)
    {
        super(message);
    }
}
