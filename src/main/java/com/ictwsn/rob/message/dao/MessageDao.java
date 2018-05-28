package com.ictwsn.rob.message.dao;

import com.ictwsn.rob.message.bean.MessageBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Administrator on 2018-04-28.
 */
public interface MessageDao {

    /**
     * 通过open_id获取子女device_id
     */
    @Select("select device_id from tbl_child where open_id = #{0}")
    String getDeviceIdByChildOpenId(String open_id);

    /**
     * 通过管理员open_id获取旗下所有device_id
     */
    @Select("select device_id from tbl_admin,tbl_robot where tbl_admin.admin_openid = #{0}" +
            " and tbl_admin.admin_id = tbl_robot.admin_id")
    List<String> getDeviceIdsByAdminOpenId(String open_id);

    /**
     * 插入音频文件数据
     */
    @Insert("insert into tbl_audio (device_id,open_id,audio_name,audio_status,audio_time,is_admin) values (" +
            "#{device_id},#{open_id},#{audio_name},#{audio_status},#{audio_time},#{is_admin})")
    int insertAudio(MessageBean messageBean);

    /**
     * 修改音频文件状态
     */
    @Update("update tbl_audio set audio_status = 1 where device_id = #{0} and open_id = #{1} and audio_name = #{2}")
    int updateAudio(String device_id, String open_id, String audio_name);

    /**
     * 搜寻device_id下未读的第一个消息名
     */
    @Select("select * from tbl_audio where device_id = #{0} and audio_status = 0 order by audio_id ASC")
    List<MessageBean> getMessageBeanByDeviceId(String device_id);

    /**
     * 通过admin_openid和old_name搜索出老人的device_id
     */
    @Select("select tbl_robot.device_id from tbl_admin,tbl_robot,tbl_user_robot,tbl_user where tbl_admin.admin_openid = #{0}" +
            " and tbl_admin.admin_id = tbl_robot.admin_id and tbl_user.user_name = #{1} and tbl_user.user_id = tbl_user_robot.user_id" +
            " and tbl_user_robot.device_id = tbl_robot.device_id")
    List<String> getDeviceIdsByAdminOpenIdAndOldName(String open_id,String older_name);


}
