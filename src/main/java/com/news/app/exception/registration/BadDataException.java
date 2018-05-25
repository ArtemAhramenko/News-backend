package com.news.app.exception.registration;

public class BadDataException extends RuntimeException{

    public BadDataException() {
        super("Incorrect Data.");
    }
}
