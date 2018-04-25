package com.ictwsn.rob.physiology.dao;

import com.ictwsn.rob.physiology.bean.PhysiologyBean;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2018-04-20.
 */
public interface PhysiologyDao {
    @Select("select tbl_robot.device_id,user_name,user_age,user_sex,user_height,user_weight from tbl_user,tbl_user_robot,tbl_robot where tbl_user.user_id=tbl_user_robot.user_id and tbl_user_robot.device_id = tbl_robot.device_id and tbl_robot.device_id = #{0}")
    PhysiologyBean getPhysiologyByDeviceId(String device_id);
}
