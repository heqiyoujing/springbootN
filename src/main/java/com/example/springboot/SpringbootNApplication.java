package com.example.springboot;

import com.example.springboot.redis.redisPool.RedisConnPool;
import com.example.springboot.redis.redisPool.RedisConnPoolConfig;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.Reader;

@EnableCaching
@SpringBootApplication
//@ComponentScan(basePackages = "com.example.springboot.test")
public class SpringbootNApplication {

	private Logger logger = LoggerFactory.getLogger(SpringbootNApplication.class);
	private static String env = "local";
	public static SqlSessionFactory sqlSessionFactory;
	public static RedisConnPoolConfig redisConfig;

	public static void main(String[] args) throws Exception{
		new SpringbootNApplication().init();
		SpringApplication.run(SpringbootNApplication.class, args);
	}

	public void init() throws Exception {
		initMySql();
		loadRedisConfig();
		RedisConnPool.getInstance().init(redisConfig);
	}

	/**
	 * 单个数据库
	 */
	private void initMySql(){
		try{
			String resource = String.format("mybatis-config-%s.xml", SpringbootNApplication.env);
			Reader reader = Resources.getResourceAsReader(resource);
			SpringbootNApplication.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			/*InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
			SpringbootNApplication.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//加载redis配置
	private void loadRedisConfig(){
		redisConfig = RedisConnPoolConfig.getInstance();
		redisConfig.setHost("192.168.18.23");
		redisConfig.setPort(6379);
		redisConfig.setPassword("123456");
		redisConfig.setMaxTotal(8);
		redisConfig.setMaxIdle(8);
		redisConfig.setMinIdle(0);
		redisConfig.setMaxWaitMillis(-1);
		redisConfig.setTimeBetweenEvictionRunsMillis(1800000);
		redisConfig.setTimeout(300);
		logger.info("redis初始化完毕");
	}
}
