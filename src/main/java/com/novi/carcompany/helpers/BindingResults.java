package com.novi.carcompany.helpers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class BindingResults {

    public static ResponseEntity<Object> showBindingResult(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();
        for (
                FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField());
            stringBuilder.append(": ");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append("\n");
        }
        return ResponseEntity.badRequest().body(stringBuilder);
    }
}
