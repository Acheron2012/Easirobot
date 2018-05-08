package com.ictwsn.rob.message.service;

import com.ictwsn.rob.message.bean.MessageBean;

import java.util.List;

/**
 * Created by Administrator on 2018-04-28.
 */
public interface MessageService {
    String getOpenIdByDeviceId(String open_id);
    void createDirByDeviceIdAndOpenId(String device_id,String open_id);
    boolean downloadAmrByDeviceAndOpenId(String device_id,String open_id,String fileName,String url);
    int insertAudio(MessageBean messageBean);
    int updateAudio(String device_id,String open_id,String audio_name);
    List<MessageBean> getMessageBeanByDeviceId(String device_id);
}
