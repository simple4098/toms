package com.fanqielaile.toms.helper;

import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.NoticeTemplate;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
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
     * @param bangInns
     * @param noticeContent
     * @return
     */
    public static SmsMessage createSmsMessage(Company company, List<BangInn> bangInns, String noticeContent) {
        List<String> receiver = new ArrayList<>();
        try {
            String sender = company.getCompanyName();
            String content = noticeContent;
            if (ArrayUtils.isNotEmpty(bangInns.toArray())) {
                for (BangInn bangInn : bangInns) {
                    receiver.add(bangInn.getMobile());
                }
            }
            return MessageBuilder.buildSmsMessage(sender, receiver, content);
        } catch (Exception e) {
            throw new TomsRuntimeException("系统内部错误");
        }


    }

//    public static ImsMessage createImsMessage(){}
}
