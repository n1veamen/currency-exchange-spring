package com.example.currency_exchange_spring.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = e.getBindingResult()
                .getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.warn("{} {}", status.value(), e.getMessage());
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), message));
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidData(InvalidDataException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        log.warn("{} {}", status.value(), e.getMessage());
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        log.warn("{} {}", status.value(), e.getMessage());
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        log.warn("{} {}", status.value(), e.getMessage());
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), e.getMessage()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExists(AlreadyExistsException e) {
        HttpStatus status = HttpStatus.CONFLICT;

        log.warn("{} {}", status.value(), e.getMessage());
        return ResponseEntity
                .status(status).
                body(new ErrorResponse(status.value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("{} {}", status.value(), e.getMessage());
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), "Internal server error"));
    }
}
