package com.ictwsn.rob.advertisement;

import com.ictwsn.utils.jpush.ChatbotPush;
import com.ictwsn.utils.tools.Tools;
import com.ictwsn.utils.tools.URLtoUTF8;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/rob")
public class Advertisement {

    public static Logger logger = LoggerFactory.getLogger(Advertisement.class);

    @RequestMapping("/ad")
    public String getAdvertisement(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "ad_id", required = true) String ad_id)
            throws IOException, ServletException {

        //搜寻广告语音文件
        File adverFile = new File(Tools.getConfigureValue("advertisement.file") + "/" + ad_id + ".mp3");
        //返回广告语音文件
        Tools.writeToClient(response, adverFile.getPath());
        return null;
    }

    @RequestMapping("/push")
    public void pushAdvertisement(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "device_id", required = true) String device_id,
                                  @RequestParam(value = "request_url", required = true) String request_url)
            throws IOException, ServletException {
        request_url = URLtoUTF8.toUtf8String(request_url);
        boolean flag = ChatbotPush.testSendPushWithCustomConfig(device_id, request_url);
        JSONObject jsonObject = new JSONObject();
        if (flag == true) {
            jsonObject.put("status", "OK");
            System.out.println("推送消息发送成功");
        } else {
            jsonObject.put("status","failed");
            System.out.println("推送消息发送失败");
        }
        jsonObject.put("device_id", device_id);
        jsonObject.put("request_url", request_url);
        Tools.responseToJSON(response, jsonObject.toString());
        return;
    }

}
