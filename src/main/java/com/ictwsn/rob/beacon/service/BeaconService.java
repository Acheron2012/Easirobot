package com.ictwsn.rob.beacon.service;

import com.ictwsn.rob.beacon.bean.BeaconBean;
import com.ictwsn.rob.beacon.bean.UserBean;

/**
 * Created by Administrator on 2017-04-16.
 */
public interface BeaconService {
    BeaconBean getUserBeacon(int user_id);
    int updateUserBeacon(BeaconBean beaconBean);
    UserBean getUserBeanByDeviceId(String device_id);
}
