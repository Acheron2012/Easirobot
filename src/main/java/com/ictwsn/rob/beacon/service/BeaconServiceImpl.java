package com.ictwsn.rob.beacon.service;

import com.ictwsn.rob.beacon.bean.BeaconBean;
import com.ictwsn.rob.beacon.bean.UserSimpleBean;
import com.ictwsn.rob.beacon.dao.BeaconDao;
import com.ictwsn.utils.tools.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-04-16.
 */
@Service
public class BeaconServiceImpl extends BaseDao implements BeaconService {

    public static Logger logger = LoggerFactory.getLogger(BeaconServiceImpl.class);

    public BeaconBean getUserBeacon(int user_id) {
        return this.sqlSessionTemplate.getMapper(BeaconDao.class).getUserBeacon(user_id);
    }

    public int updateUserBeacon(BeaconBean beaconBean) {
        return this.sqlSessionTemplate.getMapper(BeaconDao.class).updateUserBeacon(beaconBean);
    }

    public UserSimpleBean getUserBeanByDeviceId(String device_id) {
        return this.sqlSessionTemplate.getMapper(BeaconDao.class).getUserBeanByDeviceId(device_id);
    }
}
