package com.fanqielaile.toms.support.util;

import com.fanqielaile.toms.support.CallableBean;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/10/13
 * @version: v1.0.0
 */
public class ThreadCallableBean {

    private static ThreadLocal<CallableBean> tc = new ThreadLocal<>();

    public ThreadCallableBean() {
    }

    public static void setLocalThreadBean(CallableBean callableBean){
        tc.set(callableBean);
    }

    public static CallableBean getLocalThreadBean(){
        return tc.get();
    }
}
