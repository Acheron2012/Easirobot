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
     * 通过open_id获取device_id
     */
    @Select("select device_id from tbl_child where open_id = #{0}")
    String getOpenIdByDeviceId(String open_id);

    /**
     * 插入音频文件数据
     */
    @Insert("insert into tbl_audio (device_id,open_id,audio_name,audio_status,audio_time,is_admin) values (" +
            "#{device_id},#{open_id},#{audio_name},#{audio_status},#{is_admin})")
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


}
