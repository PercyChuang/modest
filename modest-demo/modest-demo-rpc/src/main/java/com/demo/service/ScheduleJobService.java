package com.demo.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.mapper.x.ScheduleJobDOMapper;
import com.demo.pojo.ScheduleJobDO;
import com.modest.mybatis.SqlSessionTemplate;
import com.modest.mybatis.util.CountHelper;

@Service("scheduleJobService")
public class ScheduleJobService {

	@Autowired
	private ScheduleJobDOMapper scheduleJobDOMapper;
	
	@Autowired
	private SqlSessionTemplate reuseSqlSession;
	
	/**
	 * 以非事务的方式来执行
	 * @return
	 */
	//@Transactional(propagation = Propagation.NEVER)
	public ScheduleJobDO test() {
		ScheduleJobDO result = scheduleJobDOMapper.getByPrimaryKey(9L);
		
		List<ScheduleJobDO> results = scheduleJobDOMapper.queryAll(new RowBounds(0,10));
		for (ScheduleJobDO scheduleJobDO : results) {
			System.out.println(scheduleJobDO.getCronExpression());
		}
		System.out.println("总条数："+CountHelper.getTotalRow());
		
		//采用执行器的方式来查询数据
		List<ScheduleJobDO> useSession = reuseSqlSession.selectList("mymapper.queryAll", null, new RowBounds(0,10));
		for (ScheduleJobDO scheduleJobDO : useSession) {
			System.out.println("use Session:" + scheduleJobDO.getCronExpression());
		}
		return result;
	}
	
}
