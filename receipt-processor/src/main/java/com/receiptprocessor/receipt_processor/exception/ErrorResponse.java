package com.receiptprocessor.receipt_processor.exception;

import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@RequestScope
@Component
public class ErrorResponse {

    public List<Field> getErrors() {
        return errors;
    }

    List<Field> errors = new ArrayList<>();


}
