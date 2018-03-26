package com.ictwsn.rob.voice.service;

import com.ictwsn.bean.UserBean;
import com.ictwsn.rob.beacon.service.BeaconServiceImpl;
import com.ictwsn.rob.voice.dao.VoiceDao;
import com.ictwsn.utils.tools.BaseDao;
import com.ictwsn.utils.tools.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public UserBean getUserByDeviceID(String deviceID) {
        return this.sqlSessionTemplate.getMapper(VoiceDao.class).getUserByDeviceID(deviceID);
    }

    public int SaveUserLastAnswerByDeviceID(String deviceID, String voiceResult) {
        return this.sqlSessionTemplate.getMapper(VoiceDao.class).SaveUserLastAnswerByDeviceID(deviceID, voiceResult);
    }


    @Cacheable(value = "baseCache", key = "'getAllDeviceID'")
    public Set<String> getAllDeviceID() {
        logger.info("首次请求，载入所有device_id");
        List<String> list_ids =  this.sqlSessionTemplate.getMapper(VoiceDao.class).getAllDeviceID();
        Set device_ids = new HashSet(list_ids);
        return device_ids;
    }

}
