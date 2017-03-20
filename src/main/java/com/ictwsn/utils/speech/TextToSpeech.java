package com.ictwsn.utils.speech;

import com.ictwsn.utils.turing.TuringAPI;
import jdk.internal.instrumentation.Logger;
import jdk.nashorn.internal.scripts.JO;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 语音合成
 */

public class TextToSpeech {

    public static org.slf4j.Logger logger = LoggerFactory.getLogger(TextToSpeech.class);


    public static void main(String[] args)
    {
        String body = Speech.getToken();
        JSONObject jsonObject = JSONObject.fromObject(body);
        Speech.TOKEN = jsonObject.getString("access_token");
        System.out.println(Speech.TOKEN);

        String tex = "相关阅读：几乎所有哺乳动物和禽类(如鼠类猪羊牛家兔和鸡鸭鹅等)都可以传染弓形虫。人类的传染源主要是这些动物的肉类，如火锅的烫涮时间过短烧烤的温度不够，肉食的弓形虫没有杀死，就有传染的危险;生肉和熟食共用一个切菜砧板，生肉上的弓形虫就会污染熟食会致胎儿发育畸形的食物";


        //拼接请求的语音地址
        String voiceURL = "http://tsn.baidu.com/text2audio?tex="+tex+"&lan=zh&cuid="+ Speech.CUID+"&ctp=1&tok="+ Speech.TOKEN+"&spd="+ Speech.SPD;
        String downloadPath = "C:\\Users\\Administrator\\Desktop\\"+ Speech.AUDIOFILENAME+".mp3";
        try {
            getFile(voiceURL,downloadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //调用语音合成
    public static boolean textToSpeech(String context){

        Speech.TOKEN = JSONObject.fromObject(Speech.getToken()).getString("access_token");
        String voiceURL = "http://tsn.baidu.com/text2audio?tex="+context+"&lan=zh&cuid="+ Speech.CUID+"&ctp=1&tok="+ Speech.TOKEN+"&spd="+ Speech.SPD;
        String downloadPath = "C:\\Users\\Administrator\\Desktop\\"+ Speech.AUDIOFILENAME+".mp3";
        try {
            getFile(voiceURL,downloadPath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean downloadAudio(String audioPath,String audioText)
    {
        String body = Speech.getToken();
        JSONObject jsonObject = JSONObject.fromObject(body);
        Speech.TOKEN = jsonObject.getString("access_token");
        //拼接请求的语音地址
        String voiceURL = "http://tsn.baidu.com/text2audio?tex="+audioText+"&lan=zh&cuid="+ Speech.CUID+"&ctp=1&tok="+ Speech.TOKEN+"&spd="+ Speech.SPD;
        try {
            getFile(voiceURL,audioPath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    //下载语音合成文件
    private static void getFile(String url, String destFileName)
            throws ClientProtocolException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpget = new HttpGet(url);

        HttpResponse response = httpclient.execute(httpget);

        HttpEntity entity = response.getEntity();

        InputStream in = entity.getContent();

        File file = new File(destFileName);

        try {
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp, 0, l);
            }
            fout.flush();
            fout.close();
        } finally {
            in.close();
        }
        httpclient.close();
    }


}
