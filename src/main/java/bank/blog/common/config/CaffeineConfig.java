package bank.blog.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
public class CaffeineConfig extends CachingConfigurerSupport {

    private static final int EXPIRE_1S = 1;
    private static final int EXPIRE_1M = 60;
    private static final int EXPIRE_1H = 3600;

    @Bean
    @Singleton
    public CacheManager cacheManager() {
        final SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(
            List.of(
                buildCache("getPopularKeywords", EXPIRE_1M)
            )
        );

        return manager;
    }

    private CaffeineCache buildCache(final String name, final int secondsToExpire) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                                               .expireAfterWrite(secondsToExpire, TimeUnit.SECONDS)
                                               .removalListener(new CustomRemovalListener())
                                               .build());
    }

    @PreDestroy
    public void clearCache() {
        cacheManager().getCacheNames()
                      .stream()
                      .forEach(cache -> cacheManager().getCache(cache).clear());
    }

    class CustomRemovalListener implements RemovalListener<Object, Object> {
        @Override
        public void onRemoval(Object key, Object value, RemovalCause cause) {
            log.info("removal called with key {}, cause {}, evicted {}\n", key, cause.toString(), cause.wasEvicted());
        }
    }

}
