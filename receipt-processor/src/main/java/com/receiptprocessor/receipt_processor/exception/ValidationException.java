package com.receiptprocessor.receipt_processor.exception;

public class ValidationException extends  RuntimeException {

    ErrorResponse e;
    public ValidationException(ErrorResponse e) {
        this.e =e;
    }

    public ErrorResponse getE() {
        return e;
    }

    public void setE(ErrorResponse e) {
        this.e = e;
    }
}
