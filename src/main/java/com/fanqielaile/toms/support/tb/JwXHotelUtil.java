package com.fanqielaile.toms.support.tb;

import com.fanqie.jw.dto.*;
import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.dto.zh.JointWisdomMappingDto;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.TomsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
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


    public static   List<RoomPrice>  buildRoomPrice(JointWisdomInnRoomMappingDto mappingDto,RoomTypeInfo roomTypeInfo,
                                           OtaRoomPriceDto priceDto, OtaCommissionPercentDto commission){

        if (mappingDto!=null && roomTypeInfo!=null && !CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
            List<RoomPrice> roomPriceList = new ArrayList<>();
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
                Double price = TomsUtil.price(detail.getRoomPrice(), DateUtil.parseDate(detail.getRoomDate()), commission, priceDto);
                roomPriceRelation.setAmountAfterTax(price.toString());
                roomPriceRelation.setAmountBeforeTax(price.toString());
                roomPriceRelation.setStart(detail.getRoomDate());
                roomPriceRelation.setEnd(detail.getRoomDate());
                roomPriceRelation.setUsed(mappingDto.getSj()==1);
                relations.add(roomPriceRelation);
            }
            roomPrice.setRelations(relations);
            roomPriceList.add(roomPrice);
            return roomPriceList;
        }
        return null;
    }

    public static List<Inventory> inventory(JointWisdomInnRoomMappingDto mappingDto,RoomTypeInfo roomTypeInfo){
        if (mappingDto!=null && roomTypeInfo!=null && !CollectionUtils.isEmpty( roomTypeInfo.getRoomDetail())){
            List<Inventory> inventoryList = new ArrayList<>();
            List<RoomDetail> roomDetail = roomTypeInfo.getRoomDetail();
            List<InventoryRelation> relations = new ArrayList<>();
            InventoryRelation inventoryRelation = null;
            Inventory  inventory = new Inventory(mappingDto.getInnCode(),mappingDto.getRoomTypeIdCode());
            for (RoomDetail detail:roomDetail){
                inventoryRelation = new InventoryRelation();
                inventoryRelation.setInventoryCount(detail.getRoomNum());
                inventoryRelation.setStart(detail.getRoomDate());
                inventoryRelation.setEnd(detail.getRoomDate());
                inventoryRelation.setUsed(mappingDto.getSj()==1);
                relations.add(inventoryRelation);
            }
            inventory.setRelations(relations);
            inventoryList.add(inventory);
            return inventoryList;
        }
        return null;
    }

    public static JointWisdomMappingDto buildMapping(OtaRoomPriceDto priceDto,RoomTypeInfo roomTypeInfo,String companyId,Integer innId,String otaId,String otaInfoId,String ratePlanCode,boolean isSj){
        JointWisdomMappingDto jointWisdomInnRoom = new JointWisdomMappingDto();
        jointWisdomInnRoom.setCompanyId(companyId);
        jointWisdomInnRoom.setInnId(innId);
        jointWisdomInnRoom.setRoomTypeId(roomTypeInfo.getRoomTypeId());
        jointWisdomInnRoom.setRoomTypeIdCode(otaId+"_"+roomTypeInfo.getRoomTypeId().toString());
        jointWisdomInnRoom.setInnCode(otaId+"_"+innId.toString());
        jointWisdomInnRoom.setRatePlanCode(ratePlanCode);
        jointWisdomInnRoom.setOtaInfoId(otaInfoId);
        jointWisdomInnRoom.setSj(isSj ? 1 : 0);
        jointWisdomInnRoom.setRoomTypeName(roomTypeInfo.getRoomTypeName());
        jointWisdomInnRoom.setRoomTypeInfo(roomTypeInfo);
        jointWisdomInnRoom.setPriceDto(priceDto);
        return jointWisdomInnRoom;
    }

    public static RoomTypeInfo buildRoomTypeInfo(List<RoomDetail> roomDetailList, JointWisdomInnRoomMappingDto mappingDto) {
        RoomTypeInfo roomTypeInfo = new RoomTypeInfo();
        roomTypeInfo.setRoomDetail(roomDetailList);
        roomTypeInfo.setRoomTypeId(mappingDto.getRoomTypeId());
        return roomTypeInfo;
    }

    /**
     *
     * @param company 公司
     * @param mapping 映射对象
     * @throws java.io.IOException
     */
    public static RoomTypeInfo buildRoomTypeInfo(Company company,JointWisdomInnRoomMappingDto mapping) {
        String room_type = DcUtil.omsRoomTypeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(), mapping.getInnId(), mapping.getRoomTypeId(), CommonApi.checkRoom, Constants.day);
        List<RoomDetail> roomDetailList = null;
        try {
            roomDetailList = InnRoomHelper.getRoomDetail(room_type);
            return JwXHotelUtil.buildRoomTypeInfo(roomDetailList, mapping);
        } catch (IOException e) {
            log.error("众荟组织房型信息异常",e);
        }
        return  null;
    }

    /**
     * 客栈下架
     * @param mappingDto 众荟关联关系
     * @param roomTypeInfo 房型信息
     */
    public static HotelRoomAvail hotelRoomAvail(JointWisdomInnRoomMappingDto mappingDto, RoomTypeInfo roomTypeInfo) {
        List<RoomDetail> roomDetail = roomTypeInfo.getRoomDetail();
        /*String startDate = null;
        String endDate = null;
        if (!CollectionUtils.isEmpty(roomDetail)){
            RoomDetail statDetail = roomDetail.get(0);
            RoomDetail endRoomDetail = roomDetail.get(roomDetail.size() - 1);
            startDate = statDetail.getRoomDate();
            endDate = endRoomDetail.getRoomDate();
        }else {
            startDate = DateUtil.format(new Date(),"yyyy-MM-dd");
            endDate = DateUtil.format(DateUtil.addDay(new Date(), 60), "yyyy-MM-dd");
        }*/

        HotelRoomAvail hotelRoomAvail =new HotelRoomAvail();
        hotelRoomAvail.setStart(DateUtil.format(new Date(),"yyyy-MM-dd"));
        hotelRoomAvail.setEnd( DateUtil.format(DateUtil.addDay(new Date(), 60), "yyyy-MM-dd"));
        hotelRoomAvail.setInnId(mappingDto.getInnCode());
        hotelRoomAvail.setRoomTypeId(mappingDto.getRoomTypeIdCode());
        hotelRoomAvail.setUsed(true);
        hotelRoomAvail.setRatePlan(mappingDto.getRatePlanCode());
        if (mappingDto.getSj()==1){
            hotelRoomAvail.setOpen();
        }else {
            hotelRoomAvail.setClose();
        }
        return hotelRoomAvail;
    }

    public static List<RoomPrice> buildRoomPrice(List<JointWisdomMappingDto> jointWisdomInnRoomList,  OtaCommissionPercentDto commission) {

        List<RoomPrice> list = new ArrayList<>();
        if (jointWisdomInnRoomList!=null ){
            RoomPrice roomPrice = null;
            Double price = null;
            for (JointWisdomMappingDto mappingDto:jointWisdomInnRoomList){
                List<RoomDetail> roomDetail = mappingDto.getRoomTypeInfo().getRoomDetail();
                RoomDetail statDetail = roomDetail.get(0);
                RoomDetail endRoomDetail = roomDetail.get(roomDetail.size() - 1);
                List<RoomPriceRelation> relations = new ArrayList<>();
                roomPrice = new RoomPrice(mappingDto.getInnCode(),mappingDto.getRoomTypeIdCode(),statDetail.getRoomDate(),
                        endRoomDetail.getRoomDate(),mappingDto.getRatePlanCode());
                RoomPriceRelation roomPriceRelation = null;
                for (RoomDetail detail:roomDetail){
                    roomPriceRelation = new RoomPriceRelation();
                    DateUtil.fromDate(1,detail.getRoomDate());
                    price = TomsUtil.price(detail.getRoomPrice(), DateUtil.parseDate(detail.getRoomDate()), commission, mappingDto.getPriceDto());
                    roomPriceRelation.setAmountAfterTax(price.toString());
                    roomPriceRelation.setAmountBeforeTax(price.toString());
                    roomPriceRelation.setStart(detail.getRoomDate());
                    roomPriceRelation.setEnd(detail.getRoomDate());
                    roomPriceRelation.setUsed(mappingDto.getSj()==1);
                    relations.add(roomPriceRelation);
                }
                roomPrice.setRelations(relations);
                list.add(roomPrice);
            }
        }

        return list;
    }

    public static List<Inventory> inventory(List<JointWisdomMappingDto> jointWisdomInnRoomList) {
        List<Inventory> list = new ArrayList<>();
        if (jointWisdomInnRoomList!=null ){
            for (JointWisdomMappingDto mappingDto:jointWisdomInnRoomList){
                List<RoomDetail> roomDetail = mappingDto.getRoomTypeInfo().getRoomDetail();
                List<InventoryRelation> relations = new ArrayList<>();
                InventoryRelation inventoryRelation = null;
                Inventory  inventory = new Inventory(mappingDto.getInnCode(),mappingDto.getRoomTypeIdCode());
                for (RoomDetail detail:roomDetail){
                    inventoryRelation = new InventoryRelation();
                    inventoryRelation.setInventoryCount(detail.getRoomNum());
                    inventoryRelation.setStart(detail.getRoomDate());
                    inventoryRelation.setEnd(detail.getRoomDate());
                    inventoryRelation.setUsed(mappingDto.getSj()==1);
                    relations.add(inventoryRelation);
                }
                inventory.setRelations(relations);
                list.add(inventory);
            }
        }
        return  list;
    }

    public static List<HotelRoomAvail> hotelRoomAvail(List<JointWisdomMappingDto> jointWisdomInnRoomList) {
        List<HotelRoomAvail> list = new ArrayList<>();
        if (jointWisdomInnRoomList!=null){
            for (JointWisdomMappingDto mappingDto:jointWisdomInnRoomList){
                List<RoomDetail> roomDetail = mappingDto.getRoomTypeInfo().getRoomDetail();
                RoomDetail statDetail = roomDetail.get(0);
                RoomDetail endRoomDetail = roomDetail.get(roomDetail.size() - 1);
                HotelRoomAvail hotelRoomAvail =new HotelRoomAvail();
                hotelRoomAvail.setStart(statDetail.getRoomDate());
                hotelRoomAvail.setEnd(endRoomDetail.getRoomDate());
                hotelRoomAvail.setInnId(mappingDto.getInnCode());
                hotelRoomAvail.setRoomTypeId(mappingDto.getRoomTypeIdCode());
                hotelRoomAvail.setUsed(true);
                hotelRoomAvail.setRatePlan(mappingDto.getRatePlanCode());
                if (mappingDto.getSj()==1){
                    hotelRoomAvail.setOpen();
                }else {
                    hotelRoomAvail.setClose();
                }
                list.add(hotelRoomAvail);
            }

        }

        return list;
    }
}
