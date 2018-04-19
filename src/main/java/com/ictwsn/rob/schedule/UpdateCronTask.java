package com.ictwsn.rob.schedule;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class UpdateCronTask  implements SchedulingConfigurer {

    public static Logger logger = LoggerFactory.getLogger(UpdateCronTask.class);

    public static String cron = "0/2 * * * * ?";
    int i = 0;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        /* 增加一个动态定时器 */
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                i++;
                // 任务逻辑
                System.out.println("第" + (i) + "次开始执行操作... " + "时间：【" +
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date()) + "】");
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //任务触发，可修改任务的执行周期
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        });
        /* 再增加一个动态定时器 */

    }


}
