package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.enums.SendType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wangdayin on 2015/5/15.
 */
@Controller
@RequestMapping("notice")
public class NoticeTemplateController extends BaseController {
    /**
     * 发送短信或者系统弹窗
     *
     * @param model
     * @param content
     * @param innIdString
     * @param sendType
     */
    @RequestMapping("send_message")
    public void sendMessage(Model model, @RequestParam String content, @RequestParam String innIdString, @RequestParam SendType sendType) {
        if (SendType.valueOf("MESSAGE").equals(sendType)) {
            //TODO 调用短信接口发送短信
        } else if (SendType.valueOf("POPUP").equals(sendType)) {
            //TODO 调用系统弹窗接口
        } else {
            //TODO 调用短信和系统弹窗接口
        }
    }
}
