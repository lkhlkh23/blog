package bank.blog.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Profile({"local", "h2"})
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void start() {
        this.redisServer = RedisServer.builder()
                                      .port(redisPort)
                                      .setting("maxmemory 128M")
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