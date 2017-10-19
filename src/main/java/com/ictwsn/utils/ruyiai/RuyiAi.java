package com.ictwsn.utils.ruyiai;

import com.ictwsn.utils.tools.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
        //不忘记短期对话记忆
        jsonObject.put("reset_session", false);

        HttpEntity httpEntity = HttpUtil.httpPost(URL, null, jsonObject.toString());
        try {
            String content = EntityUtils.toString(httpEntity, "UTF-8");
            System.out.println(content);
            try {
                //获取
                JSONObject jsonContent = JSONObject.fromObject(content);
                JSONObject jsonResult = jsonContent.getJSONObject("result");
                JSONObject jsonIntent_0 = jsonResult.getJSONArray("intents").getJSONObject(0);
                JSONObject json_result = jsonIntent_0.getJSONObject("result");
                //判断是否获取的为音频文件
                if (json_result.containsKey("media_url")) {
                    result = json_result.getString("media_url");
                } else if (jsonIntent_0.containsKey("outputs")) {
                    if (jsonIntent_0.getJSONArray("outputs").getJSONObject(1).getString("type").equals("dialog")) {
                        result = jsonIntent_0.getJSONArray("outputs").getJSONObject(1).getJSONObject("property").
                                getString("text");
                    } else if (jsonIntent_0.getJSONArray("outputs").getJSONObject(1).getString("type").equals("voice")) {
                        result = jsonIntent_0.getJSONArray("outputs").getJSONObject(1).getJSONObject("property").
                                getString("voice_url");
                    }
                } else return null;
            } catch (Exception e) {
                logger.info("未能识别当前意图");
                e.printStackTrace();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(ruyiAi("冥想音乐", "gh_655b593ac7b9_9897297a665e1d3b"));

    }

}
