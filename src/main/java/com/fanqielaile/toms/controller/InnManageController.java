package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.UserInfoDto;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.IUserInfoService;
import com.fanqielaile.toms.service.impl.InnLabelService;
import com.fanqielaile.toms.support.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 客栈管理
 * Created by wangdayin on 2015/5/15.
 */
@Controller
@RequestMapping("inn_manage")
public class InnManageController extends BaseController {
    @Resource
    private IBangInnService bangInnService;
    @Resource
    private InnLabelService innLabelService;
    @Resource
    private IUserInfoService userInfoService;

    /**
     * 查询标签对应的客栈
     *
     * @param model
     */
    @RequestMapping("find_label_inn")
    public void findLabelInn(Model model) {
        UserInfo currentUser = getCurrentUser();
        List<BangInn> bangInnAndLabel = this.bangInnService.findBangInnAndLabel(currentUser);
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, bangInnAndLabel);
    }

    /**
     * 客栈列表
     *
     * @param model
     * @return
     */
    @RequestMapping("find_inns")
    public String findInns(Model model, String innLabelId, String userId) {
        UserInfo currentUser = getCurrentUser();
        currentUser.setInnLabelId(innLabelId);
        currentUser.setUserId(userId);
        List<BangInnDto> bangInnList = this.bangInnService.findBangInnListByUserInfo(currentUser);
        model.addAttribute(Constants.DATA, bangInnList);
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        //客栈标签
        List<InnLabel> innLabels = this.innLabelService.findLabelsByCompanyId(currentUser.getCompanyId());
        model.addAttribute("labels", innLabels);
        //管理员
        List<UserInfoDto> userInfos = this.userInfoService.findUserInfos(currentUser.getCompanyId());
        model.addAttribute("userInfos", userInfos);
        return "/inn/inn_list";
    }

    /**
     * 绑定客栈列表数据
     *
     * @param model
     * @param innLabelId
     * @param userId
     */
    @RequestMapping("find_inns_data")
    public void findInnsData(Model model, String innLabelId, String userId) {
        UserInfo currentUser = getCurrentUser();
        currentUser.setInnLabelId(innLabelId);
        currentUser.setUserId(userId);
        List<BangInnDto> bangInnList = this.bangInnService.findBangInnListByUserInfo(currentUser);
        model.addAttribute(Constants.DATA, bangInnList);
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        //客栈标签
        List<InnLabel> innLabels = this.innLabelService.findLabelsByCompanyId(currentUser.getCompanyId());
        model.addAttribute("labels", innLabels);
        //管理员
        List<UserInfoDto> userInfos = this.userInfoService.findUserInfos(currentUser.getCompanyId());
        model.addAttribute("userInfos", userInfos);
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
        UserInfo currentUser = getCurrentUser();
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, this.bangInnService.findBangInnById(id));
        //客栈标签
        List<InnLabel> innLabels = this.innLabelService.findLabelsByCompanyId(currentUser.getCompanyId());
        model.addAttribute("labels", innLabels);
        //管理员
        List<UserInfoDto> userInfos = this.userInfoService.findUserInfos(currentUser.getCompanyId());
        model.addAttribute("userInfos", userInfos);
        return "/inn/inn_update";
    }

    /**
     * 更新绑定客栈
     *
     * @param model
     * @param bangInnDto
     * @return
     */
    @RequestMapping("update_inn")
    public String updateInn(Model model, BangInnDto bangInnDto) {
        this.bangInnService.modifiyBangInn(bangInnDto);
        return redirectUrl("/inn_manage/find_inns");
    }
}
