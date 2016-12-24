package zebra.shjf.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by liuxin on 16/12/21.
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {
    @Bean
    public Jedis jedis(JedisConnectionFactory jedisConnectionFactory) {
        return jedisConnectionFactory.getShardInfo().createResource();
    }
}
