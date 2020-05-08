package com.globerce.management.util.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {

    private static final String DEFAULT_MSG = "User not found.";


    public UserNotFoundException() {
        super(DEFAULT_MSG);
    }

    public UserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }


}
