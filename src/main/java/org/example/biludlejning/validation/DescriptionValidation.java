package org.example.biludlejning.validation;

public class DescriptionValidation
{
    public static boolean isDescriptionValid(String description)
    {
        return description != null
                && !description.isBlank()
                && description.length() <= 500;
    }
}
