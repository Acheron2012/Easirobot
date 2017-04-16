package com.ictwsn.rob.beacon.dao;

import com.ictwsn.rob.beacon.bean.BeaconBean;
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
}
