package com.toms.test;

import com.fanqielaile.toms.enums.SendType;

/**
 * Created by wangdayin on 2015/5/15.
 */
public class TestMehod {
    public static void main(String[] args) {
        if (SendType.valueOf("MESSAGE").equals(SendType.MESSAGE)) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }
    }
}
