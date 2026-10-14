package com.abdallah.payflow.common.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String message;
    private final int httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus.value();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public int getHttpStatus() {
        return httpStatus;
    }

}
