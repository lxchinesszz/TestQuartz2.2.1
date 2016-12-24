package zebra.shjf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestQuartzApplicationTests {
    @Autowired
    Jedis jedis;

    @Test
    public void contextLoads() {
        String name = jedis.get("123");
        System.out.println(name);
    }
    @Test
    public void start()throws Exception{
        System.out.println("");
    }

}
