package com.ayush.rateLimiterApp.tenantManagement.exception;

public class DuplicateTenantException extends RuntimeException{

    public DuplicateTenantException(String message){
        super(message);
    }
}
