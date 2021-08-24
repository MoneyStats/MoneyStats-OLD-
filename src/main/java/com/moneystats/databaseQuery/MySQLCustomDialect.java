package com.moneystats.databaseQuery;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MySQLCustomDialect extends MySQL5Dialect {
    public MySQLCustomDialect() {
        super();
        registerFunction("group_concat",
                new StandardSQLFunction("group_concat",
                        StandardBasicTypes.STRING));
    }
}