package bank.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.setting}")
    private String setting;

    private RedisServer redisServer;

    @PostConstruct
    public void start() {
        this.redisServer = RedisServer.builder()
                                      .port(redisPort)
                                      .setting(setting)
                                      .build();
        try {
            this.redisServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void stop() {
        if (this.redisServer != null) {
            this.redisServer.stop();
        }
    }

}