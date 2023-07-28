package com.crio.starter.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** 
 * Exception handler class
 */

@ControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<ExceptionInfo> handleException(ApplicationError error) {

        ExceptionInfo exceptionInfo = new ExceptionInfo(error.getMessage());
        
        return new ResponseEntity<ExceptionInfo>(exceptionInfo, error.getHttpStatus());
    }
}