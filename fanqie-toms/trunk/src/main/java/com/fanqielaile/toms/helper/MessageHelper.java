package com.fanqielaile.toms.helper;

import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.NoticeTemplate;
import com.fanqielaile.toms.model.UserInfo;
import com.tomasky.msp.client.service.IMessageManageService;
import com.tomasky.msp.client.service.impl.MessageManageServiceImpl;
import com.tomasky.msp.model.ImsMessage;
import com.tomasky.msp.model.SmsMessage;
import com.tomasky.msp.support.builder.MessageBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 发送消息
 * Created by wangdayin on 2015/6/4.
 */
public class MessageHelper {
    public static boolean sendMessageHelper() {
        return true;
    }

    /**
     * 创建短信发送对象
     *
     * @param company
     * @param mobile
     * @param noticeTemplate
     * @return
     */
    public static SmsMessage createSmsMessage(Company company, String mobile, NoticeTemplate noticeTemplate) {
        List<String> receiver = new ArrayList<>();
        if (StringUtils.isNotEmpty(mobile)) {
            String[] mobiles = mobile.split(",");
            if (ArrayUtils.isNotEmpty(mobiles)) {
                for (int i = 0; i < mobiles.length; i++) {
                    receiver.add(mobiles[i]);
                }
            }
        }
        String sender = company.getCompanyName();
        String content = noticeTemplate.getNoticeContent();
        return MessageBuilder.buildSmsMessage(sender, receiver, content);
    }

//    public static ImsMessage createImsMessage(){}
}
