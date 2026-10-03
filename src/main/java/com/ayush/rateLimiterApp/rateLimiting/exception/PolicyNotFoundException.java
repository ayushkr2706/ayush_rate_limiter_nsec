package com.ayush.rateLimiterApp.rateLimiting.exception;

public class PolicyNotFoundException extends RuntimeException{

    public PolicyNotFoundException(String message){
        super(message);
    }
}
