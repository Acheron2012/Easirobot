package com.ictwsn.rob.physiology.service;

import com.ictwsn.rob.physiology.bean.PhysiologyBean;
import com.ictwsn.rob.physiology.dao.PhysiologyDao;
import com.ictwsn.utils.tools.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018-04-20.
 */
@Service
public class PhysiologyServiceImpl extends BaseDao implements PhysiologyService {

    public static Logger logger = LoggerFactory.getLogger(PhysiologyServiceImpl.class);

    @Override
    public PhysiologyBean getPhysiologyByDeviceId(String device_id) {
        return this.sqlSessionTemplate.getMapper(PhysiologyDao.class).getPhysiologyByDeviceId(device_id);
    }

}
