package com.ayush.rateLimiterApp.globalException;

import com.ayush.rateLimiterApp.rateLimiting.exception.*;
import com.ayush.rateLimiterApp.tenantManagement.exception.DuplicateTenantException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //The JSON shape every error will have
    public record ErrorResponse(String error, String message){}

    @ExceptionHandler(DuplicateTenantException.class)
    public ResponseEntity<String> handleDuplicateTenantException(DuplicateTenantException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(
            {           TenantNotFoundException.class,
                        InvalidApiKeyException.class,
                        MissingRequestHeaderException.class}
    )
    public ResponseEntity<ErrorResponse> handleUnauthorized(Exception ex){
      return build(HttpStatus.UNAUTHORIZED, "Invalid or missing API key");
    }

    @ExceptionHandler(TenantInactiveException.class)
    public ResponseEntity<ErrorResponse> handleTenantInactiveException(TenantInactiveException ex){
        return build(HttpStatus.FORBIDDEN, "This account is not active");
    }

    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePolicyNotFoundException(PolicyNotFoundException ex){
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidPolicyIdException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPolicyIdException(InvalidPolicyIdException ex){
        return build(HttpStatus.BAD_REQUEST, "Invalid policy id");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){

        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid request");

        return build(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadBody(HttpMessageNotReadableException ex){
        log.error("Storage problem (Redis or Database" , ex);
        return build(HttpStatus.BAD_REQUEST, "Request body is missing or is not valid JSON");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleStorageProblem(DataAccessException ex){
        return build(HttpStatus.SERVICE_UNAVAILABLE, "Service temporarily unavailable");
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message){
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status.getReasonPhrase(), message));
    }
}
