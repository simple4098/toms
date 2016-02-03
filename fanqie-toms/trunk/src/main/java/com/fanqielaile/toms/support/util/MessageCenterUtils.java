package com.fanqielaile.toms.support.util;

import com.alibaba.fastjson.JSON;
import com.fanqie.util.DateUtil;
import com.tomasky.msp.client.model.PendingNotify;
import com.tomasky.msp.client.service.impl.MessageManageServiceImpl;
import com.tomasky.msp.client.support.MessageBuilder;
import com.tomasky.msp.enumeration.SmsChannel;
import com.tomasky.msp.model.SmsMessage;
import com.tomasky.msp.model.WeChatMessage;
import com.tomato.framework.log.client.BizLogClient;
import com.tomato.framework.log.model.BizLog;
import org.apache.http.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *  消息中心类
 */
public class MessageCenterUtils {


    private static final Logger LOGGER = LoggerFactory.getLogger(MessageCenterUtils.class);

    /**
     *  记录日志, 默认类型  订单管理-流转
     * @param innId 客栈ID
     * @param bizObject 日志对象
     */
    public static  void  sendLog( Integer innId, Object bizObject){
        BizType parentBizType = new BizType(2, "订单管理", null);
        BizType bizType = new BizType(27, "流转", parentBizType);
        sendLog(innId,bizObject,bizType);
    }


    /**
     *记录日志
     * @param innId 客栈ID
     * @param bizObject 日志对象
     * @param bizType 日志类型
     */
    public static  void  sendLog( Integer innId, Object bizObject,BizType bizType) {
        boolean logOpen = ResourceBundleUtil.getBoolean("log.open");
        if (!logOpen) {
            LOGGER.info("日志系统已手动关闭");
            return;
        }

        BizLog bizLog = new BizLog(innId, bizType, "toms", bizObject);
        LOGGER.info("记录日志："+ JSON.toJSON(bizLog));
        new BizLogClient().save(bizLog);


    }

    /**
     *   发送短息给单个用户
     * @param receive 接收者
     * @param content 内容
     */
    public static void  sendSms2One(String receive,String content){
        List<String> receives = new ArrayList<String>();
        receives.add(receive);
        sendSms2Many(receives,content);
    }


    /**
     *  发送短息给多个用户
     * @param receives 短信接收者
     * @param content 内容
     */
    public static void sendSms2Many(List<String> receives,String content){
        try {
            SmsMessage message =  MessageBuilder.buildSmsMessage( receives, SmsChannel.SEND_TYPE_VIP,content);
            LOGGER.info("发送短息："+ JSON.toJSON(message));
            new MessageManageServiceImpl().sendMessage(message);
            LOGGER.info("短息发送成功");
        } catch (Exception e) {
            LOGGER.error("短息发送失败",e);
            throw new RuntimeException(e);
        }
    }


    /**
     *  发送微信
     * @param content
     */
    public  static void sendWeiXin(String content){
        try {
            String openids = ResourceBundleUtil.getString("weixin.openids");
            Asserts.notEmpty(openids,"微信接受不能为空，必须先配置");
            List<String> receivers =  Arrays.asList(openids.split(","));
            PendingNotify notify = new PendingNotify();
            notify.setReceivers(receivers);
            notify.setNotifyTime(DateUtil.formatDateToString(new Date(), DateUtil.FORMAT_DATE_STR));
            notify.setNotifyType("异常订单");
            notify.setPendingTask("待处理任务");
            notify.setTip("TOMS异常单");
            notify.setDescription(content);
            LOGGER.info("发送微信："+ JSON.toJSON(notify));
            WeChatMessage message =  MessageBuilder.buildPendingNotifyWechatMessage(notify);
            new MessageManageServiceImpl().sendMessage(message);
            LOGGER.info("发送微信成功");
        } catch (Exception e) {
            LOGGER.error("微信发送失败",e);
            throw  new RuntimeException(e);
        }
    }

}


