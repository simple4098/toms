package com.fanqielaile.toms.helper;

import com.fanqielaile.toms.dto.CompanyAjaxDto;
import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
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

    /**
     * 处理消息通知出请求来的客栈id字符串
     *
     * @param innId
     * @return
     */
    public static List<BangInn> dealStringInnIds(String innId) {
        List<BangInn> bangInns = new ArrayList<>();
        try {
            if (StringUtils.isNotEmpty(innId)) {
                String[] innIds = innId.split(",");
                if (ArrayUtils.isNotEmpty(innIds)) {
                    for (int i = 0; i < innIds.length; i++) {
                        BangInn bangInn = new BangInn();
                        bangInn.setId(innIds[i]);
                        bangInns.add(bangInn);
                    }
                }
            }
        } catch (Exception e) {
            throw new TomsRuntimeException("系统内部错误");
        }
        return bangInns;
    }

}
