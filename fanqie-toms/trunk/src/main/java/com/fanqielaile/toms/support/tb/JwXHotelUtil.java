package com.fanqielaile.toms.support.tb;

import com.fanqie.jw.dto.*;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.support.util.TomsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESC : 众荟 API
 * @author : 番茄木-ZLin
 * @data : 2015/9/28
 * @version: v2.2.0
 */
public class JwXHotelUtil {
    private static final Logger log = LoggerFactory.getLogger(JwXHotelUtil.class);

    private JwXHotelUtil() {
    }


    public static RoomPrice buildRoomPrice(JointWisdomInnRoomMappingDto mappingDto,RoomTypeInfo roomTypeInfo,
                                           OtaRoomPriceDto priceDto, OtaCommissionPercentDto commission){

        if (mappingDto!=null && roomTypeInfo!=null){
            List<RoomDetail> roomDetail = roomTypeInfo.getRoomDetail();
            RoomDetail statDetail = roomDetail.get(0);
            RoomDetail endRoomDetail = roomDetail.get(roomDetail.size() - 1);
            List<RoomPriceRelation> relations = new ArrayList<>();
            RoomPrice roomPrice = new RoomPrice(mappingDto.getInnCode(),mappingDto.getRoomTypeIdCode(),statDetail.getRoomDate(),
                    endRoomDetail.getRoomDate(),mappingDto.getRatePlanCode());
            RoomPriceRelation roomPriceRelation = null;
            for (RoomDetail detail:roomDetail){
                roomPriceRelation = new RoomPriceRelation();
                DateUtil.fromDate(1,detail.getRoomDate());
                Double price = TomsUtil.price(detail.getRoomPrice(),DateUtil.parseDate(detail.getRoomDate()) , commission, priceDto);
                roomPriceRelation.setAmountAfterTax(price.toString());
                roomPriceRelation.setAmountBeforeTax(price.toString());
                roomPriceRelation.setStart(detail.getRoomDate());
                roomPriceRelation.setEnd(DateUtil.fromDate(1, detail.getRoomDate()));
                roomPriceRelation.setUsed(mappingDto.getSj()==1);
                relations.add(roomPriceRelation);
            }
            roomPrice.setRelations(relations);
            return roomPrice;
        }
        return null;
    }

    public static Inventory inventory(JointWisdomInnRoomMappingDto mappingDto,RoomTypeInfo roomTypeInfo){
        if (mappingDto!=null && roomTypeInfo!=null){
            List<RoomDetail> roomDetail = roomTypeInfo.getRoomDetail();
            RoomDetail statDetail = roomDetail.get(0);
            RoomDetail endRoomDetail = roomDetail.get(roomDetail.size() - 1);
            List<InventoryRelation> relations = new ArrayList<>();
            InventoryRelation inventoryRelation = null;
            Inventory  inventory = new Inventory(mappingDto.getInnCode(),mappingDto.getRoomTypeIdCode());
            for (RoomDetail detail:roomDetail){
                inventoryRelation = new InventoryRelation();
                inventoryRelation.setInventoryCount(detail.getRoomNum());
                inventoryRelation.setStart(detail.getRoomDate());
                inventoryRelation.setEnd(DateUtil.fromDate(1, detail.getRoomDate()));
                inventoryRelation.setUsed(mappingDto.getSj()==1);
                relations.add(inventoryRelation);
            }
            inventory.setRelations(relations);
            return inventory;
        }
        return null;
    }

    public static JointWisdomInnRoomMappingDto buildMapping(RoomTypeInfo roomTypeInfo,String companyId,Integer innId,String otaId,String otaInfoId,String ratePlanCode,int sj){
        JointWisdomInnRoomMappingDto jointWisdomInnRoom = new JointWisdomInnRoomMappingDto();
        jointWisdomInnRoom.setCompanyId(companyId);
        jointWisdomInnRoom.setInnId(innId);
        jointWisdomInnRoom.setRoomTypeId(roomTypeInfo.getRoomTypeId());
        jointWisdomInnRoom.setRoomTypeIdCode(otaId + "_" + roomTypeInfo.getRoomTypeId());
        jointWisdomInnRoom.setInnCode(otaId + "_" + roomTypeInfo.getRoomTypeId());
        jointWisdomInnRoom.setRatePlanCode(ratePlanCode);
        jointWisdomInnRoom.setOtaInfoId(otaInfoId);
        jointWisdomInnRoom.setSj(sj);
        return jointWisdomInnRoom;
    }
}
