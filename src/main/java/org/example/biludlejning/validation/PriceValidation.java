package org.example.biludlejning.validation;

import java.math.BigDecimal;

public class PriceValidation
{
    public static boolean isPriceValid(BigDecimal price)
    {
        return price != null
                && price.compareTo(BigDecimal.ZERO) >= 0;
    }
}
