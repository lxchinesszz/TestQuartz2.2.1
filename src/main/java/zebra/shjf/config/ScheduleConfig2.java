package zebra.shjf.config;

import org.quartz.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.*;
import zebra.shjf.schedule.ScheduledTasks;

/**
 * Created by liuxin on 16/12/21.
 */
public class ScheduleConfig2 {
    //<!-- 线程执行器配置，用于任务注册 -->

    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setQueueCapacity(500);
        return threadPoolTaskExecutor;
    }


    public JobDetail jobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setJobClass(ScheduledTasks.class);
        jobDetailFactoryBean.setGroup("DEK");
        JobDetail jobDetail = jobDetailFactoryBean.getObject();
        return jobDetail;
    }


    public SimpleTrigger simpleTrigger() {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setStartDelay(1000);
        simpleTriggerFactoryBean.setRepeatInterval(1000);
        simpleTriggerFactoryBean.setRepeatCount(5);
        simpleTriggerFactoryBean.setName("SimTrigger for Factory");
        SimpleTrigger simpleTrigger = simpleTriggerFactoryBean.getObject();
        return simpleTrigger;
    }


    public Scheduler scheduler(JobDetail jobDetail, SimpleTrigger simpleTrigger,ThreadPoolTaskExecutor threadPoolTaskExecutor) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setBeanName("scheduler");
        schedulerFactoryBean.setTriggers(simpleTrigger);//执行规则
        schedulerFactoryBean.setAutoStartup(true);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        return scheduler;
    }

}
