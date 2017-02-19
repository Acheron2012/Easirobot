package com.ictwsn.utils.TextToSpeech;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 语音合成测试
 * 百度接口
 */

public class TTSTest {

    private static final String serverURL = "http://vop.baidu.com/server_api";
    private static String token = "";
    private static final String testFileName = "C:\\Users\\Administrator\\Desktop\\Baidu_Voice_RestApi_SampleCode\\sample\\Feb 19, 15.41.amr";
    private static final String apiKey = "Dt70455RDRmose5bs3qFoTLp";
    private static final String secretKey = "9b2aa909022349be024b4a93d6ea252e";
    private static final String cuid = "94-DE-80-23-9B-23";

    private static final String spd = "3";
    private static final String audioFileName = "testAudio";

    public static void main(String[] args)
    {
        String body = getToken();
        JSONObject jsonObject = JSONObject.fromObject(body);
        token = jsonObject.getString("access_token");
        System.out.println(token);

        String tex = "早上起床时，胃内的食物早已消化完毕，这时水的进入就如同冲水马桶一般，将胃壁的残渣冲洗的一干二净。" +
                "晨起水喝进去约数十秒后就会到达全身各个角落，可以促进细胞的循环代谢，让身体从睡眠中醒过来，有提神醒脑的功效。" +
                "人在睡眠中也会排汗，所以水分仍在持续的流失中。晨起水可适时补充睡眠中流失的水分，让细胞充满水分，肌肤看来饱满有弹性。" +
                "可稀释尿液，冲洗尿道，并化身为粪便的软化剂，让你顺畅排除留存在体内的废物。也可以稀释逐渐黏稠的血液，降低血液的浓度，避免血压飙升。";


        //拼接请求的语音地址
        String voiceURL = "http://tsn.baidu.com/text2audio?tex="+tex+"&lan=zh&cuid="+cuid+"&ctp=1&tok="+token+"&spd="+spd;
        String downloadPath = "C:\\Users\\Administrator\\Desktop\\"+audioFileName+".mp3";
        try {
            getFile(voiceURL,downloadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //获取百度的认证密匙
    private static String getToken() {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        String getTokenURL = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" +
                "&client_id=" + apiKey + "&client_secret=" + secretKey;

        HttpGet httpGet = new HttpGet(getTokenURL);

        String body = "";

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity, "utf-8");
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
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
