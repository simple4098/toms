package com.fanqielaile.toms.support.util;

import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.FacilitiesVo;
import com.fanqielaile.toms.dto.OmsImg;
import com.fanqielaile.toms.dto.TbImgDto;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return null;
    }

    /**
     * 把图片集合转化成 json对象
     * @param imgList 图片集合
     */
    public static String obtPics(List<OmsImg> imgList) {
        List<TbImgDto> list = new ArrayList<>();
        TbImgDto tbImgDto = null;
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(imgList)){
            for (OmsImg img:imgList){
                tbImgDto = new TbImgDto(CommonApi.IMG_URL+img.getImgUrl(),img.getIsCover()==1);
                list.add(tbImgDto);
            }
        }
        return JacksonUtil.obj2json(list);
    }

    /**
     * 酒店设置转化成 json对象
     * @param facilitiesMap 客栈设施
     */
    public static String obtHotelFacilities(List<FacilitiesVo> facilitiesMap) {
        if (!CollectionUtils.isEmpty(facilitiesMap)){
            Map<String,Object> map = new HashMap<String, Object>();
            //健身房
            map.put("fitnessClub",false);
            //前台贵重物品保险柜
            map.put("frontDeskSafe",false);
            //乐场/棋牌室
            map.put("casino",false);
            //吸烟区
            map.put("smoking area",false);
            //商务设施
            map.put("Business Facilities",false);
            //自助厨房（餐厅）
            map.put("restaurant",false);
            for (FacilitiesVo f : facilitiesMap){
                if (f.getValue()==1){
                    map.put("free Wi-Fi in all rooms",true);
                    map.put("wifi",true);
                }
                if (f.getValue()==3){
                    map.put("bar",true);
                }
                if (f.getValue()==12){
                    map.put("meetingRoom",true);
                }if (f.getValue()==16){
                    map.put("cafe ",true);
                }

            }
            return  JacksonUtil.obj2json(map);
        }
        return null;
    }

    /**
     * 宽带服务 A,B,C,D。分别代表： A：无宽带，B：免费宽带，C：收费宽带，D：部分收费宽带
     * @param facilitiesMap 服务设施
     * @return
     */
    public static String internet(List<FacilitiesVo> facilitiesMap) {

        if (!CollectionUtils.isEmpty(facilitiesMap)){
            for (FacilitiesVo f : facilitiesMap){
                if (f.getValue()==4){
                    return "B";
                }
            }

        }
        return "A";
    }
}
