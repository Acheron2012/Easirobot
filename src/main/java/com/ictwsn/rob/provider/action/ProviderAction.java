package com.ictwsn.rob.provider.action;

import com.ictwsn.rob.provider.service.ProviderService;
import com.ictwsn.rob.provider.utils.OldBean;
import com.ictwsn.rob.provider.utils.ProviderOperation;
import com.ictwsn.utils.tools.BaseDao;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018-05-24.
 */
@Controller
public class ProviderAction extends BaseDao {

    public static Logger logger = LoggerFactory.getLogger(ProviderAction.class);

    @Resource
    ProviderService providerService;

    /**
     * @param request
     * @param response
     * @param device_id 设备id号
     * @param callType  1表示:紧急呼叫 2表示: 综合服务
     * @return
     * @throws IOException
     * @throws ServletException
     */

//    http://easirobot.zhongketianhe.com.cn:8080/Easirobot/rob/call?device_id=gh_655b593ac7b9_9897297a665e1d3b&call_type=1
    @RequestMapping("/rob/call")
    public String downloadWechatAudio(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(value = "device_id", required = true) String device_id,
                                      @RequestParam(value = "call_type", required = true) Integer callType)

            throws IOException, ServletException {
        logger.info("收到呼叫来自device_id呼叫：{}，呼叫类型：{}", device_id, callType);
        //通过device_id获得用户电话号码
        String user_phone = providerService.getUserPhoneByDeviceId(device_id);
        //获取援通vcode
        String vcode = (String) this.redisTemplate.opsForValue().get("vcode");
        JSONObject resultObject = JSONObject.fromObject(ProviderOperation.call(vcode,user_phone,callType));
        if(resultObject.getInt("code") == 1000) {
            logger.info("呼叫成功:{}",resultObject.toString());
        } else {
            logger.info("呼叫失败：{}",resultObject.toString());
        }
        return null;
    }

    @RequestMapping("/weixin/update_user")
    public String updateUser(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "user_id", required = true) Integer user_id)
            throws IOException, ServletException {
//        用户每次修改用户信息时，先从麦邦查询是否存在此用户
        logger.info("收到用户id:{} 修改消息", user_id);
        //查询援通资料库是否有此用户
        //从数据库获取user_service_id，判断是否为空
        OldBean oldBean = providerService.getUserByUserId(user_id);
        //获取援通vcode
        String vcode = (String) this.redisTemplate.opsForValue().get("vcode");
        if (oldBean.getUser_service_id() != 0) {
            //更新操作
            JSONObject jsonResult = JSONObject.fromObject(ProviderOperation.userUpdate(vcode, oldBean));
            if (jsonResult.getInt("code") == 1000) {
                logger.info("修改成功");
            } else {
                logger.info("修改失败:{}", jsonResult.toString());
            }
        } else {
            //援通无此人进行注册操作并将返回的user_service_id存入数据库
            JSONObject jsonResult = JSONObject.fromObject(ProviderOperation.userRegister(vcode, oldBean));
            if (jsonResult.getInt("code") == 1000) {
                //获取no(user_service_id)并存入数据库
                int result = providerService.updateUserServiceIdByUserId(user_id, Long.parseLong(jsonResult.getString("no")));
                logger.info("注册结果：{}", result);
            } else {
                logger.info("注册失败:{}", jsonResult.toString());
            }
        }
        return null;
    }

}
