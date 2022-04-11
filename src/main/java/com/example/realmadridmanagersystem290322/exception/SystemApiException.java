package com.example.realmadridmanagersystem290322.exception;

import org.springframework.http.HttpStatus;

public class SystemApiException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;

    public SystemApiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
