package com.yfengleng.util;

import java.util.Random;

public class RandomUtil {
    public static int random(int a,int b) {
        int temp = 0;
        try{
            if(a>b){
                temp = new Random().nextInt(a-b);
                return temp+b;
            }else{
                temp = new Random().nextInt(b-a);
                return temp+a;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return temp+a;
    }
}
