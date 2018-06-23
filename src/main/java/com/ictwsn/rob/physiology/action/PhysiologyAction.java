package com.ictwsn.rob.physiology.action;


import com.ictwsn.rob.physiology.bean.PhysiologyBean;
import com.ictwsn.rob.physiology.service.PhysiologyService;
import com.ictwsn.rob.provider.service.ProviderService;
import com.ictwsn.rob.provider.utils.PhyBean;
import com.ictwsn.rob.provider.utils.ProviderOperation;
import com.ictwsn.utils.tools.Tools;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PhysiologyAction {

    public static Logger logger = LoggerFactory.getLogger(PhysiologyAction.class);

    @Resource
    PhysiologyService physiologyService;
    @Resource
    ProviderService providerService;

    //欢迎界面（测试）
    @RequestMapping(value = "/rob/get_phy", method = RequestMethod.GET)
    public void getPhysiology(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "device_id", required = true) String device_id) {
        PhysiologyBean physiologyBean = physiologyService.getPhysiologyByDeviceId(device_id);
        JSONObject jsonObject = JSONObject.fromObject(physiologyBean);
        if (physiologyBean != null) {
            logger.info("获取的生理指标：{}", jsonObject.toString());
            Tools.responseToJSON(response, jsonObject.toString());
        }
        return;
    }

    //欢迎界面（测试）
    @RequestMapping(value = "/weixin/push_phy_yuantong")
    public void pushPhysiologyToYuantong(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(value = "user_id", required = true) int user_id,
                                         @RequestParam(value = "phy_id", required = true) int phy_id) {
        String id = providerService.getUserServiceIdByUserId(user_id);
        if (id != null || "".equals(id)) {
            long user_service_id = Long.parseLong(id);
            PhyBean phyBean = physiologyService.getPhyBeanByPhyId(phy_id);
            String device_id = providerService.getDeviceIdByUserId(user_id);
            String result = ProviderOperation.pushPhysiology(user_service_id, device_id, phyBean);
            if (result == null) {
                logger.info("没有返回内容");
            }
            if ("".equals(result) || " ".equals(result)) {
                logger.info("返回了，但没有内容");
            }
            logger.info("推送援通生理指标结果：{}", result);
        } else {
            logger.info("援通服务ID不存在，无法推送");
        }
//        JSONObject jsonObject = JSONObject.fromObject(physiologyBean);
//        if (physiologyBean != null) {
//            logger.info("获取的生理指标：{}", jsonObject.toString());
//            Tools.responseToJSON(response, jsonObject.toString());
//        }
//        return;
    }


}
