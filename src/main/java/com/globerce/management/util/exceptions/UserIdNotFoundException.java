package com.globerce.management.util.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserIdNotFoundException extends AuthenticationException {


    private static final String DEFAULT_MSG = "User with id: %d not found.";

    public UserIdNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserIdNotFoundException(Long id) {
        super(String.format(DEFAULT_MSG, id));
    }
}
