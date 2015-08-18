package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OtaInfoRefDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC : 渠道
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public interface IOtaInfoDao {

     List<OtaInfoRefDto> selectAllOtaByCompany(@Param("companyCode")String companyCode) ;

     OtaInfoRefDto selectAllOtaByCompanyAndType(@Param("companyId")String companyId, @Param("otaType") String otaType);

     //查询所有企业开通的ota ，以及OTA_ID
     List<OtaInfoRefDto> selectOtaInfoList();

     List<OtaInfoRefDto> selectOtaInfoListByCompanyId(@Param("companyId")String companyId);

     void saveOtaInfo(OtaInfoRefDto otaInfoRefDto);

     OtaInfoRefDto findOtaInfoByOtaIdAndCompanyId(OtaInfoRefDto otaInfoRefDto);
}
