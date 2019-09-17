package com.example.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot.SpringbootNApplication;
import com.example.springboot.TestModel;
import com.example.springboot.dao.UserDao;
import com.example.springboot.dao.UserDaoIml;
import com.example.springboot.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.example.springboot.tool.CommonObject.*;

/**
 * @author: yiqq
 * @date: 2018/9/3
 * @description:
 */
@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    UserDao userDao = new UserDaoIml(SpringbootNApplication.sqlSessionFactory);

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String hello(HttpServletRequest request){
        String msg = request.getParameter("name");
        return msg;
    }



    /**
     * 查询所有User部分列
     * @return
     */
    @RequestMapping(value = "/selectsome", method = RequestMethod.GET)
    @ResponseBody
    public List<User> SelectSome() {
        return userDao.selectsome();
    }

    /**
     * 查询所有User
     * @return
     */
    @RequestMapping(value = "/selectall", method = RequestMethod.GET)
    @ResponseBody
    public List<User> SelectAll() {
        return userDao.selectall();
    }

    /**
     * mapper方式实现mybatis查询数据库
     * sqlSession.selectOne方式
     */
    @RequestMapping(value = "/selectone", method = RequestMethod.GET)
    @ResponseBody
    public String GetSelectOne(@RequestParam(value = "name", defaultValue = "文彪承") String name
            , @RequestParam(value = "age", defaultValue = "35") int age) throws Exception{
        User user = new User();
        user.setAge(age);
        user.setName(name);
        String names = userDao.findName(user);
        return names ;
    }

    /**
     * 返回对象，可不传参数，使用默认参数
     */
    @RequestMapping(value = "/goodsCount", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<User> GetGoodCount(@RequestParam(value = "name",defaultValue = "迪丽热巴") String name
            , @RequestParam(value = "age", defaultValue = "25") int age) throws Exception{

        ArrayList<User> user = userDao.findInfoList(name, age);
        return user ;
    }

    /**
     * 返回对象
     */
    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<User> GetGoodList(@RequestParam("name") String name, @RequestParam("age") int age) throws Exception{

        ArrayList<User> user = userDao.findInfoList(name, age);
        return user ;
    }

    /**
     * 分页插件,加锁
     */
    @RequestMapping(value = "/selectallpage",produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public List<User> SelectAllPage(HttpServletRequest request
            , HttpServletResponse response
            ,@RequestParam(value = "start",defaultValue = "1") int start
            , @RequestParam(value = "end",defaultValue = "8") int end) {
        PageHelper.startPage(start, end);
        List<User> userList = userDao.selectall();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo.getList();
    }

    /**
     * insert主键自增长
     * 写入数据库
     */
    @RequestMapping(value = "/insertC", method = RequestMethod.POST)
    @ResponseBody
    public String insert() {
        User user = new User();
        //随机年龄
        Random rand = new Random();
        int age = rand.nextInt(20) + 20;
        user.setAge(age);
        user.setName(getChineseName());
        user.setRole(getRoad());
        user.setEmail(getEmail(6,9));
        user.setPhone(getTel());
        boolean res = userDao.insert(user);
        if(res == true) {
            return "主键自增长插入成功";
        }else {
            return "主键自增长插入失败";
        }
    }

    @RequestMapping(value = "testModel")
    public String testModel(){
        TestModel testModel = new TestModel().setCode("0").setMsg("成功")
                .setCommodityID("R2018").setGoodsSku("201801018").setCommodityName("JJ雨伞").setPrice("25");
        String str = JSON.toJSONString(testModel);//转化为json字符串
        return str;
    }

    @RequestMapping(value = "json")
    public Map<String, Object> getjson(){
        Map<String, Object> map = new HashMap<>();
        TestModel testModel = new TestModel().setCode("0").setMsg("成功")
                .setCommodityID("R2018").setGoodsSku("201801018").setCommodityName("JJ雨伞").setPrice("25");
        /*JSONObject jsonObject = (JSONObject) JSON.toJSON(testModel);//转化为json对象
        return (Map) jsonObject;*/
        map.put("prize", testModel);
        return map;
    }
}
