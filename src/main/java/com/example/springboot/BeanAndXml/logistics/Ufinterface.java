package com.example.springboot.BeanAndXml.logistics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author: yiqq
 * @date: 2018/9/26
 * @description: 用于生成圆通所需的运单号对应的xml字符串:
 * <?xml  version="1.0"?>
 * <ufinterface>
 * <Result>
 * <WaybillCode>
 * <Number>运单号</Number>
 * </WaybillCode>
 * </Result>
 * </ufinterface>
 */
//XML文件中的根标识
@XmlRootElement(name = "ufinterface")
public class Ufinterface {
    private Result result;

    public Ufinterface(){
    }

    public Ufinterface(Result result){
        this.result = result;
    }

    @XmlElement(name="Result")
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ufinterface{" +
                "Result=" + result +
                '}';
    }

    public static class Result{
        private WaybillCode waybillCode;
        public Result(){
        }
        public Result(WaybillCode waybillCode){
            this.waybillCode = waybillCode;
        }

        @XmlElement(name="WaybillCode")
        public WaybillCode getWaybillCode() {
            return waybillCode;
        }

        public void setWaybillCode(WaybillCode waybillCode) {
            this.waybillCode = waybillCode;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "WaybillCode=" + waybillCode +
                    '}';
        }
    }
}
