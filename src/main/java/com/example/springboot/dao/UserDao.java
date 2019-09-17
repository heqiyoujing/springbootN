package com.example.springboot.dao;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: yiqq
 * @date: 2018/7/20
 * @description:
 */
public interface UserDao {
    ArrayList<User> findInfoList(@Param("name") String name, @Param("age") int age);

    Integer findCount(@Param("name") String name, @Param("age") int age);

    String findName(User user);

    boolean insert(User user);

    List<User> selectall();

    List<User> selectsome();

    List<User> selectLambda(String name);



}
