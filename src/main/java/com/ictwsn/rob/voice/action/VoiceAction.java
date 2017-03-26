package com.ictwsn.rob.voice.action;


import com.ictwsn.utils.calendar.LunarCalendar;
import com.ictwsn.utils.dream.OliveDream;
import com.ictwsn.utils.encyclopedias.Sougou;
import com.ictwsn.utils.health.HealthyNet;
import com.ictwsn.utils.mail.EMail;
import com.ictwsn.utils.msg.Msg;
import com.ictwsn.utils.speech.TextToSpeech;
import com.ictwsn.utils.turing.TuringAPI;
import com.ictwsn.utils.weather.Heweather;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/rob")
public class VoiceAction {

    public static Logger logger = LoggerFactory.getLogger(VoiceAction.class);

    public static final String FILE_NAME = "ResultAudio.mp3";

    @RequestMapping(value = "/voice")
    public void voiceAnalysis(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "text", required = true) String text) throws ServletException, IOException {

        String user_id = request.getHeader("user_id");
//        String text = "李宇春是谁";
        text = new String(text.getBytes("ISO-8859-1"), "UTF-8");
        System.out.println(text);
        String voiceResult = "";
        boolean flag = true;
        //爬取搜狗百科
        if (text.contains("是什么") || text.contains("怎么样") || text.contains("是谁") || text.contains("的作用")) {
            text = text.replaceAll("(是什么)|(怎么样)|(是谁)|(的作用)", "");
            voiceResult = Sougou.crawlSougou(text);
            //旅游
        } else if (text.contains("旅游") || text.contains("攻略")) {
            text = text.replaceAll("(旅游)*(攻略)*", "");
            System.out.println(text);
            voiceResult = Sougou.crawlSougou(text);
        } else if (text.contains("天气")) {
            //接入和风天气
            String weather_regex = "(今天)*(.*?)天气";
            Pattern pattern = Pattern.compile(weather_regex);
            Matcher matcher = pattern.matcher(text);
            //找到天气所在城市
            if (matcher.find()) {
                String city = matcher.group(2);
                if (null == city || "".equals(city)) flag = false;
                else {
                    //预报
                    if (text.contains("预报")) {
                        voiceResult = Heweather.heweatherAPI(city, true);
                        //当日状况
                    } else {
                        voiceResult = Heweather.heweatherAPI(city, false);
                    }
                }
            }
        } else if (text.contains("周公解梦") | text.contains("梦见") | text.contains("做梦")) {
            //接入haoService周公解梦
            text = text.replace("周公解梦", "").replaceAll("\\s", "");
            voiceResult = OliveDream.getDream(text);
        } else if (text.contains("健康")) {
            //接入本地健康知识库
            voiceResult = HealthyNet.getHealthyTips();
        } else if (text.contains("万年历") | text.contains("农历") | text.contains("日历") | text.contains("阳历")) {
            Calendar calendar = Calendar.getInstance();
            //日期判断
            if (text.contains("大前")) calendar.add(Calendar.DATE, -3);
            else if (text.contains("前")) calendar.add(Calendar.DATE, -2);
            else if (text.contains("昨")) calendar.add(Calendar.DATE, -1);
            else if (text.contains("今")) calendar.add(Calendar.DATE, 0);
            else if (text.contains("明")) calendar.add(Calendar.DATE, 1);
            else if (text.contains("后")) calendar.add(Calendar.DATE, 2);
            else if (text.contains("大后")) calendar.add(Calendar.DATE, 3);
            String datetime = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            //调用haoservice万年历
            voiceResult = LunarCalendar.lunar(datetime);
        } else if (text.contains("笑话")) {
            flag = false;
        } else if (text.contains("故事")) {
            flag = false;
        } else if (text.contains("邮件")) {
            String email_message = "发(送)*邮件";
            Pattern pattern = Pattern.compile(email_message);
            Matcher matcher = pattern.matcher(text);
            //匹配到邮件消息
            if (matcher.find()) {
                String message = text.substring(text.indexOf("邮件") + 2, text.length());
                EMail.mail(message);
                voiceResult = "邮件已发送";
            }
        } else if (text.contains("短信")) {
            String email_message = "发(送)*短信";
            Pattern pattern = Pattern.compile(email_message);
            Matcher matcher = pattern.matcher(text);
            //匹配到邮件消息
            if (matcher.find()) {
                String message = text.substring(text.indexOf("短信") + 2, text.length());
                Msg.sendMessage("陈丽娟", message);
                voiceResult = "短信已发送";
            }

        } else {
            logger.info("未能识别语音内容，转入图灵接口");
            voiceResult = TuringAPI.turingRobot(text);
        }
        //未能有效处理，转入图灵接口
        if (flag == false) {
            logger.info("未能做出语音行动，转入图灵接口");
            voiceResult = TuringAPI.turingRobot(text);
        }
        //空格处理
        voiceResult = voiceResult.replace(" ", "").replaceAll("\\s", "");
        //调用百度TTS
        InputStream inputStream = TextToSpeech.returnSpeechInputStream(voiceResult);
        //返回客户端字符流
        downloadFile(inputStream,response);
        logger.info("处理完成");
        //返回处理
//        JSONObject resultObject = new JSONObject();
//        resultObject.put("error", 0);
//        resultObject.put("result", voiceResult);
//        resultObject.put("user_id", user_id);
//        responseToJSON(response, resultObject.toString());
    }

    @RequestMapping("/music")
    public String welcome(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JSONObject resultObject = new JSONObject();
        resultObject.put("error", 0);
        resultObject.put("result", "音乐文件");
        resultObject.put("user_id", 1000);
        responseToJSON(response, resultObject.toString());
        return null;
    }

    //返回下载文件流
    public void downloadFile(InputStream inputStream, HttpServletResponse response) {

        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(inputStream);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // JSON封装返回数据
    public void responseToJSON(HttpServletResponse response, String jsonContent) {
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.write(jsonContent);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
