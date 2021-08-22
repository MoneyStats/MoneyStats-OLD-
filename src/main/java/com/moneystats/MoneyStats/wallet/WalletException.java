package com.moneystats.MoneyStats.wallet;

import java.lang.reflect.Type;
import java.security.PrivilegedActionException;

public class WalletException extends Exception{
    private Type type;

    public WalletException(Type type) {
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
    public WalletException(String message, Type type) {
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
        USER_NOT_FOUND,
        WALLETDTO_NULL,
        CATEGORY_NOT_FOUND,
        INVALID_WALLET_DTO,
        WALLET_NOT_FOUND,
        STATEMENT_NOT_FOUND
    }
}
