package com.fanqielaile.toms.helper;

import com.fanqielaile.toms.dto.BangInnDto;
import org.apache.commons.lang3.StringUtils;

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
        } else if (StringUtils.isEmpty(bangInnDto.getCompanyId())) {
            return false;
        } else if (StringUtils.isEmpty(bangInnDto.getInnCode())) {
            return false;
        } else if (StringUtils.isEmpty(bangInnDto.getInnId().toString())) {
            return false;
        } else if (StringUtils.isEmpty(bangInnDto.getAccountId().toString())) {
            return false;
        }
        return true;
    }
}
