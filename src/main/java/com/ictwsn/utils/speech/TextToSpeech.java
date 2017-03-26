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


    public static void main(String[] args)
    {
        String body = Speech.getToken();
        JSONObject jsonObject = JSONObject.fromObject(body);
        Speech.TOKEN = jsonObject.getString("access_token");
        System.out.println(Speech.TOKEN);

        String tex = "人到中年，有很多时候力不从心，很多中年人有记忆力不如年轻时的感觉。记忆力的减退是大脑的衰老过程。保证睡眠时间。工作时脑神经细胞处于兴奋状态，能量消耗大，久之会疲劳。睡眠时脑细胞处于抑制状态，并使消耗的能量得到补充，帮助恢复精力。睡眠时间的长短因人而异，不能一概而论，只要次日感到精力充沛就算睡足了。健康小常识多吃益脑食物。大脑重量占体重的2％，但消耗的能量却占人体总能耗量的20％，其中85％是葡萄糖。蛋白质中的谷胱甘肽可提高脑细胞的活力，预防脑神经细胞老化。动物肝脏、鱼类等食物中含有丰富的谷胱甘肽和大脑所需的氨基酸成分。大脑还“偏爱”卵磷脂，卵磷脂在体内能产生乙酰胆碱，是脑细胞之间传递信息的“信使”，对增强记忆力至关重要。蛋黄、大豆中含有较多的卵磷脂。另外，大脑要吸收上述营养物质，亦离不开维生素B族以及微量元素如铁、锌、硒、铜的帮助，它们是大脑营养物质分解酶的重要成分，摄入这些营养素可多食绿叶蔬菜、豆类及其制品、柑桔、胡萝卜、黑木耳、动物内脏等。健康小常识保持豁达乐观的心境。平时要保持积极向上的精神状态，因为愉悦的心境有利于神经系统与各器官、系统的协调统一，使机体的生理代谢过程处于最佳状态，反馈性地增强大脑细胞的活力，对强化记忆和提高用脑效率亦颇有益处。健康小常识进行适当适度的运动。对于经常持续伏案工作的中年知识分子来说，适当适度的运动有益健康。因为适度运动能调节改善大脑中枢的兴奋与抑制过程，促进脑细胞代谢，使大脑功能得以充分发挥，延缓大脑老化。上一页12下一页";

        try {
            tex = java.net.URLEncoder.encode(tex, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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

    //返回语音流文件
    public static InputStream returnSpeechInputStream(String context){
        //获取TOKEN
        Speech.TOKEN = JSONObject.fromObject(Speech.getToken()).getString("access_token");
        try {
            //URL编码
            context = java.net.URLEncoder.encode(context, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //拼接请求字符串
        String voiceURL = "http://tsn.baidu.com/text2audio?tex="+context+"&lan=zh&cuid="+ Speech.CUID+"&ctp=1&tok="+ Speech.TOKEN+"&spd="+ Speech.SPD;
        HttpEntity entity = HttpUtil.httpGet(voiceURL,null);
        if(entity!=null){
            try {
                InputStream inputStream = entity.getContent();
                return inputStream;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            logger.info("合成语音entity为空");
        }
        return null;
    }


}
