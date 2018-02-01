package com.modest.mybatis.dialect;

/**
 * oracleDialect的支持。
 * @author Edmont Chuang
 */
public class OracleDialect extends Dialect {
    @Override
    public String getLimitString(final String sql, final int offset, final int limit) {
        String _sql = sql.trim();
        boolean isForUpdate = false;
        if (_sql.toLowerCase().endsWith(" for update")) {
            _sql = _sql.substring(0, _sql.length() - 11);
            isForUpdate = true;
        }
        StringBuffer pagingSelect = new StringBuffer(_sql.length() + 100);
        pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        pagingSelect.append(_sql);
        pagingSelect.append(" ) row_ ) where rownum_ > " + offset + " and rownum_ <= " + (offset + limit));
        if (isForUpdate) {
            pagingSelect.append(" for update");
        }
        return pagingSelect.toString();
    }

    @Override
    public String getCountSql(final String sql) {
        throw new UnsupportedOperationException();
    }
}