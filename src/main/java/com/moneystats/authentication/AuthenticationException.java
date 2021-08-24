package com.moneystats.authentication;

import java.lang.reflect.Type;

public class AuthenticationException extends Exception{
    private Type type;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public AuthenticationException(Type type) {
        this.type = type;
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AuthenticationException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    public static enum Type {
        DATABASE_ERROR,
        UNAUTHORIZED,
        NOT_ALLOWED,
        INVALID_AUTH_CREDENTIAL_DTO,
        INVALID_AUTH_INPUT_DTO,
        WRONG_CREDENTIAL,
        INVALID_TOKEN_DTO,
        AUTH_CREDENTIAL_DTO_NOT_FOUND,
        TOKEN_REQUIRED
    }
}
