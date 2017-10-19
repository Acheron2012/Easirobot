package com.ictwsn.web.req.action;

import com.ictwsn.utils.tools.Arith;
import com.ictwsn.utils.tools.Tools;
import com.ictwsn.web.req.ReqBean.ReqBean;
import com.ictwsn.web.req.ReqService.RuqStatisticService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017-10-13.
 */
@Controller
@RequestMapping("/web")
public class ReqAction {

    public static Logger logger = LoggerFactory.getLogger(ReqAction.class);

    @Resource
    RuqStatisticService ruqStatisticService;

    //得到近15天数据
    @RequestMapping(value = "/req", method = RequestMethod.GET)
    public String getStatisticsByMongo(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "company_id", required = true) int company_id)
            throws IOException, ServletException {
        //根据company_id获取近15天请求及流量数据
        JSONObject jsonObject = ruqStatisticService.getStatisticByMongo(company_id);
        //返回给客户端
        Tools.responseToJSON(response,jsonObject.toString());
        return null;
    }

    //得到当前统计表中的数据
    @RequestMapping(value = "/statistics.do", method = RequestMethod.GET)
    public String getStatisticsByMySQL(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int company_id = 1001;
        //根据company_id获取近15天请求及流量数据
        ReqBean reqBean = ruqStatisticService.getStatisticByMySQL(company_id);
        request.setAttribute("day_flow", Arith.round(Arith.div(reqBean.getDay_flow(),1024*1024),2));
        request.setAttribute("month_flow",Arith.round(Arith.div(reqBean.getMonth_flow(),1024*1024),2));
        request.setAttribute("year_flow",Arith.round(Arith.div(reqBean.getYear_flow(),1024*1024),2));
        request.setAttribute("all_flow",Arith.round(Arith.div(reqBean.getAll_flow(),1024*1024),2));
        request.setAttribute("day_req",reqBean.getDay_req());
        request.setAttribute("month_req",reqBean.getMonth_req());
        request.setAttribute("year_req",reqBean.getYear_req());
        request.setAttribute("all_req",reqBean.getAll_req());
        //返回给客户端
        return "statistics";
    }

}
