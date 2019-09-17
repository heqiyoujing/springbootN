package com.example.springboot.BeanAndXml.logistics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author: yiqq
 * @date: 2018/9/26
 * @description: 解析圆通返回xml格式物流信息的model
 */
//XML文件中的根标识
@XmlRootElement(name = "ufinterface")
@XmlType(propOrder = {
        "waybillProcessInfo"
})
public class LogisticsModel {
    private List<WaybillProcessInfo> WaybillProcessInfo;

    @XmlElementWrapper(name = "Result")
    public List<WaybillProcessInfo> getWaybillProcessInfo() {
        return WaybillProcessInfo;
    }

    public void setWaybillProcessInfo(List<WaybillProcessInfo> waybillProcessInfo) {
        WaybillProcessInfo = waybillProcessInfo;
    }

    @Override
    public String toString() {
        return "ufinterface [ Result=" + WaybillProcessInfo +"]";
    }


}
