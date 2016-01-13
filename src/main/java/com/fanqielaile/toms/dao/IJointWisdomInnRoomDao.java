package com.fanqielaile.toms.dao;

import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.Order;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * DESC : 众荟房型映射
 * @author : 番茄木-ZLin
 * @data : 2016/1/11
 * @version: v1.0.0
 */
public interface IJointWisdomInnRoomDao {

    List<JointWisdomInnRoomMappingDto> selectJsRoomMapping();

    /**
     * 通过客栈id和房型id 查询公司信息
     *
     * @param availOrder
     * @return
     */
    JointWisdomInnRoomMappingDto selectRoomMappingByInnIdAndRoomTypeId(@Param("order") Order availOrder);
    /**
     * 查询众荟的客栈房型关联关系
     * @param innId 客栈id
     * @param roomTypeId 房型id
     * @return
     */
    JointWisdomInnRoomMappingDto selectJsInnRooType(@Param("companyId") String companyId,@Param("innId") Integer innId, @Param("roomTypeId") Integer roomTypeId);

    /**
     * 查询上架成功的客栈
     * @param companyId 公司id
     * @param otaInfoId 渠道id
     */
    List<JointWisdomInnRoomMappingDto> selectJsRoomInnSj(@Param("companyId")String companyId, @Param("otaInfoId")String otaInfoId);
    /**
     *  保存关系
     * @param jointWisdomInnRoomMappingDto
     */
    void  insertJsRoomInnRooType(JointWisdomInnRoomMappingDto jointWisdomInnRoomMappingDto);
}
