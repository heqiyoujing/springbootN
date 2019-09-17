package com.example.springboot.redis;

import com.example.springboot.redis.redisPool.RedisConnPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author: yiqq
 * @date: 2018/10/25
 * @description:
 */
public class RedisClient {

    private static Logger logger = LoggerFactory.getLogger(RedisClient.class);
    private static JedisPool pool = null;


    public static void init(){
        pool = RedisConnPool.getInstance().getRedisPool();
    }

    /** 获取jedis实例*/
    public static Jedis getJedisObject(){
        if (pool != null) {
            Jedis redis = pool.getResource();
            return redis;
        }else {
            logger.info(pool+"为空");
            return null;
        }
    }

    /**释放jedis对象*/
    public static void recycleJedisObject(final Jedis jedis){
        if (jedis != null) {
            pool.returnResourceObject(jedis);
        }
    }

    /**获取list列表中元素*/
    public static List<String> lrange(String key, int start , int end){
        Jedis jedis = getJedisObject();
        try{
            if(jedis!=null){
                return jedis.lrange(key,start,end);
            }
        }finally {
            recycleJedisObject(jedis);
        }
        return null ;
    }
    /**list列表从左边增加元素*/
    public static void lpush(String key, String value){
        Jedis jedis = getJedisObject();
        if (jedis != null) {
            jedis.lpush(key, value);
        }
        logger.info("lpush :  key:" + key + ", value: " + value);
        recycleJedisObject(jedis);
    }

    /**list列表从右边弹出元素*/
    public static String rpop(String key){
        Jedis jedis = getJedisObject();
        try{
            if(jedis!=null){
                return jedis.rpop(key);
            }
        }finally {
            recycleJedisObject(jedis);
        }
        return null ;
    }

    /**获取list列表长度*/
    public static long llen(String key){
        Jedis jedis = getJedisObject();
        try{
            if(jedis!=null){
                return jedis.llen(key);
            }
        }finally {
            recycleJedisObject(jedis);
        }
        return 0 ;
    }

    /**获取字符串*/
    public static String get(String key){
        Jedis jedis = getJedisObject();
        String value = null;
        if (jedis != null) {
            value = jedis.get(key);
            if (value==null) {
                value = "";
            }
        } else {
            logger.error("redisEnvError! ");
        }
        recycleJedisObject(jedis);
        return value;
    }

    /**添加key value 并且设置过期时间*/
    public static void set(String key,String value,int liveTime){
        Jedis jedis = getJedisObject();
        if (jedis != null) {
            jedis.set(key, value);
            jedis.expire(key, liveTime);
        }
        logger.info("RedisSetKey  key: " + key + ",  liveTime: " + liveTime + "seconds");
        recycleJedisObject(jedis);
    }

    /**添加key value */
    public static void set(String key,String value){
        Jedis jedis = getJedisObject();
        if (jedis != null) {
            jedis.set(key, value);
            logger.info("RedisSetKey  key: " + key + ",  value:  " + value);
        } else {
            logger.error("jedis is null");
        }
        recycleJedisObject(jedis);
    }

    /**删除key value*/
    public static void delete(String key){
        Jedis jedis = getJedisObject();
        if (jedis != null && jedis.exists(key)) {
            jedis.del(key);
        }
        recycleJedisObject(jedis);
    }

    /**散列set*/
    public static void hPut(String key,String field,String value){
        Jedis jedis = getJedisObject();
        try{
            if (jedis != null) {
                jedis.hset(key,field,value);
            }
        }finally {
            recycleJedisObject(jedis);
        }
    }
    /**散列get*/
    public static String hGet(String key,String field){
        Jedis jedis = getJedisObject();
        try{
            if(jedis!=null){
                return jedis.hget(key,field);
            }
        }finally {
            recycleJedisObject(jedis);
        }
        return null ;
    }

    /**集合set*/
    public static void sAdd(String key,String field,String value){
        Jedis jedis = getJedisObject();
        try{
            if (jedis != null) {
                jedis.sadd(key,field,value);
            }
        }finally {
            recycleJedisObject(jedis);
        }
    }
    /**集合get*/
    public static long sRem(String key,String field){
        Jedis jedis = getJedisObject();
        try{
            if(jedis!=null){
                return jedis.srem(key,field);
            }
        }finally {
            recycleJedisObject(jedis);
        }
        return 0L ;
    }





}
