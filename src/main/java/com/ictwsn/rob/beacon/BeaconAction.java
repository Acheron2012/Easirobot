package com.ictwsn.rob.beacon;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/rob/beacon")
public class BeaconAction {

    public static Logger logger = LoggerFactory.getLogger(BeaconAction.class);

    //消息触发
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String welcome(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "scenario", required = true) String scenario) throws IOException, ServletException {
        String settings_path = this.getClass().getClassLoader().getResource("com/ictwsn/settings.xml").getPath();
        //获取解析后的合成语音
        String audioPath = BeaconTool.parsingBeaconXML(settings_path,scenario);
        try {
            //下载音频文件
            BeaconTool.downloadFile(request, response, audioPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
