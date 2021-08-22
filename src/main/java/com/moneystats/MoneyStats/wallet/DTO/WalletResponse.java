package com.moneystats.MoneyStats.wallet.DTO;

import java.lang.reflect.Type;

public class WalletResponse {
    private Type type;

    public WalletResponse(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static enum Type {
        WALLET_DELETED,
        WALLET_ADDED
    }
}
