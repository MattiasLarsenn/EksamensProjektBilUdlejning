package org.example.biludlejning.exceptionHandling;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.example.biludlejning.exceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgument(IllegalArgumentException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSqlException(SQLException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(InvalidDescriptionException.class)
    public ModelAndView handleInvalidDescription(InvalidDescriptionException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ModelAndView handleInvalidEmail(InvalidEmailException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(InvalidNameException.class)
    public ModelAndView handleInvalidName(InvalidNameException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ModelAndView handleInvalidPhoneNumber(InvalidPhoneNumberException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(InvalidPriceException.class)
    public ModelAndView handleInvalidPrice(InvalidPriceException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(InvalidRentalDateException.class)
    public ModelAndView handleInvalidRentalDate(InvalidRentalDateException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(InvalidRentalStatusException.class)
    public ModelAndView handleInvalidRentalStatus(InvalidRentalStatusException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(DamageNotFoundException.class)
    public ModelAndView handleDamageNotFound(DamageNotFoundException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(RentalAgreementNotFoundException.class)
    public ModelAndView handleRentalAgreementNotFound(RentalAgreementNotFoundException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ModelAndView handleCarNotFound(CarNotFoundException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ModelAndView handleCustomerNotFound(CustomerNotFoundException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView handleNoSuchElement(NoSuchElementException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(CarAlreadyRentedException.class)
    public ModelAndView handleCarAlreadyRented(CarAlreadyRentedException e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception e)
    {
        ModelAndView mav = new ModelAndView("errors/error");

        mav.addObject("errorMessage", "Der opstod en uventet fejl");

        return mav;
    }






}
