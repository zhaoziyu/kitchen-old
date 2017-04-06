package com.restaurant.dinner.protal.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Demo
 *
 * @date 2016-09-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Component
public class DemoTask {
    /* 开始注入Service @Resource */

    @Scheduled(cron = "0 */1 * * * ?")
    public void work1() {
        System.out.println("定时任务1：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
    }

    @Scheduled(cron = "0 */2 * * * ?")
    public void work2() {
        System.out.println("定时任务2：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
    }
}
