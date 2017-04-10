package com.ictwsn.utils.encyclopedias;

import com.ictwsn.utils.msg.Msg;
import com.ictwsn.utils.tools.HttpUtil;
import com.ictwsn.utils.tools.Mongodb;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bson.Document;
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
        String encyclopedia = "";
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
                    encyclopedia = jsonObject.getString("mbabstract");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encyclopedia;
    }

    public static String getEncyclopedia(String name, String keyWord) {

        //分类
        String category = "";
        if (keyWord.matches("是谁|是哪个|是什么人"))
            category = "人名";
        else if (keyWord.matches("是什么|怎么样|如何|的作用|的用处|有什么用|(的)*功能|有什么好处"))
            category = "物名";
        else if (keyWord.matches("在哪儿|在哪里|位于哪里|在哪个地方"))
            category = "地名";
        else category = "其它";
        //查询本地库获取内容
        String content = selectLocalEncyclopedia(name, category);
        //若为空，则爬取搜狗百科内容，并存入本地百科库
        if (content == null) {
            //爬取搜狗百科获得信息
            content = crawlSougou(name);
            //信息过滤
            content = content.replaceAll("\\[(\\d)*\\]", "");
            //插入本地百科库
            insertLocalEncyclopedia(name, category, keyWord, content);
            logger.info("资料不存在，已将“{}”百科内容添加到本地资料库", name);
        } else logger.info("资料存在，已从本地百科库中获取“{}”", name);
        return content;
    }

    public static String selectLocalEncyclopedia(String name, String category) {
        // 获取数据库
        MongoDatabase mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("encyclopedia");
        //设置查询条件
        BasicDBObject doc = new BasicDBObject();
        doc.put("name", name);
        doc.put("category", category);

        int dataCount = (int) collection.count(doc);
        if (dataCount == 0) return null;
        else {
            FindIterable<Document> findIterable = collection.find(doc);
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
            return jsonObject.getString("content");
        }
    }

    public static void insertLocalEncyclopedia(String name, String category, String keyWord, String content) {
        MongoDatabase mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("encyclopedia");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("category", category);
        jsonObject.put("keyword", keyWord);
        jsonObject.put("datetime", System.currentTimeMillis());
        jsonObject.put("content", content);
        Document document = Document.parse(jsonObject.toString());
        collection.insertOne(document);
    }

}
