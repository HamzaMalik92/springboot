package com.example.demo.fcm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;
import java.util.LinkedHashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class,MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<LinkedHashMap<String, String>> handleValidationExceptions(Exception ex) {
        LinkedHashMap<String, String> errors = new LinkedHashMap<>();
        if(ex instanceof MethodArgumentNotValidException methodArgumentNotValidException){
            methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

//            Cast the error to FieldError and get the rejected value
            Object rejectedValue = ((FieldError) error).getRejectedValue();
            int fieldLength = rejectedValue != null ? String.valueOf(rejectedValue).length() : 0;

            errors.put(fieldName, errorMessage);
            errors.put(fieldName + "'s length", String.valueOf(fieldLength));
        });
        }else{
            errors.put("error", ex.getMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<LinkedHashMap<String, String>> handleAllExceptions(Exception ex) {
        LinkedHashMap<String, String> errorResponse = new LinkedHashMap<>();
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
