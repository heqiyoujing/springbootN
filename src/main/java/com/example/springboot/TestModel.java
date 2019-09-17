package com.example.springboot;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: yiqq
 * @date: 2018/10/18
 * @description:
 */
public class TestModel {
    private String code;
    private String msg;
    @JsonProperty("CommodityID")
    private String CommodityID;
    @JsonProperty("GoodsSku")
    private String GoodsSku;
    @JsonProperty("CommodityName")
    private String CommodityName;
    @JsonProperty("Price")
    private String Price;

    public String getCode() {
        return code;
    }

    public TestModel setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public TestModel setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getCommodityID() {
        return CommodityID;
    }

    public TestModel setCommodityID(String commodityID) {
        this.CommodityID = commodityID;
        return this;
    }

    public String getGoodsSku() {
        return GoodsSku;
    }

    public TestModel setGoodsSku(String goodsSku) {
        this.GoodsSku = goodsSku;
        return this;
    }

    public String getCommodityName() {
        return CommodityName;
    }

    public TestModel setCommodityName(String commodityName) {
        this.CommodityName = commodityName;
        return this;
    }

    public String getPrice() {
        return Price;
    }

    public TestModel setPrice(String price) {
        this.Price = price;
        return this;
    }


    @Override
    public String toString() {
        return "TestModel{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", CommodityID='" + CommodityID + '\'' +
                ", GoodsSku='" + GoodsSku + '\'' +
                ", CommodityName='" + CommodityName + '\'' +
                ", Price='" + Price + '\'' +
                '}';
    }
}
