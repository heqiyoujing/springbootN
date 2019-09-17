package com.example.springboot.controller;

import com.example.springboot.SpringbootNApplication;
import com.example.springboot.dao.UserDao;
import com.example.springboot.dao.UserDaoIml;
import com.example.springboot.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: yiqq
 * @date: 2018/10/24
 * @description:
 */
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    UserDao userDao = new UserDaoIml(SpringbootNApplication.sqlSessionFactory);

    /**
     * 查询所有User部分列
     * @return
     */
    @RequestMapping(value = "/SelectLambda", method = RequestMethod.GET)
    @ResponseBody
    public List<User> SelectLambda(@RequestParam(value = "name", defaultValue = "文彪承") String name) {
        List<User> list = userDao.selectLambda(name);
        list.forEach(user->{
            System.out.println(user);
            logger.info(user.toString());
        });
        return list;
    }
}
