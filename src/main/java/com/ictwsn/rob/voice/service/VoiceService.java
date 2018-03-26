package com.ictwsn.rob.voice.service;

import com.ictwsn.bean.UserBean;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017-09-15.
 */
public interface VoiceService {
    String getUserNameByDeviceID(String deviceId);

    List<String> getEmailAddressByDeviceID(String deviceID);

    List<Long> getPhonesByDeviceID(String deviceID);

    String getAnswerByDeviceIDAndQuestion(String deviceID, String question);

    UserBean getUserByDeviceID(String deviceID);

    int SaveUserLastAnswerByDeviceID(String deviceID, String voiceResult);

    Set<String> getAllDeviceID();
}
