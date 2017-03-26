package com.ictwsn.utils.dream;

import com.ictwsn.utils.tools.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 * Created by Administrator on 2017-03-25.
 *
 */

public class OliveDream {

    public static Logger logger = LoggerFactory.getLogger(OliveDream.class);

    public static final String key = "d8f799bdd1374b51946501ecdd4e5179";
    public static String haoServiceURL = "http://apis.haoservice.com/lifeservice/dream/query";

    public static String getDream(String text){
        String result = "";
        HttpEntity httpEntity = HttpUtil.httpPost(haoServiceURL+"?q="+text+"&key="+key,null);
        try {
            JSONObject jsonObject = JSONObject.fromObject(EntityUtils.toString(httpEntity));
            if(jsonObject!=null){
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                result = jsonArray.getJSONObject(0).getString("list");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args){
        getDream("蛇");
    }

}
