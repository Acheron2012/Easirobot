package com.ictwsn.utils.weather;

import com.ictwsn.utils.tools.HttpUtil;
import com.ictwsn.utils.tools.Tools;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017-04-11.
 */
public class Suggestion {

    private static Logger logger = LoggerFactory.getLogger(Suggestion.class);

    public static void main(String[] args) {
        System.out.println(getSuggestion("李娟", "北京"));
    }

    public static String getSuggestion(String name, String city) {
        String suggestionResult = "";
        //先得到今日天气及温度
        String nowUrl = "https://free-api.heweather.com/v5/now?city=" + city + "&key=" + Heweather.userKey;
        String weatherContent = HttpUtil.getContent(nowUrl, "utf-8");
        JSONObject weatherObject = JSONObject.fromObject(weatherContent).getJSONArray("HeWeather5").getJSONObject(0);
        //天气状态
        JSONObject now = weatherObject.getJSONObject("now");
        String nowcond = now.getJSONObject("cond").getString("txt");
        //体感温度
        String fl = now.getString("fl") + "度";
        //风
        JSONObject wind = now.getJSONObject("wind");
        //风力
        String sc = wind.getString("sc").replace("-", "到") + "级";
        //添加当前天气状况
        //getDateSx判断早中晚
        suggestionResult = Tools.getDateSx() + "。" + name + "老人。" + city + "今日天气：" + nowcond + "。体感温度：" + fl
                + "。风力：" + sc + "。";
        //天气建议
        String suggestionUrl = "https://free-api.heweather.com/v5/suggestion?city=" + city + "&key=" + Heweather.userKey;
        String suggestionContent = HttpUtil.getContent(suggestionUrl, "utf-8");
        if (suggestionContent == null) return null;
        JSONObject suggestionObject = JSONObject.fromObject(suggestionContent).getJSONArray("HeWeather5").getJSONObject(0).getJSONObject("suggestion");
        //舒适度指数
        String comf = suggestionObject.getJSONObject("comf").getString("txt");
        //穿衣指数
        String drsg = suggestionObject.getJSONObject("drsg").getString("txt");
        drsg = drsg.replace("建议", "温馨提示：");
        //去除部分文字
        drsg = drsg.replace("年老体弱者", "").replace("等", "").replace("体弱者", "");
        suggestionResult += comf;
        suggestionResult += drsg;
        //祝福
        suggestionResult += "愿您度过愉快的一天！";
        return suggestionResult;
    }

}
