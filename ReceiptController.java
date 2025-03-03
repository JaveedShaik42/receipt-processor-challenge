package com.receiptprocessor.receipt_processor.controller;

import com.receiptprocessor.receipt_processor.exception.ErrorResponse;
import com.receiptprocessor.receipt_processor.exception.ReceiptNotFoundException;
import com.receiptprocessor.receipt_processor.exception.ValidationException;
import com.receiptprocessor.receipt_processor.model.PointsResponse;
import com.receiptprocessor.receipt_processor.model.Receipt;
import com.receiptprocessor.receipt_processor.model.ReceiptResponse;
import com.receiptprocessor.receipt_processor.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<ReceiptResponse> processReceipt(@Valid @RequestBody Receipt receipt) {
        try {
            String id = receiptService.processReceipt(receipt);
            return ResponseEntity.ok(new ReceiptResponse(id));
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(new ReceiptResponse(ex.getMessage()));
        }
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponse> getPoints(@PathVariable String id) {
        return receiptService.getPoints(id)
                .map(points -> ResponseEntity.ok(new PointsResponse(points)))
                .orElseThrow(() -> new ReceiptNotFoundException(id));
    }
}

