package com.fanqielaile.toms.support.util;

import com.alibaba.fastjson.JSON;
import com.fanqie.util.DateUtil;
import com.fanqie.util.PropertiesUtil;
import com.fanqielaile.toms.dto.orderLog.OrderBizType;
import com.fanqielaile.toms.dto.orderLog.OrderLogData;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.LogDec;
import com.fanqielaile.toms.enums.OrderLogDec;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.Order;
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

    private static BizLogClient bizLogClient = new BizLogClient();

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


    /**
     *
     * @param otaType 渠道类型
     * @param innId 客栈id
     * @param roomTypeId 房型id
     * @param userName 用户名称（用户id），可以为null； 如果为null就填系统操作，
     * @param logDec 日志描述
     * @param content 日志内容
     */
    public static void savePushTomsLog(OtaType otaType, Integer innId, Integer roomTypeId,
                                       String userName, LogDec logDec, String content){
        boolean logOpen = ResourceBundleUtil.getBoolean("log.open");
        if (logOpen){
            LOGGER.info("=====记录日志开始======");
            BizType parentBizType = new BizType(logDec.getpId(),logDec.getValue(), null);
            BizType bizType = new BizType(logDec.getLogTypeId(), logDec.getValue(), parentBizType);
            BizData bizData = new BizData(logDec,userName==null?"系统操作":userName,content,innId,roomTypeId,otaType);
            BizLog bizLog = new BizLog(innId, bizType, "TOMS", bizData);
            bizLogClient.save(bizLog);
            LOGGER.info("=====记录日志结束======");
        }
    }

    public static void savePushTomsLog(ChannelSource channelSource, Integer innId, Integer roomTypeId,
                                       String userName, LogDec logDec, String content){
        boolean logOpen = ResourceBundleUtil.getBoolean("log.open");
        if (logOpen){
            LOGGER.info("=====记录日志开始======");
            BizType parentBizType = new BizType(logDec.getpId(),logDec.getValue(), null);
            BizType bizType = new BizType(logDec.getLogTypeId(), logDec.getValue(), parentBizType);
            BizData bizData = new BizData(logDec,userName==null?"系统操作":userName,content,innId,roomTypeId,channelSource);
            BizLog bizLog = new BizLog(innId, bizType, "TOMS", bizData);
            bizLogClient.save(bizLog);
            LOGGER.info("=====记录日志结束======");
        }
    }

    /**
     * 推送toms订单日志
     */
    public static void savePushTomsOrderLog(Integer innId, OrderLogDec logDec, OrderLogData logData) {
        boolean aBoolean = ResourceBundleUtil.getBoolean("log.open");
        if (aBoolean) {
            LOGGER.info("=====记录日志开始======");
            OrderBizType orderBizType = new OrderBizType(logDec.getLogTypeId(), logDec.getpId());
            BizLog biz = new BizLog(innId, orderBizType, "TOMS", logData);
            bizLogClient.save(biz);
            LOGGER.info("=====记录日志结束======");
        }
    }

	/**
	 * 发送取消订单申请微信
	 * 
	 * @param order
	 * 			channelOrderCode 订单号
	 *       	refundStatus 是否扣款
	 * @param innName 申请客栈名
	 */
	public static void sendApplyCancelOrderWeiXin(Order order, String innName) {
		try {
			String openids = ResourceBundleUtil.getString("weixin.apply.cancel.order.openids");
			Asserts.notEmpty(openids, "微信接受不能为空，必须先配置");
			List<String> receivers = Arrays.asList(openids.split(","));
			String title = "有新的信用住订单申请取消！";
			String project = "信用住  未入住";
			String applyTime = DateUtil.format(new Date(), DateUtil.FORMAT_DATE_STR_ONE);
			String remark;
			if (order.isRefundStatus()) {
				remark = "备注：扣款";
			} else {
				remark = "备注：不扣款";
			}
			LOGGER.info("Apply for WeChat sent to cancel the order：title = " + title + ", applyTime = " + applyTime + ", orderNo = "
					+ order.getChannelOrderCode() + ", applier = " + innName + ", remark = " + remark);
			WeChatMessage message = MessageBuilder.buildCancelApplyWechatMessage(title, project, applyTime,
					order.getChannelOrderCode(), innName, remark, receivers);
			new MessageManageServiceImpl().sendMessage(message);
			LOGGER.info("Send cancel the order application WeChat success");
		} catch (Exception e) {
			LOGGER.error("Cancel the order for WeChat send failure！", e);
			throw new RuntimeException("发送微信失败！", e);
		}
	}
}


