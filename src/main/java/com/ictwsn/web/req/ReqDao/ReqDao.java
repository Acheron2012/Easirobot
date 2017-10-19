package com.ictwsn.web.req.ReqDao;

import com.ictwsn.web.req.ReqBean.ReqBean;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Administrator on 2017-04-16.
 */
public interface ReqDao {
    //搜寻该用户是否有此设备id
    @Select("select user_name from tbl_user,tbl_user_robot,tbl_robot where tbl_user.user_id=tbl_user_robot.user_id and tbl_user_robot.device_id = tbl_robot.device_id and tbl_robot.device_id = #{0} ")
    String getUserNameByDeviceID(String deviceID);

    //清空当天流量及请求次数
    @Update("update tbl_request set day_flow = 0,day_req = 0")
    int resetFlowAndReqByDay();

    //清空当月流量及请求次数
    @Update("update tbl_request set month_flow = 0,month_req = 0")
    int resetFlowAndReqByMonth();

    //清空当年流量及请求次数
    @Update("update tbl_request set year_flow = 0,year_req = 0")
    int resetFlowAndReqByYear();

    //查询当前请求内容
    @Select("select * from tbl_request where company_id = #{0}")
    ReqBean getRequestBeanByCompanyId(int company_id);




    //更新流量
    @Update("update tbl_request set day_flow = #{day_flow},month_flow = #{month_flow},year_flow = #{year_flow},all_flow = #{all_flow}," +
            "day_req = #{day_req},month_req = #{month_req},year_req=#{year_req},all_req = #{all_req} where company_id = #{company_id}")
    int updateFlowByCompanyId(ReqBean flowBean);

}
