package com.modest.mybatis.dialect;

public abstract class Dialect {
    public abstract String getLimitString(String sql, int offset, int limit);

    public abstract String getCountSql(String sql);

    public static enum Type {
        MYSQL, ORACLE;
    }
}
