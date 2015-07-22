package com.fanqielaile.toms.service.impl;

import com.fanqie.util.StringUtil;
import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dao.PermissionDao;
import com.fanqielaile.toms.dao.UserInfoDao;
import com.fanqielaile.toms.dto.UserInfoDto;
import com.fanqielaile.toms.enums.UserType;
import com.fanqielaile.toms.helper.PermissionHelper;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.Permission;
import com.fanqielaile.toms.model.Role;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.ICompanyService;
import com.fanqielaile.toms.service.IUserInfoService;
import com.fanqielaile.toms.support.event.TomsApplicationEvent;
import com.fanqielaile.toms.support.listener.RolePermissionChangeListener;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdayin on 2015/6/2.
 */
@Service
public class CompanyService implements ICompanyService {
    @Resource
    private CompanyDao companyDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private RolePermissionChangeListener rolePermissionChangeListener;

    @Override
    public List<Company> findCompanyByCompany(Company company) {
        return this.companyDao.selectCompanyByCompany(company);
    }

    @Override
    public Company findCompanyByCompanyCode(String companyCode) {
        return this.companyDao.selectCompanyByCompanyCode(companyCode);
    }

    @Override
    public Company findCompanyByid(String id) {
        return this.companyDao.selectCompanyById(id);
    }
    @Override
    public void modifyCompanyInfo(Company company)throws Exception{
        if (company!=null && !StringUtils.isEmpty(company.getId())){
            if (!StringUtils.isEmpty(company.getCompanyCode())){
                Company companyCode = companyDao.selectCompanyByCompanyCode(company.getCompanyCode());
                if (companyCode==null ||(companyCode!=null && companyCode.getId().equals(company.getId()))){
                    companyDao.updateCompany(company);
                }else {
                    throw new Exception("修改公司异常，公司编码不是唯一的!");
                }
            }

        }else {
            throw new Exception("修改公司信息失败，没有找到公司信息");
        }
    }

    @Override
    public void removeCompany(String companyId) {

        //删除此公司下的用户
        userInfoDao.deleteUserInfoByCompanyId(companyId);
        //删除此公司绑定的客栈
        bangInnDao.deleteBangInnByCompanyId(companyId);
        //
        companyDao.deleteCompanyById(companyId);
    }

    @Override
    public void addCompany(Company company, String permissionIds) {
        while (true) {
            String companyCode = StringUtil.getRandomNumber();
            //根据公司唯一码查询公司
            Company company1 = this.companyDao.selectCompanyByCompanyCode(companyCode);
            if (company1 == null) {
                company.setCompanyCode(companyCode);
                break;
            }
        }
        company.setType(1);
        company.setId(company.getUuid());
        //创建公司
        this.companyDao.insertCompany(company);
        //删除公司与权限的关联关系
        this.companyDao.deleteCompanyPermissionByCompanyId(company.getId());
        //创建公司与权限的关联关系
        company.setPermissionList(PermissionHelper.dealPermissionString(permissionIds));
        this.companyDao.insertCompanyPermission(company);
        //创建公司并创建一个登陆用户
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginName(company.getCompanyCode());
        //初始密码
        userInfo.setPassword("111111");
        userInfo.setUserName(company.getCompanyName());
        userInfo.setDataPermission(1);
        userInfo.setCompanyId(company.getId());
        userInfo.setUserType(UserType.ADMIN);
        this.userInfoService.createUserInfo(userInfo, PermissionHelper.dealPermissionString(permissionIds));
    }

    @Override
    public void modifyCompanyPermission(Company company, String permissionIds) {
        //公司具有的权限
        List<Permission> permissionList = this.permissionDao.selectPermissionByCompanyId(company.getId());
        //需要删除员工的权限id
        List<Permission> deletePermissionIds = dealCompanyPermission(permissionList, permissionIds);
        //删除员工下多余的权限
        if (ArrayUtils.isNotEmpty(deletePermissionIds.toArray())) {
            //公司下属员工
            List<UserInfoDto> userInfoDtos = userInfoDao.selectUserInfos(company.getId());
            this.permissionDao.deleteCompanyUserPermission(userInfoDtos, deletePermissionIds);
        }
        //删除公司权限
        this.companyDao.deleteCompanyPermissionByCompanyId(company.getId());
        //创建权限
        company.setPermissionList(PermissionHelper.dealPermissionString(permissionIds));
        this.companyDao.insertCompanyPermission(company);
        //删除公司下员工多余的权限，并调用监听器
        rolePermissionChangeListener.onApplicationEvent(new TomsApplicationEvent(new Role()));

    }

    /**
     * 处理公司的权限
     *
     * @param permissionList
     * @param permissionIds
     * @return
     */
    private static List<Permission> dealCompanyPermission(List<Permission> permissionList, String permissionIds) {
        //数据库中的权限信息
        List<String> dataPermissionListString = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(permissionList.toArray())) {
            for (Permission permission : permissionList) {
                dataPermissionListString.add(permission.getId());
            }
        }
        //传入参数的权限信息
        List<String> paramPermissionList = new ArrayList<>();
        if (StringUtils.isNotEmpty(permissionIds)) {
            String[] permissionArray = permissionIds.split(",");
            if (ArrayUtils.isNotEmpty(permissionArray)) {
                for (int i = 0; i < permissionArray.length; i++) {
                    paramPermissionList.add(permissionArray[i]);
                }
            }
        }
        List<Permission> result = new ArrayList<>();
        //移除需要删除的数据
        dataPermissionListString.removeAll(paramPermissionList);
        if (ArrayUtils.isNotEmpty(dataPermissionListString.toArray())) {
            for (int i = 0; i < dataPermissionListString.size(); i++) {
                Permission permission = new Permission();
                permission.setId(dataPermissionListString.get(i));
                result.add(permission);

            }
        }
        return result;
    }


}
