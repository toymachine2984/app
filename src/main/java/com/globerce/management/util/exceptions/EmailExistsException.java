package com.globerce.management.util.exceptions;

public class EmailExistsException extends Exception {

    private static final String DEFAULT_MSG = "There is an account with that email address: %s.";

    public EmailExistsException(String s) {
        super(String.format(DEFAULT_MSG, s));
    }

    public EmailExistsException(String msg, Throwable t) {
        super(msg, t);
    }

}
