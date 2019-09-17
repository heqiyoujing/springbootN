package com.example.springboot.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class StringUtil {

    public final static String andString(String... ss) {
        if (ss == null) return null;
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < ss.length; i++) {
            if(ss[i] == null)continue;
            buf.append(ss[i]);
        }
        return buf.toString();
    }

    public static List<String> split(String strS, String strChar) {
        List<String> list = new ArrayList<String>();
        int index = strS.indexOf(strChar);
        String strTmp = strS;
        int len = strS.length(), charLen = strChar.length();
        if (index == -1) {
            list.add(strS); // 只有一个条件时的处理
        } else {
            while (index > -1) { // 两个以上条件的处理
                len = strTmp.length();
                list.add(strTmp.substring(0, index + charLen - 1));
                strTmp = strTmp.substring(index + strChar.length(), len);
                index = strTmp.indexOf(strChar);
                if (index == -1) { // 剩下最后一个条件时
                    list.add(strTmp.substring(0, strTmp.length()));
                }
            }
        }
        return list;
    }

    public static boolean isEmpty(String str) {
        boolean isEmpty = true;
        if (str != null && !"".equals(str.trim())) {
            isEmpty = false;
        }
        return isEmpty;
    }

    public static String isNull(Object str){
        String ss = "";
        if(str != null && !"".equals(str.toString().trim())){
            ss = str.toString();
        }
        return ss;
    }

    public static byte[] charSet(String str){
        try {
            if(str==null)return "".getBytes("UTF-8");
            else return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "".getBytes();
    }

    public static boolean validateNull(String ...strArrays){
        boolean flag = true;
        for(String str:strArrays){
            if(str==null||("").equals(str)||str.equals("null")||("undefined").equals(str)){
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static String urlDecode(String str) {
        String result = "";
        if (str != null && !"".equals(str)) {
            try {
                result = URLDecoder.decode(str, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
