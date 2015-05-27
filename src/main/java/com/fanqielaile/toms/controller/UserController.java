package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.dto.UserInfoDto;
import com.fanqielaile.toms.helper.PermissionHelper;
import com.fanqielaile.toms.model.Permission;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IPermissionService;
import com.fanqielaile.toms.service.IUserInfoService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.JsonModel;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
 * 用户
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
     * 根据登陆名查询用户
     * @param model
     * @param loginName
     */
    @RequestMapping("find_user_by_name")
    public void findUserByName(Model model, String loginName) {
        UserInfo userInfo = this.userInfoService.findUserInfoByLoginName(loginName);
        if (null == userInfo) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "该账号已经注册过");
        }
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
     * 查询当前所属公司下属员工
     *
     * @param model
     */
    @RequestMapping("find_users")
    public String findUsers(Model model) {
        List<UserInfoDto> userInfos = this.userInfoService.findUserInfos(getCurrentUser().getCompanyId());
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, userInfos);
        if (null != userInfos) {
            model.addAttribute("company", userInfos.get(0).getCompanyName());
        }
        return "/system/user_list";
    }

    /**
     * 根据id查询用户信息
     *
     * @param model
     * @param id
     */
    @RequestMapping("find_user")
    public String findUser(Model model, String id) {
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, this.userInfoService.findUserInfoById(id));
        return "system/update_user";
    }

    /**
     * 更新用户信息
     *
     * @param model
     * @param userInfo
     */
    @RequestMapping("update_user")
    public String updateUser(Model model, UserInfo userInfo) {
        boolean flag = this.userInfoService.modifyUserInfo(userInfo);
        if (flag) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.MESSAGE, "修改成功");
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "修改失败");
        }
        return redirectUrl("/user/find_users");
    }

    /**
     * 查询公司其他员工
     * @param model
     * @param userInfo
     */
    @RequestMapping("find_other_user")
    public void findOtherUser(Model model, UserInfo userInfo) {
        UserInfo userInfo1 = this.userInfoService.findUserInfoById(userInfo.getId());
        if (null != userInfo1) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, this.userInfoService.findOtherUserInfoById(userInfo1));
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "查询错误");
        }
    }

    /**
     * 删除用户
     *
     * @param model
     * @param id
     */
    @RequestMapping("delete_user")
    public String deleteUser(Model model, String id, String replaceUserId) {
        boolean flag = this.userInfoService.removeUserInfo(id, replaceUserId);
        if (flag) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.MESSAGE, "删除成功");
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "删除失败");
        }
        return redirectUrl("/user/find_users");
    }

    /**
     * 删除用户
     *
     * @param model
     * @param id
     */
    @RequestMapping("only_delete_user")
    public void deleteOnlyUser(Model model, String id) {
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

    /**
     * 检查用户修改基本信息
     *
     * @param model
     * @param id
     * @param loginName
     */
    @RequestMapping("/check_user")
    public void checkUser(Model model, String id, String loginName) {
        UserInfo userInfo = this.userInfoService.findUserInfoById(id);
        if (userInfo != null) {
            if (userInfo.getLoginName().equals(loginName)) {
                model.addAttribute("status", true);
            } else {
                UserInfo userInfo1 = this.userInfoService.findUserInfoByLoginName(loginName);
                if (null != userInfo1) {
                    model.addAttribute("status", false);
                    model.addAttribute("message", "登录名已存在，请重新修改");
                } else {
                    model.addAttribute("status", true);
                }
            }
        } else {
            model.addAttribute("status", false);
            model.addAttribute("message", "该用户不存在");
        }
    }

    /**
     * 检查用户的登录账号
     *
     * @param model
     * @param loginName
     */
    @RequestMapping("/check_user_name")
    public void checkUserLoginName(Model model, String loginName) {
        UserInfo userInfo = this.userInfoService.findUserInfoByLoginName(loginName);
        if (userInfo == null) {
            model.addAttribute("status", true);
        } else {
            model.addAttribute("status", false);
        }
    }

    /**
     * 更新用户密码
     *
     * @param model
     * @param password
     * @return
     */
    @RequestMapping("update_password")
    public String updatePassword(Model model, String password) {
        UserInfo userInfo = getCurrentUser();
        userInfo.setPassword(password);
        this.userInfoService.modifyUserInfo(userInfo);
        return "login";
    }

}
