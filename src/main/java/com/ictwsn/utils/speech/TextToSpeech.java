package com.ictwsn.utils.speech;

import com.ictwsn.utils.tools.HttpUtil;
import com.ictwsn.utils.turing.TuringAPI;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;


import java.io.*;

/**
 * 语音合成
 */

public class TextToSpeech {

    public static org.slf4j.Logger logger = LoggerFactory.getLogger(TextToSpeech.class);


    public static void main(String[] args) {
//        String body = Speech.getToken();
//        JSONObject jsonObject = JSONObject.fromObject(body);
//        Speech.TOKEN = jsonObject.getString("access_token");
//        System.out.println(Speech.TOKEN);

        String tex = "\uD83D\uDE01 *肉片煸干之后会出";
        InputStream inputStream = returnCombineSpeechInputStream(tex);
        if (inputStream == null) System.out.println("我是空");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\Audio_new.mp3");
            byte[] b = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(b)) != -1) {
                    fileOutputStream.write(b, 0, len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        try {
//            tex = java.net.URLEncoder.encode(tex, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        try {
//            System.out.println(tex.getBytes("UTF-8").length);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        //拼接请求的语音地址
//        String voiceURL = "http://tsn.baidu.com/text2audio?tex=" + tex + "&lan=zh&cuid=" + Speech.CUID +
//                "&ctp=1&tok=" + Speech.TOKEN + "&spd=" + Speech.SPD + "&per=" + Speech.PER;
//        String downloadPath = "C:\\Users\\Administrator\\Desktop\\" + Speech.AUDIOFILENAME + ".mp3";
//        try {
//            getFile(voiceURL, downloadPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    //调用语音合成
    public static boolean textToSpeech(String context) {

        Speech.TOKEN = JSONObject.fromObject(Speech.getToken()).getString("access_token");
        String voiceURL = "http://tsn.baidu.com/text2audio?tex=" + context + "&lan=zh&cuid=" + Speech.CUID +
                "&ctp=1&tok=" + Speech.TOKEN + "&spd=" + Speech.SPD + "&per=" + Speech.PER;
        String downloadPath = "C:\\Users\\Administrator\\Desktop\\" + Speech.AUDIOFILENAME + ".mp3";
        try {
            getFile(voiceURL, downloadPath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean downloadAudio(String audioPath, String audioText) {
        String body = Speech.getToken();
        JSONObject jsonObject = JSONObject.fromObject(body);
        Speech.TOKEN = jsonObject.getString("access_token");
        try {
            //URL编码
            audioText = java.net.URLEncoder.encode(audioText, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //拼接请求的语音地址
        String voiceURL = "http://tsn.baidu.com/text2audio?tex=" + audioText + "&lan=zh&cuid=" +
                Speech.CUID + "&ctp=1&tok=" + Speech.TOKEN + "&spd=" + Speech.SPD + "&per=" + Speech.PER;
        try {
            getFile(voiceURL, audioPath);
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

    //当长度大于1024字节时（一个中文占两个字节），创建序列流来合并两个流
    public static InputStream returnCombineSpeechInputStream(String context) {
        //获取TOKEN
        Speech.TOKEN = JSONObject.fromObject(Speech.getToken()).getString("access_token");
        int count = (context.length() / 512) + 1;
        logger.info("合并长度为：" + count);
        InputStream inputStream = null;
        String tempContent;
        int tempLength = 0;
        for (int i = 0; i < count; i++) {
            logger.info("已进入第{}次合并",i+1);
            if (i == count - 1) {
                tempContent = context.substring(tempLength, context.length());
                tempLength = context.length();
            } else {
                tempContent = context.substring(tempLength, tempLength + 512);
                tempLength += 512;
            }
            try {
                //URL编码
                tempContent = java.net.URLEncoder.encode(tempContent, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //拼接请求字符串
            String voiceURL = "http://tsn.baidu.com/text2audio?tex=" + tempContent + "&lan=zh&cuid=" +
                    Speech.CUID + "&ctp=1&tok=" + Speech.TOKEN + "&spd=" + Speech.SPD + "&per=" + Speech.PER;
            //发送请求返回结果
            HttpEntity entity = HttpUtil.httpGet(voiceURL, null);
            if (entity != null) {
                try {
                    InputStream tempInputSream = entity.getContent();
                    if (inputStream == null) {
                        inputStream = tempInputSream;
                    } else {
                        //创建一个序列流，合并两个字节流
                        inputStream = new SequenceInputStream(inputStream, tempInputSream);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                logger.info("合成语音entity为空");
                return null;
            }
        }
        return inputStream;
    }


    //返回语音流文件
    public static InputStream returnSpeechInputStream(String context) {
        //获取TOKEN
        Speech.TOKEN = JSONObject.fromObject(Speech.getToken()).getString("access_token");
        try {
            //URL编码
            context = java.net.URLEncoder.encode(context, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //拼接请求字符串
        String voiceURL = "http://tsn.baidu.com/text2audio?tex=" + context + "&lan=zh&cuid=" +
                Speech.CUID + "&ctp=1&tok=" + Speech.TOKEN + "&spd=" + Speech.SPD + "&per=" + Speech.PER;
        HttpEntity entity = HttpUtil.httpGet(voiceURL, null);
        if (entity != null) {
            try {
                InputStream inputStream = entity.getContent();
                return inputStream;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("合成语音entity为空");
        }
        return null;
    }


}
