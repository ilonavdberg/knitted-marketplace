package com.knitted.marketplace.exception;

import com.knitted.marketplace.exception.exceptions.InvalidEnumValueException;
import com.knitted.marketplace.exception.exceptions.InvalidStatusChangeException;
import com.knitted.marketplace.exception.exceptions.ItemAlreadySoldException;
import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidStatusChangeException.class)
    public ResponseEntity<String> handleInvalidStatusChangeException(InvalidStatusChangeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
