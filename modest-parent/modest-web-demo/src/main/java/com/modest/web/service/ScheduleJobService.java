package com.modest.web.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.modest.mybatis.SqlSessionTemplate;
import com.modest.mybatis.util.CountHelper;
import com.modest.web.dao.ScheduleJobDOMapper;
import com.modest.web.pojo.ScheduleJobDO;

@Service("scheduleJobService")
public class ScheduleJobService {

	@Autowired
	private ScheduleJobDOMapper scheduleJobDOMapper;
	
	@Autowired
	private SqlSessionTemplate reuseSqlSession;
	
	@Value("${jdbc.write.pool.size.max}")
	private String pro;
	
	@Autowired
	private ScheduleJobDO bb;
	
	@Autowired
	private ScheduleJobDO cc;
	
	/**
	 * 以非事务的方式来执行
	 * @return
	 */
	//@Transactional(propagation = Propagation.NEVER)
	public ScheduleJobDO test() {
		System.out.println(bb);
		System.out.println(cc);
		System.out.println("service里面能否拿到配置文件："+pro);
		
		ScheduleJobDO result = scheduleJobDOMapper.getByPrimaryKey(9L);
		
		List<ScheduleJobDO> results = scheduleJobDOMapper.queryAll(new RowBounds(0,10));
		for (ScheduleJobDO scheduleJobDO : results) {
			System.out.println(scheduleJobDO.getCronExpression());
		}
		System.out.println("总条数："+CountHelper.getTotalRow());
		
		//采用执行器的方式来查询数据
		List<ScheduleJobDO> useSession = reuseSqlSession.selectList("mytemple.queryAll", null, new RowBounds(0,10));
		for (ScheduleJobDO scheduleJobDO : useSession) {
			System.out.println("use Session:" + scheduleJobDO.getCronExpression());
		}
		return result;
	}
	
}
