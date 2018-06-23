package com.ictwsn.rob.provider.service;

import com.ictwsn.rob.provider.dao.ProviderDao;
import com.ictwsn.rob.provider.utils.ChildBean;
import com.ictwsn.rob.provider.utils.OldBean;
import com.ictwsn.utils.tools.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018-05-28.
 */
@Service
public class ProviderServiceImpl extends BaseDao implements ProviderService{
    public static Logger logger = LoggerFactory.getLogger(ProviderServiceImpl.class);

    @Override
    public String getUserServiceIdByUserId(int user_id) {
        return this.sqlSessionTemplate.getMapper(ProviderDao.class).getUserServiceIdByUserId(user_id);
    }

    @Override
    public OldBean getUserByUserId(int user_id) {
        return this.sqlSessionTemplate.getMapper(ProviderDao.class).getUserByUserId(user_id);
    }

    @Override
    public int updateUserServiceIdByUserId(int user_id, long user_service_id) {
        return this.sqlSessionTemplate.getMapper(ProviderDao.class).updateUserServiceIdByUserId(user_id,user_service_id);
    }

    @Override
    public String getUserPhoneByDeviceId(String device_id) {
        return this.sqlSessionTemplate.getMapper(ProviderDao.class).getUserPhoneByDeviceId(device_id);
    }

    @Override
    public int updateServiceStatusByUserId(int user_id, String service_status) {
        return this.sqlSessionTemplate.getMapper(ProviderDao.class).updateServiceStatusByUserId(user_id,service_status);
}

    @Override
    public int updateServiceStatusByDeviceId(String device_id, String service_status) {
        return this.sqlSessionTemplate.getMapper(ProviderDao.class).updateServiceStatusByDeviceId(device_id,service_status);
    }

    @Override
    public String getDeviceIdByUserId(int user_id) {
        return this.sqlSessionTemplate.getMapper(ProviderDao.class).getDeviceIdByUserId(user_id);
    }

    @Override
    public OldBean getUserByOpenId(String open_id) {
        return this.sqlSessionTemplate.getMapper(ProviderDao.class).getUserByOpenId(open_id);
    }

    @Override
    public ChildBean getChildByOpenId(String open_id) {
        return this.sqlSessionTemplate.getMapper(ProviderDao.class).getChildByOpenId(open_id);
    }


}
