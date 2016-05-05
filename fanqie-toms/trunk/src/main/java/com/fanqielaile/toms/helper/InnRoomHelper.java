package com.fanqielaile.toms.helper;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.TomsConstants;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * DESC : 获取oms客栈 房型  房态数据
 * @author : 番茄木-ZLin
 * @data : 2015/12/17
 * @version: v1.0.0
 */
public class InnRoomHelper {

    private static  final Logger log = LoggerFactory.getLogger(InnRoomHelper.class);
    /**
     * 获取oms房态数据
     * @param roomStatusUrl oms房态url
     * @throws IOException
     */
    public static List<RoomStatusDetail> getRoomStatus(String roomStatusUrl) throws IOException {
        String httpGets = HttpClientUtil.httpGets(roomStatusUrl, null);
        JSONObject jsonObject = JSONObject.fromObject(httpGets);
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list") != null) {
            return  JacksonUtil.json2list(jsonObject.get("list").toString(), RoomStatusDetail.class);
        }else {
            log.info("oms 房态返回错误："+jsonObject.get("message"));
        }
        return null;
    }

    /**
     * 获取oms房型数据
     * @param roomTypeUrl oms房型 url
     * @throws IOException
     */
    public static List<RoomTypeInfo> getRoomTypeInfo(String roomTypeUrl) throws IOException{
        String roomTypeGets = HttpClientUtil.httpGets(roomTypeUrl, null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
        //房型
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null) {
            return JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
        }else {
            log.info("oms 房型返回错误："+jsonObject.get("message"));
        }
        return null;
    }

    /**
     * 获取oms客栈基本信息
     * @param innUrl oms 客栈 url
     * @throws IOException
     */
    public static InnDto getInnInfo(String innUrl)throws IOException{
        String innInfoGet = HttpClientUtil.httpGets(innUrl, null);
        JSONObject jsonInn = JSONObject.fromObject(innInfoGet);
        //客栈
        if (TomsConstants.SUCCESS.equals(jsonInn.get("status").toString()) && jsonInn.get("list")!=null) {
            return JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
        }
        return null;
    }

    /**
     * 更新每一个房型的房态
     * @param list 房型集合
     * @param statusDetails
     */
    public static void updateRoomTypeInfo(List<RoomTypeInfo> list, List<RoomStatusDetail> statusDetails) {

        if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(statusDetails)){
            for (RoomTypeInfo roomTypeInfo:list){
                for (RoomStatusDetail detail:statusDetails){
                    if (roomTypeInfo.getRoomTypeId().equals(detail.getRoomTypeId())){
                        roomTypeInfo.setRoomDetail(detail.getRoomDetail());
                        roomTypeInfo.setRatePlanConfig(detail.getRatePlanConfig());
                        roomTypeInfo.setRatePlanCode(detail.getRatePlanCode());
                    }
                }
            }
        }
    }

    /**
     * 获取oms 某一个房型的房态信息
     * @param checkRoomUrl 单个房型 房态信息
     * @return
     * @throws IOException
     */
    public static List<RoomDetail> getRoomDetail(String checkRoomUrl)throws IOException{
        String roomTypeGets = HttpClientUtil.httpGets(checkRoomUrl, null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString())) {
            Object o1 = jsonObject.get("data");
            if(!JSONNull.getInstance().equals(o1)){
                String data = jsonObject.getJSONArray("data").toString();
                if (!StringUtils.isEmpty(data)){
                    return  JacksonUtil.json2list(jsonObject.getJSONArray("data").toString(), RoomDetail.class);
                }
            }
        }
        return null;
    }

    /**
     * 获取客栈下架的房型
     * @param company 公司信息
     */
    public static List<SellingRoomType> obtSellingRoomType(String from , String to,Company company )throws Exception{
        String sellingRoomTypeUrl = DcUtil.omsSellingRoomType(from,to,company.getOtaId(), company.getUserAccount(),
                company.getUserPassword(), CommonApi.sellingRoomType);
        String roomTypeGets = HttpClientUtil.httpGets(sellingRoomTypeUrl, null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null) {
            return JacksonUtil.json2list(jsonObject.getJSONArray("list").toString(), SellingRoomType.class);
        }
        return null;
    }


    public static List<RoomTypeInfo> obtRoomTypeInfoList(Company company,TBParam tbParam){
        String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId(), CommonApi.ROOM_TYPE);
        String roomStatus = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId(), CommonApi.roomStatus, Constants.day);
        log.info("zh room_type url :"+room_type);
        log.info("zh roomStatus url :"+roomStatus);
        List<RoomTypeInfo> roomTypeInfoList = null;
        try {
            roomTypeInfoList = InnRoomHelper.getRoomTypeInfo(room_type);
            List<RoomStatusDetail> statusDetails = InnRoomHelper.getRoomStatus(roomStatus);
            InnRoomHelper.updateRoomTypeInfo(roomTypeInfoList, statusDetails);
        } catch (IOException e) {
            log.error("获取客栈的房型房态信息异常",e);
        }
        return roomTypeInfoList;
    }

}
