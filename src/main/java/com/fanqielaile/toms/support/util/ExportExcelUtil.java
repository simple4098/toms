package com.fanqielaile.toms.support.util;

import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqielaile.toms.dto.OrderParamDto;
import com.fanqielaile.toms.dto.OrderStatisticsDto;
import com.fanqielaile.toms.dto.fc.FcInnImg;
import com.fanqielaile.toms.dto.fc.FcInnInfoDto;
import com.fanqielaile.toms.dto.fc.FcRoomTypeDtoInfo;
import com.fanqielaile.toms.model.OrderOtherPrice;
import com.fanqielaile.toms.model.fc.FcHotelInfo;
import com.fanqielaile.toms.service.IOrderService;

import org.apache.commons.collections.ListUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.util.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Resource
    private static IOrderService orderService;
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
    	Map map = new HashMap<String, Integer>();
    	int i=0;
    	int j=5;
    	int k=0;
    	int width=3;
    	int listWidth=11;
    	int[] priceSize = null; //记录其他消费项目的子项目长度
    	if(orderOtherPrice != null && orderOtherPrice.size() != 0){
    		priceSize = new int[orderOtherPrice.size()];
    	}
    	if(orderStatisticsDto.getOtherConsumer() != null && orderStatisticsDto.getOtherConsumer().size() != 0){
    		int count=0;
//    		List<OrderOtherPrice> list = new ArrayList<>();
    		orderStatisticsDto.getOtherConsumer().remove(null);
    		for(OrderOtherPrice item:orderStatisticsDto.getOtherConsumer()){
    			count++;
    		}
//    		orderStatisticsDto.setOtherConsumer(list);
    		width = 4 + count;
    	}
    	if(orderOtherPrice != null && orderOtherPrice.size() != 0){
	    	for(OrderOtherPrice item : orderOtherPrice){
	    		if(item != null && item.getPriceNameList() != null && item.getPriceNameList().size() != 0){
		    		for(String subType : item.getPriceNameList()){
		    			map.put(item.getConsumerProjectName()+":"+subType, j+i);
		    			i++;
		    		}
		    		priceSize[k] = item.getPriceNameList().size();
		    		k++;
		    		j = j + 2 + item.getPriceNameList().size();
		    	}
	    	}
	    	listWidth = listWidth + j;
	    }
    	HSSFWorkbook workbook = new HSSFWorkbook();//建立工作空间
        HSSFSheet sheet = null;//建立sheet
        sheet = workbook.createSheet("订单列表");// 创建sheet
        HSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFRow row = sheet.createRow(0);// 设置excel第一行
//        createOneRow(width,row);
        Region region = new Region((short)0,(short)0,(short)0,(short)width);
        sheet.addMergedRegion(region);
        row.createCell(0).setCellValue("时间范围："+orderParamDto.getBeginDate()+"~"+orderParamDto.getEndDate()+"            渠道："+orderParamDto.getChannelSource()+"         酒店："+orderParamDto.getInnName());
        row = sheet.createRow(1);// 设置excel第2行
//        createOneRow(width, row);
        region = new Region((short)1,(short)0,(short)1,(short)width);
        sheet.addMergedRegion(region);
        row.createCell(0).setCellValue("营业汇总");
//        createRows(width,2,4,sheet);
        sheet.createRow(2);
        sheet.createRow(3);
        sheet.createRow(4);
        sheet.createRow(5);
        region = new Region((short)2,(short)3,(short)2,(short)(width-1));
        sheet.addMergedRegion(region);
        sheet.getRow(2).createCell(0).setCellValue("房间数");
        sheet.getRow(2).createCell(1).setCellValue("总营业额");
        sheet.getRow(2).createCell(2).setCellValue("房费成本");
        sheet.getRow(2).createCell(3).setCellStyle(style);
        sheet.getRow(2).createCell(3).setCellValue("其他消费");
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
        if(width != 4){
        	sheet.getRow(4).createCell(3).setCellValue("成本");
        	sheet.getRow(5).createCell(3).setCellValue("数量");
        	for(int m=4;m<width;m++){
        		sheet.getRow(3).createCell(m).setCellValue(orderStatisticsDto.getOtherConsumer().get(m-4).getConsumerProjectName());
        		sheet.getRow(4).createCell(m).setCellValue(orderStatisticsDto.getOtherConsumer().get(m-4).getTotalCost()+"");
        		sheet.getRow(5).createCell(m).setCellValue(orderStatisticsDto.getOtherConsumer().get(m-4).getNums()+"");
        	}
        }
        sheet.getRow(3).createCell(width).setCellValue(orderStatisticsDto.getProfit()+"");
        
        
        setResposeHeader(response, fileName);
        String[] innHeader = {"序号", "渠道订单号", "订单状态", "客栈名称", "客人姓名", "房型", "房间数", "住离日期","房费成本","总收入","利润","岗点（经手人）", "下单时间"};
        String[] innDataMeta = {"id", "channelOrderCode", "orderStatus", "innName", "guestName", "roomTypeName", "homeAmount", "liveLeaveDate","costPrice", "prepayPrice","profit","operator","orderTime"};
        
        row = sheet.createRow(6);
//        createOneRow(listWidth, row);
        region = new Region((short)6,(short)0,(short)6,(short)width);
        sheet.addMergedRegion(region);
        row.createCell(0).setCellValue("订单明细");
        row = sheet.createRow(7);
        sheet.createRow(8);
        int otherPriceStart = 5;
        int subTypeIndex = 5;
        boolean isMerger;
        
        for (int n = 0; n < 5; n++) {
        	region = new Region((short)7,(short)n,(short)8,(short)n);
            sheet.addMergedRegion(region);
            row.createCell(n).setCellValue(innHeader[n]);// 设置单元格中表头
        }
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
        for (int n = otherPriceStart; n <= listWidth; n++) {
        	region = new Region((short)7,(short)n,(short)8,(short)n);
            sheet.addMergedRegion(region);
            row.createCell(n).setCellValue(innHeader[5+(n-otherPriceStart)]);// 设置单元格中表头
        }
        
        int imgAll = 0;
        int roomAll = 0;
        int innIndex = 1;
        if (!CollectionUtils.isEmpty(orderDtos)) {
            for (OrderParamDto order : orderDtos) {
                Map innMap = order.toMap();
                HSSFRow row1 = null;
                List<OrderOtherPrice> otherTotalCost = orderService.statisticsOrderOtherPrice(order.getId());
                BigDecimal profit = orderService.countOrderProfit(order, otherTotalCost);
                row1 = sheet.createRow(innIndex);// 设置下一行数据
                for (int m = 0; m < innDataMeta.length; m++) {
                    row1.createCell(m).setCellValue(innMap.get(innDataMeta[m]) + "");// 设置每一个单元格的信息
                }
                innIndex++;

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
    
    /**
     * 创建表格的多行
     * @param width
     * 			表格宽度-1
     * @param start 
     * 			起始行-1
     * @param height
     * 			行数
     * @param sheet
     * @param row
     */
    private static void createRows(int width, int start, int height, HSSFSheet sheet) {
		// TODO Auto-generated method stub
    	HSSFRow row = null;
    	for(int m=0;m<=height;m++){
    		row = sheet.createRow(start + m);
          	createOneRow(width, row);
        }
	}

	/**
     * 创建表格的一行
     * @param width
     * 			表格宽度-1
     * @param row
     */
    private static void createOneRow(int width, HSSFRow row) {
    	 for(int m=0;m<=width;m++){
         	row.createCell(m);
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