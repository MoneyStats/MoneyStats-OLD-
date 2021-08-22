package com.moneystats.MoneyStats.statement;

import java.lang.reflect.Type;

public class StatementException extends Exception {
    private Type type;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public StatementException(Type type) {
        super(type.toString());
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
    public StatementException(String message, Type type) {
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
        INVALID_STATEMENT_DTO,
        USER_NOT_FOUND,
        WALLET_NOT_FOUND,
        STATEMENT_NOT_FOUND
    }
}
