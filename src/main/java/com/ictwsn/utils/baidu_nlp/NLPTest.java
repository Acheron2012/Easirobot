package com.ictwsn.utils.baidu_nlp;

import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.nlp.ESimnetType;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2017-11-23.
 */
public class NLPTest {
    //设置APPID/AK/SK
    public static final String APP_ID = "10429043";
    public static final String API_KEY = "LhjWx8HNvrhGmqqTHQXCQBQL";
    public static final String SECRET_KEY = "0op59XOEjZqrc1vt1zjmeCiumuxG257R";

    public static void main(String[] args) {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//        dnnlmCn(client);
        NLPDepParser(client);

    }

    public static void dnnlmCn(AipNlp client) {
        // 获取中文DNN语言模型
        JSONObject response = client.dnnlmCn("百度是个搜索公司");
        System.out.println(response.toString());

    }

    public static void simnet(AipNlp client) {

        // 获取两个文本的相似度
        JSONObject response = client.simnet("百度是个搜索公司", "谷歌是个搜索公司", null);
        System.out.println(response.toString());

        // 选择CNN算法
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("model", "CNN");
        JSONObject response1 = client.simnet("百度是个搜索公司", "谷歌是个搜索公司", options);
        System.out.println(response1.toString());
    }


    public static void NLPCommentTag(AipNlp client) {

        // 获取美食评论情感属性
        JSONObject response = client.commentTag("这家餐馆味道不错", ESimnetType.FOOD);
        System.out.println(response.toString());

        // 获取酒店评论情感属性
        JSONObject response_ = client.commentTag("喜来登酒店不错", ESimnetType.HOTEL);
        System.out.println(response_.toString());
    }

    public static void sentimentClassify(AipNlp client) {

        // 获取一条文本的情感倾向
        JSONObject response = client.sentimentClassify("百度是一家黑心的公司");
        System.out.println(response.toString());
    }

    public static void NLPDepParser(AipNlp client) {

        String text = "今天天气不错";
        JSONObject json = client.depParser(text, null);
        System.out.println(json.toString(2));
    }


}
