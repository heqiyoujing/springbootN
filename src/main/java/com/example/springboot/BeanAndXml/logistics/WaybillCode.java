package com.example.springboot.BeanAndXml.logistics;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author: yiqq
 * @date: 2018/9/26
 * @description: 运单号，用于生成圆通所需的运单号对应的xml字符串。
 */
public class WaybillCode {
    private String Number;

    public WaybillCode(){
    }

    public WaybillCode(String number){
        this.Number=number;
    }

    @XmlElement(name="Number")
    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    @Override
    public String toString() {
        return "WaybillCode{" +
                "Number='" + Number + '\'' +
                '}';
    }
}
