package zebra.shjf.schedule;

import com.google.gson.*;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.ScheduledTask;
import redis.clients.jedis.Jedis;
import zebra.shjf.TestQuartzApplicationTests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by liuxin on 16/12/22.
 * 测试类
 * 主要是使用redis数组库，动态的创建定时任务，在制定时间完成
 */
public class TestQuartz extends TestQuartzApplicationTests {

    @Autowired
    Jedis jedis;

    @Test
    public void startThread() throws Exception {

        Gson gson = new Gson();
        JobEntity jobEntity = gson.fromJson(jedis.get("jobEntity"), JobEntity.class);

        //构建一个jobDetail
        JobDetail jobDetail = JobBuilder.newJob(ScheduledTasks.class).withIdentity("MyJob", "MyGoup").//
                usingJobData("className", jobEntity.getClassName())
                .usingJobData("methodName", jobEntity.getMethodName()).build();
        jobDetail.getJobDataMap().put("test", jobEntity);
        //动态构建表达式
        CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule(jobEntity.getCronExpression());
        //构建触发器
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobEntity.getJobName(), jobEntity.getJobGroup()).withSchedule(cron).build();

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();

        Thread thread = new Thread();
        thread.sleep(10000);

    }

    @Test
    public void start3() throws Exception {
        //准备添加从redis中得到的实体类，目的是遍历，然后添加到定时容器中，去执行
        ArrayList<JobEntity>arrayList=new ArrayList<JobEntity>();

        Gson gson = new Gson();
        //从redis中得到json数组对象
        String str = jedis.get("jobEntity");
        //json解析器
        JsonParser parser = new JsonParser();
        //解析出json元素，jsonElement对象中有一些方法判断是对象还是数组，各对应不同的处理
        JsonElement jsonElement = parser.parse(str);

        //如果是json数组就转换为jsonArray
        JsonArray jsonArray = null;
        if (jsonElement.isJsonArray()) {
            jsonArray = jsonElement.getAsJsonArray();
        }
        //遍历
        Iterator it= jsonArray.iterator();
        while (it.hasNext()){
            JsonElement e = (JsonElement)it.next();
            //把获得的数组中每一个对象，重新添加到数组中
            arrayList.add(gson.fromJson(e,JobEntity.class));
        }
        //容器
        Scheduler scheduler=null;
        //遍历数组
        for(JobEntity jobEntity:arrayList){
            //遍历获得每个job对象
            JobDetail jobDetail = JobBuilder.newJob(ScheduledTasks.class).withIdentity(jobEntity.getJobName(), jobEntity.getJobGroup()).//
                    usingJobData("className", jobEntity.getClassName())
                    .usingJobData("methodName", jobEntity.getMethodName()).build();
            //jobDetail.getJobDataMap().put("test", jobEntity);
            //为每个任务动态构建表达式
            CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule(jobEntity.getCronExpression());
            //构建触发器
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobEntity.getJobName(), jobEntity.getJobGroup()).withSchedule(cron).build();

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
        }
        scheduler.start();

        Thread thread = new Thread();
        thread.sleep(10000);

    }


    //保存数组
    @Test
    public void start2() {
        Gson gson = new Gson();
        JobEntity jobEntity = new JobEntity();
        jobEntity.setMethodName("test");
        jobEntity.setJobName("MyJob2");
        jobEntity.setClassName("zebra.shjf.schedule.Test2");
        jobEntity.setCronExpression("0/1 * * * * ?");
        jobEntity.setJobGroup("MyGroup2");

        JobEntity jobEntity2 = new JobEntity();
        jobEntity2.setMethodName("xun");
        jobEntity2.setJobName("MyJob");
        jobEntity2.setClassName("zebra.shjf.schedule.Test");
        jobEntity2.setCronExpression("0/1 * * * * ?");
        jobEntity2.setJobGroup("MyGroup");

        ArrayList<JobEntity> list = new ArrayList<JobEntity>();
        list.add(jobEntity);
        list.add(jobEntity2);
        jedis.set("jobEntity", gson.toJson(list));
    }
}

