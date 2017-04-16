package com.ictwsn.utils.poetry;

import com.ictwsn.utils.hanlp.HanLPUtil;
import com.ictwsn.utils.tools.Mongodb;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017-04-16.
 */
public class Poetry {

    public static Logger logger = LoggerFactory.getLogger(Poetry.class);

    public static void main(String[] args) {
        System.out.println(getPoetry("曲"));
    }


    public static String getOnePoetry() {
        //获取一个mongodb链接
        MongoDatabase mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("poetry");
        //设置查询条件
        BasicDBObject doc = new BasicDBObject();
        int dataCount = (int) collection.count();
        if (dataCount == 0) return null;
        else {
            if (dataCount == 0) return null;
            //随机选择
            Random random = new Random();
            int number = random.nextInt(dataCount);
            FindIterable<Document> findIterable = collection.find()
                    .skip(number - 1).limit(1);
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
            return jsonObject.getString("title") + "：" + jsonObject.getString("author") + "。"
                    + jsonObject.getString("content");
        }
    }



    public static String getPoetry(String text) {
        HashMap<String, String> hashMap = HanLPUtil.getSeparatewords(text);
        //获取一个mongodb链接
        MongoDatabase mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("poetry");
        //设置查询条件
        BasicDBObject doc = new BasicDBObject();
        //模糊匹配
        Pattern authorPattern = null;
        Pattern categoryPattern = null;
        if (hashMap.get("nr") != null) {
            authorPattern = Pattern.compile("^.*" + hashMap.get("nr").toString() + ".*$", Pattern.CASE_INSENSITIVE);
            doc.put("author", authorPattern);
        }
        if (hashMap.get("n") != null) {
            categoryPattern = Pattern.compile("^.*" + hashMap.get("n").toString() + ".*$", Pattern.CASE_INSENSITIVE);
            doc.put("category", categoryPattern);
        }
        if (authorPattern == null && categoryPattern == null) return null;
        int dataCount = (int) collection.count(doc);
        if (dataCount == 0) return null;
        else {
            if (dataCount == 0) return null;
            //随机选择
            Random random = new Random();
            int number = random.nextInt(dataCount);
            FindIterable<Document> findIterable = collection.find(doc)
                    .skip(number - 1).limit(1);
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
            return jsonObject.getString("title") + "：" + jsonObject.getString("author") + "。"
                    + jsonObject.getString("content");
        }
    }

}
