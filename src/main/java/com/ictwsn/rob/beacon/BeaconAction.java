package com.ictwsn.rob.beacon;


import com.ictwsn.utils.speech.Speech;
import com.ictwsn.utils.speech.TextToSpeech;
import com.ictwsn.utils.tools.Tools;
import com.ictwsn.utils.turing.TuringAPI;
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
import java.io.InputStream;

@Controller
@RequestMapping("/rob")
public class BeaconAction {

    public static Logger logger = LoggerFactory.getLogger(BeaconAction.class);

    public static final int CONVERSATION_TRUE = 1;

    /**
     * @param request
     * @param response
     * @param scenario 情景触发 字符串
     * @param uuid     用户id  字符串
     * @param conv     是否为情景对话，0为否，1为是，默认为0 整型
     * @param message  情景对话的语音内容 字符串
     * @return
     * @throws IOException
     * @throws ServletException
     */
    //消息触发
    @RequestMapping(value = "/beacon", method = RequestMethod.GET)
    public String beaconScenario(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "scenario", required = true) String scenario,
                                 @RequestParam(value = "uuid", required = true) String uuid,
                                 @RequestParam(value = "conv", required = true) int conv,
                                 @RequestParam(value = "message", required = false) String message) throws IOException, ServletException {
        //切换声音
        Speech.PER = "3";
        //解决中文乱码
        message = new String(message.getBytes("ISO-8859-1"), "UTF-8");
        //语音结果
        String voiceResult = "";
        //音频流
        InputStream inputStream = null;
        //进入情景对话，接入图灵
        if (conv == CONVERSATION_TRUE) {
            voiceResult = TuringAPI.turingRobot(message, uuid);
            inputStream = TextToSpeech.returnCombineSpeechInputStream(voiceResult);
        }
        //触发beacon语音
        else {
            String settings_path = BeaconAction.class.getClassLoader().getResource("").getPath() + "/" + "settings.xml";
            //获取解析后的合成语音
            String audioPath = BeaconTool.parsingBeaconXML(settings_path, scenario);
            System.out.println(audioPath);
            try {
                //读取音频文件
                inputStream = BeaconTool.readInputStram(audioPath);
//                BeaconTool.downloadFile(request, response, audioPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //返回给客户端
        Tools.downloadAudioFile(inputStream,response);
        return null;
    }



}
