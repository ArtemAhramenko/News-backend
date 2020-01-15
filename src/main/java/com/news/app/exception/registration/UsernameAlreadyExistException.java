package com.news.app.exception.registration;

import java.util.function.Supplier;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 10.09.2017 16:54
 */
public class UsernameAlreadyExistException extends RuntimeException {

    public UsernameAlreadyExistException() {
        super("User with such username already exist.");
    }
}
