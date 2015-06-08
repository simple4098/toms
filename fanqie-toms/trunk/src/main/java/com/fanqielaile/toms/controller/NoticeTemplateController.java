package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.enums.SendType;
import com.fanqielaile.toms.helper.BangInnDataCheckHelper;
import com.fanqielaile.toms.helper.MessageHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.NoticeTemplate;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.ICompanyService;
import com.fanqielaile.toms.service.INoticeTemplateService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.Constants;
import com.tomasky.msp.client.service.IMessageManageService;
import com.tomasky.msp.client.service.impl.MessageManageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 消息通知
 * Created by wangdayin on 2015/5/15.
 */
@Controller
@RequestMapping("notice")
public class NoticeTemplateController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NoticeTemplateController.class);
    @Resource
    private INoticeTemplateService noticeTemplateService;
    @Resource
    private IBangInnService bangInnService;
    @Resource
    private ICompanyService companyService;
    @Resource
    private IMessageManageService messageManageService;

    /**
     * 发送短信或者系统弹窗
     *
     * @param model
     * @param noticeContent
     * @param innId
     * @param sendType
     */
    @RequestMapping("send_message")
    public void sendMessage(Model model, String noticeContent, String innId, String sendType) throws IOException {
        try {
            logger.info("发送消息通知传入参数:noticeContent=" + noticeContent + " innId=" + innId + " +sendType=" + sendType);
            //构建发送短信对象
            UserInfo currentUser = getCurrentUser();
            //公司信息
            Company company = this.companyService.findCompanyByid(currentUser.getCompanyId());
            //绑定客栈信息
            List<BangInn> bangInns = this.bangInnService.findBangInnByStringBangInn(BangInnDataCheckHelper.dealStringInnIds(innId));
            if (SendType.MESSAGE.name().equals(sendType)) {
                messageManageService.sendMessage(MessageHelper.createSmsMessage(company, bangInns, noticeContent));
            } else if (SendType.POPUP.name().equals(sendType)) {
                //TODO 调用系统弹窗接口
            } else {
                //TODO 调用短信和系统弹窗接口
            }
        } catch (Exception e) {
            logger.error("发送消息失败", e);
            throw new TomsRuntimeException("system  is error");
        }
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
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
