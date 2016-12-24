package zebra.shjf.schedule;

import org.quartz.Scheduler;

import java.io.Serializable;

/**
 * Created by liuxin on 16/12/22.
 */
public class JobEntity implements Serializable {
    //cron表达式
    private String cronExpression;
    //组名
    private String jobGroup = Scheduler.DEFAULT_GROUP;

    private String jobName;

    private String className; // 执行任务的类(完整路径  包含包名)

    private String methodName;//执行任务的方法名

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
