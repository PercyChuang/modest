package com.modest.mybatis.dialect;

public class MySql5Dialect extends Dialect {

    @Override
    public String getLimitString(final String sql, final int offset, final int limit) {
        return MySql5PageHepler.getLimitString(sql, offset, limit);
    }

    @Override
    public String getCountSql(final String sql) {
        return MySql5PageHepler.getCountString(sql);
    }

}
