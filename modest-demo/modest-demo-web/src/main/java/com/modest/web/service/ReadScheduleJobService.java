package com.modest.web.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.modest.datasource.annotation.DataSource;
import com.modest.mybatis.SqlSessionTemplate;
import com.modest.mybatis.util.CountHelper;
import com.modest.web.dao.ScheduleJobDOMapper;
import com.modest.web.pojo.ScheduleJobDO;

@DataSource("read")
@Service("readScheduleJobService")
public class ReadScheduleJobService {

	@Autowired
	private ScheduleJobDOMapper scheduleJobDOMapper;
	
	@Autowired
	private SqlSessionTemplate reuseSqlSession;
	
	
	@Value("${jdbc.write.pool.size.max}")
	private String pro;
	
	/**
	 * 以非事务的方式来执行
	 * @return
	 */
	//@Transactional(propagation = Propagation.NEVER)
	@SuppressWarnings("unused")
	public ScheduleJobDO test() {
		System.out.println("service里面能否拿到配置文件："+pro);
		ScheduleJobDO result = scheduleJobDOMapper.getByPrimaryKey(9L);
		List<ScheduleJobDO> results = scheduleJobDOMapper.queryAll(new RowBounds(0,10));
		System.out.println("总条数："+CountHelper.getTotalRow());
		
		return result;
	}
	
}
