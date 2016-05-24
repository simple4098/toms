package com.fanqielaile.toms.support.util;

import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.enums.TBType;

/**
 * DESC : 渠道视图util
 * @author : 番茄木-ZLin
 * @data : 2015/12/3
 * @version: v1.0.0
 */
public class ModelViewUtil {

    private ModelViewUtil(){}

    /**
     * 申请渠道 表单页面
     * @param infoRefDto 渠道信息
     */
    public static String view( OtaInfoRefDto infoRefDto){
        if (infoRefDto!=null){
            if ( OtaType.FC.equals(infoRefDto.getOtaType())){
                return "/match/ota/fc";
            }else if(OtaType.TB.equals(infoRefDto.getOtaType())){
                return "/match/ota/tb";
            }else if(OtaType.CREDIT.equals(infoRefDto.getOtaType())){
                return "/match/ota/tb-credit";
            }else if (OtaType.XC.equals(infoRefDto.getOtaType())){
                return "/match/ota/xc";
            }else if (OtaType.ZH.equals(infoRefDto.getOtaType())){
                return "/match/ota/zh";
            } else if (OtaType.QUNAR.equals(infoRefDto.getOtaType())) {
                return "/match/ota/qunar";
            }
        }
        return "/match/not_ota";
    }

    /**
     * 渠道列表
     * @param otaInfo
     * @return
     */
    public static String otaListView(OtaInfoRefDto otaInfo){
        if(otaInfo!=null ){
            if ( OtaType.FC.equals(otaInfo.getOtaType())){
                return "/match/inn_fc_match_list";
            }
            if ( OtaType.TB.equals(otaInfo.getOtaType())){
                    if(TBType.DEFAULT==otaInfo.getTbType()){
                        return "/match/ota/tb_type";
                    }
                    return "/match/inn_tb_match_list";
                }if ( OtaType.CREDIT.equals(otaInfo.getOtaType())){
                    if(TBType.DEFAULT==otaInfo.getTbType()){
                        return "/match/ota/tb_type_credit";
                    }
                    return "/match/inn_tb_credit_match_list";
            }
            if(OtaType.XC.equals(otaInfo.getOtaType())){
                return "/match/inn_xc_match_list";
            }if(OtaType.ZH.equals(otaInfo.getOtaType())){
                return "/match/inn_zh_match_list";
            }if(OtaType.QUNAR.equals(otaInfo.getOtaType())){
                return "/match/inn_qunar_match_list";
            }
        }

        return "/match/not_ota";
    }

}
