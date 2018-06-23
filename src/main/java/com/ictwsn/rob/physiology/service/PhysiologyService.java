package com.ictwsn.rob.physiology.service;

import com.ictwsn.rob.physiology.bean.PhysiologyBean;
import com.ictwsn.rob.provider.utils.PhyBean;

/**
 * Created by Administrator on 2018-04-20.
 */
public interface PhysiologyService {
    PhysiologyBean getPhysiologyByDeviceId(String device_id);
    PhyBean getPhyBeanByPhyId(long phy_id);
}
