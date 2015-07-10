package com.fanqielaile.toms.enums;

import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.service.impl.MTService;
import com.fanqielaile.toms.service.impl.TBService;
import com.fanqielaile.toms.support.SpringContextUtil;

/**
 * DESC :
 *
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
    },
    MT{
        @Override
        public ITPService create() {
            return (MTService)SpringContextUtil.getBean("mtService");
        }
    };
    public abstract ITPService create();
}
