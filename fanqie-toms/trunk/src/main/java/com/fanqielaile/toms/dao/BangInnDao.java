package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.UserInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
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
    List<BangInn> selectBangInnByInnLabelId(@Param("innLabelId")String innLabelId,@Param("userInfo")UserInfo userInfo);

    List<BangInn> selectBangInnByInnLabelId(@Param("innLabelId")String innLabelId);

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
    List<BangInnDto> selectBangInnListByUserInfo(UserInfo userInfo, PageBounds pageBounds);

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

    /**
     * 根据客栈id查询公司
     *
     * @param innId
     * @return
     */
    List<BangInnDto> selectCompanyByInnId(@Param("innId") int innId);

    /**
     * 新增绑定客栈
     *
     * @param bangInnDto
     */
    void createBangInn(BangInnDto bangInnDto);

    /**
     * 根据公司id和客栈id查询绑定客栈
     *
     * @param companyId
     * @param innId
     * @return
     */
    BangInn selectBangInnByCompanyIdAndInnId(@Param("companyId") String companyId, @Param("innId") int innId);

    /**
     * 查询绑定客栈信息
     *
     * @param bangInnList
     * @return
     */
    List<BangInn> selectBangInnByStringInnId(@Param("bangInnList") List<BangInn> bangInnList);

    /**
     * 根据公司ID查询绑定客栈
     *
     * @param companyId
     * @return
     */
    List<BangInn> selectBangInnByCompanyId(@Param("companyId") String companyId);

    /**
     * 根据加盟号查询绑定客栈
     *
     * @param userInfo
     * @param code
     * @return
     */
    BangInn selectBangInnByUserAndCode(@Param("userInfo") UserInfo userInfo, @Param("code") String code);

    /**
     * 根据客栈ID查询客栈信息
     *
     * @param innId
     * @return
     */
    BangInn selectBangInnByInnId(@Param("innId") int innId);

    /**
     * 根据tb酒店ID查询绑定客栈信息
     *
     * @param otaHotelId
     * @return
     */
    BangInnDto selectBangInnByTBHotelId(@Param("otaHotelId") String otaHotelId);

    /**
     * 删除此客栈在此公司的关系
     * @param id 公司id
     * @param innId 客栈id
     */
    void deleteBangInnByCompanyIdAndInnId(@Param("companyId")String id,@Param("innId") Integer innId);
}
