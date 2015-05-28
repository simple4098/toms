package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.enums.SendType;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.NoticeTemplate;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.INoticeTemplateService;
import com.fanqielaile.toms.support.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息通知
 * Created by wangdayin on 2015/5/15.
 */
@Controller
@RequestMapping("notice")
public class NoticeTemplateController extends BaseController {
    @Resource
    private INoticeTemplateService noticeTemplateService;
    @Resource
    private IBangInnService bangInnService;
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

    /**
     * 消息通知页面
     *
     * @param model
     * @return
     */
    @RequestMapping("find_notices")
    public String findNotices(Model model) {
        UserInfo currentUser = getCurrentUser();
        //客栈的通知模板
        List<NoticeTemplate> noticeTemplateList = this.noticeTemplateService.findNoticeTemplates(currentUser.getCompanyId());
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, noticeTemplateList);
        //客栈的分类
        List<BangInn> bangInnAndLabel = this.bangInnService.findBangInnAndLabel(currentUser);
        model.addAttribute("bangInnList", bangInnAndLabel);
        return "/notice/notice_list";
    }
}
