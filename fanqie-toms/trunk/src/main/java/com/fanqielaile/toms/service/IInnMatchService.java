package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/15
 * @version: v1.0.0
 */
public interface IInnMatchService {

    /**
     * 获取oms客栈数据
     * @param bangInn 绑定客栈信息
     */
    InnDto obtOmsInn(BangInn bangInn) throws TomsRuntimeException;
}
