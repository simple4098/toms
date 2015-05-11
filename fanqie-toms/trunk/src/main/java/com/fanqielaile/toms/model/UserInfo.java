package com.fanqielaile.toms.model;

import com.fanqielaile.toms.enums.UserType;

/**
 * Created by wangdayin on 2015/5/11.
 */
public class UserInfo extends Domain{
    //用户类型
    private UserType userType;
    //登陆账号
    private String loginName;
    //电话号码
    private String telephone;
    //用户姓名
    private String userName;
    //密码
    private String password;
    //数据权限
    private Integer dataPermission;
    //所属公司ID
    private String companyId;
    //角色ID
    private String roleId;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer isDataPermission() {
        return dataPermission;
    }

    public void setDataPermission(Integer dataPermission) {
        this.dataPermission = dataPermission;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
