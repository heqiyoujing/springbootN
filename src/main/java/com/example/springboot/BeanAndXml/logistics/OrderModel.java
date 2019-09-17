package com.example.springboot.BeanAndXml.logistics;

/**
 * @author: yiqq
 * @date: 2018/9/27
 * @description: 订单号和运单号
 */
public class OrderModel {
    private String order_id; //订单号
    private String waybill;  //运单号

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }
}
