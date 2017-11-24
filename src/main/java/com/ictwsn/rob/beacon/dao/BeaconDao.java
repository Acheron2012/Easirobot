package com.ictwsn.rob.beacon.dao;

import com.ictwsn.rob.beacon.bean.BeaconBean;
import com.ictwsn.rob.beacon.bean.UserBean;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Administrator on 2017-04-16.
 */
public interface BeaconDao {
    //搜寻该用户是否有此设备id
    @Select("select * from tbl_beacon where user_id = #{0}")
    BeaconBean getUserBeacon(int user_id);

    //搜寻该用户是否有此设备id
    @Update("update tbl_beacon set last_time = #{last_time}, scenario = #{scenario},history=#{history} where user_id = #{user_id}")
    int updateUserBeacon(BeaconBean beaconBean);

    //通过device_id获取beaconBean
    @Select("SELECT tbl_user.* FROM tbl_user,tbl_robot,tbl_user_robot where tbl_user.user_id = tbl_user_robot.user_id and tbl_user_robot.device_id = tbl_robot.device_id and tbl_robot.device_id = #{0}")
//    @Select("SELECT * FROM tbl_user where user_id = 13")
    UserBean getUserBeanByDeviceId(String device_id);


}
