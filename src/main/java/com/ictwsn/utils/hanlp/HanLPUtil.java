package com.ictwsn.utils.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;
import com.ictwsn.utils.tools.Mongodb;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017-04-08.
 */
public class HanLPUtil {

    public static Logger logger = LoggerFactory.getLogger(HanLPUtil.class);

    public static Segment shortestSegment = null;
    public static MongoDatabase mongoDatabase = null;

    static {
        //开启用户自定义配置，不开启词性标注
        shortestSegment = new ViterbiSegment().enableCustomDictionary(true).enablePartOfSpeechTagging(false);
        //开启所有命名识别
        //人名、地名、机构、音译人名、日本人名
        shortestSegment.enableAllNamedEntityRecognize(true);
    }

    public static void analysisUserVoice(String userId, String text) {
        // 从连接池获取一个连接
        mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("user_voice");
        Document document = new Document();
        document.put("userId", userId);
        document.put("dataTime", System.currentTimeMillis());
        //插入文本信息
        document.put("text", text);
        //插入分词
        document.put("word", shortestSegment.seg(text).toString());
        //插入分词关键字，设定最大为5个
        document.put("keyWord", HanLP.extractKeyword(text, 3).toString());
        collection.insertOne(document);
    }

    public static void main(String[] args) {
        analysisUserVoice("1000","泽田依子是上外日本文化经济学院的外教");
    }

}
