package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.NoticeTemplate;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IInnLabelService;
import com.fanqielaile.toms.service.INoticeTemplateService;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangdayin on 2015/5/12.
 */
@Controller
@RequestMapping("system")
public class SystemController extends BaseController {
    @Resource
    private IInnLabelService iInnLabelService;
    @Resource
    private INoticeTemplateService noticeTemplateService;

    /**
     * 查询当前登陆用户所在的客栈标签
     *
     * @param model
     */
    @RequestMapping("find_label")
    public void findLabels(Model model) {
        //TODO 获取当前登陆用户中的所属公司
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, this.iInnLabelService.findLabelsByCompanyId(null));
    }

    /**
     * 创建客栈标签
     *
     * @param model
     * @param innLabel
     */
    @RequestMapping("create_inn_label")
    public void createInnLabel(Model model, InnLabel innLabel) {
        if (StringUtils.isNotEmpty(innLabel.getLabelName())) {
            //TODO 获取当前登陆用户
//            UserInfo currentUser = getCurrentUser();
//            innLabel.setCompanyId(currentUser.getCompanyId());
            this.iInnLabelService.createInnLabel(innLabel);
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "请检查参数");
        }
    }

    /**
     * 根据标签ID查询客栈标签
     *
     * @param model
     * @param id    标签ID
     */
    @RequestMapping("find_label_id")
    public void findLabelById(Model model, String id) {
        InnLabel innLabel = this.iInnLabelService.findLabelById(id);
        if (null != innLabel) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, innLabel);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "没有找到标签");
        }
    }

    /**
     * 更新客栈标签
     *
     * @param model
     * @param innLabel
     */
    @RequestMapping("update_label")
    public void updateLabel(Model model, InnLabel innLabel) {
        boolean flag = this.iInnLabelService.modifyLableById(innLabel);
        if (flag) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "修改失败,没有找到该标签信息");
        }
    }

    /**
     * 删除标签
     *
     * @param model
     * @param id
     */
    @RequestMapping("delete_label")
    public void deleteLabel(Model model, String id) {
        boolean flag = this.iInnLabelService.removeLabelById(id);
        if (flag) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "删除失败，不存在该标签");
        }
    }

    /**
     * 查询通知模板列表
     *
     * @param model
     */
    @RequestMapping("find_notices")
    public void findNotices(Model model) {
        //TODO 获取当前登陆用户所在的公司
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, this.noticeTemplateService.findNoticeTemplates(null));
    }

    /**
     * 新增通知模板
     *
     * @param model
     * @param noticeTemplate
     */
    @RequestMapping("create_notice")
    public void createNotice(Model model, NoticeTemplate noticeTemplate) {
        //TODO 获取当前登陆用户中的所属公司
        if (StringUtils.isNotEmpty(noticeTemplate.getNoticeTitle()) && StringUtils.isNotEmpty(noticeTemplate.getNoticeContent())) {
            this.noticeTemplateService.createNoticeTemplate(noticeTemplate);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "请检查参数");
        }
    }

    /**
     * 根据id查询通知模板
     *
     * @param model
     * @param id
     */
    @RequestMapping("find_notice")
    public void findNotice(Model model, String id) {
        NoticeTemplate noticeTemplate = this.noticeTemplateService.findNoticeTemplateById(id);
        if (noticeTemplate == null) {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "没有改通知模板信息");
        } else {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, noticeTemplate);
        }
    }

    /**
     * 修改通知模板
     *
     * @param model
     * @param noticeTemplate
     */
    @RequestMapping("update_notice")
    public void updateNotice(Model model, NoticeTemplate noticeTemplate) {
        boolean flag = this.noticeTemplateService.modifyNoticeTemplate(noticeTemplate);
        if (flag) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "修改失败");
        }
    }

    /**
     * 删除通知模板
     *
     * @param model
     * @param id
     */
    @RequestMapping("delete_notice")
    public void deleteNotice(Model model, String id) {
        boolean flag = this.noticeTemplateService.removeNoticeTemplateById(id);
        if (flag) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "删除失败");
        }

    }

}
