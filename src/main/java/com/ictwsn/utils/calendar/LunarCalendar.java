package com.ictwsn.utils.calendar;

import com.ictwsn.utils.dream.OliveDream;
import com.ictwsn.utils.tools.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Administrator on 2017-03-25.
 */
public class LunarCalendar {
    public static Logger logger = LoggerFactory.getLogger(OliveDream.class);

    public static final String key = "0c19e9782ca8487f9a3dc45239d0e953";
    public static String haoServiceURL = "http://apis.haoservice.com/lifeservice/laohuangli/d";

    public static String lunar(String datetime){
        String result = "";
        HttpEntity httpEntity = HttpUtil.httpPost(haoServiceURL+"?date="+datetime+"&key="+key,null);
        try {
            JSONObject jsonObject = JSONObject.fromObject(EntityUtils.toString(httpEntity));
            if(jsonObject!=null){
                JSONObject resultObject = jsonObject.getJSONObject("result");
                //阳历
                result += "阳历："+resultObject.getString("yangli")+"。";
                //阴历
                result += "阴历："+resultObject.getString("yinli")+"。";
                //五行
                result += "五行："+resultObject.getString("wuxing")+"。";
                //冲刹
                result += "冲刹："+resultObject.getString("chongsha")+"。";
                //拜祭
                result += "拜祭："+resultObject.getString("baiji")+"。";
                //吉神
                result += "吉神："+resultObject.getString("jishen")+"。";
                //宜
                result += "宜："+resultObject.getString("yi")+"。";
                //凶神
                result += "凶神："+resultObject.getString("xiongshen")+"。";
                //忌
                result += "忌："+resultObject.getString("ji");
                result = result.replace(" ","。").replaceAll("(\\s)+","。").replaceAll("(。)+","。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
