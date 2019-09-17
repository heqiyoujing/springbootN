package com.example.springboot.BeanAndXml.logistics;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author: yiqq
 * @date: 2018/9/26
 * @description: 物流信息
 */
public class WaybillProcessInfo {
    private String waybill_No; //运单号
    private String upload_Time; //走件产生时间
    private String processInfo; //物流信息

    @XmlElement(name = "Waybill_No")
    public String getWaybill_No() {
        return waybill_No;
    }

    public void setWaybill_No(String waybill_No) {
        this.waybill_No = waybill_No;
    }

    @XmlElement(name = "Upload_Time")
    public String getUpload_Time() {
        return upload_Time;
    }

    public void setUpload_Time(String upload_Time) {
        this.upload_Time = upload_Time;
    }

    @XmlElement(name = "ProcessInfo")
    public String getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(String processInfo) {
        this.processInfo = processInfo;
    }

    @Override
    public String toString() {
        return "WaybillProcessInfo{" +
                "waybill_No='" + waybill_No + '\'' +
                ", upload_Time='" + upload_Time + '\'' +
                ", processInfo='" + processInfo + '\'' +
                '}';
    }

}
