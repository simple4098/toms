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
            }else if (OtaType.XC.equals(infoRefDto.getOtaType())){
                return "/match/ota/xc";
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
        if (otaInfo!=null && OtaType.FC.equals(otaInfo.getOtaType())){
            return "/match/inn_fc_match_list";
        }
        if (otaInfo!=null && OtaType.TB.equals(otaInfo.getOtaType())){
            if(TBType.DEFAULT==otaInfo.getTbType()){
                return "/match/ota/tb_type";
            }
            return "/match/inn_tb_match_list";
        }
        return "/match/not_ota";
    }

}
