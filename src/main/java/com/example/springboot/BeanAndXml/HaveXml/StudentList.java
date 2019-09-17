package com.example.springboot.BeanAndXml.HaveXml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author: yiqq
 * @date: 2018/9/25
 * @description:
 */
@XmlRootElement(name="list")
public class StudentList {

    List<Student> students;    //所有学生信息的集合

    @XmlElement(name = "student")
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
