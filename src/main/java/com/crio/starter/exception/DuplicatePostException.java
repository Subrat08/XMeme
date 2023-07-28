package com.crio.starter.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception must be thrown when a user tries to access a meme post that is not available in the database.
 */

public class DuplicatePostException extends ApplicationError{
    
    private static final String DEFAULT_MESSAGE = "Duplicate post!";

    public DuplicatePostException() {
        super(DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }

    public DuplicatePostException(String messageString) {
        super(messageString, HttpStatus.CONFLICT);
    }
}