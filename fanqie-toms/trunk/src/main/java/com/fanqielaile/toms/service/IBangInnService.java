package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.UserInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.io.IOException;
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

    /**
     * 根据当前登录用户查询绑定客栈列表
     *
     * @param userInfo
     * @return
     */
    List<BangInnDto> findBangInnListByUserInfo(UserInfo userInfo, PageBounds pageBounds);

    /**
     * 根据id查询绑定客栈
     *
     * @param id
     * @return
     */
    BangInnDto findBangInnById(String id);

    /**
     * 更新绑定的客栈
     *
     * @param bangInnDto
     */
    void modifiyBangInn(BangInnDto bangInnDto);

    /**
     * 根据客栈id查询绑定的第三方公司
     *
     * @param innId
     * @return
     */
    List<BangInnDto> findCompanyByInnId(int innId);

    /**
     * 新增绑定客栈
     *
     * @param bangInnDto
     */
    void addBanginn(BangInnDto bangInnDto);

    /**
     * 根据公司id和客栈id查询绑定客栈
     *
     * @param companyId
     * @param innId
     * @return
     */
    BangInn findBangInnByCompanyIdAndInnId(String companyId, int innId);

    /**
     * 查询绑定客栈信息
     *
     * @param bangInnList
     * @return
     */
    List<BangInn> findBangInnByStringBangInn(List<BangInn> bangInnList);

    /***
     * 根据公司ID查询绑定的客栈
     *
     * @param companyId
     * @return
     */
    List<BangInn> findBangInnByCompanyId(String companyId);

    /**
     * 根据code查询绑定客栈
     *
     * @param userInfo
     * @param code
     * @return
     */
    BangInn findBangInnByUserAndCode(UserInfo userInfo, String code);

    /**
     * 根据公司ID查询绑定客栈的图片信息
     *
     * @param companyId
     * @return
     */
    List<BangInn> findBangInnImages(String companyId) throws IOException;

    /**
     * 绑定客栈房型信息
     *
     * @param bangInnDto
     * @return
     */
    List<RoomTypeInfo> findBangInnRoomImage(BangInnDto bangInnDto) throws IOException;

    /**
     * @param bangInnDto
     * @return
     */
    InnDto selectBangInnImage(BangInnDto bangInnDto) throws IOException;
}
