package com.ictwsn.utils.encyclopedias;

import com.ictwsn.utils.msg.Msg;
import com.ictwsn.utils.tools.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 爬取搜狗百科
 * Created by Administrator on 2017-03-25.
 */

public class Sougou {

    public static final String url = "http://baike.sogou.com/lemma/default/ShowLemmaDefault,$FinalBorder.$NewSearchBar.sf.sd";
    public static Logger logger = LoggerFactory.getLogger(Msg.class);

    public static String crawlSougou(String textField) {
        String encyclopedias = "";
        //设置请求头
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Host", "baike.sogou.com");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        //请求表单
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("formids", "TextField,Submit,Submit_0"));
        data.add(new BasicNameValuePair("submitmode", "submit"));
        data.add(new BasicNameValuePair("submitname", ""));
        data.add(new BasicNameValuePair("TextField", textField));
        data.add(new BasicNameValuePair("Submit", "进入词条"));
        //302跳转位置
        String location = "";
        Header[] heads = HttpUtil.postFormReturnHeaders(url, headers, data);
        for (Header h : heads) {
            if ("Location".equals(h.getName())) location = h.getValue();
        }
        //查询到结果
        if (!"".equals(location)) {
            //获取百科信息
            HttpEntity httpEntity = HttpUtil.httpGet(location, null);
            try {
                String HTMLContent = EntityUtils.toString(httpEntity);
                //正则匹配
                Pattern pattern = Pattern.compile("window\\.lemmaData = (.*?)var hasRelationTable", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(HTMLContent);
                if (matcher.find()) {
                    String word = matcher.group(1).trim().replaceAll("\\n", "").replaceAll("\\s", "");
                    word = word.substring(0, word.length() - 1);
                    JSONObject jsonObject = JSONObject.fromObject(word);
                    encyclopedias = jsonObject.getString("mbabstract");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encyclopedias;
    }

}
