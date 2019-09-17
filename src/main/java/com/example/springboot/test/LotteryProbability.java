package com.example.springboot.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: yiqq
 * @date: 2018/10/20
 * @description:
 */
public class LotteryProbability {
    public static int draw(List<Double> lotteryProbList) {
        List<Double> sortRateList = new ArrayList<Double>();
        double sumRate = 0d;
        // 计算概率总和
        sumRate = lotteryProbList.stream().mapToDouble(Double::doubleValue).sum();
        // 随机生成一个随机数
        double random = new java.security.SecureRandom().nextDouble();
        if (sumRate != 0) {
            double rate = 0d;
            for (int i = 0; i < lotteryProbList.size(); i++) {
                double prob = lotteryProbList.get(i);
                rate += prob;
                sortRateList.add(rate / sumRate); //计算区间
            }
            sortRateList.add(random);
            Collections.sort(sortRateList);
            return sortRateList.indexOf(random);
        }
        return -1;
    }
}
