package com.ictwsn.rob.physiology.service;

import com.ictwsn.rob.physiology.bean.PhysiologyBean;

/**
 * Created by Administrator on 2018-04-20.
 */
public interface PhysiologyService {
    PhysiologyBean getPhysiologyByDeviceId(String device_id);
}
