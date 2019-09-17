package com.example.springboot.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: yiqq
 * @date: 2018/9/12
 * @description:
 */
public class AppConfigTest {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        MyService myService = ctx.getBean(MyService.class);
        System.out.println(myService.hello());
    }
}
