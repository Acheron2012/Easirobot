package com.ictwsn.rob.voice.action;


import com.ictwsn.utils.calendar.LunarCalendar;
import com.ictwsn.utils.encyclopedias.Sougou;
import com.ictwsn.utils.hanlp.HanLPUtil;
import com.ictwsn.utils.library.Library;
import com.ictwsn.utils.mail.EMail;
import com.ictwsn.utils.msg.Msg;
import com.ictwsn.utils.news.SinaNews;
import com.ictwsn.utils.recipe.Xiachufang;
import com.ictwsn.utils.speech.Speech;
import com.ictwsn.utils.speech.TextToSpeech;
import com.ictwsn.utils.tools.CheckChinese;
import com.ictwsn.utils.tools.HttpUtil;
import com.ictwsn.utils.tools.Tools;
import com.ictwsn.utils.turing.TuringAPI;
import com.ictwsn.utils.weather.Heweather;
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
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/rob")
public class VoiceAction {

    public static Logger logger = LoggerFactory.getLogger(VoiceAction.class);

    public static final String FILE_NAME = "ResultAudio.mp3";

//    public static void main(String[] args) {
//        voiceAnalysis();
//    }

    @RequestMapping(value = "/voice")
    public void voiceAnalysis(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "text", required = true) String text) throws ServletException, IOException {
        String user_id = "1000";
//    public static void voiceAnalysis() {
//        String user_id = request.getHeader("user_id");
//        String text = "讲个家庭故事";
        logger.info("语音文本:{}", text);
        if (CheckChinese.isMessyCode(text)) {
            text = new String(text.getBytes("ISO-8859-1"), "UTF-8");
            logger.info("编码后语音文本:{}", text);
        }
        //收集用户语音信息
        HanLPUtil.analysisUserVoice(user_id, text);


        //返回结果
        String voiceResult = "";
        boolean flag = true;
        //爬取搜狗百科
        String encyclopedias = "(.*?)((是谁|是哪个|是什么人)|(是什么|怎么样|如何|的作用|的用处|有什么用|(的)*功能|有什么好处)|(在哪儿|在哪里|位于哪里|在哪个地方))";
        //自定义上传
        String custom = "((我(喜欢的|收藏的|上传的|的))|播放我的)(.+)";
        //语音选择

        if (text.matches(custom)) {
            Pattern pattern = Pattern.compile(custom);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                //搜寻目录下的用户语音文件
                File customFiles = new File(Tools.getConfigureValue("custom.file"));
                File[] files = customFiles.listFiles();
                for (File f : files) {
                    if (!f.isDirectory()) {
                        //切割出目录下的文件名
                        String customeFileName = getCustomFileName(f.getName());
                        if (customeFileName != null) {
                            //当语音名与库中的上传文件名匹配时(使用拼音)
                            if (Tools.getPingYin(customeFileName).contains(
                                    Tools.getPingYin(matcher.group(4)))) {
                                //返回自定义语音文件
                                Tools.writeToClient(response, f.getPath());
                                return;
                            }
                        }
                    }
                }
                voiceResult = "未查找到您的音频文件";
            } else {
                voiceResult = "未查找到您的音频文件";
            }
            //菜谱
        } else if (text.matches("(.*?)(怎么做|食谱|菜谱|如何做|怎样做)") || text.matches("(怎么做|食谱|菜谱|如何做|怎样做)(.*?)")) {
            text = text.replaceAll("怎么做|食谱|菜谱|如何做|怎样做", "");
            voiceResult = Xiachufang.getRecipe(text);
            if (voiceResult == null) {
                logger.info("未识别菜名：{}",text);
                flag = false;
            }
            //旅游
        } else if (text.contains("旅游") || text.contains("攻略")) {
            text = text.replaceAll("(旅游)*(攻略)*", "");
            voiceResult = Sougou.crawlSougou(text);
            //爬取新浪新闻
        } else if (text.contains("新鲜事") || text.matches("(讲|说)*(个)*新闻")) {
            voiceResult = SinaNews.getCurrentNews();
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
            //接入图灵
            else flag = false;
        } else if (text.contains("健康")) {
            Pattern pattern = Pattern.compile("健康(咨询|常识|(小)*知识)*");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                //接入本地健康知识库
                voiceResult = Library.getOneDataFromLibrary("health", "message");
            } else flag = false;
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
            else flag = false;
            String datetime = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            //调用haoservice万年历
            voiceResult = LunarCalendar.lunar(datetime);
        } else if (text.contains("笑话")) {
            Pattern pattern = Pattern.compile("(讲|说)(个)*(.*?)笑话");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                //接入本地健康知识库
                voiceResult = Library.getOneDataByCondition("joke", "content",
                        matcher.group(3) + "笑话");
                if (voiceResult == null) flag = false;
            } else flag = false;
        } else if (text.contains("故事")) {
            Pattern pattern = Pattern.compile("(讲|说)(个)*(.*?)故事");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                //接入本地健康知识库
                voiceResult = Library.getOneDataByCondition("story", "content",
                        matcher.group(3) + "故事");
                if (voiceResult == null) flag = false;
            } else flag = false;
        } else if (text.contains("歇后语")) {
            Pattern pattern = Pattern.compile("(讲|说)*(个)*(.*?)歇后语");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                //接入本地知识库
                voiceResult = Library.getOneDataFromLibrary("xiehouyu", "content");
                if (voiceResult == null) flag = false;
            } else flag = false;
        } else if (text.contains("名言")) {
            Pattern pattern = Pattern.compile("(讲|说)*(个)*(.*?)(名人)*名言");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                voiceResult = Library.getThreeDataField("quote", "category", "content", "person");
                if (voiceResult == null) flag = false;
            } else flag = false;
            //古诗文
        } else if (text.matches("(讲|说|吟|读|背|诵)*(个|一篇|一章|一首)*(先秦|两汉|魏晋|南北朝|隋|唐|五|宋|金|元|明|清|古)*(代)*(诗|词|曲|文言文)")) {
            Pattern pattern = Pattern.compile("(讲|说|吟|读|背|诵)*(个|一篇|一章|一首)*(先秦|两汉|魏晋|南北朝|隋|唐|五|宋|金|元|明|清|古)*(代)*(诗|词|曲|文言文)");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String dynasty = matcher.group(3);
                String form = matcher.group(5);
                if (dynasty != null && form != null) {
                    if (text.contains("古"))
                        voiceResult = Library.getFiveDataByConditionField("poetry", "dynasty",
                                "form", "title", "author", "content", "form", form);
                    else {
                        voiceResult = Library.getFiveDataByTwoConditionsOfPattern("poetry", "dynasty",
                                "form", "title", "author", "content", "dynasty",
                                dynasty, "form", form);
                    }
                } else if (form != null)
                    voiceResult = Library.getFiveDataByConditionField("poetry", "dynasty",
                            "form", "title", "author", "content", "form", form);
                else flag = false;
            } else flag = false;

        } else if (text.contains("历史上的今天")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
            String date = simpleDateFormat.format(new Date());
            voiceResult = Library.getOneDataByConditionField("history", "content", "date", date.toString());
        } else if (text.contains("邮件")) {
            String email_message = "发(送)*邮件";
            Pattern pattern = Pattern.compile(email_message);
            Matcher matcher = pattern.matcher(text);
            //匹配到邮件消息
            if (matcher.find()) {
                String message = text.substring(text.indexOf("邮件") + 2, text.length());
                EMail.mail(message);
                voiceResult = "邮件已发送";
            } else flag = false;
        } else if (text.contains("短信")) {
            String email_message = "发(送)*短信";
            Pattern pattern = Pattern.compile(email_message);
            Matcher matcher = pattern.matcher(text);
            //匹配到邮件消息
            if (matcher.find()) {
                String message = text.substring(text.indexOf("短信") + 2, text.length());
                Msg.sendMessage("陈丽娟", message);
                voiceResult = "短信已发送";
            } else flag = false;
            //用户自定义上传内容
        } else if (text.contains("微信")) {
            String email_message = "发(送)*微信";
            Pattern pattern = Pattern.compile(email_message);
            Matcher matcher = pattern.matcher(text);
            //匹配到邮件消息
            if (matcher.find()) {
                String message = text.substring(text.indexOf("微信") + 2, text.length());
                logger.info("微信消息:{}",message);
                HttpUtil.httpGet("http://www.zyrill.top/Weixin/temptextcheck?text=" + message, null);
                voiceResult = "微信已发送";
            } else flag = false;
            //用户自定义上传内容
        } else if (text.matches(".{2}养生")) {
            //主要类别：运动、饮食、心理、医疗
//            text = text.substring(0, 2);
//            System.out.println("text="+text);
            if (text.matches("饮食|健康|保健|运动"))
                //在资料库中随机找寻一条数据
                voiceResult = Library.getOneDataByConditionField("regimen", "content", "category", text);
            else if (text.matches("心理") || text.matches("心里"))
                voiceResult = Library.getOneDataByConditionField("old_health", "content", "category", "老人心理");
            else if (text.matches("医疗"))
                voiceResult = Library.getOneDataByConditionField("regimen", "content", "category", "健康养生");
            else
                voiceResult = Library.getOneDataFromLibrary("regimen", "content");
            //声音切换
        } else if (text.matches("切换(成)*(.*?)(声|生|神|身|音)")) {
            String sex_voice = "切换(成)*(.*?)(声|生|神|身|音)";
            Pattern pattern = Pattern.compile(sex_voice);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String sex_sound = matcher.group(2);
                //发音人选择, 0为女声，1为男声，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女声
                if (sex_sound.equals("女")) Speech.PER = "0";
                if (sex_sound.equals("男")) Speech.PER = "1";
                if (sex_sound.equals("标准男")) Speech.PER = "3";
                if (sex_sound.equals("标准女")) Speech.PER = "4";
                voiceResult = "声音已切换";
            } else flag = false;
        }else if (text.matches(encyclopedias)) {
            if (!text.contains("你")) {
                Pattern pattern = Pattern.compile(encyclopedias);
                Matcher matcher = pattern.matcher(text);
                if (matcher.find() && matcher.group(1) != null || matcher.group(2) != null) {
                    voiceResult = Sougou.getEncyclopedia(matcher.group(1), matcher.group(2));
                } else flag = false;
            } else flag = false;
        }
        //取消梦境功能
       /* else if (text.contains("周公解梦") | text.contains("梦见") | text.contains("做梦")) {
            //接入haoService周公解梦
            text = text.replace("周公解梦", "").replaceAll("\\s", "");
            voiceResult = OliveDream.getDream(text);
        }*/
        else {
            logger.info("未能识别语音内容，转入图灵接口");
            voiceResult = TuringAPI.turingRobot(text, null);
        }
        //未能有效处理，转入图灵接口
        if (flag == false) {
            logger.info("未能做出语音行动，转入图灵接口");
            voiceResult = TuringAPI.turingRobot(text, null);
        }
        //空格处理
        voiceResult = voiceResult.replace(" ", "").replaceAll("\\s", "");
        logger.info("返回内容:{}", voiceResult);
