package com.womenhz.swee.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class RedisCacheTest {

    //https://segmentfault.com/a/1190000020253409 短地址服务

    @Autowired
    private RedisCache redisCache;

    @Test
    public void testSet() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        redisCache.set("1","tom");
        log.info(redisCache.get("1"));
    }

}
