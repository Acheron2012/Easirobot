package com.ictwsn.rob.provider.service;

import com.ictwsn.rob.provider.utils.ChildBean;
import com.ictwsn.rob.provider.utils.OldBean;

/**
 * Created by Administrator on 2018-05-28.
 */
public interface ProviderService {
    String getUserServiceIdByUserId(int user_id);
    OldBean getUserByUserId(int user_id);
    int updateUserServiceIdByUserId(int user_id,long user_service_id);
    String getUserPhoneByDeviceId(String device_id);
    int updateServiceStatusByUserId(int user_id,String service_status);
    int updateServiceStatusByDeviceId(String device_id,String service_status);
    String getDeviceIdByUserId(int user_id);
    OldBean getUserByOpenId(String open_id);
    ChildBean getChildByOpenId(String open_id);
}
