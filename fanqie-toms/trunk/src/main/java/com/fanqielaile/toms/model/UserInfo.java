package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.UserType;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * 用户
 * Created by wangdayin on 2015/5/11.
 */
public class UserInfo extends Domain implements UserDetails {
    //用户类型
    private UserType userType;
    //登陆账号
    @NotBlank(message = "账户名不能为空")
    private String loginName;
    //电话号码
    @NotBlank(message = "电话号码不能为空")
    private String telephone;
    //用户姓名
    @NotBlank(message = "用户姓名不能为空")
    private String userName;
    //密码
    @NotBlank(message = "密码不能为空")
    private String password;
    //数据权限
    private Integer dataPermission;
    //所属公司ID
    private String companyId;
    //角色ID
    private String roleId;
    //角色对象
    private Role role;
    //客栈的标签id
    private String innLabelId;
    //管理员ID
    private String userId;

    public String getInnLabelId() {
        return innLabelId;
    }

    public void setInnLabelId(String innLabelId) {
        this.innLabelId = innLabelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private Set<GrantedAuthority> authorities = Collections.emptySet();

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

    public Integer getDataPermission() {
        return dataPermission;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
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
