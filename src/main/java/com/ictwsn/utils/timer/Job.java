package com.ictwsn.utils.timer;

import com.ictwsn.utils.tools.BaseDao;
import com.ictwsn.utils.tools.Mongodb;
import com.ictwsn.utils.tools.Tools;
import com.ictwsn.web.req.ReqBean.ReqBean;
import com.ictwsn.web.req.ReqDao.ReqDao;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Job extends BaseDao {

    public static Logger logger = LoggerFactory.getLogger(Job.class);

    private static int company_id = 1001;
    public static MongoDatabase mongoDatabase = null;

    public void startJob() {
        logger.info("定时任务开始");
    }

    public void resetFlowByDay() {
        //提取当天的数据流量
        ReqBean reqBean = this.sqlSessionTemplate.getMapper(ReqDao.class).getRequestBeanByCompanyId(company_id);
        //存入mongodb
        saveFlowByDay(reqBean.getDay_flow(), reqBean.getDay_req());
        logger.info("执行每天流量清零任务");
        this.sqlSessionTemplate.getMapper(ReqDao.class).resetFlowAndReqByDay();
    }

    public void resetFlowByMonth() {
        logger.info("执行每月流量清零任务");
        this.sqlSessionTemplate.getMapper(ReqDao.class).resetFlowAndReqByMonth();
    }

    public void resetFlowByYear() {
        logger.info("执行每年流量清零任务");
        this.sqlSessionTemplate.getMapper(ReqDao.class).resetFlowAndReqByYear();
    }

    public void saveFlowByDay(long flow, long req) {
        // 从连接池获取一个连接
        mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("dataflow");
        Document document = new Document();
        document.put("company_id", company_id);
        // 放入timestamp
        Date date = null;
        date = Tools.getNowDay7();
        document.put("dateTime", date);
        //存入当天流量
        document.put("flow", flow);
        //存入当天请求次数
        document.put("req", req);
        collection.insertOne(document);
    }


}
