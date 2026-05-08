package org.example.biludlejning.validation;

public class EmailValidation
{
    public static boolean isEmailValid(String email)
    {
        return email != null
                && email.contains("@")
                && !email.startsWith("@")
                && !email.endsWith("@")
                && email.indexOf("@") == email.lastIndexOf("@")
                && !email.contains(" ")
                && email.contains(".")
                && email.lastIndexOf(".") > email.indexOf("@");
    }

}
