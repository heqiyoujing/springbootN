package com.example.springboot.BeanAndXml.NoXml;
import java.util.ArrayList;
import java.util.List;
/**
 * @author: yiqq
 * @date: 2018/9/25
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("邪恶小法师");
        student.setAge("110");
        student.setSex("男");
        List<Student.Friend> list = new ArrayList<>();
        Student.Friend f1 = new Student.Friend();
        f1.setName("德玛西亚之力");
        f1.setAge("888");
        f1.setSex("男");
        Student.Friend f2 = new Student.Friend();
        f2.setName("无双剑姬");
        f2.setAge("898");
        f2.setSex("女");
        list.add(f1);
        list.add(f2);
        student.setFriend(list);				//将java对象转换为XML字符串
        String xmlStr = XmlAndJavaObjectConvert.convertToXml(student);
        System.out.println(xmlStr);				//将xml字符串转换为java对象
        System.out.println(XmlAndJavaObjectConvert.convertXmlStrToObject(Student.class, xmlStr));
        Student student1 = (Student)XmlAndJavaObjectConvert.convertXmlStrToObject(Student.class, xmlStr);
        System.out.println(student1.getFriend());

    }

 }
