package com.ayush.rateLimiterApp.rateLimiting.exception;

public class TenantInactiveException extends RuntimeException{

    public TenantInactiveException(String message){
        super(message);
    }
}
