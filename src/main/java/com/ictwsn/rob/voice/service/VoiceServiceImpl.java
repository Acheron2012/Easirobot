package com.ictwsn.rob.voice.service;

import com.ictwsn.rob.beacon.service.BeaconServiceImpl;
import com.ictwsn.rob.voice.dao.VoiceDao;
import com.ictwsn.utils.tools.BaseDao;
import com.ictwsn.utils.tools.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-09-15.
 */
@Service
public class VoiceServiceImpl extends BaseDao implements VoiceService {

    public static Logger logger = LoggerFactory.getLogger(BeaconServiceImpl.class);

    public String getUserNameByDeviceID(String deviceId) {
        return this.sqlSessionTemplate.getMapper(VoiceDao.class).getUserNameByDeviceID(deviceId);
    }

    public List<String> getEmailAddressByDeviceID(String deviceID) {
        return this.sqlSessionTemplate.getMapper(VoiceDao.class).getEmailAddressByDeviceID(deviceID);
    }

    public List<Long> getPhonesByDeviceID(String deviceID) {
        return this.sqlSessionTemplate.getMapper(VoiceDao.class).getPhonesByDeviceID(deviceID);
    }

    public String getAnswerByDeviceIDAndQuestion(String deviceID, String question) {
        String questionPinYin = Tools.getPingYin(question);
        return this.sqlSessionTemplate.getMapper(VoiceDao.class).getAnswerByDeviceIDAndQuestion(deviceID, questionPinYin);
    }

}
