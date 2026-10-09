package com.abdallah.payflow.email.exception;

public class FailedSendException extends RuntimeException {
    public FailedSendException(String message) {
        super(message);
    }
}
