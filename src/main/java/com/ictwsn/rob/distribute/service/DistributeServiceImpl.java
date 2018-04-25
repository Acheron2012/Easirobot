package com.ictwsn.rob.distribute.service;

import com.ictwsn.rob.distribute.bean.DistributeBean;
import com.ictwsn.rob.distribute.dao.DistributeDao;
import com.ictwsn.utils.tools.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2018-04-23.
 */
@Service
public class DistributeServiceImpl extends BaseDao implements DistributeService{

    public static Logger logger = LoggerFactory.getLogger(DistributeServiceImpl.class);

    @Override
    public int getCountOfDeviceId() {
        return this.sqlSessionTemplate.getMapper(DistributeDao.class).getCountOfDeviceId();
    }

    @Override
    public String getDeviceIdAsc() {
        return this.sqlSessionTemplate.getMapper(DistributeDao.class).getDeviceIdAsc();
    }

    @Override
    public int updateMacDeviceStatusByDeviceId(String device_id, String mac_address, Date application_time) {
        return this.sqlSessionTemplate.getMapper(DistributeDao.class).updateMacDeviceStatusByDeviceId(device_id,
                mac_address, application_time);
    }

    @Override
    public int insertMacDevice(DistributeBean distributeBean) {
        return this.sqlSessionTemplate.getMapper(DistributeDao.class).insertMacDevice(distributeBean);
    }
}
