package com.example.springboot.BeanAndXml.NoXml;
import java.util.List;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author: yiqq
 * @date: 2018/9/25
 * @description:
 */

//XML文件中的根标识
@XmlRootElement(name = "Student")
//控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder = {
        "name",
        "age",
        "sex",
        "friend"
})
public class Student {
    private String name;
    private String age;
    private String sex;
    private List<Friend> friend;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    @XmlElementWrapper(name="friendList")
    public List<Friend> getFriend() {
        return friend;
    }
    public void setFriend(List<Friend> friend) {
        this.friend = friend;
    }
    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", sex=" + sex + ", friendList=" + friend + "]";
    }
    public static class Friend{
        private String name;
        private String age;
        private String sex;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }		public String getAge() {
            return age;
        }
        public void setAge(String age) {
            this.age = age;
        }
        public String getSex() {
            return sex;
        }
        public void setSex(String sex) {
            this.sex = sex;
        }
        @Override
        public String toString() {
            return "Friend [name=" + name + ", age=" + age + ", sex=" + sex + "]";
        }
    }

}
