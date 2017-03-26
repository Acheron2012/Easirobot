package com.ictwsn.utils.health;

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
public class HealthyNet {

    public static Logger logger = LoggerFactory.getLogger(HealthyNet.class);

    public static String getHealthyTips(){
        // 连接到数据库
        MongoDatabase mongoDatabase = Mongodb.getMongoDatabase();

        MongoCollection<Document> collection = mongoDatabase.getCollection("health");

        //获取数据总数
        int healthCount = (int)collection.count();
        //随机选择
        Random random = new Random();
        int number = random.nextInt(healthCount);
        System.out.println(number);
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        for(int i=1;i<number;i++){
            mongoCursor.next();
        }
        JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
        return jsonObject.getString("message");
    }

}
