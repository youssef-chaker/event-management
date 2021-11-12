package com.youssef.pharmacie.usermicroservice;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

@ControllerAdvice
public class ExceptionHandlingAspect {
    @ExceptionHandler
    public ResponseEntity<Object> handleValidation(ConstraintViolationException ex){
        return ResponseEntity.badRequest().body(new ValidationErrorResponse("validation error","",ex.getConstraintViolations()));
    }
}
