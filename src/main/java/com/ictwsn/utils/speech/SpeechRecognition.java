package com.ictwsn.utils.speech;


import com.ictwsn.utils.turing.TuringUtils;
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
        System.out.println(recognition(Speech.SPEECHFILENAME));
    }


    public static String recognition(String speechPath) throws Exception {

        String speechText = null;
        File audioFile = new File(speechPath);

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
            if("0".equals(resJson.getString("err_no")))
                //提取返回的结果并过滤掉特殊字符
                speechText = TuringUtils.StringFilter(resJson.getString("result"));

        }
        return speechText;
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
