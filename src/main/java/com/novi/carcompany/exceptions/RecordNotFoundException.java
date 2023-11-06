package com.novi.carcompany.exceptions;


public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {

        super();
    }

    public RecordNotFoundException(String message) {

        super(message);
    }
}
