package com.fanqielaile.toms.helper;

import com.fanqielaile.toms.dto.CompanyAjaxDto;
import com.fanqielaile.toms.dto.BangInnDto;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 绑定客栈数据检查
 * Created by wangdayin on 2015/6/2.
 */
public class BangInnDataCheckHelper {
    /**
     * 检查新增绑定客栈的数据
     *
     * @param bangInnDto
     * @return
     */
    public static boolean checkBangInn(BangInnDto bangInnDto) {
        if (StringUtils.isEmpty(bangInnDto.getInnName())) {
            return false;
        } else if (StringUtils.isEmpty(bangInnDto.getInnId().toString())) {
            return false;
        } else if (StringUtils.isEmpty(bangInnDto.getAccountId().toString())) {
            return false;
        } else if (StringUtils.isEmpty(bangInnDto.getCompanyCode())) {
            return false;
        } else if (StringUtils.isEmpty(bangInnDto.getMobile())) {
            return false;
        }
        return true;
    }

}
