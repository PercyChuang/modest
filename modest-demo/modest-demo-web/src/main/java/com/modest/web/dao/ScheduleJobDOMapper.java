package com.modest.web.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.modest.web.pojo.ScheduleJobDO;


@Repository
public interface ScheduleJobDOMapper extends BaseMapper<ScheduleJobDO>{
	
	public List<ScheduleJobDO> queryAll();
	
	public List<ScheduleJobDO> queryAll(RowBounds rowBounds);
	
}