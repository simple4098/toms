package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2015/5/15.
 */
public interface IBangInnService {
    /**
     * 根据标签id查询绑定客栈
     *
     * @param innLabelId
     * @return
     */
    List<BangInn> findBangInnByInnLabelId(String innLabelId);

    /**
     * 根据查询当前用户能否访问的标签
     *
     * @param userInfo
     * @return
     */
    List<BangInn> findBangInnBuUser(UserInfo userInfo);

    /**
     * 根据当前登录用户查询标签和客栈列表
     *
     * @param userInfo
     * @return
     */
    List<BangInn> findBangInnAndLabel(UserInfo userInfo);
}
