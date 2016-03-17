package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.Result;

import java.util.List;

/**
 * DESC : 查询企业开通渠道
 * @author : 番茄木-ZLin
 * @data : 2015/7/3
 * @version: v1.0.0
 */
public interface IOtaInfoService {

    /**
     * 查询企业开通的渠道列表
     * @param companyCode 企业唯一码
     */
    List<OtaInfoRefDto> findAllOtaByCompany(String companyCode);

    /**
     * 查询公司下面某一个具体的渠道信息
     * @param companyId 企业id
     * @param otaType 渠道类型
     */
    OtaInfoRefDto findAllOtaByCompanyAndType(String companyId,OtaType otaType);

    /**
     *  全部的渠道的关联表信息
     */
    List<OtaInfoRefDto> findOtaInfoList();

    /**
     * 查询所有的渠道
     */
    List<OtaInfoRefDto> findAllOtaInfo();

    /**
     * 渠道列表（没有开通的渠道也要显示出来）
     * @param companyId 公司id
     */
    List<OtaInfoRefDto> findOtaInfoListByCompanyId(String companyId);

    /**
     * 保存企业的开通的渠道信息
     * @param otaInfoRefDto  公司与渠道关联对象
     */
    void saveOtaInfo(OtaInfoRefDto otaInfoRefDto)throws Exception;

    /**
     * 查询公司下面某一个具体的渠道信息
     * @param companyId 公司id
     * @param otaInfoId 渠道id
     */
    OtaInfoRefDto findOtaInfoByCompanyIdAndOtaInnOtaId(String companyId, String otaInfoId);

    /**
     * 查询渠道详细信息
     * @param otaInfoId 渠道id
     */
    OtaInfoRefDto findOtaInfo(String otaInfoId);

    /**
     * 更新淘宝渠道的 直连方式
     * @return
     */
    Result updateOtaInfoTbType(OtaInfoRefDto otaInfoRefDto);

    /**
     * 查询appKey 的公司id集合 排序当前公司id
     * @param otaInfo 渠道信息
     */
    List<String> findOtaByAppKey(OtaInfoRefDto otaInfo);

    /**
     * 查询公司具体渠道信息
     * @param otaInfoRefId otainfo_company_ref 主键
     * @return
     */
    OtaInfoRefDto findOtaInfoRefDtoById(String otaInfoRefId);

    /**
     * 更新公司渠道 appKey  appSecret sessionKey;
     * @param otaInfoRefDto
     */
    void updateOtaInfo(OtaInfoRefDto otaInfoRefDto);

    List<OtaInfoRefDto> findAllOtaByType(OtaType credit);
}
