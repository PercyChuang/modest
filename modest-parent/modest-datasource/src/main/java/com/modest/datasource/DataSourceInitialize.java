package com.modest.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

class DataSourceInitialize extends JdbcDaoSupport implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceInitialize.class);

    @Override
    protected void initDao() throws Exception {
        super.initDao();
        Thread thread = new Thread() {

            @Override
            public void run() {
                init(false);
                init(true);
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void init(final boolean readOnly) {
        try {
            Connection connection = getConnection();
            if (logger.isTraceEnabled()) {
                logger.trace("C3P0 Connection: {}", connection);
            }
            boolean origReadOnly = connection.isReadOnly();
            connection.setReadOnly(readOnly);
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(1);");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (logger.isTraceEnabled()) {
                    logger.trace("C3P0 Data count: {}", count);
                }
            }
            resultSet.close();
            statement.close();
            connection.setReadOnly(origReadOnly);
            releaseConnection(connection);
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
    }

}
