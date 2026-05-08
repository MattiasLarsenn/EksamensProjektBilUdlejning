package org.example.biludlejning.validation;

public class PhoneNumberValidation
{
    public static boolean isPhoneNumberValid(String phoneNumber)
    {
        return phoneNumber != null
                && phoneNumber.matches("[2-9]\\d{7}");
    }
}
