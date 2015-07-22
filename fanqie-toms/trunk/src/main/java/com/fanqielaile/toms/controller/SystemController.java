package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.IInnLabelService;
import com.fanqielaile.toms.service.INoticeTemplateService;
import com.fanqielaile.toms.service.IPermissionService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.Constants;
/*import com.tomato.framework.log.support.UserInfoContext;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 设置
 * Created by wangdayin on 2015/5/12.
 */
@Controller
@RequestMapping("system")
public class SystemController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(SystemController.class);
    @Resource
    private IInnLabelService iInnLabelService;
    @Resource
    private INoticeTemplateService noticeTemplateService;
    @Resource
    private IPermissionService permissionService;
    @Resource
    private IBangInnService bangInnService;

    /**
     * 登陆成功跳转
     *
     * @param model
     */
    @RequestMapping("login_success")
    public String loginSuccess(Model model) {
        try {
            UserInfo currentUser = getCurrentUser();
            List<Permission> permissionList = this.permissionService.findPermissionByCompanyId(currentUser.getCompanyId());
            model.addAttribute(Constants.DATA, permissionList);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
         /*   UserInfoContext.setUserInfo(currentUser.getId());*/
        } catch (Exception e) {
            logger.error("用户登录失败", e);
        }
        return "welcome";
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping("logout")
    public String logout() {
        //日志
       /* UserInfoContext.release();*/
        //清空session
        SecurityContextHolder.clearContext();
        return "login";
    }

    /**
     * 查询当前登陆用户所在公司客栈标签
     *
     * @param model
     */
    @RequestMapping("find_labels")
    public String findLabels(Model model) {
        try {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, this.iInnLabelService.findLabelsByCompanyId(getCurrentUser().getCompanyId()));
        } catch (Exception e) {
            logger.error("查询当前登录用户所在公司的标签，查询错误", e);
        }
        return "/system/label_list";
    }

    /**
     * 创建客栈标签
     *
     * @param innLabel
     */
    @RequestMapping("create_inn_label")
    public String createInnLabel(@Valid InnLabel innLabel) {
        try {
            UserInfo currentUser = getCurrentUser();
            innLabel.setCompanyId(currentUser.getCompanyId());
            this.iInnLabelService.createInnLabel(innLabel);
        } catch (Exception e) {
            logger.error("创建客栈标签失败", e);
        }

        return redirectUrl("/system/find_labels");
    }

    /**
     * 根据标签ID查询客栈标签
     *
     * @param model
     * @param id    标签ID
     */
    @RequestMapping("find_label")
    public void findLabelById(Model model, String id) {
        try {
            InnLabel innLabel = this.iInnLabelService.findLabelById(id);
            if (null != innLabel) {
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
                model.addAttribute(Constants.DATA, innLabel);
            } else {
                model.addAttribute(Constants.STATUS, Constants.ERROR);
                model.addAttribute(Constants.MESSAGE, "没有找到标签");
            }
        } catch (Exception e) {
            logger.error("根据客栈标签ID，查询客栈标签信息，查询出错", e);
        }
    }

    /**
     * 更新客栈标签
     *
     * @param model
     * @param innLabel
     */
    @RequestMapping("update_label")
    public String updateLabel(Model model, InnLabel innLabel) {
        try {
            this.iInnLabelService.modifyLableById(innLabel);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } catch (Exception e) {
            logger.error("更新客栈标签失败", e);
        }
        return redirectUrl("/system/find_labels");
    }

    /**
     * 删除标签
     *
     * @param model
     * @param id
     */
    @RequestMapping("delete_label")
    public void deleteLabel(Model model, String id) {
        try {
            //判断该标签下是否存在绑定的客栈
            List<BangInn> bangInnList = this.bangInnService.findBangInnByInnLabelId(id);
            if (null == bangInnList || bangInnList.size() == 0) {
                this.iInnLabelService.removeLabelById(id);
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            } else {
                model.addAttribute(Constants.STATUS, Constants.ERROR);
                model.addAttribute(Constants.MESSAGE, "删除失败，该标签下有绑定客栈，不能删除");
            }
        } catch (Exception e) {
            logger.error("删除客栈标签失败", e);
        }

    }

    /**
     * 查询通知模板列表
     *
     * @param model
     */
    @RequestMapping("find_notices")
    public String findNotices(Model model) {
        try {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, this.noticeTemplateService.findNoticeTemplates(getCurrentUser().getCompanyId()));
        } catch (Exception e) {
            logger.error("查询通知模板错误", e);
        }
        return "/system/notice_list";
    }

    /**
     * 新增通知模板
     *
     * @param noticeTemplate
     */
    @RequestMapping("create_notice")
    public String createNotice(@Valid NoticeTemplate noticeTemplate) {
        try {
            noticeTemplate.setCompanyId(getCurrentUser().getCompanyId());
            this.noticeTemplateService.createNoticeTemplate(noticeTemplate);
        } catch (Exception e) {
            logger.error("创建客栈通知模板失败", e);
        }

        return redirectUrl("/system/find_notices");
    }

    /**
     * 根据id查询通知模板
     *
     * @param model
     * @param id
     */
    @RequestMapping("find_notice")
    public void findNotice(Model model, String id) {
        try {
            NoticeTemplate noticeTemplate = this.noticeTemplateService.findNoticeTemplateById(id);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, noticeTemplate);
        } catch (Exception e) {
            logger.error("根据ID查询客栈通知模板，查询错误", e);
        }

    }

    /**
     * 跳转到模板编辑页面
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("update_notice_page")
    public String updateNoticePage(Model model, String id) {
        NoticeTemplate noticeTemplate = this.noticeTemplateService.findNoticeTemplateById(id);
        if (noticeTemplate == null) {
            logger.error("根据ID查询客栈模板，跳转到编辑页面，查询错误", id);
            throw new TomsRuntimeException("没有找到该模板信息");
        } else {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, noticeTemplate);
        }
        return "/system/update_notice";
    }
    /**
     * 修改通知模板
     *
     * @param model
     * @param noticeTemplate
     */
    @RequestMapping("update_notice")
    public String updateNotice(Model model, NoticeTemplate noticeTemplate) {
        try {
            this.noticeTemplateService.modifyNoticeTemplate(noticeTemplate);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } catch (Exception e) {
            logger.error("修改通知模板错误", e);
        }
        return redirectUrl("/system/find_notices");
    }

    /**
     * 删除通知模板
     *
     * @param model
     * @param id
     */
    @RequestMapping("delete_notice")
    public void deleteNotice(Model model, String id) {
        try {
            this.noticeTemplateService.removeNoticeTemplateById(id);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } catch (Exception e) {
            logger.error("删除客栈通知模板错误", e);
        }
    }

    /**
     * 跳转到创建权限页面:初始化使用
     *
     * @return
     */
    @RequestMapping("create_permission_page")
    public String createPage() {
        return "permission";
    }

    /**
     * 新增权限：初始化使用
     *
     * @param permission
     * @return
     */
    @RequestMapping("create_permission")
    public String createPermission(Permission permission) {
        permissionService.createPermission(permission);
        return "permission";
    }

    @RequestMapping("images")
    public String findImages(Model model) {
        try {
            List<BangInn> bangInnList = this.bangInnService.findBangInnImages(getCurrentUser().getCompanyId());
            model.addAttribute("bangInns", bangInnList);
            model.addAttribute("imgUrl", CommonApi.IMG_URL);
        } catch (IOException e) {
            logger.debug("查询绑定客栈图片信息出错", e);
            throw new TomsRuntimeException("查询绑定客栈图片信息出错");
        }
        return "system/images";
    }

    @RequestMapping("find_room_images")
    public String findRoomImages(Model model, String id) {
        //查询绑定客栈是否存在
        BangInnDto bangInnDto = this.bangInnService.findBangInnById(id);
        if (null != bangInnDto) {
            try {
                List<RoomTypeInfo> roomTypeInfos = this.bangInnService.findBangInnRoomImage(bangInnDto);
                model.addAttribute("roomImages", roomTypeInfos);
                model.addAttribute("bangInn", bangInnDto);
                model.addAttribute("imgUrl", CommonApi.IMG_URL);
            } catch (IOException e) {
                logger.debug("获取房型图片出错", e);
                throw new TomsRuntimeException("获取房型图片出错");
            }
        }
        return "system/room_image";
    }

}
