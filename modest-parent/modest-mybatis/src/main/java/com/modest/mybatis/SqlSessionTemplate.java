package com.modest.mybatis;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.PersistenceExceptionTranslator;

/**
 * 无论多少个DAO都使用同一个sqlSession
 * close时输出日志便于框架问题 定位。
 * @author 庄濮向 Edmond Chuang
 */
public class SqlSessionTemplate extends org.mybatis.spring.SqlSessionTemplate {
    private static final Logger logger = LoggerFactory.getLogger(SqlSessionTemplate.class);

    public SqlSessionTemplate(final SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    public SqlSessionTemplate(final SqlSessionFactory sqlSessionFactory, final ExecutorType executorType) {
        super(sqlSessionFactory, executorType);
    }

    public SqlSessionTemplate(final SqlSessionFactory sqlSessionFactory, final ExecutorType executorType,
            final PersistenceExceptionTranslator exceptionTranslator) {
        super(sqlSessionFactory, executorType, exceptionTranslator);
    }

    /* (non-Javadoc)
     * 和spring整合的时候，默认是不允许手动关闭的。
     * 这里小改一下，改为提示不允许但不抛出异常。
     * @see org.mybatis.spring.SqlSessionTemplate#close()
     */
    @Override
    public void close() {
        if (logger.isDebugEnabled()) {
            logger.debug("Manual close is not allowed over a Spring managed SqlSession");
        }
    }

}
