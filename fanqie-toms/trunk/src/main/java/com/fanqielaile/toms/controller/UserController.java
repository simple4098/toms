package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.helper.PermissionHelper;
import com.fanqielaile.toms.model.Permission;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IPermissionService;
import com.fanqielaile.toms.service.IUserInfoService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.JsonModel;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangdayin on 2015/5/11.
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private IPermissionService permissionService;

    /**
     * 查询当前登陆用户所在公司的权限
     *
     * @param model
     */
    @RequestMapping("company_permission")
    public void findConpanyPermission(Model model) {
        List<Permission> permissions = this.permissionService.findPermissionByCompanyId(getCurrentUser().getCompanyId());
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, permissions);
    }

    /**
     * 创建用户
     *
     * @param model
     * @param userInfo
     * @param bindingResult
     * @return
     */
    @RequestMapping("/create_user")
    public Model createUser(Model model, @Valid UserInfo userInfo, BindingResult bindingResult, String permissionIds) {
        if (StringUtils.isNotEmpty(permissionIds)) {
            boolean flag = userInfoService.createUserInfo(userInfo, PermissionHelper.dealPermissionString(permissionIds));
            if (flag) {
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            } else {
                model.addAttribute(Constants.STATUS, Constants.ERROR);
                model.addAttribute(Constants.MESSAGE, "新增失败");
            }
        }
        return new JsonModel().getModel(model, bindingResult);

    }

    /**
     * 查询当前所属公司下是员工
     *
     * @param model
     */
    @RequestMapping("find_users")
    public void findUsers(Model model) {
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, this.userInfoService.findUserInfos(getCurrentUser().getCompanyId()));
    }

    /**
     * 根据id查询用户信息
     *
     * @param model
     * @param id
     */
    @RequestMapping("find_user")
    public void findUser(Model model, String id) {
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, this.userInfoService.findUserInfoById(id));
    }

    /**
     * 更新用户信息
     *
     * @param model
     * @param userInfo
     */
    @RequestMapping("update_user")
    public void updateUser(Model model, UserInfo userInfo) {
        boolean flag = this.userInfoService.modifyUserInfo(userInfo);
        if (flag) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.MESSAGE, "修改成功");
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "修改失败");
        }
    }

    /**
     * 删除用户
     *
     * @param model
     * @param id
     */
    @RequestMapping("delete_user")
    public void deleteUser(Model model, String id) {
        boolean flag = this.userInfoService.removeUserInfo(id);
        if (flag) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.MESSAGE, "删除成功");
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "删除失败");
        }
    }

    /**
     * 查询用户的权限列表
     *
     * @param model
     * @param userId
     */
    @RequestMapping("user_permission")
    public void findUserPermission(Model model, String userId) {
        UserInfo userInfo = this.userInfoService.findUserInfoById(userId);
        if (null != userInfo) {
            List<Permission> permissions = this.permissionService.findPermissionByUserId(userId);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, permissions);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "该用户不存在");
        }
    }

    /**
     * 更新用户权限
     *
     * @param model
     * @param userId
     * @param permissions
     */
    @RequestMapping("update_permission")
    public void updatePermission(Model model, String userId, String permissions, int dataPermission) {
        if (StringUtils.isNotEmpty(permissions)) {
            UserInfo userInfo = this.userInfoService.findUserInfoById(userId);
            if (null != userInfo) {
                this.userInfoService.modifyUserPermission(userInfo, PermissionHelper.dealPermissionString(permissions), dataPermission);
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            }
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "修改错误");
        }
    }


}
