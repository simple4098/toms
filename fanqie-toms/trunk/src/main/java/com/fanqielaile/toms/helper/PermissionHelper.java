package com.fanqielaile.toms.helper;

import com.fanqielaile.toms.model.Permission;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdayin on 2015/5/15.
 */
public class PermissionHelper {
    /**
     * 帮助处理权限字符串
     *
     * @param permissionString
     * @return
     */
    public static List<Permission> dealPermissionString(String permissionString) {
        List<Permission> permissionList = new ArrayList<>();
        if (StringUtils.isNotEmpty(permissionString)) {
            String[] permissionArray = permissionString.split(",");
            if (ArrayUtils.isNotEmpty(permissionArray)) {
                for (int i = 0; i < permissionArray.length; i++) {
                    Permission permission = new Permission();
                    permission.setId(permissionArray[i]);
                    permissionList.add(permission);
                }
            }
        }
        return permissionList;
    }
}
