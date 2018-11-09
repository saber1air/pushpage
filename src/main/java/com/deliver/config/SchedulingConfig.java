package com.deliver.config;

import com.deliver.service.DeliverRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;


/**
 * Created by pdl on 2018/10/17.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    private DeliverRecordService deliverRecordService;

    @Scheduled(cron = "0 0 9 * * ?") // 每天早上九点执行一次
    //@Scheduled(cron = "0 0/5 * * * ?") // 每10秒执行一次
    public void geTuiAM() {
        deliverRecordService.deliverGeTuiAM();
        System.out.println("getToken定时任务启动AM");
    }

    @Scheduled(cron = "0 0 18 * * ?") // 每天晚上六点执行一次
    //@Scheduled(cron = "0 0/1 * * * ?") // 每10秒执行一次
    public void geTuiPM() {
        deliverRecordService.deliverGeTuiPM();
        System.out.println("getToken定时任务启动PM");
    }
}