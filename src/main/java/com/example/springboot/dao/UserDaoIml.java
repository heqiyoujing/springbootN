package com.example.springboot.dao;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yiqq
 * @date: 2018/7/20
 * @description:
 */
public class UserDaoIml implements UserDao{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private SqlSessionFactory sessionFactory;
    public UserDaoIml(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ArrayList<User> findInfoList(@Param("name") String name, @Param("age") int age) {
        SqlSession sqlSession = null;
        ArrayList<User> users = new ArrayList();
        try {
            sqlSession = sessionFactory.openSession();
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            users = mapper.findInfoList(name, age);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return  users;
        }
    }

    public Integer findCount(@Param("name") String name, @Param("age") int age) {
        SqlSession sqlSession = null;
        int users =0;
        try {
            sqlSession = sessionFactory.openSession();
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            users = mapper.findCount(name, age);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return  users;
        }
    }

    public String findName(User user) {
        SqlSession sqlSession = null;
        String nameR =null;
        try {
            sqlSession = sessionFactory.openSession();
            nameR = sqlSession.selectOne("com.example.springboot.dao.UserDao.findName", user);
            return nameR;
        } catch (Exception e) {
            e.printStackTrace();
            return  nameR;
        }
    }

    public boolean insert(User user) {
        SqlSession sqlSession = null;
        boolean res =false;
        try {
            sqlSession = sessionFactory.openSession();
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            res = mapper.insert(user);
            if( res == true) {
                sqlSession.commit();
                res = true;
            } else {
                sqlSession.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    public List<User> selectall() {
//        System.out.println("执行数据库查询");
        SqlSession sqlSession = null;
        List<User> res =new ArrayList<>();
        try {
            sqlSession = sessionFactory.openSession();
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            res = mapper.selectall();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<User> selectsome() {
//        System.out.println("执行数据库查询");
        SqlSession sqlSession = null;
        List<User> res =new ArrayList<>();
        try {
            sqlSession = sessionFactory.openSession();
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            res = mapper.selectsome();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    public List<User> selectLambda(String name) {
//        System.out.println("执行数据库查询");
        SqlSession sqlSession = null;
        List<User> res =new ArrayList<>();
        try {
            sqlSession = sessionFactory.openSession();
            res = sqlSession.selectList("com.example.springboot.dao.UserDao.SelectLambda", name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
