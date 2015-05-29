package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/15.
 */
public interface BangInnDao {
    /**
     * 新增绑定客栈信息
     *
     * @param bangInn
     * @return
     */
    int insertBangInn(BangInn bangInn);

    /**
     * 根据标签id查询绑定客栈
     *
     * @param innLabelId
     * @return
     */
    List<BangInn> selectBangInnByInnLabelId(String innLabelId);

    /**
     * 更新客栈的管理者
     *
     * @param oldUserId
     * @param newUserId
     * @return
     */
    int updateBangInnUserId(@Param("oldUserId") String oldUserId, @Param("newUserId") String newUserId);

    /**
     * 查询当前用户能够查询的标签
     *
     * @param userInfo
     * @return
     */
    List<BangInn> selectBangInnByUser(UserInfo userInfo);

    /**
     * 根据用户id查询管理的客栈
     *
     * @param userId
     * @return
     */
    List<BangInn> selectBangInnByUserId(String userId);

    /**
     * 根据当前登录用户查询客栈列表
     *
     * @param userInfo
     * @return
     */
    List<BangInnDto> selectBangInnListByUserInfo(UserInfo userInfo);

    /**
     * 根据id查询绑定客栈
     *
     * @param Id
     * @return
     */
    BangInnDto selectBangInnById(String Id);

    /**
     * 更新绑定客栈
     *
     * @param bangInnDto
     */
    void updateBangInn(BangInnDto bangInnDto);
}
