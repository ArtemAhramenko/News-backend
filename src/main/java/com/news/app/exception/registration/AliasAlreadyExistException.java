package com.news.app.exception.registration;

public class AliasAlreadyExistException extends RuntimeException{

    public AliasAlreadyExistException() {
        super("User with such alias already exist.");
    }

}
