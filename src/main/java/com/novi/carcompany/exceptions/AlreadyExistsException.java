package com.novi.carcompany.exceptions;


public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException() {

        super();
    }

    public AlreadyExistsException(String message) {

        super(message);
    }
}
