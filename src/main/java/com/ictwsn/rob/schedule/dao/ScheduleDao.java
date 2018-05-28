package com.ictwsn.rob.schedule.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2018-05-17.
 */
public interface ScheduleDao {
    @Select("select device_id from tbl_robot")
    List<String> getAllDeviceId();
}
