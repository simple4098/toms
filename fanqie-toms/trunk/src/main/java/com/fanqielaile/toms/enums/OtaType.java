package com.fanqielaile.toms.enums;

import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.service.impl.*;
import com.fanqielaile.toms.support.SpringContextUtil;

/**
 * DESC : 渠道类型
 * @author : 番茄木-ZLin
 * @data : 2015/7/3
 * @version: v1.0.0
 */
public enum OtaType {
    TB{
        @Override
        public ITPService create() {
            return (TBService)SpringContextUtil.getBean("tbService");
        }
    },FC{
        @Override
        public ITPService create() {
            return (FcService)SpringContextUtil.getBean("fcService");
        }
    },XC{
        @Override
        public ITPService create() {
            return (XcService)SpringContextUtil.getBean("xcService");
        }
    },ZH{
        @Override
        public ITPService create() {
            return (ZhService)SpringContextUtil.getBean("zhService");
        }
    },CREDIT{
        @Override
        public ITPService create() {
            return (CreditService)SpringContextUtil.getBean("creditService");
        }
    }, QUNAR {
        @Override
        public ITPService create() {
            return (QunarService) SpringContextUtil.getBean("qunarService");
        }
    };
    public abstract ITPService create();
}
