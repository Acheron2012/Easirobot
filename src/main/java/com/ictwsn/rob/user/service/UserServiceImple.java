package com.ictwsn.rob.user.service;

import com.ictwsn.rob.user.bean.UserBean;
import com.ictwsn.rob.user.dao.UserDao;
import com.ictwsn.utils.tools.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018-06-20.
 */
@Service
public class UserServiceImple extends BaseDao implements UserService{
    public static Logger logger = LoggerFactory.getLogger(UserServiceImple.class);

    @Override
    public int updateUserInformation(UserBean userBean) {
        return this.sqlSessionTemplate.getMapper(UserDao.class).updateUserInformation(userBean);
    }
}
