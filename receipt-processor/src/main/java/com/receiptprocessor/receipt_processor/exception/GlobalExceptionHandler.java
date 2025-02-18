package com.receiptprocessor.receipt_processor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("new ErrorResponse()");
    }

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ReceiptNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("new ErrorResponse(ex.getMessage())");
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationFoundException(ValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getE());
    }
}
