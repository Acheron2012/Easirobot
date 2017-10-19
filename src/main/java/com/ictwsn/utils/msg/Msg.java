package com.ictwsn.utils.msg;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-03-21.
 */
public class Msg {

    public static Logger logger = LoggerFactory.getLogger(Msg.class);


    //阿里大于基本参数
    public static final String url = "http://gw.api.taobao.com/router/rest";
    public static final String appkey = "23709690";
    public static final String secret = "3e7e624fd3128e18d01273ecf9842494";

    public static void sendMessage(String name, List<Long> phones, String content){

//        System.out.println(name);
//        System.out.println(phones.toString());
//        System.out.println(content);

        String phones_string = "";
        for(int i = 0; i<phones.size(); i++)
            if(phones.get(i)!=null)
                phones_string += phones.get(i) + ",";
        phones_string = phones_string.substring(0,phones_string.length()-1);
        System.out.println("发送号码:"+phones_string);

        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        //自定义用户id
        req.setExtend("1000");
        //短信类型
        req.setSmsType("normal");
        //短息签名
        req.setSmsFreeSignName("智能孝子");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("content",content);
        //消息模板
        req.setSmsParamString(jsonObject.toString());
        //手机号，多个以逗号分隔
        req.setRecNum(phones_string);
        //短信模板id
        req.setSmsTemplateCode("SMS_56635394");
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        logger.info(rsp.getBody());
    }

    public static void main(String[] args) {
        List<Long> phones = new ArrayList<Long>();
        phones.add(15611535191L);
//        phones.add(18813124313L);
//        phones.add(18810287398L);
        sendMessage("老人", phones, "发短信好慢啊");
    }
}
