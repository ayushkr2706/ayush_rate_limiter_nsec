package com.ayush.rateLimiterApp.globalException;

import com.ayush.rateLimiterApp.apiCredentialManagement.exception.ApiKeyNotFoundException;
import com.ayush.rateLimiterApp.rateLimiting.exception.PolicyNotFoundException;
import com.ayush.rateLimiterApp.rateLimiting.exception.TenantNotFoundException;
import com.ayush.rateLimiterApp.rateLimiting.exception.TenantInactiveException;
import com.ayush.rateLimiterApp.tenantManagement.exception.DuplicateTenantException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateTenantException.class)
    public ResponseEntity<String> handleDuplicateTenantException(DuplicateTenantException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<String> handlePolicyNotFoundException(PolicyNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<String> handlePolicyNotFoundException(TenantNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(TenantInactiveException.class)
    public ResponseEntity<String> handleTenantInactiveException(TenantInactiveException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
