package com.ictwsn.utils.turing;


import com.ictwsn.utils.speech.Speech;
import com.ictwsn.utils.speech.SpeechRecognition;
import com.ictwsn.utils.speech.TextToSpeech;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TuringAPI {

    public static Logger logger = LoggerFactory.getLogger(TuringAPI.class);
    //用户id，任意值，用于上下文标识
    public static final String userid = "123456";
    //图灵机器人注册api key
    public static final String apikey = "737e3c633624422f8f5d67fb8798e930";

    public static void turing(){
        String speechText = null;
        try {
            speechText = SpeechRecognition.recognition(Speech.SPEECHFILENAME);
            System.out.println("语音指令："+speechText);
            if(speechText!=null)
                TTS(turingRobot(speechText));
            else
                logger.info("语音识别错误");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //调用语音合成
    private static void TTS(String context){
        System.out.println("合成语音："+context.trim());
        TextToSpeech.textToSpeech(context.trim());
    }

    //调用图灵接口
    public static String turingRobot(String content)
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String turingURL = "http://www.tuling123.com/openapi/api";

        HttpPost httpPost = new HttpPost(turingURL);
        HttpResponse httpResponse = null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key",apikey);
        jsonObject.put("info",content);
//        jsonObject.put("userid",userid);

        StringEntity stringEntity = new StringEntity(jsonObject.toString(),"utf-8");
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");//发送json数据需要设置contentType
        httpPost.setEntity(stringEntity);

        //发送请求
        try {
            httpResponse = httpclient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==200){
                String result = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
                JSONObject resultObject = JSONObject.fromObject(result);
                //获取返回结果编码
                if("100000".equals(resultObject.getString("code"))){
                    //返回过滤后的文本信息
//                    System.out.println("返回的图灵信息:"+resultObject.get("text").toString());
                    return TuringUtils.filterSpeech(resultObject.get("text").toString());
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args)
    {
        turing();
    }

}
