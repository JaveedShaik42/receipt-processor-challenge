package com.receiptprocessor.receipt_processor.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Item {
    @NotNull
    @Pattern(regexp = "^[\\w\\s\\-]+$")
    private String shortDescription;

    @NotNull
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String price;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

