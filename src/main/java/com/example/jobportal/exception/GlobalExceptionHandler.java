package com.example.jobportal.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(
            ResourceNotFoundException ex,
            Model model
    ) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public String handleDuplicate(
            DuplicateResourceException ex,
            Model model
    ) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/duplicate";
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequest(
            BadRequestException ex,
            Model model
    ) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/400";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(
            Exception ex,
            Model model
    ) {
        model.addAttribute(
                "errorMessage",
                "Something went wrong. Please try again later."
        );
        return "error/500";
    }
}
