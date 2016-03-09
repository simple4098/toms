package com.fanqielaile.toms.support.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanqielaile.toms.support.util.Constants;
import com.tomato.mq.client.event.listener.MsgEventListener;
import com.tomato.mq.client.event.model.MsgEvent;
import com.tomato.mq.client.event.publisher.MsgEventPublisher;
import com.tomato.mq.support.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * DESC : 事件处理，客栈上、下架
 * @author : 番茄木-ZLin
 * @data : 2016/3/7
 * @version: v1.0.0
 */
public class MsgConsumer implements MsgEventListener {

    private static  final Logger log = LoggerFactory.getLogger(MsgConsumer.class);
    @Autowired
    private StringRedisTemplate redisTemplate;
    private String systemName;

    public MsgConsumer(String systemName) {
        this.systemName = systemName;
        log.info("=============start listener===================");
        MsgEventPublisher.getInstance().addListener(this, MessageType.SYS_EVENT, systemName);
    }

    @Override
    public void onEvent(MsgEvent msgEvent) {
        JSONObject jsonObject = JSON.parseObject(msgEvent.getSource().toString());
        String bizType = jsonObject.getString("bizType");
        if (Constants.INN_UP_DOWN.equals(bizType)){
            String content = jsonObject.getString("content");
            log.info("=====队列增加数据=================="+content);
            //TBParam tbParam = JacksonUtil.json2obj(content, TBParam.class);
            ListOperations<String, String> operations = redisTemplate.opsForList();
            operations.leftPush(Constants.REDIS,content);
        }
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

}
