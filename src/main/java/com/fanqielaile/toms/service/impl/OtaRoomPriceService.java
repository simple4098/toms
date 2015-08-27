package com.fanqielaile.toms.service.impl;

import com.fanqie.util.TomsConstants;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dao.IOtaRoomPriceDao;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.service.IOtaRoomPriceService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DESC :房价管理
 * @author : 番茄木-ZLin
 * @data : 2015/8/19
 * @version: v1.0.0
 */
@Service
public class OtaRoomPriceService implements IOtaRoomPriceService {

    @Resource
    private CompanyDao companyDao;
    @Resource
    private IOtaRoomPriceDao otaRoomPriceDao;


    @Override
    public List<RoomTypeInfo> obtOmsRoomInfo(BangInn bangInn) throws Exception {
        Company company = companyDao.selectCompanyById(bangInn.getCompanyId());
        String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.ROOM_TYPE);
        String roomTypeGets = HttpClientUtil.httpGets(room_type, null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            return JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
        }
        return  null;
    }

    @Override
    public OtaRoomPriceDto findRoomPrice(OtaRoomPriceDto roomPriceDto) {
       return otaRoomPriceDao.selectOtaRoomPriceDto(roomPriceDto);
    }

    @Override
    public void saveRoomPriceDto(OtaRoomPriceDto roomPriceDto , BangInn bangInn) throws Exception {
        Double value = roomPriceDto.getValue();
        if (value<0){
            try {
                Company company = companyDao.selectCompanyById(bangInn.getCompanyId());
                String room_type = DcUtil.roomPriceTypeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.ROOM_TYPE, roomPriceDto.getStartDateStr(),roomPriceDto.getEndDateStr());
                String roomTypeGets = HttpClientUtil.httpGets(room_type, null);
                JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
                List<Double> priceList = new ArrayList<Double>();
                if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
                    List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
                    for (RoomTypeInfo r:list){
                          if (roomPriceDto.getRoomTypeId().equals(r.getRoomTypeId())){
                              List<RoomDetail> roomDetailList = r.getRoomDetail();
                              for (RoomDetail roomDetail:roomDetailList){
                                  priceList.add(roomDetail.getRoomPrice());
                              }
                          }
                    }
                }
                if (!CollectionUtils.isEmpty(priceList)){
                    Collections.sort(priceList);
                    Double price  = priceList.get(0);
                    //value本来为负数, 转化为整数比较
                    if (!(price+value >= 1)){
                        throw  new TomsRuntimeException("减小的价格不能低于1元");
                    }
                }

            } catch (Exception e) {
                throw  new TomsRuntimeException(e.getMessage());
            }
        }
        otaRoomPriceDao.saveOtaRoomPriceDto(roomPriceDto);
    }


}
