package com.ictwsn.rob.schedule;


import com.ictwsn.rob.schedule.dao.ScheduleDao;
import com.ictwsn.utils.tools.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class UpdateCronTask extends BaseDao implements SchedulingConfigurer {

    public static Logger logger = LoggerFactory.getLogger(UpdateCronTask.class);

    public static String REQUEST_URL = "http://easirobot.zhongketianhe.com.cn:8080/Easirobot/rob/get_push_text";

    public List<String> allDeviceIds = new ArrayList<String>();

    //"0/5 * * * * ?" 每5秒执行一次

    public static String cron = "0 0 0/1 * * ?";

    int i = 0;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        allDeviceIds = this.sqlSessionTemplate.getMapper(ScheduleDao.class).getAllDeviceId();
        /* 增加一个动态定时器 */
//        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
//            @Override
//            public void run() {
//                i++;
//                // 任务逻辑
//                System.out.println("第" + (i) + "次开始执行操作... " + "时间：【" +
//                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date()) + "】");
////              遍历所有的device_id,并推送
//                for(String device_id : allDeviceIds) {
//                    ChatbotPush.testSendPushWithCustomConfig(device_id, REQUEST_URL, 6);
//                }
//                logger.info("推送完成：共计{}个",allDeviceIds.size());
//            }
//        }, new Trigger() {
//            @Override
//            public Date nextExecutionTime(TriggerContext triggerContext) {
//                //任务触发，可修改任务的执行周期
//                CronTrigger trigger = new CronTrigger(cron);
//                Date nextExec = trigger.nextExecutionTime(triggerContext);
//                return nextExec;
//            }
//        });
        /* 再增加一个动态定时器 */

    }


}
