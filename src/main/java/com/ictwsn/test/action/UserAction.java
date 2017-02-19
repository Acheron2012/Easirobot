package com.ictwsn.test.action;


import com.ictwsn.test.bean.UserBean;
import com.ictwsn.test.service.UserService;
import com.ictwsn.utils.redis.RedisOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserAction {
	
	public static Logger logger = LoggerFactory.getLogger(UserAction.class);
	
	@Resource UserService userService;
	@Resource RedisOperation redisOperation;
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String createUser(HttpServletRequest request,HttpServletResponse response,UserBean userBean)
	{
		userService.insertUser(userBean);
		return null;
	}
	
}
