package com.ictwsn.rob.beacon.service;

import com.ictwsn.rob.beacon.bean.BeaconBean;
import com.ictwsn.rob.beacon.bean.UserSimpleBean;

/**
 * Created by Administrator on 2017-04-16.
 */
public interface BeaconService {
    BeaconBean getUserBeacon(int user_id);
    int updateUserBeacon(BeaconBean beaconBean);
    UserSimpleBean getUserBeanByDeviceId(String device_id);
}
