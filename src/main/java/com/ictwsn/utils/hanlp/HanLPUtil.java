package com.ictwsn.utils.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.ictwsn.utils.tools.Mongodb;
import com.ictwsn.utils.tools.Tools;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017-04-08.
 */
public class HanLPUtil {

    public static Logger logger = LoggerFactory.getLogger(HanLPUtil.class);

    public static Segment shortestSegment = null;
    public static MongoDatabase mongoDatabase = null;

    static {
        //开启用户自定义配置，不开启词性标注
        shortestSegment = new ViterbiSegment().enableCustomDictionary(true).enablePartOfSpeechTagging(true);
        //开启所有命名识别
        //人名、地名、机构、音译人名、日本人名
        shortestSegment.enableAllNamedEntityRecognize(true);

        // 动态增加
//        CustomDictionary.add("孔雀女的瓜");

    }

    public static void analysisUserVoice(String deviceID, String text) {
        // 从连接池获取一个连接
        mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("user_voice");
        Document document = new Document();
        document.put("deviceID", deviceID);
        document.put("dataTime", System.currentTimeMillis());
        //插入文本信息
        document.put("text", text);
        //插入分词
        document.put("word", shortestSegment.seg(text).toString());
        //插入分词关键字，设定最大为5个
        document.put("keyWord", HanLP.extractKeyword(text, 5).toString());
        collection.insertOne(document);
    }

    public static void saveUserVoice(String deviceID, String text, int company_id, String label) {
        // 从连接池获取一个连接
        mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("user_voice");
        Document document = new Document();
        document.put("deviceID", deviceID);
        // 放入timestamp
        Date date = null;
        date = Tools.getNowDate8();
        document.put("dataTime", date);
        //插入文本信息
        document.put("text", text);
        //插入分词关键字，设定最大为5个
        document.put("keyWord", HanLP.extractKeyword(text, 5).toString());
        //和子女还是和机器人对话 label='child' or 'robot'
        document.put("label", label);
        //公司id
        document.put("company_id", company_id);
        collection.insertOne(document);
    }


    public static List<String> getUserVoiceKeywords(String device_id) {
        StringBuffer voiceText = new StringBuffer();
        // 从连接池获取一个连接
        mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("user_voice");
        Document document = new Document();
        document.put("deviceID", device_id);
        FindIterable<Document> findIterable = collection.find(document);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            voiceText.append(mongoCursor.next().getString("text") + "。");
        }
        logger.info("用户语音：{}", voiceText.toString());
        //插入分词关键字，设定最大为5个
        List<String> list = HanLP.extractKeyword(voiceText.toString(), 1);
        return list;
    }

    public static HashMap getSeparatewords(String text) {
        List<Term> listTerm = shortestSegment.seg(text);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (Term t : listTerm) {
            if (t.toString().contains("/nr")) hashMap.put("nr", t.toString().replace("/nr", ""));
            if (t.toString().contains("/n")) hashMap.put("n", t.toString().replace("/n", ""));
        }

        return hashMap;
    }

    public static void EntityRecognition() {
        String[] testCase = new String[]{
                "签约仪式前，秦光荣、李纪恒、仇和等一同会见了参加签约的企业家。",
                "王国强、高峰、汪洋、张朝阳光着头、韩寒、小四",
                "张浩和胡健康复员回家了",
                "王总和小丽结婚了",
                "编剧邵钧林和稽道青说",
                "这里有关天培的有关事迹",
                "龚学平等领导,邓颖超生前",
                "中关村今天天气怎么样",
                "孔雀女的瓜是笨蛋",
        };
        for (String sentence : testCase) {
            List<Term> termList = shortestSegment.seg(sentence);
            System.out.println(termList);
        }
    }

    public static void main(String[] args) {
//        HanLPUtil.saveUserVoice("gh_655b593ac7b9_9897297a665e1d3b", "下班回家买菜", 1001, "robot");
//        System.out.println(getSeparatewords("讲一首唐诗"));
        EntityRecognition();
    }

}
