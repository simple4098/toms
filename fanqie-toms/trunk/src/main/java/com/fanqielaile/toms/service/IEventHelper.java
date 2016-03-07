package com.fanqielaile.toms.service;

import com.alibaba.fastjson.JSONObject;
import com.fanqie.core.dto.TBParam;

/**
 * DESC :事件处理
 * @author : 番茄木-ZLin
 * @data : 2016/3/7
 * @version: v1.0.0
 */
public interface IEventHelper {

    /**
     *
     * @param jsonObject 事件处理的参数
     */
    public void pushEvent(JSONObject jsonObject) throws Exception;
}
