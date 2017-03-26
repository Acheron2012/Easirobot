package com.ictwsn.utils.msg;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017-03-21.
 */
public class Msg {

    public static Logger logger = LoggerFactory.getLogger(Msg.class);


    //阿里大于基本参数
    public static final String url = "http://gw.api.taobao.com/router/rest";
    public static final String appkey = "23709690";
    public static final String secret = "3e7e624fd3128e18d01273ecf9842494";

    public static void sendMessage(String name,String content){

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
        req.setRecNum("18813124313");
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

    public static void main(String[] args){

    }
}
