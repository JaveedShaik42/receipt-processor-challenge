package com.receiptprocessor.receipt_processor.exception;

import lombok.Getter;

@Getter
public class ValidationException extends  RuntimeException {

    ErrorResponse e;
    public ValidationException(ErrorResponse e) {
        this.e =e;
    }

    public void setE(ErrorResponse e) {
        this.e = e;
    }
}
