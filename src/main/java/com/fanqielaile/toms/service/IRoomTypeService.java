package com.fanqielaile.toms.service;

import com.fanqie.core.dto.ParamDto;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.UserInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.util.List;

/**
 * DESC : 第三方公司 查看房态房量
 * @author : 番茄木-ZLin
 * @data : 2015/6/3
 * @version: v1.0.0
 */
public interface IRoomTypeService {

    /**
     * 请求接口查询第三方的房态房量
     * @param paramDto 查询参数
     * @param userInfo 当前用户信息
     */
    RoomTypeInfoDto  findRoomType(ParamDto paramDto,UserInfo userInfo)throws Exception;

    /**
     * 房价管理展示客栈
     * @param bangInn 绑定的客栈信息
     * @throws Exception
     */
    RoomTypeInfoDto  findRoomType( String otaInfoId,ParamDto paramDto,BangInn bangInn)throws Exception;

    /**
     * 房态数量new-通过客栈名称和用户公司等查询客栈列表
     * @param userInfo
     * @return
     */
    List<BangInn> findRoomTypeByName(String innLabelId,String innName,UserInfo userInfo,PageBounds pageBounds);
}
