package com.fanqielaile.toms.controller;

import com.fanqie.core.dto.ParamDto;
import com.fanqielaile.toms.dto.ActiveInnDto;
import com.fanqie.util.Pagination;
import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.UserInfoDto;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.ICompanyService;
import com.fanqielaile.toms.service.IInnActiveService;
import com.fanqielaile.toms.service.IUserInfoService;
import com.fanqielaile.toms.service.impl.InnLabelService;
import com.fanqielaile.toms.support.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客栈管理
 * Created by wangdayin on 2015/5/15.
 */
@Controller
@RequestMapping("inn_manage")
public class InnManageController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(InnManageController.class);
    @Resource
    private IBangInnService bangInnService;
    @Resource
    private InnLabelService innLabelService;
    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private IInnActiveService innActiveService;
    @Resource
    private ICompanyService companyService;

    /**
     * 查询标签对应的客栈
     *
     * @param model
     */
    @RequestMapping("find_label_inn")
    public void findLabelInn(Model model) {
        try {
            UserInfo currentUser = getCurrentUser();
            List<BangInn> bangInnAndLabel = this.bangInnService.findBangInnAndLabel(currentUser);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, bangInnAndLabel);
        } catch (Exception e) {
            logger.error("查询标签对应的客栈，查询出错", e);
        }
    }

    /**
     * 客栈列表
     *
     * @param model
     * @return
     */
    @RequestMapping("find_inns")
    public String findInns(Model model, String innLabelId, String userId, @RequestParam(defaultValue = "1", required = false) int page) {
        try {
            UserInfo currentUser = getCurrentUser();
            currentUser.setInnLabelId(innLabelId);
            currentUser.setUserId(userId);
            List<BangInnDto> bangInnList = this.bangInnService.findBangInnListByUserInfo(currentUser, new PageBounds(page, defaultRows));
            model.addAttribute(Constants.DATA, bangInnList);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            List<BangInn> bangInns = this.bangInnService.findBangInnByCompanyId(currentUser.getCompanyId());
            model.addAttribute("bangs", bangInns);
            //客栈标签
            List<InnLabel> innLabels = this.innLabelService.findLabelsByCompanyId(currentUser.getCompanyId());
            model.addAttribute("labels", innLabels);
            //管理员
            List<UserInfoDto> userInfos = this.userInfoService.findUserInfos(currentUser.getCompanyId());
            model.addAttribute("userInfos", userInfos);
            //公司信息
            Company company = this.companyService.findCompanyByid(currentUser.getCompanyId());
            model.addAttribute("company", company);
            //分页对象
            Paginator paginator = ((PageList) bangInnList).getPaginator();
            model.addAttribute("pagination", PaginationHelper.toPagination(paginator));
            //保存查询条件
            model.addAttribute("innLabel", innLabelId);
            model.addAttribute("userId", userId);
        } catch (Exception e) {
            logger.error("查询客栈列表，列表内容查询错误", e);
        }
        return "/inn/inn_list";
    }

    /**
     * 跳转到编辑页面
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("to_update_inn")
    public String toUpdateInn(Model model, String id) {
        try {
            UserInfo currentUser = getCurrentUser();
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, this.bangInnService.findBangInnById(id));
            //客栈标签
            List<InnLabel> innLabels = this.innLabelService.findLabelsByCompanyId(currentUser.getCompanyId());
            model.addAttribute("labels", innLabels);
            //管理员
            List<UserInfoDto> userInfos = this.userInfoService.findUserInfos(currentUser.getCompanyId());
            model.addAttribute("userInfos", userInfos);
        } catch (Exception e) {
            logger.error("根据客栈ID查询客栈信息，跳转到客栈编辑页面，查询错误", e);
        }
        return "/inn/inn_update";
    }

    /**
     * 更新绑定客栈
     *
     * @param bangInnDto
     * @return
     */
    @RequestMapping("update_inn")
    public String updateInn(BangInnDto bangInnDto) {
        try {
            this.bangInnService.modifiyBangInn(bangInnDto);
        } catch (Exception e) {
            logger.error("更新绑定客栈失败", e);
        }
        return redirectUrl("/inn_manage/find_inns");
    }

    //活跃客栈
    @RequestMapping("/activeInn")
    public String activeInn(Model model,ParamDto paramDto){
        UserInfo currentUser = getCurrentUser();
        //paramDto.setInnIds();
        try {
            ActiveInnDto activeInnDto = innActiveService.findActiveInnDto(paramDto,currentUser);
            model.addAttribute("inn",activeInnDto);
            model.addAttribute("paramDto",paramDto);
        } catch (Exception e) {
            logger.error("活跃报表错误",e);
        }
        return "/inn/active_inn";
    }
}