//        TextToSpeech.downloadAudio("C:\\Users\\Administrator\\Desktop\\audio.mp3", voiceResult);
        //字符串长度判断，并调用百度TTS
        InputStream inputStream = null;
        //当长度大于1024字节时（一个中文占两个字节），需要将字节流合并
        if (voiceResult.length() > 512)
            inputStream = TextToSpeech.returnCombineSpeechInputStream(voiceResult);
        else inputStream = TextToSpeech.returnSpeechInputStream(voiceResult);
//        //返回客户端字符流
        Tools.downloadAudioFile(inputStream, response);
        System.out.println("处理完成");
        //返回处理
//        JSONObject resultObject = new JSONObject();
//        resultObject.put("error", 0);
//        resultObject.put("result", voiceResult);
//        resultObject.put("user_id", user_id);
//        responseToJSON(response, resultObject.toString());
    }

//    @RequestMapping("/music")
//    public String welcome(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        JSONObject resultObject = new JSONObject();
//        resultObject.put("error", 0);
//        resultObject.put("result", "音乐文件");
//        resultObject.put("user_id", 1000);
//        responseToJSON(response, resultObject.toString());
//        return null;
//    }


    // JSON封装返回数据
//    public void responseToJSON(HttpServletResponse response, String jsonContent) {
//        response.setContentType("text/html;charset=utf-8");
//        try {
//            PrintWriter out = response.getWriter();
//            out.write(jsonContent);
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //得到用户自定义文件名
    public static String getCustomFileName(String fileName) {
        Pattern pattern = Pattern.compile("\\d{4}-(.*?)-(.*?)\\.", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) return matcher.group(2);
        return null;
    }


}
