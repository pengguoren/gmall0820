package com.cq.gamll.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by wanggenshen
 * Date: on 2018/7/7 16:28.
 * Description: 打印任意内容
 */
public class PrintWordsJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
        System.out.println("PrintWordsJob start at:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100));
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        System.out.println("得到任务名"+jobKey.getName());
        System.out.println("得到组名"+jobKey.getGroup());
        System.out.println("得到全类名"+jobExecutionContext.getJobDetail().getJobClass().getName());
        System.out.println("得到简单类名"+jobExecutionContext.getJobDetail().getJobClass().getSimpleName());
    }
}