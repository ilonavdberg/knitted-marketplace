package com.knitted.marketplace.exception;

import com.knitted.marketplace.exception.exceptions.*;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<String> handleRecordNotFoundException(RecordNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid number format: " + e.getMessage());
    }

    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<String> handleInvalidEnumValueException(InvalidEnumValueException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(ItemAlreadySoldException.class)
    public ResponseEntity<String> handleItemAlreadySoldException(ItemAlreadySoldException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidStatusChangeException.class)
    public ResponseEntity<String> handleInvalidStatusChangeException(InvalidStatusChangeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ItemPublicationValidationException.class)
    public ResponseEntity<String> handleItemPublicationValidationException(ItemPublicationValidationException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String message = (e.getRootCause() != null) ? e.getRootCause().getMessage() : "Unknown cause";
        return new ResponseEntity<>("Data integrity violation: " + message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<String> handleRecordAlreadyExistsException(RecordAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

}
