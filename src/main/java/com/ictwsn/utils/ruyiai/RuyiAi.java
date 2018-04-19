package com.ictwsn.utils.ruyiai;

import com.ictwsn.utils.tools.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017-09-29.
 */
public class RuyiAi {

    public static Logger logger = LoggerFactory.getLogger(RuyiAi.class);
    public static String URL = "http://api.ruyi.ai/v1/message";
    public static String APP_KEY = "c8ae0a55-7776-430e-975c-9908ca7ff731";


    public static String ruyiAi(String question, String deviceID) {
        String result = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("q", question);
        jsonObject.put("app_key", APP_KEY);
        jsonObject.put("user_id", deviceID);
//        boolean resestSession;
//        if (Tools.getNumberByHighProbability() == 1) {
//            resestSession = false;
//        } else {
//            resestSession = true;
//        }
        //不忘记短期对话记忆
        jsonObject.put("reset_session", false);

        HttpEntity httpEntity = HttpUtil.httpPost(URL, null, jsonObject.toString());
        try {
            String content = EntityUtils.toString(httpEntity, "UTF-8");
            System.out.println(content);
            try {
                //获取
                JSONObject jsonContent = JSONObject.fromObject(content);
                if (jsonContent.getInt("code") != 0) {
                    return "修改ID";
                }
                JSONObject jsonResult = jsonContent.getJSONObject("result");
                JSONObject jsonIntent_0 = jsonResult.getJSONArray("intents").getJSONObject(0);
                JSONObject json_result = jsonIntent_0.getJSONObject("result");
                //判断是否获取的为音频文件
                if (json_result.containsKey("track_list")) {
                    logger.info("获取track_list");
                    JSONArray trackListArray = json_result.getJSONArray("track_list");
                    //随机获得其中一个链接
                    JSONObject mediaObject = trackListArray.getJSONObject(new Random().nextInt(trackListArray.size()));
                    result = mediaObject.getString("media_url");
                } else if (json_result.containsKey("response") && json_result.getJSONObject("response").containsKey("data")) {
                    logger.info("获取response->data");
                    //判断是否含有data字段
                    JSONArray dataArray = json_result.getJSONObject("response").getJSONArray("data");
                    //随机获得其中一个链接
                    int number = new Random().nextInt(dataArray.size());
                    JSONObject membersObject = dataArray.getJSONObject(number).getJSONObject("members");
                    result = membersObject.getString("url");
                } else if (json_result.containsKey("mp3_audio_url")) {
                    logger.info("获取mp3_audio_url");
                    result = json_result.getString("mp3_audio_url");
                } else if (jsonIntent_0.containsKey("outputs")) {

                    JSONArray objectArray = jsonIntent_0.getJSONArray("outputs");
                    Iterator<JSONObject> it = objectArray.iterator();
                    //放到HashMap中作映射
                    Map<String, JSONObject> outputMap = new HashMap<String, JSONObject>();
                    while (it.hasNext()) {
                        JSONObject tempOutput = it.next();
                        outputMap.put(tempOutput.getString("type"), tempOutput);
                    }
                    //自定义优先级
                    if (outputMap.get("voice") != null) {
                        logger.info("获取outputs->voice");
                        result = outputMap.get("voice").getJSONObject("property").getString("voice_url");
                    } else if (outputMap.get("wechat.music") != null) {
                        logger.info("获取outputs->wechat.music");
                        result = outputMap.get("wechat.music").getJSONObject("property").getString("music_url");
                    } else if (outputMap.get("dialog") != null) {
                        logger.info("获取outputs->dialog");
                        result = outputMap.get("dialog").getJSONObject("property").getString("text");
                    } else if (outputMap.get("wechat.text") != null) {
                        logger.info("获取outputs->wechat.text");
                        result = outputMap.get("dialog").getJSONObject("property").getString("text");
                    } else {
                        logger.info("未在outputs中获取");
                        return null;
                    }


//                    Iterator<Object> it = ja.iterator();
//                    List<PubCodeModel> list=new ArrayList<PubCodeModel>();
//                    while (it.hasNext()) {
//                    }
//
//                    if (jsonIntent_0.getJSONArray("outputs").getJSONObject(1).getString("type").equals("dialog")) {
//                        result = jsonIntent_0.getJSONArray("outputs").getJSONObject(1).getJSONObject("property").
//                                getString("text");
//                    } else if {
//                        jsonIntent_0.getJSONArray("outputs").contains("voice")
//                        result = jsonIntent_0.getJSONArray("outputs").getJSONObject(1).getJSONObject("property").
//                                getString("voice_url");
//                    } else if (jsonIntent_0.getJSONArray("outputs").getJSONObject())
                } else {
                    logger.info("未从RUYI获取任何信息，返回''字符串");
                    return null;
                }

            } catch (Exception e) {
                logger.info("未能识别当前意图1");
                e.printStackTrace();
                return null;
            }
        } catch (IOException e) {
            logger.info("未能识别当前意图2");
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(ruyiAi("热门新闻", "gh_655b593ac7b9_9897297a665e1d3b"));
    }

}
