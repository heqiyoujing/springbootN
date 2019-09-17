package com.example.springboot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yiqq
 * @date: 2018/9/29
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        for (Integer a : list) {
            if (a > 2) {
                continue;
            }
            System.out.println(a);
        }


        Map<String, Object> result = new HashMap<>(2);
        result.putAll(get());
        System.out.println(result);


    }

    public static Map<String, Object> get(){
        Map<String, Object> results = new HashMap<>(2);
        for (int i = 0; i < 10; i++) {
            results.put("1", "result");
            results.put("2", "success");
        }
        results.put("1", "aaaaa");
        results.put("2", "bbbbb");
        return results;
    }
}
