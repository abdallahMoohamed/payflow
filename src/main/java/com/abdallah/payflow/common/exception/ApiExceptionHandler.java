package com.abdallah.payflow.common.exception;


import com.abdallah.payflow.user.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailExists(EmailAlreadyExistsException ex) {
        // 1. Create payload containing exception details
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, conflict);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleWalletNotFound(NotFoundException ex) {
        // 1. Create payload containing exception details
        HttpStatus conflict = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, conflict);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception ex) {
        System.out.println("exception: " + ex);
        // 1. Create payload containing exception details
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                "An unexpected error occurred",
                internalServerError,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, internalServerError);
    }

}

