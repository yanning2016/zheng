package com.zheng.demo.concurrent;

/**
 * Created by jondai on 2017/7/9.
 */
public class ConcurrentTest {
    public static void main(String[] args){
        test();
        test();
        test();
    }


    public static void test(){
        String str = "hello";
        Integer count = 100;

        try {
            count ++;
            System.out.println(count);
            Thread.currentThread().sleep(10000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
