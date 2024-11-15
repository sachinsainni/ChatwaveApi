package com.sachin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
//        Map<String, String> errors = new HashMap<>();
        
    	String errors = "";
        // Extract the details of each violation
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
//            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
//            errors.put("error", errorMessage);
        	if(errors.isEmpty()) {
        		errors = errorMessage;
        	}
        }

        // Return a response with the validation errors
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
