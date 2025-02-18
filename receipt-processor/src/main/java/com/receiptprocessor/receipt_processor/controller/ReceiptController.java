package com.receiptprocessor.receipt_processor.controller;

import com.receiptprocessor.receipt_processor.exception.ErrorResponse;
import com.receiptprocessor.receipt_processor.exception.ReceiptNotFoundException;
import com.receiptprocessor.receipt_processor.model.PointsResponse;
import com.receiptprocessor.receipt_processor.model.Receipt;
import com.receiptprocessor.receipt_processor.model.ReceiptResponse;
import com.receiptprocessor.receipt_processor.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;
    private final ErrorResponse errorResponse;
    public ReceiptController(ReceiptService receiptService, ErrorResponse errorResponse) {
        this.receiptService = receiptService;
        this.errorResponse = errorResponse;
    }

    @PostMapping("/process")
    public ResponseEntity<ReceiptResponse> processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        if(!errorResponse.getErrors().isEmpty()) {
            return ResponseEntity.ok(new ReceiptResponse(errorResponse));
        }
        return ResponseEntity.ok(new ReceiptResponse(id));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponse> getPoints(@PathVariable String id) {
        return receiptService.getPoints(id)
                .map(points -> ResponseEntity.ok(new PointsResponse(points)))
                .orElseThrow(() -> new ReceiptNotFoundException(id));
    }
}

