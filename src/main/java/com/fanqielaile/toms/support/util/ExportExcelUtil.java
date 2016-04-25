package com.fanqielaile.toms.support.util;

import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqielaile.toms.dto.OrderParamDto;
import com.fanqielaile.toms.dto.OrderStatisticsDto;
import com.fanqielaile.toms.dto.fc.FcInnImg;
import com.fanqielaile.toms.dto.fc.FcInnInfoDto;
import com.fanqielaile.toms.dto.fc.FcRoomTypeDtoInfo;
import com.fanqielaile.toms.model.DailyInfos;
import com.fanqielaile.toms.model.OrderOtherPrice;
import com.fanqielaile.toms.model.fc.FcHotelInfo;
import com.fanqielaile.toms.service.IOrderService;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.util.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 导入导出工具类
 *
 * @author tanqp
 */
public class ExportExcelUtil {
    private static final Logger log = LoggerFactory.getLogger(ExportExcelUtil.class);
    /**
     * 设置头信息
     *
     * @param response
     * @param fileName
     */
    private static void setResposeHeader(HttpServletResponse response,
                                         String fileName) {
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setContentType("application/x-download");
        try {
            response.setHeader("Content-Disposition", "attachment;filename="
                    + java.net.URLEncoder.encode(fileName, "utf-8"));//  客户端不缓存 
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出数据
     *
     * @param fcHotelInfoList 数据
     * @param response
     * @param fileName        文件名
     * @throws Exception
     */
    public static void execlExport(List<FcInnInfoDto> fcHotelInfoList, HttpServletResponse response, String fileName) throws Exception {

        String[] innHeader = {"客栈id", "客栈名称", "地址", "联系电话", "省份Code", "城市code", "商圈", "房间数", "百度经度", "百度纬度", "腾讯经度", "腾讯纬度", "描述"};
        String[] innDataMeta = {"innId", "innName", "addr", "frontPhone", "provinceCode", "cityCode", "businessCode", "roomNum", "baiduLon", "baiduLat", "txLon", "txLat", "innInfo"};


        String[] roomHeader = {"客栈id", "房型名称", "房型ID", "楼层", "床长", "床宽", "面积", "床型", "设施", "简介"};
        String[] roomDataMeta = {"innId", "roomTypeName", "roomTypeId", "floorNum", "bedLen", "bedWid", "roomArea", "bedType", "facilities", "roomInfo"};


        String[] imgHeader = {"客栈id", "房型ID", "图片url", "图片名称"};
        String[] imgDataMeta = {"innId", "roomTypeId", "imgUrl", "imgName"};


        setResposeHeader(response, fileName);
        HSSFWorkbook workbook = new HSSFWorkbook();//建立工作空间
        HSSFSheet sheet = null;//建立sheet
        int imgAll = 0;
        int roomAll = 0;
        int innIndex = 1;
        if (!CollectionUtils.isEmpty(fcHotelInfoList)) {
            for (FcInnInfoDto fcInnInfoDto : fcHotelInfoList) {
                Map innMap = fcInnInfoDto.toMap();

                HSSFRow row1 = null;
                if (workbook.getSheet("客栈列表") == null) {
                    sheet = workbook.createSheet("客栈列表");// 动态创建sheet
                    row1 = sheet.createRow(0);// 设置excel第一行
                    for (int k = 0; k < innHeader.length; k++) {
                        row1.createCell(k).setCellValue(innHeader[k]);// 设置单元格中表头
                    }
                }
                row1 = sheet.createRow(innIndex);// 设置下一行数据
                for (int j = 0; j < innDataMeta.length; j++) {
                    row1.createCell(j).setCellValue(innMap.get(innDataMeta[j]) + "");// 设置每一个单元格的信息
                }
                innIndex++;

            }

            for (FcInnInfoDto fcInnInfoDto : fcHotelInfoList) {
                List<FcInnImg> fcInnImgList = fcInnInfoDto.getFcInnImgList();
                if (!CollectionUtils.isEmpty(fcInnImgList)) {
                    imgAll += fcInnImgList.size();
                }

                if (!CollectionUtils.isEmpty(fcInnImgList)) {
                    HSSFRow row3;
                    int imgSize = fcInnImgList.size();
                    if (workbook.getSheet("客栈图片列表") == null) {
                        sheet = workbook.createSheet("客栈图片列表");// 动态创建sheet
                        row3 = sheet.createRow(0);// 设置excel第一行
                        for (int k2 = 0; k2 < imgHeader.length; k2++) {
                            row3.createCell(k2).setCellValue(imgHeader[k2]);// 设置单元格中表头
                        }
                    }
                    int o2 = imgAll - imgSize + 1;
                    for (FcInnImg fcInnImg : fcInnImgList) {
                        Map map2 = fcInnImg.toMap();
                        row3 = sheet.createRow(o2);// 设置下一行数据
                        for (int j2 = 0; j2 < imgDataMeta.length; j2++) {
                            row3.createCell(j2).setCellValue(map2.get(imgDataMeta[j2]) + "");// 设置每一个单元格的信息
                        }
                        o2++;
                    }
                }


            }

            for (FcInnInfoDto fcInnInfoDto : fcHotelInfoList) {
                List<FcRoomTypeDtoInfo> roomTypeInfoList = fcInnInfoDto.getRoomTypeInfoList();
                if (!CollectionUtils.isEmpty(roomTypeInfoList)) {
                    roomAll += roomTypeInfoList.size();
                }
                if (!CollectionUtils.isEmpty(roomTypeInfoList)) {
                    HSSFRow row2;
                    int roomSize = roomTypeInfoList.size();
                    if (workbook.getSheet("客栈房型列表") == null) {
                        sheet = workbook.createSheet("客栈房型列表");// 动态创建sheet
                        row2 = sheet.createRow(0);// 设置excel第一行
                        for (int k1 = 0; k1 < roomHeader.length; k1++) {
                            row2.createCell(k1).setCellValue(roomHeader[k1]);// 设置单元格中表头
                        }
                    }
                    int o1 = roomAll - roomSize + 1;
                    for (FcRoomTypeDtoInfo typeDtoInfo : roomTypeInfoList) {
                        Map map1 = typeDtoInfo.toMap();
                        row2 = sheet.createRow(o1);// 设置下一行数据
                        for (int j1 = 0; j1 < roomDataMeta.length; j1++) {
                            row2.createCell(j1).setCellValue(map1.get(roomDataMeta[j1]) + "");// 设置每一个单元格的信息
                        }
                        o1++;

                    }
                }
            }
        }

        try {
            log.info("--------------开始写excel数据-----------------");
            workbook.write(response.getOutputStream());// 返回输出流
            log.info("--------------结束写excel数据-----------------");
        } catch (IOException e) {
            log.info("--------------excel异常-----------------");
            throw new Exception(e.getMessage());
        } finally {
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }

    /**
     * 导出订单列表
     * @param orderParamDto 
     * @param orderDtos
     * @param orderOtherPrice 
     * @param orderStatisticsDto 
     * @param response
     * @param fileName
     */
    @SuppressWarnings("deprecation")
	public static void execlOrderExport(OrderParamDto orderParamDto, List<OrderParamDto> orderDtos, OrderStatisticsDto orderStatisticsDto, List<OrderOtherPrice> orderOtherPrice, HttpServletResponse response, String fileName) throws Exception {
    	//将其他消费类型和子类型与excle表中的列对应
    	Map<String, Integer> map = new HashMap<String, Integer>();
    	int subtypeIndex=0;//用于其他消费子类型计数
    	int typeLocation=5;//用于其他消费父类型定位
    	int width=3;//营业汇总相关表格的宽度
    	int listWidth=12;//订单详细列表的宽度
    	HSSFWorkbook workbook = new HSSFWorkbook();//建立工作空间
        HSSFSheet sheet = workbook.createSheet("订单列表");// 创建sheet
        HSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFRow row = sheet.createRow(0);// 设置excel第一行
        setResposeHeader(response, fileName);
        //对没有订单的情况进行处理
        if(orderStatisticsDto == null){
        	 Region region = new Region((short)0,(short)0,(short)0,(short)10);
             sheet.addMergedRegion(region);
            
             if(orderParamDto.getChannelSource() != null){
            	 row.createCell(0).setCellValue("时间范围："+orderParamDto.getBeginDate()+"~"+orderParamDto.getEndDate()+"            渠道："+orderParamDto.getChannelSource().getText()+"         酒店："+orderParamDto.getInnName());
             }else{
            	 row.createCell(0).setCellValue("时间范围："+orderParamDto.getBeginDate()+"~"+orderParamDto.getEndDate()+"            渠道："+"暂无"+"         酒店："+orderParamDto.getInnName());
             }
             row = sheet.createRow(1);
             region = new Region((short)1,(short)0,(short)1,(short)10);
             sheet.addMergedRegion(region);
             row.createCell(0).setCellValue("暂无订单数据");
        	try {
                log.info("--------------开始写excel数据-----------------");
                workbook.write(response.getOutputStream());// 返回输出流
                log.info("--------------结束写excel数据-----------------");
            } catch (IOException e) {
                log.info("--------------excel异常-----------------");
                throw new Exception(e.getMessage());
            } finally {
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        	return;
        }
        //统计其他消费项目长度
    	if(orderStatisticsDto.getOtherConsumer() != null && orderStatisticsDto.getOtherConsumer().size() != 0){
    		int otherCount=0;
//    		List<OrderOtherPrice> list = new ArrayList<>();
    		orderStatisticsDto.getOtherConsumer().remove(null);
    		for(OrderOtherPrice item:orderStatisticsDto.getOtherConsumer()){
    			otherCount++;
    		}
//    		orderStatisticsDto.setOtherConsumer(list);
    		if(otherCount != 0){//有其他消费的列表宽度
    			width = 4 + otherCount;
    		}
    	}
    	//对其他消费相关的订单详细列表进行定位
    	if(orderOtherPrice != null && orderOtherPrice.size() != 0){
	    	for(OrderOtherPrice item : orderOtherPrice){
	    		item.getPriceNameList().remove(null);
	    		subtypeIndex = 0;
	    		if(item.getPriceNameList().size() != 0){
		    		for(String subType : item.getPriceNameList()){
		    			map.put(item.getConsumerProjectName()+":"+subType, typeLocation+subtypeIndex);
		    			subtypeIndex++;
		    		}
		    		map.put(item.getConsumerProjectName()+"合计", typeLocation+subtypeIndex);
		    		typeLocation = typeLocation + 2 + subtypeIndex;
		    	}
	    	}
	    	listWidth = listWidth + typeLocation - 5;
	    }
    	Region region = new Region((short)0,(short)0,(short)0,(short)width);
        sheet.addMergedRegion(region);
        if(orderParamDto.getChannelSource() != null){
       	 	row.createCell(0).setCellValue("时间范围："+orderParamDto.getBeginDate()+"~"+orderParamDto.getEndDate()+"            渠道："+orderParamDto.getChannelSource().getText()+"         酒店："+orderParamDto.getInnName());
        }else{
       	 	row.createCell(0).setCellValue("时间范围："+orderParamDto.getBeginDate()+"~"+orderParamDto.getEndDate()+"            渠道："+"暂无"+"         酒店："+orderParamDto.getInnName());
        }        
        row = sheet.createRow(1);// 设置excel第2行
        region = new Region((short)1,(short)0,(short)1,(short)width);
        sheet.addMergedRegion(region);
        row.createCell(0).setCellValue("营业汇总");
        sheet.createRow(2);
        sheet.createRow(3);
        sheet.createRow(4);
        sheet.createRow(5);
        sheet.getRow(2).createCell(0).setCellValue("房间数");
        sheet.getRow(2).createCell(1).setCellValue("总营业额");
        sheet.getRow(2).createCell(2).setCellValue("房费成本");
        sheet.getRow(2).createCell(width).setCellValue("利润");
        for(int m=0;m<=2;m++){
        	region = new Region((short)3,(short)m,(short)5,(short)m);
            sheet.addMergedRegion(region);
        }
        region = new Region((short)3,(short)width,(short)5,(short)width);
        sheet.addMergedRegion(region);
        sheet.getRow(3).createCell(0).setCellValue(orderStatisticsDto.getOrderNightNumber());
        sheet.getRow(3).createCell(1).setCellValue(orderStatisticsDto.getTotalPrice()+"");
        sheet.getRow(3).createCell(2).setCellValue(orderStatisticsDto.getTotalCostPrice()+"");
        if(width > 4){//有其他消费时
        	region = new Region((short)2,(short)3,(short)2,(short)(width-1));
            sheet.addMergedRegion(region);
        	sheet.getRow(2).createCell(3).setCellStyle(style);
            sheet.getRow(2).createCell(3).setCellValue("其他消费");
        	sheet.getRow(4).createCell(3).setCellValue("成本");
        	sheet.getRow(5).createCell(3).setCellValue("数量");
        	for(int m=4;m<width;m++){
        		sheet.getRow(3).createCell(m).setCellValue(orderStatisticsDto.getOtherConsumer().get(m-4).getConsumerProjectName());
        		sheet.getRow(4).createCell(m).setCellValue(orderStatisticsDto.getOtherConsumer().get(m-4).getTotalCost()+"");
        		sheet.getRow(5).createCell(m).setCellValue(orderStatisticsDto.getOtherConsumer().get(m-4).getNums()+"");
        	}
        }
        sheet.getRow(3).createCell(width).setCellValue(orderStatisticsDto.getProfit()+"");
        
        
        String[] innHeader = {"序号", "渠道订单号", "订单状态", "客栈名称", "客人姓名", "房型", "房间数", "住离日期","房费成本","总收入","利润","岗点（经手人）", "下单时间"};
        String[] innDataMeta = {"id", "channelOrderCode", "orderStatus", "innName", "guestName", "roomTypeName", "homeAmount", "liveLeaveDate","costPrice", "prepayPrice","profit","operator","orderTime"};
        
        row = sheet.createRow(6);
        region = new Region((short)6,(short)0,(short)6,(short)width);
        sheet.addMergedRegion(region);
        row.createCell(0).setCellValue("订单明细");
        row = sheet.createRow(7);
        sheet.createRow(8);
        int otherPriceStart = 5;
        int subTypeIndex = 5;
        //处理其他消费前订单相关表头显示内容
        for (int n = 0; n < 5; n++) {
        	region = new Region((short)7,(short)n,(short)8,(short)n);
            sheet.addMergedRegion(region);
            row.createCell(n).setCellValue(innHeader[n]);
        }
        //处理有其他消费时订单相关表头显示内容
        if(orderOtherPrice != null && orderOtherPrice.size() != 0){
        	for(OrderOtherPrice item : orderOtherPrice){
        		subTypeIndex = otherPriceStart;
//        		item.getPriceNameList().remove(null);
            	region = new Region((short)7,(short)otherPriceStart,(short)7,(short)(item.getPriceNameList().size()+otherPriceStart));
                sheet.addMergedRegion(region);
                row.createCell(otherPriceStart).setCellValue(item.getConsumerProjectName()+"人数");
                for(String subType : item.getPriceNameList()){
                	sheet.getRow(8).createCell(subTypeIndex).setCellValue(subType);
                	subTypeIndex++;
                }
                sheet.getRow(8).createCell(subTypeIndex).setCellValue("合计");
                region = new Region((short)7,(short)(item.getPriceNameList().size()+otherPriceStart+1),(short)8,(short)(item.getPriceNameList().size()+otherPriceStart+1));
                sheet.addMergedRegion(region);
                row.createCell(item.getPriceNameList().size()+otherPriceStart+1).setCellValue(item.getConsumerProjectName()+"成本");
                otherPriceStart = item.getPriceNameList().size()+otherPriceStart+2;
                
            }
        }
        //处理其他消费相关信息后订单相关表头显示内容
        for (int n = otherPriceStart; n <= listWidth; n++) {
        	region = new Region((short)7,(short)n,(short)8,(short)n);
            sheet.addMergedRegion(region);
            row.createCell(n).setCellValue(innHeader[5+(n-otherPriceStart)]);// 设置单元格中表头
        }
        
        int innIndex = 9;
        int index = 1;//订单编号
        //对订单列表内容进行填空
        if (!CollectionUtils.isEmpty(orderDtos)) {
            for (OrderParamDto order : orderDtos) {
                Map innMap = order.toMap();
//                order.getDailyInfoses().remove(null);
                int count = order.getDailyInfoses().size();
                if(count == 0){
                	count = 1;
                }
//                List<OrderOtherPrice> otherTotalCost = orderService.statisticsOrderOtherPrice(order.getId());
//                BigDecimal profit = orderService.countOrderProfit(order, otherTotalCost);
                row = sheet.createRow(innIndex);// 设置下一行数据
                for(int m=1;m<count;m++){
                	sheet.createRow(innIndex+m);
                }
                //合并房型前的单元格
                for(int m=0;m<listWidth-7;m++){
                	region = new Region((short)innIndex,(short)(m),(short)(innIndex+count-1),(short)(m));
                    sheet.addMergedRegion(region);
                }
                //合并房间数后的单元格
                for(int m=listWidth-5;m<=listWidth;m++){
                	region = new Region((short)innIndex,(short)(m),(short)(innIndex+count-1),(short)(m));
                    sheet.addMergedRegion(region);
                }
                row.createCell(0).setCellValue(index);
                for (int m = 1; m < 5; m++) {
                    row.createCell(m).setCellValue(innMap.get(innDataMeta[m]) + "");// 设置其他消费前的单元格信息
                }
                for (int m = listWidth-5; m <= listWidth; m++) {
                	if(m == listWidth-1 && innMap.get("operator") == null){
                		row.createCell(m).setCellValue("系统");
                		continue;
                	}
                	row.createCell(m).setCellValue(innMap.get(innDataMeta[m-listWidth+12]) + "");// 设置其他消费后的单元格信息
                }
                for(OrderOtherPrice item : order.getOrderOtherPriceList()){
                	//其他消费数量在对应的单元格填空
                	if(item.getConsumerProjectName() != null && item.getPriceName() != null){
                		row.createCell((short)map.get(item.getConsumerProjectName()+":"+item.getPriceName()).shortValue()).setCellValue(item.getNums());
                	}
                }
                Integer totalLocate = null;
                Integer totalCost = null;
                //对其他消费的统计填空
                if(order.getOtherTotalCost() != null){
                	  for(OrderOtherPrice item : order.getOtherTotalCost()){
						if (item != null && item.getConsumerProjectName() != null) {
							totalLocate = map.get(item.getConsumerProjectName() + "合计");
							totalCost = totalLocate + 1;
							row.createCell(totalLocate.shortValue()).setCellValue(item.getNums());
							if(item.getTotalCost() != null){
								row.createCell(totalCost.shortValue()).setCellValue(item.getTotalCost().floatValue());
							}
						}
                      }
                }
                int m = innIndex;
                order.getDailyInfoses().remove(null);
                //对房间名和房间数填空
                for(DailyInfos daily: order.getDailyInfoses()){
                	if(daily.getRoomTypeName() != null && daily.getRoomTypeNums() != null){
                    	sheet.getRow(m).createCell(listWidth-7).setCellValue(daily.getRoomTypeName());
                    	sheet.getRow(m).createCell(listWidth-6).setCellValue(daily.getRoomTypeNums());
                	}
//                	if(daily.getRoomTypeName() != null){
//                    	sheet.getRow(m).createCell(listWidth-7).setCellValue(daily.getRoomTypeName());
//                	}
//                	if(daily.getRoomTypeNums() != null){
//                    	sheet.getRow(m).createCell(listWidth-6).setCellValue(daily.getRoomTypeNums());
//                	}
                	m++;
                	innIndex++;
                }
                index++;
            }
            try {
                log.info("--------------开始写excel数据-----------------");
                workbook.write(response.getOutputStream());// 返回输出流
                log.info("--------------结束写excel数据-----------------");
            } catch (IOException e) {
                log.info("--------------excel异常-----------------");
                throw new Exception(e.getMessage());
            } finally {
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        }
    }
    


	public static void zhExeclExport(List<JointWisdomInnRoomMappingDto> allList, HttpServletResponse response, String s)throws Exception {

        String[] innHeader = { "酒店名称", "酒店地址", "省份", "城市", "房型名称", "酒店代码", "房型代码", "房价代码"};
        String[] innDataMeta = {"innName", "address", "province", "city", "roomTypeName", "innCode", "roomTypeIdCode", "ratePlanCode"};
        setResposeHeader(response, s);
        HSSFWorkbook workbook = new HSSFWorkbook();//建立工作空间
        HSSFSheet sheet = null;//建立sheet
        int innIndex = 1;
        if (!CollectionUtils.isEmpty(allList)) {
            for (JointWisdomInnRoomMappingDto jointWisdom : allList) {
                Map innMap = jointWisdom.toMap();
                HSSFRow row1 = null;
                if (workbook.getSheet("众荟客栈匹配") == null) {
                    sheet = workbook.createSheet("众荟客栈匹配");// 动态创建sheet
                    row1 = sheet.createRow(0);// 设置excel第一行
                    for (int k = 0; k < innHeader.length; k++) {
                        row1.createCell(k).setCellValue(innHeader[k]);// 设置单元格中表头
                    }
                }
                row1 = sheet.createRow(innIndex);// 设置下一行数据
                for (int j = 0; j < innDataMeta.length; j++) {
                    row1.createCell(j).setCellValue(innMap.get(innDataMeta[j]) + "");// 设置每一个单元格的信息
                }
                innIndex++;
            }
            try {
                log.info("--------------开始写excel数据-----------------");
                workbook.write(response.getOutputStream());
                log.info("--------------结束写excel数据-----------------");
            } catch (IOException e) {
                log.info("--------------excel异常-----------------");
                throw new Exception(e.getMessage());
            } finally {
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        }

    }
}