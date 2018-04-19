package com.ictwsn.utils.tools;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017-11-30.
 */
public class ActionTool {

    private static Logger logger = LoggerFactory.getLogger(ActionTool.class);

    //封装为JSON数据
    private static String convertToJSON(String text, int code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        JSONObject jsonResult = new JSONObject();
        if (text.startsWith("http")) {
            //特殊字符转码
            text = URLtoUTF8.toUtf8String(text);
            jsonResult.put("url", text);
            jsonResult.put("text", "");
        } else {
            jsonResult.put("url", "");
            jsonResult.put("text", text);
        }
        jsonObject.put("result", jsonResult);
        logger.info("返回内容：{}", jsonResult);
        return jsonObject.toString();
    }


    // JSON封装文本内容并返回数据，并计算流量和下载次数
    public static void responseToJSON(HttpServletResponse response, String voiceResult, int code) {
        //Convert to JSON format
        String jsonContent = convertToJSON(voiceResult, code);
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.write(jsonContent);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
