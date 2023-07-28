package com.crio.starter.exception;


import org.springframework.http.HttpStatus;

/**
 * This class represents an exeption that may occure during the execution of the application.
 */

public class ApplicationError extends Exception {
    
    private static final String DEFAULT_MSG = "Server side error. Please try again.";

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public ApplicationError() {
        super(DEFAULT_MSG);
    }

    public ApplicationError(String messageString) {
        super(messageString);
    }

    public ApplicationError(String messageString, HttpStatus httpStatus) {
        super(messageString);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}