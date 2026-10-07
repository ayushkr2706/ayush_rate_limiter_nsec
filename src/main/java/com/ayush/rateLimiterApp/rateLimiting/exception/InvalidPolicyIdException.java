package com.ayush.rateLimiterApp.rateLimiting.exception;

public class InvalidPolicyIdException extends RuntimeException{

    public InvalidPolicyIdException(String message){
        super(message);
    }
}
