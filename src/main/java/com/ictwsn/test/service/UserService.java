package com.ictwsn.test.service;

import com.ictwsn.test.bean.UserBean;


public interface UserService {
	public UserBean selectUser(UserBean userBean);
	public void insertUser(UserBean userBean);
}
