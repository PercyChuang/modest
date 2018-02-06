package com.demo.mapper.x;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.demo.mapper.BaseMapper;
import com.demo.pojo.ScheduleJobDO;


@Repository("/twomapper")
public interface ScheduleJobDOMapper extends BaseMapper<ScheduleJobDO>{
	
	public List<ScheduleJobDO> queryAll();
	
	public List<ScheduleJobDO> queryAll(RowBounds rowBounds);
	
}