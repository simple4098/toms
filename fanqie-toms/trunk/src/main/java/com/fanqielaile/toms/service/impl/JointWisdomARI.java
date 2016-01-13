package com.fanqielaile.toms.service.impl;

import com.fanqie.jw.dto.Inventory;
import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqie.jw.dto.RoomPrice;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.service.IJointWisdomARI;
import com.fanqielaile.toms.support.JointWisdomARIUtils;
import com.fanqielaile.toms.support.tb.JwXHotelUtil;
import org.opentravel.ota._2003._05.OTAHotelInvCountNotifRS;
import org.opentravel.ota._2003._05.OTAHotelRatePlanNotifRS;
import org.springframework.stereotype.Service;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/1/13
 * @version: v1.0.0
 */
@Service
public class JointWisdomARI implements IJointWisdomARI {



    @Override
    public void updateJsPriceInventory(JointWisdomInnRoomMappingDto mappingDto, RoomTypeInfo roomTypeInfo,
                                       OtaRoomPriceDto priceDto, OtaCommissionPercentDto commission) {
        if (roomTypeInfo!=null){
            RoomPrice roomPrice = JwXHotelUtil.buildRoomPrice(mappingDto,roomTypeInfo,priceDto,commission);
            Inventory inventory = JwXHotelUtil.inventory(mappingDto, roomTypeInfo);
            try {
                OTAHotelRatePlanNotifRS otaHotelRatePlanNotifRS = JointWisdomARIUtils.pushRoomPrice(roomPrice);
                OTAHotelInvCountNotifRS otaHotelInvCountNotifRS = JointWisdomARIUtils.pushRoomInventory(inventory);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
