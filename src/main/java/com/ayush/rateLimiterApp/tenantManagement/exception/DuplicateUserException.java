package com.ayush.rateLimiterApp.tenantManagement.exception;

public class DuplicateUserException extends RuntimeException{

    public DuplicateUserException(String message){
        super(message);
    }
}
