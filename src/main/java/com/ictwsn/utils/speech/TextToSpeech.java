package com.ictwsn.utils.speech;

import com.ictwsn.utils.turing.TuringAPI;
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

        String tex = "自贡近来三日天气。2017年03月21日。白天：小雨。夜晚：小雨。降水量：1.8毫升。降水概率：92。最高温度：16度。最低温度：11度。最低温度：17千米。风向：17。微风级2017年03月22日。白天：多云。夜晚：多云。降水量：0.4毫升。降水概率：99。最高温度：20度。最低温度：11度。最低温度：19千米。风向：19。微风级2017年03月23日。白天：多云。夜晚：小雨。降水量：0.0毫升。降水概率：2。最高温度：21度。最低温度：13度。最低温度：20千米。风向：20。微风级";


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
