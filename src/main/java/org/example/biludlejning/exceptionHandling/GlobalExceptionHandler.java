package org.example.biludlejning.exceptionHandling;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import org.example.biludlejning.exceptions.CarNotFoundException;
import org.example.biludlejning.exceptions.CustomerNotFoundException;
import org.example.biludlejning.exceptions.DamageNotFoundException;
import org.example.biludlejning.exceptions.InvalidDescriptionException;
import org.example.biludlejning.exceptions.InvalidEmailException;
import org.example.biludlejning.exceptions.InvalidNameException;
import org.example.biludlejning.exceptions.InvalidPhoneNumberException;
import org.example.biludlejning.exceptions.InvalidPriceException;
import org.example.biludlejning.exceptions.InvalidRentalDateException;
import org.example.biludlejning.exceptions.InvalidRentalStatusException;
import org.example.biludlejning.exceptions.RentalAgreementNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ModelAttribute
    public void exposeErrorMessage(@RequestParam(value = "error", required = false) String errorMessage, Model model)
    {
        if (errorMessage != null && !errorMessage.isBlank())
        {
            model.addAttribute("errorMessage", errorMessage);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSqlException(SQLException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(InvalidDescriptionException.class)
    public ModelAndView handleInvalidDescription(InvalidDescriptionException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ModelAndView handleInvalidEmail(InvalidEmailException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(InvalidNameException.class)
    public ModelAndView handleInvalidName(InvalidNameException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ModelAndView handleInvalidPhoneNumber(InvalidPhoneNumberException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(InvalidPriceException.class)
    public ModelAndView handleInvalidPrice(InvalidPriceException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(InvalidRentalDateException.class)
    public ModelAndView handleInvalidRentalDate(InvalidRentalDateException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(InvalidRentalStatusException.class)
    public ModelAndView handleInvalidRentalStatus(InvalidRentalStatusException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(DamageNotFoundException.class)
    public ModelAndView handleDamageNotFound(DamageNotFoundException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(RentalAgreementNotFoundException.class)
    public ModelAndView handleRentalAgreementNotFound(RentalAgreementNotFoundException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception e, HttpServletRequest request)
    {
        return redirectBackWithError("Der opstod en uventet fejl: " + e.getMessage(), request);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ModelAndView handleCarNotFound(CarNotFoundException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ModelAndView handleCustomerNotFound(CustomerNotFoundException e, HttpServletRequest request)
    {
        return redirectBackWithError(e.getMessage(), request);
    }

    private ModelAndView redirectBackWithError(String errorMessage, HttpServletRequest request)
    {
        String referer = request.getHeader("Referer");
        String encodedError = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);

        if (referer == null || referer.isBlank())
        {
            return new ModelAndView("redirect:/?error=" + encodedError);
        }

        try
        {
            URI refererUri = URI.create(referer);
            String path = (refererUri.getPath() == null || refererUri.getPath().isBlank()) ? "/" : refererUri.getPath();
            String query = refererUri.getQuery();

            String separator = (query == null || query.isBlank()) ? "?" : "&";
            String redirectTarget = path + (query == null ? "" : "?" + query) + separator + "error=" + encodedError;

            return new ModelAndView("redirect:" + redirectTarget);
        }
        catch (IllegalArgumentException ex)
        {
            return new ModelAndView("redirect:/?error=" + encodedError);
        }
    }
}
