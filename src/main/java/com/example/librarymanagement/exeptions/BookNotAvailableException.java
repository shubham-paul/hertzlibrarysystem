package com.example.librarymanagement.exeptions;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(final String message) {
        super(message);
    }
}
