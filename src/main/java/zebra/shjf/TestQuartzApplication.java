package zebra.shjf;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import zebra.shjf.schedule.ScheduledTasks;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class TestQuartzApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext cac = SpringApplication.run(TestQuartzApplication.class, args);
        String[] names = cac.getBeanDefinitionNames();
        Arrays.asList(names).forEach(name -> System.out.println(name));//打印bean
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(ScheduledTasks.class).withIdentity("testkey", "testvalue").withDescription("一个测试的类").build();
        jobDetail.getJobDataMap().put("ConfigurableApplicationContext",cac);//重点是这句话
        Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?")).startNow().build();
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
    }
}
