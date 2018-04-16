package com.modest.generate.appender;

import com.modest.generate.config.AppenderConfig;
import com.modest.generate.model.FileVo;

public interface BaseAppender {
	
	public abstract FileVo doInvoke(AppenderConfig appender);
}
