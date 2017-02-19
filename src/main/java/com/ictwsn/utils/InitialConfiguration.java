package com.ictwsn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class InitialConfiguration implements ApplicationListener<ContextRefreshedEvent> {
	// ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用
	
	public static Logger logger = LoggerFactory.getLogger(InitialConfiguration.class);
	
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		logger.info("spring容易初始化完毕================================================");		
	}

}
