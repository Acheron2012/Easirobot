package com.ictwsn.utils.tools;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-03-16.
 */
public class Mongodb {

    public static Logger logger = LoggerFactory.getLogger(Mongodb.class);

    private static List<ServerAddress> addrs = null;
    private static List<MongoCredential> credentials = null;
    private static MongoClient mongoClient = null;
    private final static Object syncLock = new Object();
    private static MongoDatabase mongoDatabase;

    static {
        //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress(Tools.getConfigureValue("mongodb.serverip"),
                Integer.valueOf(Tools.getConfigureValue("mongodb.serverport")));
        addrs = new ArrayList<ServerAddress>();
        addrs.add(serverAddress);

        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential(Tools.getConfigureValue("mongodb.database"),
                Tools.getConfigureValue("mongodb.username"), Tools.getConfigureValue("mongodb.password").toCharArray());
        credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);
        //通过连接认证获取MongoDB连接
        mongoClient = new MongoClient(addrs, credentials);
    }

    public static MongoDatabase getMongoDatabase() {
        if (mongoDatabase == null) {
            synchronized (syncLock) {
                if (mongoDatabase == null) {
                    try {
                        //连接到数据库
                        mongoDatabase = mongoClient.getDatabase(Tools.getConfigureValue("mongodb.database"));
                        System.out.println("Connect to database successfully");
                    } catch (Exception e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    }
                }
            }
        }
        return mongoDatabase;
    }

    public static void main(String[] args) {

    }

}
