package com.example.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @author: yiqq
 * @date: 2018/10/25
 * @description:
 */
@RestController
public class RedisController {

    public  static Integer EXPIRE_TIME = 24*60*60;
    private static String REDIS_LIST_KEY = "redis_name_all";

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/redis")
    public String set(){
        RedisClient.init();
        //往redis写入字符串
        RedisClient.set("yiqq","程序员",EXPIRE_TIME);
        System.out.println(RedisClient.get("yiqq"));
        //往redis写入散列值
        RedisClient.hPut("car","name","BenChi");
        RedisClient.hPut("car","prive","100");
        RedisClient.hPut("car","version","X5");
        System.out.println(RedisClient.hGet("car","version"));
        //往redis写入list列表
        RedisClient.lpush(REDIS_LIST_KEY,"yiqq"+ new Random(100).nextInt());
        if(RedisClient.llen(REDIS_LIST_KEY)>10){
            RedisClient.rpop(REDIS_LIST_KEY);
        }
        List<String> list = RedisClient.lrange(REDIS_LIST_KEY, 0, 5);
        System.out.println(list.toString());


        return "1";
    }
}
