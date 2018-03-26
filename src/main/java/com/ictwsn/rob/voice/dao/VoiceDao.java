package com.ictwsn.rob.voice.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Administrator on 2017-04-16.
 */
public interface VoiceDao {
    //搜寻该用户是否有此设备id
    @Select("select user_name from tbl_user,tbl_user_robot,tbl_robot where tbl_user.user_id=tbl_user_robot.user_id and tbl_user_robot.device_id = tbl_robot.device_id and tbl_robot.device_id = #{0} ")
    String getUserNameByDeviceID(String deviceID);

    @Select("select child_email from tbl_child where device_id = #{0}")
    List<String> getEmailAddressByDeviceID(String deviceID);

    @Select("select child_phone from tbl_child where device_id = #{0}")
    List<Long> getPhonesByDeviceID(String deviceID);

    @Select("select question_answer from tbl_question where device_id = #{0} and question_pinyin = #{1}")
    String getAnswerByDeviceIDAndQuestion(String deviceID, String questionPinYin);

    /**
     * 查询device_id下用户的所有信息
     */
    @Select("select tbl_user.* from tbl_user,tbl_user_robot,tbl_robot where tbl_user.user_id=tbl_user_robot.user_id and tbl_user_robot.device_id = tbl_robot.device_id and tbl_robot.device_id = #{0} ")
    com.ictwsn.bean.UserBean getUserByDeviceID(String deviceID);

    /**
     * 把用户本次回答的结果进行记录
     */
    @Update("update tbl_user,tbl_user_robot,tbl_robot set tbl_user.last_answer = #{1} where  tbl_user.user_id=tbl_user_robot.user_id and tbl_user_robot.device_id = tbl_robot.device_id and tbl_robot.device_id = #{0} ")
    int SaveUserLastAnswerByDeviceID(String deviceID, String voiceResult);

    @Select("select device_id from tbl_robot")
    List<String> getAllDeviceID();



}
