package com.modest.mybatis.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 庄濮向 Edmond Chuang
 */
public final class CountHelper {

    private static Logger logger = LoggerFactory.getLogger(CountHelper.class);
    private static final ThreadLocal<Integer> totalRowLocals = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void getCount(final String sql, final StatementHandler statementHandler,
            final Configuration configuration, final List<ParameterMapping> parameterMappings,
            final Connection connection) throws Exception {
        Object parameterObject = statementHandler.getParameterHandler().getParameterObject();
        if (logger.isDebugEnabled()) {
            logger.debug("Total count sql [{}] parameters [{}] ", sql, parameterObject);
        }
        BoundSql countBoundSql = new BoundSql(configuration, sql, parameterMappings, parameterObject);
        MappedStatement ms = (MappedStatement) ReflectionUtils.getFieldValue(statementHandler, "mappedStatement");
        DefaultParameterHandler handler = new DefaultParameterHandler(ms, parameterObject, countBoundSql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            handler.setParameters(ps);
            rs = ps.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Total count [{}], sql [{}]", count, sql);
            }
            totalRowLocals.set(count);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception igr) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception igr) {
                }
            }
        }
    }

    public static int getTotalRow() {
        Integer totalRow = totalRowLocals.get();
        totalRowLocals.remove();
        return totalRow;
    }
}