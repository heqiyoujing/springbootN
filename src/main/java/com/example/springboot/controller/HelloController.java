package com.example.springboot.controller;

import com.whalin.MemCached.MemCachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

/**
 * @author: yiqq
 * @date: 2018/9/5
 * @description:
 */
@Controller
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private MemCachedClient memCachedClient;

    /**
     * memcache缓存系统
     */
    @RequestMapping("/memcache")
    @ResponseBody
    public String memcache() throws InterruptedException{
        // 放入缓存
        boolean flag = memCachedClient.set("mem", "name");
        // 取出缓存
        Object value = memCachedClient.get("mem");
        System.out.println(value);
        // 3s后过期
        memCachedClient.set("num", "666", new Date(3000));
        /*memCachedClient.set("b", "2", new Date(System.currentTimeMillis()+3000));与上面的区别是当设置了这个时间点
        之后，它会以服务端的时间为准，也就是说如果本地客户端的时间跟服务端的时间有差值，这个值就会出现问题。
        例：如果本地时间是20:00:00,服务端时间为20:00:10，那么实际上会是40秒之后这个缓存key失效*/
        Object key = memCachedClient.get("num");
        System.out.println(key);
        //多线程睡眠3s
        //Thread.sleep(3000);
        key = memCachedClient.get("num");
        System.out.println(value);
        System.out.println(key);
        return "success";
    }

    @RequestMapping("hello")
    public String hello(HashMap<String, Object> map){
        map.put("hello", "欢迎进入HTML页面");
        return "/index";
    }

    /**
     * 默认界面设置
     */
    /*@RequestMapping("")
    public String view(HashMap<String, Object> map){
        map.put("hello", "程序员");
        return "login";
    }*/

    @RequestMapping(value = "/test", /*params = {"name=yiqq"},*/method = RequestMethod.GET)
    @ResponseBody
    public String test(@RequestParam(value = "name", required = false, defaultValue = "yiqq")String name){
        System.out.println(name);
        logger.info("test: 我是test！");
        return "test";
    }

    @RequestMapping(value = "/tests/{name}", /*params = {"name=yiqq"},*/method = RequestMethod.GET)
    @ResponseBody
    public String tests(@PathVariable(value = "name")String name){
        System.out.println(name);
        logger.info("tests: 我是tests！");
        return "tests";
    }

    @RequestMapping(value = "/test_ss", method = RequestMethod.GET)
    @ResponseBody
    public String testss(@CookieValue("JSESSIONID") String sessionId){
        System.out.println("JSESSIONID = " + sessionId);
        return "tests";
    }

}
