package com.ictwsn.rob.distribute.service;

import com.ictwsn.rob.distribute.bean.DistributeBean;

import java.util.Date;

/**
 * Created by Administrator on 2018-04-21.
 */
public interface DistributeService {
    int getCountOfDeviceId();
    String getDeviceIdAsc();
    int updateMacDeviceStatusByDeviceId(String device_id, String mac_address, Date application_time);
    int insertMacDevice(DistributeBean distributeBean);
    String getDeviceIdByMac(String MAC);
}
