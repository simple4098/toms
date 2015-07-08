package com.fanqielaile.toms.support.util;

import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dto.FacilitiesVo;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * DESC : tp店 服务设施
 * @author : 番茄木-ZLin
 * @data : 2015/7/8
 * @version: v1.0.0
 */
public class TPServiceUtil {

    private TPServiceUtil(){}

    /**
     * 电视 3 空调 2 宽带 4 卫浴5 早餐1 电话6
     * @param list
     * @return
     */
    public static String jsonService(List<FacilitiesVo> list){
        if (!CollectionUtils.isEmpty(list)){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("bar",false);
            map.put("pubtoilet",false);
            map.put("idd",false);
            for (FacilitiesVo f : list){
                if (f.getValue()==3){
                   map.put("catv",true);
                }
                if (f.getValue()==6){
                    map.put("ddd",true);
                }
                if (f.getValue()==5){
                    map.put("toilet",true);
                }
            }
            return  JacksonUtil.obj2json(map);
        }
        return "";
    }
}
