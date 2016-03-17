package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.model.OtaInfo;
import com.fanqielaile.toms.model.Result;
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

     OtaInfoRefDto selectCompanyIdByAppKey(@Param("appKey") String appKey, @Param("appSecret") String appSecret);

     OtaInfoRefDto selectOtaInfoByCompanyIdAndOtaInnOtaId(@Param("companyId")String companyId, @Param("otaInfoId")String otaInfoId);

     OtaInfoRefDto selectOtaInfoByType(@Param("otaType") String otaType);
     /**
     * 查询所有的渠道
     */
     List<OtaInfoRefDto> selectAllOtaInfo();

    /**
     * 查询渠道详细信息
     * @param otaInfoId 渠道id
     */
     OtaInfoRefDto selectOtaInfo(@Param("otaInfoId") String otaInfoId);

    /**
     * 更新去啊这渠道TbType（直连类型）
     */
     void updateOtaInfoTbType(OtaInfoRefDto otaInfoRefDto);

    /**
     * 查询appKey 的公司id集合 排序当前公司id
     * @param otaInfo 渠道对象
     * @return 公司id集合
     */
     List<String> selectOtaByAppKey(OtaInfoRefDto otaInfo);

    /**
     *
     * @param otaInfo 渠道对象
     * @return 拥有此appKey 时间最早的一条数据
     */
     OtaInfoRefDto selectOtaInfoByAppKey(OtaInfoRefDto otaInfo);

    List<OtaInfoRefDto> selectOtaCompanyRefByOtaType(@Param("otaTypeName") String otaTypeName);

    /**
     * 查询公司具体渠道信息
     * @param otaInfoRefId otainfo_company_ref 主键
     * @return
     */
    OtaInfoRefDto selectOtaInfoRefDtoById(@Param("otaInfoRefId")String otaInfoRefId);

    void updateOtaInfo(OtaInfoRefDto otaInfoRefDto);

    List<OtaInfoRefDto> selectAllOtaByType(@Param("otaTypeName")String otaTypeName);
}
