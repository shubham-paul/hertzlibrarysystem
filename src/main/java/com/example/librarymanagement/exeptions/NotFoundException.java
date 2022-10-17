package com.example.librarymanagement.exeptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(final String message) {
        super(message);
    }
}
