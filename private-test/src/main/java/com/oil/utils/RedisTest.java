package com.oil.utils;

import com.oil.mapper.OilInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

@Component
public class RedisTest {
    @Resource
    OilInfoDAO oilInfoDAO;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    private void testRedis() {
//        String key = "redisKey";
//        valueOperations.set(key, "redisValue");
//        String value = valueOperations.get(key);
//        System.out.println("redis value is: " + value);
        HashMap hashMap = oilInfoDAO.searchOilInfo(1);
        System.out.println(hashMap);
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        RedisTest redisTest = context.getBean(RedisTest.class);
        redisTest.testRedis();
    }
}
