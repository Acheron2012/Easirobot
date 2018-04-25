package com.ictwsn.rob.physiology.action;


import com.ictwsn.rob.physiology.bean.PhysiologyBean;
import com.ictwsn.rob.physiology.service.PhysiologyService;
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
@RequestMapping("/rob")
public class PhysiologyAction {

    public static Logger logger = LoggerFactory.getLogger(PhysiologyAction.class);

    @Resource
    PhysiologyService physiologyService;

    //欢迎界面（测试）
    @RequestMapping(value = "/get_phy", method = RequestMethod.GET)
    public void getPhysiology(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "device_id", required = true) String device_id) {
        PhysiologyBean physiologyBean = physiologyService.getPhysiologyByDeviceId(device_id);
        JSONObject jsonObject = JSONObject.fromObject(physiologyBean);
        if(physiologyBean!=null) {
            logger.info("获取的生理指标：{}",jsonObject.toString());
            Tools.responseToJSON(response, jsonObject.toString());
        }
        return;
    }



}
