package com.news.app.exception.registration;

public class UnconfirmedUserException extends RuntimeException {

    public UnconfirmedUserException() {
        super("User not confirmed.");
    }
}
