package com.ictwsn.rob.voice.service;

import java.util.List;

/**
 * Created by Administrator on 2017-09-15.
 */
public interface VoiceService {
    String getUserNameByDeviceID(String deviceId);
    List<String> getEmailAddressByDeviceID(String deviceID);
    List<Long> getPhonesByDeviceID(String deviceID);
    String getAnswerByDeviceIDAndQuestion(String deviceID, String question);
}
