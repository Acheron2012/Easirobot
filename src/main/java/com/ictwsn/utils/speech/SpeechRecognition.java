package com.ictwsn.utils.speech;


import net.sf.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

/**
 * 语音识别
 */

public class SpeechRecognition {

    public static void main(String[] args) throws Exception {
        recognition();
    }


    private static void recognition() throws Exception {

        File audioFile = new File(Speech.SPEECHFILENAME);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(Speech.SERVERURL);
        //识别后缀名
        String audioFormat = audioFile.getName().substring(audioFile.getName().lastIndexOf(".")+1,audioFile.getName().length());
        System.out.println("Suffix:"+audioFormat);


        //注：码率为8K，频道为单声道，数据格式为wav或amr
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("format", audioFormat);
        jsonParam.put("rate", 8000);
        jsonParam.put("channel", "1");
        jsonParam.put("token", Speech.getToken());
        jsonParam.put("cuid", Speech.CUID);
        jsonParam.put("len", audioFile.length());
        jsonParam.put("speech", DatatypeConverter.printBase64Binary(loadFile(audioFile)));

        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        httpPost.setEntity(entity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            // 请求结束，返回结果
            String resData = EntityUtils.toString(httpResponse.getEntity());
            JSONObject resJson = JSONObject.fromObject(resData);
            System.out.println(resJson);
        }
    }


    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            is.close();
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }

}
