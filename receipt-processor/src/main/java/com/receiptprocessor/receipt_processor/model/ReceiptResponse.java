package com.receiptprocessor.receipt_processor.model;

import com.receiptprocessor.receipt_processor.exception.ErrorResponse;

public class ReceiptResponse {
    private String id;
    ErrorResponse response;
    public ReceiptResponse(String id) {
        this.id = id;
    }

    public ReceiptResponse(ErrorResponse response) {
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

