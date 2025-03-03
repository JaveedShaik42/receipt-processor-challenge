package com.receiptprocessor.receipt_processor.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private String message;

    @JsonIgnore
    private List<Field> errors = new ArrayList<>();

    public ErrorResponse() {
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getters and setters for errors if needed internally
    public List<Field> getErrors() {
        return errors;
    }

    public void setErrors(List<Field> errors) {
        this.errors = errors;
    }
}