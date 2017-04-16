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

    private static MongoDatabase mongoDatabase = null;

    static {
        // 连接到数据库
        mongoDatabase = Mongodb.getMongoDatabase();
    }

    public static String getOneDataFromLibrary(String collectionName, String getField) {

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
        return jsonObject.getString(getField);
    }

    public static String getOneDataByCondition(String collectionName, String getField, String condition) {
        // 连接到数据库
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        //获取数据总数
        int dataCount = (int) collection.count(new Document("category", condition));
        if (dataCount == 0) return null;
        //随机选择
        Random random = new Random();
        int number = random.nextInt(dataCount);
        logger.info("{}：随机获取第{}条数据，条件：{}", collectionName, number, condition);
        FindIterable<Document> findIterable = collection.find(new Document("category", condition))
                .skip(number - 1).limit(1);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
        return jsonObject.getString(getField);
    }

    public static String getOneDataByConditionField(String collectionName, String getField, String conditionField, String condition) {
        // 连接到数据库
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        //获取数据总数
        int dataCount = (int) collection.count(new Document(conditionField, condition));
        if (dataCount == 0) return null;
        //随机选择
        Random random = new Random();
        int number = random.nextInt(dataCount);
        logger.info("{}：随机获取第{}条数据，条件：{}", collectionName, number, condition);
        FindIterable<Document> findIterable = collection.find(new Document(conditionField, condition))
                .skip(number - 1).limit(1);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
        return jsonObject.getString(getField);
    }

    public static String getThreeDatafield(String collectionName, String getField1,
                                           String getField2, String getField3) {
        // 连接到数据库
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        FindIterable<Document> findIterable = null;
        //获取数据总数
        int dataCount = (int) collection.count();
        if (dataCount == 0) return null;
        //随机选择
        Random random = new Random();
        int number = random.nextInt(dataCount);
        logger.info("{}：随机获取第{}条数据", collectionName, number);
        findIterable = collection.find()
                .skip(number - 1).limit(1);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
        return jsonObject.getString(getField1) + jsonObject.getString(getField2) + jsonObject.getString(getField3);
    }

    public static String getThreeDatafieldByCondition(String collectionName, String getField1,
                                           String getField2, String getField3,String conditionField, String condition) {
        // 连接到数据库
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        Document document = new Document(conditionField, condition);
        FindIterable<Document> findIterable = null;
        //获取数据总数
        int dataCount = (int) collection.count(document);
        if (dataCount == 0) return null;
        //随机选择
        Random random = new Random();
        int number = random.nextInt(dataCount);
        logger.info("{}：随机获取第{}条数据", collectionName, number);
        findIterable = collection.find(document)
                .skip(number - 1).limit(1);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
        return jsonObject.getString(getField1) + jsonObject.getString(getField2) + jsonObject.getString(getField3);
    }



}
