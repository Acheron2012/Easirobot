package com.ictwsn.utils.weather;

import com.ictwsn.utils.tools.HttpUtil;
import com.ictwsn.utils.tools.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Administrator on 2017-03-20.
 */
public class Heweather {

    private static Logger logger = LoggerFactory.getLogger(Heweather.class);

    public static final String userKey = "e8e58155f9314374bd9e4c389055cdd7";

    /**
     * @param city 城市
     * @param flag 状态码，true为天气预报，false为实时天气
     * @return
     */
    public static String heweatherAPI(String city, boolean flag) {
        String url = "https://free-api.heweather.com/v5/now?city=" + city + "&key=" + userKey;
        if (flag) {
            url = "https://free-api.heweather.com/v5/forecast?city=" + city + "&key=" + userKey;
        }
        String text = "";
        HttpEntity httpEntity = HttpUtil.httpGet(url, null);
        if (httpEntity != null) {
            try {
                text = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (flag) return forecastWeather(text);
        else return analysisWeather(text);
    }

    public static String forecastWeather(String text) {
        String forecastInfo = "";
        JSONObject jsonObject = JSONObject.fromObject(text).getJSONArray("HeWeather5").getJSONObject(0);
        //城市名称及时间
        JSONObject basic = jsonObject.getJSONObject("basic");
        String city = basic.getString("city");
        forecastInfo += city + "近来三日天气。";
        JSONArray daily_forecast = jsonObject.getJSONArray("daily_forecast");
        //遍历预报天气
        Iterator<Object> it = daily_forecast.iterator();
        while (it.hasNext()) {
            JSONObject ob = (JSONObject) it.next();
            //预报日期
            String date = ob.getString("date");
            date = Tools.stringToChineseDate(date);
            forecastInfo += date + "。";
            //天气状况
            JSONObject cond = ob.getJSONObject("cond");
            //白天状况
            String txt_d = cond.getString("txt_d");
            forecastInfo += "白天：" + txt_d + "。";
            //夜晚状况
            String txt_n = cond.getString("txt_n");
            forecastInfo += "夜晚：" + txt_n + "。";
            //降水量（mm）
            String pcpn = ob.getString("pcpn");
            forecastInfo += "降水量：" + pcpn + "毫升。";
            //降水概率
            String pop = ob.getString("pop");
            forecastInfo += "降水概率：" + pop + "。";
            JSONObject tmp = ob.getJSONObject("tmp");
            //最高温度
            String max = tmp.getString("max");
            forecastInfo += "最高温度：" + max + "度。";
            //最低温度
            String min = tmp.getString("min");
            forecastInfo += "最低温度：" + min + "度。";
            //能见度（km）
            String vis = ob.getString("vis");
            forecastInfo += "能见度：" + vis + "千米。";
            //风
            JSONObject wind = ob.getJSONObject("wind");
            //风向
            String dir = wind.getString("dir");
            forecastInfo += "风向：" + dir + "。";
            //风力
            String sc = "风力：" + wind.getString("sc").replace("-", "到") + "级";
            forecastInfo += sc;
        }
        return forecastInfo;
    }

    public static String analysisWeather(String text) {

        JSONObject jsonObject = JSONObject.fromObject(text).getJSONArray("HeWeather5").getJSONObject(0);
        //城市名称及时间
        JSONObject basic = jsonObject.getJSONObject("basic");
        String city = basic.getString("city");
        String loctime = basic.getJSONObject("update").getString("loc");
        loctime = Tools.stringToChineseTime(loctime);
        //天气状态
        JSONObject now = jsonObject.getJSONObject("now");
        String nowcond = now.getJSONObject("cond").getString("txt");
        //体感温度
        String fl = now.getString("fl") + "度";
        //相对湿度（%）
        //String hum = "百分之"+now.getString("hum");
        //降水量（mm）
        String pcpn = now.getString("pcpn") + "毫升";
        //"pres": "1025",  //气压
        //温度
        String tmp = now.getString("tmp") + "度";
        //能见度（km）
        String vis = now.getString("vis") + "千米";
        //风
        JSONObject wind = now.getJSONObject("wind");
        //风向
        String dir = wind.getString("dir");
        //风力
        String sc = wind.getString("sc").replace("-", "到") + "级";
        String weatherinfo = city + "。" + loctime + "。" + "天气：" + nowcond + "。" + "体感温度：" + fl + "。" + "室外温度：" + tmp + "。" + "降水量：" + pcpn + "。" +
                "能见度：" + vis + "。" + "风向：" + dir + "。" + "风力：" + sc;
        return weatherinfo;
    }


    public static void main(String[] args) {
//        analysisWeather(heweatherAPI());
//        System.out.println(Tools.stringToChineseTime("2017-03-20 13:51:23"));
    }

}
