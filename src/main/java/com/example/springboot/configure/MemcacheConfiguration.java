package com.example.springboot.configure;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yiqq
 * @date: 2018/9/10
 * @description:
 * Memcache:不支持持久化、只支持key-value键值、多线程 、集群分布式
 * Redis：支持持久化、丰富的数据类型、单线程 、集群分布式
 * Ehcache：直接在jvm虚拟机中缓存，速度快，效率高，单个应用或者对缓存访问要求很高的应用，用ehcache。核心程序仅仅依赖slf4j。
 */
@Configuration
public class MemcacheConfiguration {

    @Value("${memcache.servers}")
    private String[] servers;
    @Value("${memcache.failover}")
    private boolean failover;
    @Value("${memcache.initConn}")
    private int initConn;
    @Value("${memcache.minConn}")
    private int minConn;
    @Value("${memcache.maxConn}")
    private int maxConn;
    @Value("${memcache.maintSleep}")
    private int maintSleep;
    @Value("${memcache.nagle}")
    private boolean nagel;
    @Value("${memcache.socketTO}")
    private int socketTO;
    @Value("${memcache.aliveCheck}")
    private boolean aliveCheck;

    @Bean
    public SockIOPool sockIOPool () {
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setFailover(failover);
        pool.setInitConn(initConn);
        pool.setMinConn(minConn);
        pool.setMaxConn(maxConn);
        pool.setMaintSleep(maintSleep);
        pool.setNagle(nagel);
        pool.setSocketTO(socketTO);
        pool.setAliveCheck(aliveCheck);
        pool.initialize();
        return pool;
    }

    @Bean
    public MemCachedClient memCachedClient(){
        return new MemCachedClient();
    }

}
