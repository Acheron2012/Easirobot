package com.ictwsn.utils.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Job {
	
	public static Logger logger = LoggerFactory.getLogger(Job.class);
	
	public void startJob()
	{
		logger.info("定时任务开始");
	}
}
