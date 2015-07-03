package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.OtaInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC : 渠道
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public interface IOtaInfoDao {

    public List<OtaInfo> selectAllOtaByCompany(@Param("companyCode")String companyCode) ;

    public OtaInfo selectAllOtaByCompanyAndType(@Param("companyId")String companyId, @Param("otaType") String otaType);
}
