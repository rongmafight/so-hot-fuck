package com.make.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.db.nosql.redis.RedisDS;
import cn.hutool.setting.Setting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.util.function.Function;

/**
 * @Description: 封装jedis，防止使用jedis后未close
 * @author: MR
 * @date: 2022年02月24日 10:01
 */

@Slf4j
@Component
public class JedisWrapper {

    private static RedisDS redisDS;

    @PostConstruct
    public void init() {
        redisDS = RedisDS.create(new Setting("redis.setting", CharsetUtil.CHARSET_UTF_8, false), null);
    }

    @PreDestroy
    public void close() {
        log.info("redis pool destroy");
        redisDS.close();
    }

    public static <T> T useJedis(Function<Jedis, T> function) {
        try (Jedis jedis = redisDS.getJedis()) {
            return function.apply(jedis);
        }
    }
}
