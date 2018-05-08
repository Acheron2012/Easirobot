package com.ictwsn.rob.distribute.dao;

import com.ictwsn.rob.distribute.bean.DistributeBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * Created by Administrator on 2018-04-21.
 */
public interface DistributeDao {
    /** 查询数据库是否还有空闲device_id */
    @Select("select count(*) from tbl_mac_device where is_allocated = 0")
    int getCountOfDeviceId();

    /** 得到其中一个device_id */
    @Select("select device_id from tbl_mac_device where is_allocated = 0 order by number ASC limit 1")
    String getDeviceIdAsc();

    /** 更改获得已获得的device_id状态 */
    @Update("update tbl_mac_device set mac_address=#{1},application_time=#{2},is_allocated=1 where device_id = #{0}")
    int updateMacDeviceStatusByDeviceId(String device_id, String mac_address, Date application_time);

    /** 将获取到的URL插入到表中并更新状态 */
    @Insert("insert into tbl_mac_device (mac_address,device_id,is_allocated,application_time) values(#{mac_address},#{device_id},#{is_allocated},#{application_time})")
    int insertMacDevice(DistributeBean distributeBean);

    /** 通过mac地址获取device_id */
    @Select("select device_id from tbl_mac_device where mac_address = #{0} and is_allocated = 1")
    String getDeviceIdByMac(String MAC);


}
