package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.service.*;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.Constants;
/*import com.tomato.framework.log.support.UserInfoContext;*/
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    @Resource
    private ICompanyService companyService;

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

    /**
     * 跳转到公司管理页面
     *
     * @return
     */
    @RequestMapping("find_companys")
    public String findCompany(Model model) {
        try {
            List<Company> companyList = this.companyService.findCompanyByCompany(null);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, companyList);
            //封装权限信息
            List<Permission> permissionList = this.permissionService.findPermissionByCompanyId(null);
            model.addAttribute("permissions", permissionList);
        } catch (Exception e) {
            logger.error("查询当前公司下属员工,查询失败", e);
        }
        return "system/company_list";
    }

    /**
     * 跳转到公司基本信息页面
     */
    @RequestMapping("find_company")
    public String findCompanyInfo(Model model,@RequestParam String id) {
        try {
            Company company = companyService.findCompanyByid(id);
            model.addAttribute(Constants.DATA, company);
        } catch (Exception e) {
            logger.error("查询当前公司信息失败", e);
        }
        return "system/update_company";
    }

    /**
     * 更新公司信息
     */
    @RequestMapping("update_company")
    @ResponseBody
    public Object updateCompany(Model model,Company company) {
        Map<String,Object> param = new HashMap<String, Object>();
        try {
            companyService.modifyCompanyInfo(company);
            param.put(Constants.STATUS, Constants.SUCCESS);
            param.put(Constants.MESSAGE, "更新成功");
        } catch (Exception e) {
            param.put(Constants.STATUS, Constants.ERROR);
            param.put(Constants.MESSAGE, e.getMessage());
            logger.error("更新公司信息失败!", e);
        }
        return param;
    }

    /**
     * 删除公司
     * @param model
     */
    @RequestMapping("delete_company")
    public void deleteCompany(Model model,@RequestParam String companyId) {
        try {
            companyService.removeCompany(companyId);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.MESSAGE, "删除成功!");
        } catch (Exception e) {
            logger.error("更新公司信息失败!", e);
        }
    }

    /**
     * 查询公司的权限
     *
     * @param companyId
     * @param model
     */
    @RequestMapping("find_company_permission")
    public void findCompanyPermission(String companyId, Model model) {
        try {
            Company company = this.companyService.findCompanyByid(companyId);
            if (company != null) {
                //公司可以分配的权限
                List<Permission> permissionList = this.permissionService.findPermissionByCompanyId(companyId);
                model.addAttribute(Constants.DATA, permissionList);
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
                model.addAttribute("company", company);
            } else {
                throw new TomsRuntimeException("查询公司出错");
            }
        } catch (Exception e) {
            logger.error("查询公司权限出错", e);
        }
    }

    /**
     * 创建公司
     *
     * @param model
     * @param company
     * @param permissionIds
     */
    @RequestMapping("create_company")
    public void createCompany(Model model, Company company, String permissionIds) {
        try {
            if (StringUtils.isNotEmpty(permissionIds)) {
                this.companyService.addCompany(company, permissionIds);
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("创建公司出错", e);
            throw new TomsRuntimeException("创建公司出错", e);
        }

    }

    @RequestMapping("update_company_permission")
    public void updateCompanyPermission(Model model, String companyId, String permissionIds) {
        try {
            if (StringUtils.isNotEmpty(permissionIds)) {
                Company company = this.companyService.findCompanyByid(companyId);
                if (null != company) {
                    this.companyService.modifyCompanyPermission(company, permissionIds);
                    model.addAttribute(Constants.STATUS, Constants.SUCCESS);
                }
            } else {
                model.addAttribute(Constants.STATUS, Constants.ERROR);
                model.addAttribute(Constants.MESSAGE, "修改公司权限错误，原因：传入参数有误");
            }
        } catch (Exception e) {
            logger.error("更新公司权限失败", e);
            throw new TomsRuntimeException("更新公司权限失败",e);
        }
    }

    /**
     * 跳转到绑定客栈风光图片展示页面
     *
     * @param model
     * @param id    客栈ID
     * @return
     */
    @RequestMapping("images")
    public String findImages(Model model, String id) {
        try {
            List<BangInn> bangInnList = this.bangInnService.findBangInnByCompanyId(getCurrentUser().getCompanyId());
            model.addAttribute("bangInns", bangInnList);
            //根据ID查询客栈信息
            if (StringUtils.isNotEmpty(id)) {
                BangInnDto bangInnDto = this.bangInnService.findBangInnById(id);
                InnDto innDto = this.bangInnService.selectBangInnImage(bangInnDto);
                if (null != innDto) {
                    bangInnDto.setInnDto(innDto);
                }
                model.addAttribute("imgUrl", CommonApi.IMG_URL);
                model.addAttribute("bangInn", bangInnDto);
                //查看客栈对应的风光图片

            }
        } catch (Exception e) {
            logger.debug("查询绑定客栈图片信息出错", e);
            throw new TomsRuntimeException("查询绑定客栈图片信息出错");
        }
        return "system/images";
    }

    /**
     * @param model
     * @param id
     * @return
     */
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
