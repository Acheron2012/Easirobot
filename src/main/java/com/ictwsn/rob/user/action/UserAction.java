package com.ictwsn.rob.user.action;

import com.ictwsn.rob.user.bean.UserBean;
import com.ictwsn.rob.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018-06-20.
 */
@Controller
@RequestMapping("/user")
public class UserAction {
    public static Logger logger = LoggerFactory.getLogger(UserAction.class);

    @Resource
    UserService userService;

    @RequestMapping(value = "/update",method= RequestMethod.POST)
    public String userUpdate(HttpServletRequest request, HttpServletResponse response,
                            UserBean userBean) throws ServletException, IOException {
        //插入数据库
        int result = userService.updateUserInformation(userBean);
        logger.info("页面更新老人信息执行结果：{}",result);
        if(userBean.getUser_id()==0) {
            logger.info("id号为空,返回空");
            return null;
        }
        return "redirect:/weixin/update_user?user_id="+userBean.getUser_id();
    }

}
