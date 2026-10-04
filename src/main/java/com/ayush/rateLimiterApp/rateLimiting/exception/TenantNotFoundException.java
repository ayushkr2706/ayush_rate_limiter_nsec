package com.ayush.rateLimiterApp.rateLimiting.exception;

public class TenantNotFoundException extends RuntimeException{

    public TenantNotFoundException(String message){
        super(message);
    }
}
