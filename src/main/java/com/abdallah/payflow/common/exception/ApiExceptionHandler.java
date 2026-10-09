package com.abdallah.payflow.common.exception;


import com.abdallah.payflow.email.exception.FailedSendException;
import com.abdallah.payflow.redis.exception.RateLimitExceededException;
import com.abdallah.payflow.transaction.exception.DuplicateTransactionException;
import com.abdallah.payflow.transaction.exception.InsufficientBalanceException;
import com.abdallah.payflow.transaction.exception.InvalidTransferException;
import com.abdallah.payflow.user.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorized(FailedSendException ex) {
        // 1. Create payload containing exception details
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                unauthorized,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, unauthorized);
    }

    @ExceptionHandler(FailedSendException.class)
    public ResponseEntity<Object> handleFailedSend(FailedSendException ex) {
        // 1. Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        // 1. Create payload containing exception details
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                unauthorized,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, unauthorized);
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<Object> handleRateLimitExceeded(RateLimitExceededException ex) {
        // 1. Create payload containing exception details
        HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                tooManyRequests,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, tooManyRequests);
    }

    @ExceptionHandler(InvalidTransferException.class)
    public ResponseEntity<Object> handleInvalidTransfer(InvalidTransferException ex) {
        // 1. Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> handleInsufficientBalance(InsufficientBalanceException ex) {
        // 1. Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(DuplicateTransactionException.class)
    public ResponseEntity<Object> handleDuplicateTransaction(DuplicateTransactionException ex) {
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        // 1. Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, badRequest);
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

