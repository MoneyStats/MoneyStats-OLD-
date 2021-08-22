package com.moneystats.MoneyStats.statement.DTO;

import java.lang.reflect.Type;

public class StatementResponse {
    private Type type;

    public StatementResponse(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static enum Type {
        STATEMENT_ADDED
    }
}
