package com.ictwsn.utils.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.ictwsn.utils.tools.Mongodb;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static List<String> getUserVoiceKeywords(String userId) {
        StringBuffer voiceText = new StringBuffer();
        // 从连接池获取一个连接
        mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("user_voice");
        Document document = new Document();
        document.put("userId", userId);
        FindIterable<Document> findIterable = collection.find(document);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            voiceText.append(mongoCursor.next().getString("text")+"。");
        }
        logger.info("用户语音：{}",voiceText.toString());
        //插入分词关键字，设定最大为5个
        List<String> list = HanLP.extractKeyword(voiceText.toString(), 1);
        return list;
    }

    public static HashMap getSeparatewords(String text) {
        List<Term> listTerm = shortestSegment.seg(text);
        HashMap<String,String> hashMap = new HashMap<String, String>();
        for(Term t : listTerm) {
            if(t.toString().contains("/nr")) hashMap.put("nr",t.toString().replace("/nr",""));
            if(t.toString().contains("/n")) hashMap.put("n",t.toString().replace("/n",""));
        }

        return hashMap;
    }

    public static void main(String[] args) {
        System.out.println(getSeparatewords("讲一首唐诗"));
    }

}
