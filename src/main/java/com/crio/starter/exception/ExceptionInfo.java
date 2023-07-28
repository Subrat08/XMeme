package com.crio.starter.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class contains the information about an exception in the form of a simple message that is easy to understand.
 */

@NoArgsConstructor
@Data
public class ExceptionInfo {

    private String messageString = "Something went wrong. Please try again";

    public ExceptionInfo(String messageString) {
        this.messageString = messageString;
    }
    
}