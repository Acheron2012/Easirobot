package com.ictwsn.rob.distribute.action;

import com.ictwsn.rob.distribute.bean.DistributeBean;
import com.ictwsn.rob.distribute.service.DistributeService;
import com.ictwsn.rob.voice.action.VoiceAction;
import com.ictwsn.utils.tools.ActionTool;
import com.ictwsn.utils.tools.HttpUtil;
import com.ictwsn.utils.tools.Tools;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2018-04-20.
 */

@Controller
@RequestMapping("/rob")
public class DistributeAction {

    public static Logger logger = LoggerFactory.getLogger(DistributeAction.class);
    // Mac地址错误 502
    public static final int MAC_ERROR = 502;

    @Resource
    DistributeService distributeService;

    //欢迎界面（测试）
    @RequestMapping(value = "/dis", method = RequestMethod.POST)
    public void welcome(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(value = "MAC", required = true) String MAC) {

        String device_id = "";
        logger.info("MAC:{}", MAC);
        if(MAC==null||"".equals(MAC)) {
            logger.info("网络不好，MAC地址为空");
            ActionTool.responseToJSON(response, "mac error", MAC_ERROR);
            return;
        }
        /** 查找该MAC对应的device_id */
        device_id = distributeService.getDeviceIdByMac(MAC);
        /** 若未查找到MAC对应的device_id，则进行分配 */
        if(device_id==null) {
            /** 判断数据库是否有多余的device_id未使用 */
            int count = distributeService.getCountOfDeviceId();
            // 若没有可分配的dievice，申请一个
            if(count==0) {
                String application_url = "http://easirobot.zhongketianhe.com.cn/Weixin/apply_deviceid?mac="+MAC.replace(":","").replace("-","");
                HttpEntity httpEntity = HttpUtil.httpGet(application_url,null);
                try {
                    String returnResult = EntityUtils.toString(httpEntity);
                    JSONObject returnJsonObject = JSONObject.fromObject(returnResult);
                    String code = returnJsonObject.getString("code");
                    //申请成功
                    if(code.equals("ok")) {
                        device_id = returnJsonObject.getString("device_id");
                        DistributeBean distributeBean = new DistributeBean();
                        distributeBean.setApplication_time(new Date());
                        distributeBean.setDevice_id(device_id);
                        distributeBean.setIs_allocated(1);
                        distributeBean.setMac_address(MAC);
                        //插入数据库并更细申请状态
                        int status = distributeService.insertMacDevice(distributeBean);
                        logger.info("申请device_id并插入数据库条数：{}",status);
                        //申请失败
                    } else {
                        device_id = "";
                        logger.info("device_id微信申请失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //数据库已存在未分配的device_id，进行分配
            } else {
                device_id = distributeService.getDeviceIdAsc();
                int status = distributeService.updateMacDeviceStatusByDeviceId(device_id,MAC,new Date());
                logger.info("分配数据库已有device_id并更新条数：{}",status);
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", VoiceAction.ACCESS);
        jsonObject.put("MAC",MAC);
        jsonObject.put("device_id",device_id);
        Tools.responseToJSON(response, jsonObject.toString());
        return;
    }


}
