package com.ictwsn.web.req.ReqService;

import com.ictwsn.web.req.ReqBean.ReqBean;
import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2017-10-12.
 */
public interface RuqStatisticService {
    void updateStatistic(long flow);
    JSONObject getStatisticByMongo(int company_id);
    ReqBean getStatisticByMySQL(int company_id);

}
