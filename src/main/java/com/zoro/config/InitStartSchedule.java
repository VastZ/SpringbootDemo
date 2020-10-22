package com.zoro.config;

import java.util.HashMap;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zoro.job.BaseJob;

/**
 * 这个类用于启动SpringBoot时，加载作业。run方法会自动执行。
 *
 * 另外可以使用 ApplicationRunner
 *
 */
//@Component
public class InitStartSchedule implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MyJobFactory myJobFactory;

	@Override
	public void run(String... args) throws Exception {
		/**
		 * 用于程序启动时加载定时任务，并执行已启动的定时任务(只会执行一次，在程序启动完执行)
		 */

		// 查询job状态为启用的
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("jobStatus", "1");
		// List<SysJob> jobList= sysJobService.querySysJobList(map);
		// 通过SchedulerFactory获取一个调度器实例
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		// 如果不设置JobFactory，Service注入到Job会报空指针
		scheduler.setJobFactory(myJobFactory);
		// 启动调度器
		scheduler.start();

		String jobClassName = "test";
		String jobGroupName = "test";
		// 构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass("com.zoro.job.TestTask1").getClass())
				.withIdentity(jobClassName, jobGroupName).build();
		// 表达式调度构建器(即任务执行的时间)
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("* 24 * * * ?");
		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
				.withSchedule(scheduleBuilder).startNow().build();
		// 任务不存在的时候才添加
		if (!scheduler.checkExists(jobDetail.getKey())) {
			try {
				scheduler.scheduleJob(jobDetail, trigger);
			} catch (SchedulerException e) {
				logger.info("\n创建定时任务失败" + e);
				throw new Exception("创建定时任务失败");
			}
		}
	}

	public static BaseJob getClass(String classname) throws Exception {
		Class<?> c = Class.forName(classname);
		return (BaseJob) c.newInstance();
	}
}
