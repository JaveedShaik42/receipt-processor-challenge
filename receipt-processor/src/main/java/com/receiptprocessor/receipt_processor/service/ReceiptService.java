package com.receiptprocessor.receipt_processor.service;

import com.receiptprocessor.receipt_processor.exception.ErrorResponse;
import com.receiptprocessor.receipt_processor.exception.Field;
import com.receiptprocessor.receipt_processor.exception.ValidationException;
import com.receiptprocessor.receipt_processor.model.Item;
import com.receiptprocessor.receipt_processor.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReceiptService {
    private final Map<String, Receipt> receipts = new HashMap<>();
    private final Map<String, Integer> points = new HashMap<>();




    public String processReceipt(Receipt receipt) {
        ErrorResponse  errorResponse = validateReceipt(receipt);
        if(!errorResponse.getErrors().isEmpty()) {
            throw  new ValidationException(errorResponse);
        }
        String id = UUID.randomUUID().toString();
        receipts.put(id, receipt);
        points.put(id, calculatePoints(receipt));
        return id;
    }

    public Optional<Integer> getPoints(String id) {
        return Optional.ofNullable(points.get(id));
    }

    private ErrorResponse validateReceipt(Receipt receipt) {
        // Validate total matches sum of items
        //
        ErrorResponse errorResponse = new ErrorResponse();
        String retailer = receipt.getRetailer();
        String purchaseDate = receipt.getPurchaseDate();
        String purchaseTime = receipt.getPurchaseTime();


        if(retailer.isEmpty()) {
            Field f = new Field("Retailer", "Cant be empty");
            errorResponse.getErrors().add(f);
        }
        double sumOfItems = receipt.getItems().stream()
                .mapToDouble(item -> Double.parseDouble(item.getPrice()))
                .sum();

        double totalAmount = Double.parseDouble(receipt.getTotal());
        if (Math.abs(sumOfItems - totalAmount) > 0.01) {
            Field f = new Field("Total Amount", "Cant be empty");
            errorResponse.getErrors().add(f);
        }
        if(purchaseDate.isEmpty()) {
            Field f = new Field("Purchase Date", "Cant be empty");
            errorResponse.getErrors().add(f);
        }
        if(purchaseTime.isEmpty()) {
            Field f = new Field("Purchase Time", "Cant be empty");
            errorResponse.getErrors().add(f);
        }


        return errorResponse;
    }

    private int calculatePoints(Receipt receipt) {
        int total = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name
        total += calculateRetailerNamePoints(receipt.getRetailer());

        // Rule 2: 50 points if the total is a round dollar amount with no cents
        total += calculateRoundDollarPoints(receipt.getTotal());

        // Rule 3: 25 points if the total is a multiple of 0.25
        total += calculateMultipleQuarterPoints(receipt.getTotal());

        // Rule 4: 5 points for every two items on the receipt
        total += calculateItemCountPoints(receipt.getItems().size());

        // Rule 5: Points for item descriptions
        total += calculateItemDescriptionPoints(receipt.getItems());

        // Rule 6: 6 points if the day in the purchase date is odd
        total += calculateOddDayPoints(receipt.getPurchaseDate());

        // Rule 7: 10 points if the time of purchase is between 2:00pm and 4:00pm
        total += calculateTimePoints(receipt.getPurchaseTime());

        return total;
    }

    private int calculateRetailerNamePoints(String retailer) {
        return retailer.replaceAll("[^A-Za-z0-9]", "").length();
    }

    private int calculateRoundDollarPoints(String total) {
        return total.endsWith(".00") ? 50 : 0;
    }

    private int calculateMultipleQuarterPoints(String total) {
        double amount = Double.parseDouble(total);
        return Math.abs(amount % 0.25) < 0.001 ? 25 : 0;
    }

    private int calculateItemCountPoints(int itemCount) {
        return (itemCount / 2) * 5;
    }

    private int calculateItemDescriptionPoints(List<Item> items) {
        int points = 0;
        for (Item item : items) {
            String trimmedDescription = item.getShortDescription().trim();
            if (trimmedDescription.length() % 3 == 0) {
                double price = Double.parseDouble(item.getPrice());
                points += Math.ceil(price * 0.2);
            }
        }
        return points;
    }

    private int calculateOddDayPoints(String purchaseDate) {
        int day = Integer.parseInt(purchaseDate.split("-")[2]);
        return day % 2 == 1 ? 6 : 0;
    }

    private int calculateTimePoints(String purchaseTime) {
        LocalTime time = LocalTime.parse(purchaseTime);
        LocalTime startTime = LocalTime.of(14, 0); // 2:00 PM
        LocalTime endTime = LocalTime.of(16, 0);   // 4:00 PM

        return (time.isAfter(startTime) && time.isBefore(endTime)) ? 10 : 0;
    }

    // Helper methods for additional validation if needed
    private boolean isValidPrice(String price) {
        try {
            double amount = Double.parseDouble(price);
            return amount >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        try {
            LocalTime.parse(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Receipt getReceipt(String id) {
        return receipts.get(id);
    }

    // Method to clear all data (useful for testing)
    public void clearAll() {
        receipts.clear();
        points.clear();
    }
}