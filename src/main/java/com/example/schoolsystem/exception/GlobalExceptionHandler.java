package com.example.schoolsystem.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.openapitools.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<Object> handleHibernateException(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private String getRequestUrl(WebRequest request) {
        return request.getDescription(false).replaceAll("[\n\r\t]", "_");
    }

    private ResponseEntity<Object> handleException(Exception ex, HttpStatus status, WebRequest request) {
        logger.error(String.format("API %s failed with error: %s", getRequestUrl(request), ex.getMessage()));
        ApiError apiError = new ApiError(status.toString(), ex.getMessage(), status.value(), LocalDateTime.now());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }
}
