package com.ictwsn.rob.provider.dao;

import com.ictwsn.rob.provider.utils.ChildBean;
import com.ictwsn.rob.provider.utils.OldBean;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Administrator on 2018-05-28.
 */

public interface ProviderDao {

    @Select("select user_service_id from tbl_user where user_id = #{0}")
    String getUserServiceIdByUserId(int user_id);

    @Select("select * from tbl_user where user_id = #{0}")
    OldBean getUserByUserId(int user_id);

    @Select("select tbl_user.* from tbl_child,tbl_user,tbl_user_robot where tbl_child.device_id = tbl_user_robot.device_id and tbl_user_robot.user_id = tbl_user.user_id and tbl_child.open_id = #{0}")
    OldBean getUserByOpenId(String open_id);

    @Select("select * from tbl_child where open_id = #{0}")
    ChildBean getChildByOpenId(String open_id);

    @Update("update tbl_user set user_service_id = #{1} where user_id = #{0}")
    int updateUserServiceIdByUserId(int user_id,long user_service_id);

    @Select("select tbl_user.user_phone from tbl_user,tbl_user_robot where tbl_user.user_id = tbl_user_robot.user_id and tbl_user_robot.device_id = #{0}")
    String getUserPhoneByDeviceId(String device_id);

    @Update("update tbl_user set service_status = #{1} where user_id = #{0}")
    int updateServiceStatusByUserId(int user_id,String service_status);

    @Update("update tbl_user,tbl_user_robot set tbl_user.service_status = #{1} where tbl_user.user_id = tbl_user_robot.user_id and tbl_user_robot.device_id = #{0}")
    int updateServiceStatusByDeviceId(String device_id,String service_status);

    @Select("select device_id from tbl_user_robot where user_id = #{0}")
    String getDeviceIdByUserId(int user_id);

}
