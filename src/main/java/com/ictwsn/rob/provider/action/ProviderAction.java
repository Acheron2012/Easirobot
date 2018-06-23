package com.ictwsn.rob.provider.action;

import com.ictwsn.rob.provider.service.ProviderService;
import com.ictwsn.rob.provider.utils.ChildBean;
import com.ictwsn.rob.provider.utils.OldBean;
import com.ictwsn.rob.provider.utils.ProviderOperation;
import com.ictwsn.rob.voice.action.VoiceAction;
import com.ictwsn.utils.jpush.ChatbotPush;
import com.ictwsn.utils.tools.ActionTool;
import com.ictwsn.utils.tools.BaseDao;
import com.ictwsn.utils.tools.Tools;
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
import java.util.Date;

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
        String callString = "";
        if (callType == 1) {
            callString = "紧急呼叫";
        } else {
            callString = "综合服务";
        }
        //通过device_id获得用户电话号码
        String user_phone = providerService.getUserPhoneByDeviceId(device_id);
        //获取援通vcode
        String vcode = (String) this.redisTemplate.opsForValue().get("vcode");
        JSONObject resultObject = JSONObject.fromObject(ProviderOperation.call(vcode, user_phone, callType));
        if (resultObject.getInt("code") == 1000) {
            logger.info("呼叫成功:{}。", resultObject.toString());
            //返回文字到客户端
            ActionTool.responseToJSON(response, callString + resultObject.getString("msg") + "。请留意接收服务电话", VoiceAction.ACCESS);
        } else {
            logger.info("呼叫失败：{}。", resultObject.toString());
        }
        providerService.updateServiceStatusByDeviceId(device_id, Tools.getDayHourMinuteByISODate(new Date())
                + ":" + resultObject.getString("msg"));
        return null;
    }

    @RequestMapping("/weixin/update_child")
    public String updateChild(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "open_id", required = true) String open_id)
            throws IOException, ServletException {
        logger.info("收到子女open_id:{} 修改消息", open_id);
        //获取子女用户的信息
        ChildBean childBean = providerService.getChildByOpenId(open_id);
        if (childBean.getChild_phone() == 0) {
            logger.info("child_phone为空，open_id：{}", open_id);
            return null;
        }
        //去数据库查询此子女是否有对应的老人用户，即用户名不为空
        OldBean oldBean = providerService.getUserByOpenId(open_id);
        //获取援通vcode
        String vcode = (String) this.redisTemplate.opsForValue().get("vcode");
        if (oldBean.getUser_service_id() != 0) {
            //用户已注册成功，可以修改
            //更新操作
            String device_id = providerService.getDeviceIdByUserId(oldBean.getUser_id());
            JSONObject jsonResult = JSONObject.fromObject(ProviderOperation.childUpdate(vcode, device_id, oldBean, childBean));
            if (jsonResult.getInt("code") == 1000) {
                logger.info("修改成功");
            } else {
                logger.info("修改失败:{}", jsonResult.toString());
            }
            providerService.updateServiceStatusByUserId(oldBean.getUser_id(), Tools.getDayHourMinuteByISODate(new Date()) + ":" +
                    jsonResult.getString("msg"));
        } else {
            logger.info("无法修改，用户service_id为0,service_id:{}", oldBean.getUser_service_id());
        }
        return null;
    }

    @RequestMapping("/weixin/update_user")
    public String updateUser(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "user_id", required = true) Integer user_id)
            throws IOException, ServletException {
        String message = "";
//        用户每次修改用户信息时，先从麦邦查询是否存在此用户
        logger.info("收到用户id:{} 修改消息", user_id);
        //查询援通资料库是否有此用户
        //从数据库获取user_service_id，判断是否为空
        OldBean oldBean = providerService.getUserByUserId(user_id);
        //获取援通vcode
        String vcode = (String) this.redisTemplate.opsForValue().get("vcode");
        if (oldBean.getUser_service_id() != 0) {
            //更新操作
            String device_id = providerService.getDeviceIdByUserId(oldBean.getUser_id());
            JSONObject jsonResult = JSONObject.fromObject(ProviderOperation.userUpdate(vcode, device_id, oldBean));
            if (jsonResult.getInt("code") == 1000) {
                logger.info("修改成功");
                message = "修改成功";
            } else {
                logger.info("修改失败:{}", jsonResult.toString());
                message = "修改失败";
            }
            providerService.updateServiceStatusByUserId(user_id, Tools.getDayHourMinuteByISODate(new Date()) + ":" +
                    jsonResult.getString("msg"));
        } else {
            //援通无此人进行注册操作并将返回的user_service_id存入数据库
            JSONObject jsonResult = JSONObject.fromObject(ProviderOperation.userRegister(vcode, oldBean));
            if (jsonResult.getInt("code") == 1000) {
                //获取no(user_service_id)并存入数据库
                logger.info("注册结果：{}", jsonResult.toString());
                int result = providerService.updateUserServiceIdByUserId(user_id, Long.parseLong(jsonResult.getString("no")));
                logger.info("注册结果：{},user_service_id插入数据库状况：{}", jsonResult.toString(), result);
                message = jsonResult.toString();
            } else {
                logger.info("注册失败:{}", jsonResult.toString());
                message = jsonResult.toString();
            }
            providerService.updateServiceStatusByUserId(user_id, Tools.getDayHourMinuteByISODate(new Date()) + ":" +
                    jsonResult.getString("msg"));
        }
        String device_id = providerService.getDeviceIdByUserId(user_id);
        //修改用户生理指标需推送给机器人
        if (device_id != null) {
            //生理指标推送给机器人
            JSONObject physiologyObject = new JSONObject();
            physiologyObject.put("age", oldBean.getUser_age());
            physiologyObject.put("sex", oldBean.getUser_sex());
            physiologyObject.put("height", oldBean.getUser_height());
            physiologyObject.put("weight", oldBean.getUser_weight());
            String pushIdAndjsonObject = "8:" + physiologyObject.toString();
            logger.info("推送user_id:{}的生理指标为：{}", user_id, physiologyObject.toString());
            ChatbotPush.testSendPushWithPhysiology(device_id, pushIdAndjsonObject);
        }
        request.setAttribute("message", message);
        return "index2";
    }

}
