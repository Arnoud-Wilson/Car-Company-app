package com.novi.carcompany.controllers;

import com.novi.carcompany.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionController {

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<String> exception(RecordNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IndexOutOfBoundsException.class)
    public ResponseEntity<String> exception(IndexOutOfBoundsException exception) {
        return new ResponseEntity<>("Dit ID is niet aanwezig in de database", HttpStatus.NOT_FOUND);
    }
}
