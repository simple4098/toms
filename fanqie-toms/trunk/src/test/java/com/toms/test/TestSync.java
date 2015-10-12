package com.toms.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/10/10
 * @version: v1.0.0
 */
public class TestSync {

    static void test(){
        ExecutorService es = Executors.newFixedThreadPool(100);
        final  int b=0;
        for(int i=0;i<100000;i++){
            final int finalI = i;
            es.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    System.out.println(b);
                    return null;
                }
            });
        }

    }

    public static void main(String[] args) {
        test();
    }
}
