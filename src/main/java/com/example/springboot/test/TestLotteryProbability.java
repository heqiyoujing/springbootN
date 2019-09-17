package com.example.springboot.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yiqq
 * @date: 2018/10/20
 * @description:
 */
public class TestLotteryProbability {


    public static void main(String[] args) {
        List<Double> lotteryProbList = new ArrayList<>();
        lotteryProbList.add(0.0001);
        lotteryProbList.add(0.0008);
        lotteryProbList.add(0.34);
        lotteryProbList.add(0.33);
        lotteryProbList.add(0.25);
        lotteryProbList.add(0.06);
        lotteryProbList.add(0.0181);
        lotteryProbList.add(0.001);


        int count1 = 0;
        int count2 = 0;
        int count3 = 0;

        for (int i=0;i<100000;i++) {
            int num = LotteryProbability.draw(lotteryProbList);
            if(num == 2){
                count1++;
            }
            if(num == 3){
                count2++;
            }
            if(num == 4){
                count3++;
            }
        }
        System.out.println(count1);
        System.out.println(count2);
        System.out.println(count3);

    }


}
