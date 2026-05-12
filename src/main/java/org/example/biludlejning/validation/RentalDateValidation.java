package org.example.biludlejning.validation;

import java.time.LocalDate;

public class RentalDateValidation
{
    public static boolean validateDate(LocalDate startDate, LocalDate endDate)
    {
        return startDate != null
                && endDate != null
                && endDate.isAfter(startDate)
                && !startDate.isBefore(LocalDate.now());
    }
}
