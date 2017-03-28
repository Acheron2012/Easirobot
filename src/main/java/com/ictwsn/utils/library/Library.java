package com.ictwsn.utils.library;

import com.ictwsn.utils.tools.Mongodb;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by Administrator on 2017-03-25.
 */
public class Library {

    public static Logger logger = LoggerFactory.getLogger(Library.class);

    public static String getOneDataFromLibrary(String collectionName, String field) {
        // 连接到数据库
        MongoDatabase mongoDatabase = Mongodb.getMongoDatabase();

        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

        //获取数据总数
        int dataCount = (int) collection.count();
        //随机选择
        Random random = new Random();
        int number = random.nextInt(dataCount);
        logger.info("{}：随机获取第{}条数据", collectionName, number);
        FindIterable<Document> findIterable = collection.find().skip(number - 1).limit(1);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
        return jsonObject.getString(field);
    }

    public static String getOneDataByCondition(String collectionName, String field, String condition) {
        // 连接到数据库
        MongoDatabase mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        //获取数据总数
        int dataCount = (int) collection.count(new Document("category", condition));
        if(dataCount==0) return null;
        //随机选择
        Random random = new Random();
        int number = random.nextInt(dataCount);
        logger.info("{}：随机获取第{}条数据，条件：{}", collectionName, number, condition);
        FindIterable<Document> findIterable = collection.find(new Document("category", condition))
                .skip(number - 1).limit(1);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
        return jsonObject.getString(field);
    }

}
