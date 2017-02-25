package com.ictwsn.utils.speech;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 语音合成
 */

public class TextToSpeech {

    public static void main(String[] args)
    {
        String body = Speech.getToken();
        JSONObject jsonObject = JSONObject.fromObject(body);
        Speech.TOKEN = jsonObject.getString("access_token");
        System.out.println(Speech.TOKEN);

        String tex = "早上起床时，胃内的食物早已消化完毕，这时水的进入就如同冲水马桶一般，将胃壁的残渣冲洗的一干二净。" +
                "晨起水喝进去约数十秒后就会到达全身各个角落，可以促进细胞的循环代谢，让身体从睡眠中醒过来，有提神醒脑的功效。" +
                "人在睡眠中也会排汗，所以水分仍在持续的流失中。晨起水可适时补充睡眠中流失的水分，让细胞充满水分，肌肤看来饱满有弹性。" +
                "可稀释尿液，冲洗尿道，并化身为粪便的软化剂，让你顺畅排除留存在体内的废物。也可以稀释逐渐黏稠的血液，降低血液的浓度，避免血压飙升。";


        //拼接请求的语音地址
        String voiceURL = "http://tsn.baidu.com/text2audio?tex="+tex+"&lan=zh&cuid="+ Speech.CUID+"&ctp=1&tok="+ Speech.TOKEN+"&spd="+ Speech.SPD;
        String downloadPath = "C:\\Users\\Administrator\\Desktop\\"+ Speech.AUDIOFILENAME+".mp3";
        try {
            getFile(voiceURL,downloadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
