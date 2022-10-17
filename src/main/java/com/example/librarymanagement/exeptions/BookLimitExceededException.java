package com.example.librarymanagement.exeptions;

public class BookLimitExceededException extends RuntimeException{

    public BookLimitExceededException(final String message) {
        super(message);
    }
}
