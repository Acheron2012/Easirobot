package com.ictwsn.rob.voice;


import com.ictwsn.utils.weather.Heweather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/rob/voice")
public class VoiceAction {

    public static Logger logger = LoggerFactory.getLogger(VoiceAction.class);
    public static String voiceContent = "";

    public static void voiceAnalysis() {

        String text = "自贡天气预报";
        boolean flag = true;

        if (text.contains("天气")) {
            //接入和风天气
            String weather_regex = "(.*?)天气";
            Pattern pattern = Pattern.compile(weather_regex);
            Matcher matcher = pattern.matcher(text);
            //找到天气所在城市
            if (matcher.find()) {
                String city = matcher.group(1);
                //预报
                if (text.contains("预报")) {
                    voiceContent = Heweather.heweatherAPI(city,true);
                    //当日状况
                } else {
                    voiceContent = Heweather.heweatherAPI(city,false);

                }
            }
        } else if (text.contains("计算")) {
        } else if (text.contains("健康")) {
        } else if (text.contains("笑话")) {
        } else if (text.contains("故事")) {
        } else if (text.contains("轻音乐")) {
        } else {
            logger.info("未能识别语音内容，转入图灵接口");
        }
        //未能有效处理，转入图灵接口
        if (flag == false) {
            logger.info("未能做出语音行动，转入图灵接口");
        }
        System.out.println(voiceContent);
    }

    public static void main(String[] args) {
        voiceAnalysis();
    }

}
