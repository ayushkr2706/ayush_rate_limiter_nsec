package com.ayush.rateLimiterApp.rateLimiting.exception;

public class TenantNotAllowedException extends RuntimeException{

    public TenantNotAllowedException(String message){
        super(message);
    }
}
