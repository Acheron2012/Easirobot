package com.ictwsn.rob.user.dao;

import com.ictwsn.rob.user.bean.UserBean;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Administrator on 2018-06-20.
 */
public interface UserDao {
    @Update("update tbl_user set user_name=#{user_name},user_sex=#{user_sex},user_weight=#{user_weight},user_height=#{user_height}," +
            "user_age=#{user_age},user_phone=#{user_phone},user_address=#{user_address},user_province=#{user_province}," +
            "identity_card=#{identity_card},user_city=#{user_city},user_area=#{user_area},user_street=#{user_street} where " +
            "user_id = #{user_id}")
    int updateUserInformation(UserBean userBean);
}
