package com.ictwsn.test.service;

import org.springframework.stereotype.Service;

import com.ictwsn.test.bean.UserBean;
import com.ictwsn.test.dao.UserDao;
import com.ictwsn.utils.BaseDao;

@Service
public class UserServiceImpl extends BaseDao implements UserService{
	

	public UserBean selectUser(UserBean userBean) {
		UserDao userDao = this.sqlSessionTemplate.getMapper(UserDao.class);
		userBean = userDao.selectUser(userBean);
		return userBean;
	}

	public void insertUser(UserBean userBean) {
		UserDao userDao = this.sqlSessionTemplate.getMapper(UserDao.class);
		userDao.insertUser(userBean);
	}

}
