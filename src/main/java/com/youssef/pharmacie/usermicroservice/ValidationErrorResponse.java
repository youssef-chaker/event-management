package com.youssef.pharmacie.usermicroservice;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidationErrorResponse extends ErrorResponse{
    public ValidationErrorResponse() {
        super();
    }

    public ValidationErrorResponse(String title, String detail, Set<ConstraintViolation<?>> violations) {
        super(title, detail);
        for (var violation: violations){
            super.getErrors().put(violation.getPropertyPath().toString(),violation.getMessageTemplate());
        }
    }
}
