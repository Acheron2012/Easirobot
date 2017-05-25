package com.ictwsn.rob.beacon.action;


import com.hankcs.hanlp.suggest.Suggester;
import com.ictwsn.rob.beacon.BeaconTool;
import com.ictwsn.rob.beacon.bean.BeaconBean;
import com.ictwsn.rob.beacon.service.BeaconService;
import com.ictwsn.utils.hanlp.HanLPUtil;
import com.ictwsn.utils.library.Library;
import com.ictwsn.utils.poetry.Poetry;
import com.ictwsn.utils.speech.TextToSpeech;
import com.ictwsn.utils.tools.Tools;
import com.ictwsn.utils.turing.TuringAPI;
import com.ictwsn.utils.weather.Suggestion;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/rob")
public class BeaconAction {

    public static Logger logger = LoggerFactory.getLogger(BeaconAction.class);

    public static final int CONVERSATION_TRUE = 1;

    @Resource
    BeaconService beaconService;

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
        //解决中文乱码
        if (message != null)
            message = new String(message.getBytes("ISO-8859-1"), "UTF-8");
        //语音结果
        String voiceResult = "";
        //音频流
        InputStream inputStream = null;
        //更新的场景值
        JSONObject updateScenario = new JSONObject();
        //进入情景对话，接入图灵
        if (conv == CONVERSATION_TRUE) {
            voiceResult = TuringAPI.turingRobot(message, uuid);
            inputStream = TextToSpeech.returnCombineSpeechInputStream(voiceResult);
        }
        //触发beacon语音
        else {
            //获取当前用户的beacon信息
            BeaconBean beaconBean = beaconService.getUserBeacon(1000);
            //获取scenario
            JSONObject jsonObject = JSONObject.fromObject(beaconBean.getScenario());
            //判断是否为新的一天
            Date lastDate = beaconBean.getLast_time();
            System.out.println(lastDate + "====" + new Date());
            if (Tools.isSameDate(lastDate, new Date())) {
                System.out.println("为同一天");
                //判断时间是否过了晚上5点
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
                if (Integer.valueOf(simpleDateFormat.format(new Date())) > 150000 && beaconBean.getHistory() == 0) {
                    //是否已经播报过了
                    System.out.println("进入历史上的今天");
                    //返回历史上的今天
                    simpleDateFormat = new SimpleDateFormat("MM月dd日");
                    System.out.println("format=" + simpleDateFormat.format(new Date()));
                    voiceResult += Tools.getDateSx() + "。" + "智能孝子为您讲述历史上的今天：";
                    voiceResult += Library.getOneDataByConditionField("history", "content", "date", simpleDateFormat.format(new Date()));
                    //更新历史上的今天
                    beaconBean.setHistory(1);
                } else {
                    int scenarioCount = jsonObject.getInt(scenario);
                    //第1次和第4次时采用场景beancon
                    if (scenarioCount == 0 || scenarioCount == 4) {
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
                    //根据场景和用户习惯推荐语音
                    else {
                        //判断场景次数，第二次依据特定场景推荐
                        if (scenarioCount == 1 || scenarioCount == 3 || scenarioCount == 5) {
                            //场景为厨房，推荐食疗
                            if ("kitchen".equals(scenario)) {
                                voiceResult += "智能孝子食疗推荐：";
                                voiceResult += Library.getThreeDatafield("food_therapy", "name", "description", "summary");
                            }
                            //场景为沙发，老人健康推荐
                            if ("sofa".equals(scenario)) {
                                String[] category = {"四季养生", "老人健康", "医疗护理", "健康饮食"};
                                voiceResult += "智能孝子养生保健：";
                                voiceResult += Library.getThreeDatafieldByCondition("old_health", "title",
                                        "category", "description", "category", category[new Random().nextInt(category.length)]);
                            }
                        }
                        //诗歌
                        else if (scenarioCount == 6 || scenarioCount == 11) {
                            voiceResult = "我要吟诗了：";
                            voiceResult = Poetry.getOnePoetry();
                        }
                        //名人名言
                        else if (scenarioCount == 9) {
                            voiceResult += "名人名" +
                                    "言：";
                            voiceResult = Library.getOneDataFromLibrary("quote", "content") +
                                    Library.getOneDataFromLibrary("quote", "person");
                        }
                        //名人名言
                        else if (scenarioCount == 9) {
                            //接入本地健康知识库
                            voiceResult += "我的健康知识：";
                            voiceResult = Library.getOneDataFromLibrary("health", "message");
                        } else {
                            //获取用户语音并分词
                            List<String> list = HanLPUtil.getUserVoiceKeywords(1000 + "");
                            String[] categories = {"老人保健", "老人生活", "老人饮食", "老人心理", "老人健身", "老人用品"};
                            Suggester suggester = new Suggester();
                            for (String ca : categories) {
                                suggester.addSentence(ca);
                            }
                            String wordSuggestion = suggester.suggest(list.get(0), 1).toString().replaceAll("\\[", "").replaceAll("\\]", "");
                            logger.info("语义推荐：" + wordSuggestion);       // 语义
                            voiceResult += "智能孝子为您推荐：";
                            try {
                                voiceResult += Library.getThreeDatafieldByCondition("old_health", "title",
                                        "summary", "content", "category", wordSuggestion);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println("==========" + list.toString());
                        }
                        //返回音频文件
                        inputStream = TextToSpeech.returnCombineSpeechInputStream(voiceResult);
                    }
                }
                System.out.println(jsonObject.toString());
                //更新数据库场景次数
                //更新beacon场景次数为0
                Set<Map.Entry> entrySet = jsonObject.entrySet();
                for (Map.Entry<String, Integer> entry : entrySet) {
                    if (entry.getKey().equals(scenario)) {
                        int number = Integer.valueOf(entry.getValue()) + 1;
                        updateScenario.put(entry.getKey(), number);
                    } else updateScenario.put(entry.getKey(), Integer.valueOf(entry.getValue()));
                }
            } else {
                System.out.println("不为同一天");
                //今日天气及穿衣建议
                voiceResult = Suggestion.getSuggestion("陈丽娟", "北京");
                //更新beacon场景次数为0
                Set<Map.Entry> entrySet = jsonObject.entrySet();
                for (Map.Entry<String, Integer> entry : entrySet) {
                    updateScenario.put(entry.getKey(), 0);
                }
                //更新历史上的今天
                beaconBean.setHistory(0);
                System.out.println(updateScenario);
            }
            //更新请求的场景次数
            beaconBean.setScenario(updateScenario.toString());
            //更新为当前时间
            beaconBean.setLast_time(new Date());
            beaconService.updateUserBeacon(beaconBean);
            logger.info(voiceResult);
            inputStream = TextToSpeech.returnCombineSpeechInputStream(voiceResult);
        }
        //返回给客户端
        Tools.downloadAudioFile(inputStream, response);
        return null;
    }

}
