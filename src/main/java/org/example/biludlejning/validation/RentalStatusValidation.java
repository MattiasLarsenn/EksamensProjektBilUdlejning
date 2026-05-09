package org.example.biludlejning.validation;

public class RentalStatusValidation
{
    public static boolean isStatusValid(String status)
    {
        return status != null &&
                (status.equalsIgnoreCase("aktiv")
                || status.equalsIgnoreCase("afsluttet")
                || status.equalsIgnoreCase("Anulleret"));
    }
}
