package com.example.springboot.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mawy
 * @date 2017/11/23
 */
public enum WPCEnum {
    DEPT_NO("商家编号","deptNo",0),
    DEPT_NAME("商家名称","deptName",1),
    WH_NAME("库房","whName",2),
    BUSINESS_DATE("业务日期","businessDate",3),
    INOUT_TYPE("出入库类型","inOutType",4),
    CHARGING_ACTION("计费动作","chargingAction",5),
    BILL_NO("单据编号","billNo",6),
    BILL_TYPE("单据类型","billType",7),
    SP_SOURCE_BILL_NO("销售平台单据号","spSourceBillNo",8),
    GOODS_NO("商品代码","goodsNo",9),
    GOODS_NAME("商品名称","goodsName",10),
    AMOUNT("数量","amount",11),
    UNIT_PRICE("单价","unitPrice",12),
    AMOUNT_PAID("实收金额","amountPaid",13),
    DATA_STATUS("数据状态","dataStatus",14),
    BUSINESS_TYPE("业务类型","businessType",15),
    SALE_PROPERTY("商品销售属性","saleProperty",16),
    QUOTATION_NAME("报价单名称","quotationName",17),
    FIRST_PRICE("首件价格","firstPrice",18),
    FIRST_NUM("首件数","firstNum",19),
    CONTINUATION_PRICE("续件价格","continuationPrice",20),
    MERCHANT_DISCOUNT("商家折扣","merchantDiscount",21),
    BILLING_TYPE("计费分类","billingType",22);

    private String name;
    private String field;
    private Integer code;
    WPCEnum(String name, String filed, Integer code){
        this.name = name;
        this.field = filed;
        this.code =code;
    }
    public static String getName(int code) {
        for (WPCEnum obj : WPCEnum.values()) {
            if (obj.getCode() == code) {
                return obj.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static String[] getFields() {
        List<String> list = new ArrayList<>();
       for(WPCEnum obj: WPCEnum.values()){
           list.add(obj.field);
       }
        return list.stream().toArray(String[]::new);
    }

    public static String[] getNames() {
        List<String> list = new ArrayList<>();
        for(WPCEnum obj: WPCEnum.values()){
            list.add(obj.name);
        }
        return list.stream().toArray(String[]::new);
    }
}
