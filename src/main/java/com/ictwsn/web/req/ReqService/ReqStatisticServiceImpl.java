package com.ictwsn.web.req.ReqService;

import com.ictwsn.utils.tools.Arith;
import com.ictwsn.utils.tools.BaseDao;
import com.ictwsn.utils.tools.Mongodb;
import com.ictwsn.utils.tools.Tools;
import com.ictwsn.web.req.ReqBean.ReqBean;
import com.ictwsn.web.req.ReqDao.ReqDao;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2017-10-12.
 */
@Service
public class ReqStatisticServiceImpl extends BaseDao implements RuqStatisticService {

    public static int company_id = 1001;

    private static Logger logger = LoggerFactory.getLogger(ReqStatisticServiceImpl.class);

    private static final String collectionName = "dataflow";


    public void updateStatistic(long flow) {
//        System.out.println(flow);
        ReqBean reqBean = this.sqlSessionTemplate.getMapper(ReqDao.class).getRequestBeanByCompanyId(company_id);
        reqBean.setDay_flow(reqBean.getDay_flow() + flow);
        reqBean.setMonth_flow(reqBean.getMonth_flow() + flow);
        reqBean.setYear_flow(reqBean.getYear_flow() + flow);
        reqBean.setAll_flow(reqBean.getAll_flow() + flow);
        reqBean.setDay_req(reqBean.getDay_req() + 1);
        reqBean.setMonth_req(reqBean.getMonth_req() + 1);
        reqBean.setYear_req(reqBean.getYear_req() + 1);
        reqBean.setAll_req(reqBean.getAll_req() + 1);
        System.out.println(reqBean.toString());
        //更新数据库内容
        this.sqlSessionTemplate.getMapper(ReqDao.class).updateFlowByCompanyId(reqBean);
    }

    public ReqBean getStatisticByMySQL(int company_id) {
        ReqBean reqBean = this.sqlSessionTemplate.getMapper(ReqDao.class).getRequestBeanByCompanyId(company_id);
        return reqBean;
    }

    public JSONObject getStatisticByMongo(int company_id) {

        // 连接到数据库
        MongoDatabase mongoDatabase = Mongodb.getMongoDatabase();

        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

        //得到明天时间（8个小时的延迟）
        Date end_time = Tools.getDayByNumber(new Date(),1);

        //得到前15天时间
        Date start_time = Tools.getDayByNumber(end_time,-14);
        System.out.println("start:" + start_time + ",end:" + end_time);

        BasicDBObject query = new BasicDBObject("dateTime", new BasicDBObject("$gte", start_time)
                .append("$lte", end_time));
        query.append("company_id",company_id);

        FindIterable<Document> findIterable = collection.find(query).sort(new Document("dateTime",1) ).limit(15);

        MongoCursor<Document> mongoCursor = findIterable.iterator();
        JSONArray reqArray = new JSONArray();
        JSONArray flowArray = new JSONArray();
        JSONArray dateArray = new JSONArray();

        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            reqArray.add(document.getLong("req"));
            flowArray.add(Arith.round(Arith.div(document.getLong("flow"),1024*1024),2));
            dateArray.add(Tools.getOnlyDayByDate(Tools.changeToLocalChina(document.getDate("dateTime"))));
//            System.out.println(document.getDate("dateTime"));
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("req",reqArray);
        jsonObject.put("flow",flowArray);
        jsonObject.put("date",dateArray);
        System.out.println(jsonObject.toString());
        return jsonObject;
    }

}
