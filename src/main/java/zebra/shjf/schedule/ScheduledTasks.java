package zebra.shjf.schedule;

import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import zebra.shjf.HelloService;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuxin on 16/12/21.
 * 反射执行类和执行方法，默认为无参
 */
public class ScheduledTasks implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("------进入定时方法区域-------");
        try {
            ConfigurableApplicationContext cac = (ConfigurableApplicationContext) jobExecutionContext.getJobDetail().getJobDataMap().get("ConfigurableApplicationContext");
            HelloService helloService = (HelloService) cac.getBean("helloService");
            helloService.hh();
        } catch (Exception e) {

        }
    }
}
   /* JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
    JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
    String className = dataMap.getString("className");
    String methName = dataMap.getString("methodName");
    JobEntity jobEntity = (JobEntity) dataMap.get("test");
        System.out.println(jobEntity.getJobName());
                //doSomething

                try {
                Class clazz = Class.forName(className);
                Method method = clazz.getMethod(methName);
                method.invoke(clazz.newInstance(), null);

                } catch (Exception e) {
                e.printStackTrace();
                }

             try {
            SchedulerContext schedulerContext = jobExecutionContext.getScheduler().getContext();
            ApplicationContext applicationContext = (ApplicationContext) schedulerContext.get("applicationContext");
            HelloService helloService =(HelloService) applicationContext.getBean("helloService");
            helloService.hh();
        } catch (Exception e) {

        }







                */