package com.fanqielaile.toms.support.util;

import com.fanqielaile.toms.dto.fc.FcInnImg;
import com.fanqielaile.toms.dto.fc.FcInnInfoDto;
import com.fanqielaile.toms.dto.fc.FcRoomTypeDtoInfo;
import com.fanqielaile.toms.model.fc.FcHotelInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


/**
 * 导入导出工具类
 *
 * @author tanqp
 */
public class ExportExcelUtil {
    /**
     * 设置头信息
     *
     * @param response
     * @param fileName
     */
    private static void setResposeHeader(HttpServletResponse response,
                                         String fileName) {
        response.setContentType("application/octet-stream;charset=utf-8");
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
     * @param fileName  文件名
     * @throws Exception
     */
    public static void execlExport(List<FcInnInfoDto> fcHotelInfoList, HttpServletResponse response, String fileName) throws IOException {

        String[] innHeader = {"客栈id","客栈名称", "地址", "联系电话", "省份Code", "城市code", "商圈","房间数" ,"百度经度","百度纬度","腾讯经度","腾讯纬度","描述"};
        String[] innDataMeta = {"innId","innName", "addr", "frontPhone", "provinceCode", "cityCode", "businessCode","roomNum","baiduLon","baiduLat","txLon","txLat","innInfo"};


        String[] roomHeader = {"客栈id","房型名称", "房型ID","楼层", "床长", "床宽", "面积","床型","设施", "简介"};
        String[] roomDataMeta = {"innId","roomTypeName", "roomTypeId" ,"floorNum", "bedLen", "bedWid","roomArea", "bedType","facilities", "roomInfo"};


        String[] imgHeader = {"客栈id","房型ID","图片url", "图片名称"};
        String[] imgDataMeta = {"innId","roomTypeId", "imgUrl" ,"imgName"};



        setResposeHeader(response, fileName);
        HSSFWorkbook workbook = new HSSFWorkbook();//建立工作空间
        HSSFSheet sheet = null;//建立sheet
        int imgAll=0;
        int roomAll=0;
        int innIndex=1;
        if (!CollectionUtils.isEmpty(fcHotelInfoList)){
            for (FcInnInfoDto fcInnInfoDto:fcHotelInfoList){
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
                    row1.createCell(j).setCellValue(innMap.get(innDataMeta[j])+"");// 设置每一个单元格的信息
                }
                innIndex++;

            }

            for (FcInnInfoDto fcInnInfoDto:fcHotelInfoList){
                List<FcInnImg> fcInnImgList = fcInnInfoDto.getFcInnImgList();
                if (!CollectionUtils.isEmpty(fcInnImgList)){
                    imgAll += fcInnImgList.size();
                }

                if (!CollectionUtils.isEmpty(fcInnImgList)){
                    HSSFRow row3;
                    int imgSize = fcInnImgList.size();
                    if (workbook.getSheet("客栈图片列表") == null) {
                        sheet = workbook.createSheet("客栈图片列表");// 动态创建sheet
                        row3 = sheet.createRow(0);// 设置excel第一行
                        for (int k2 = 0; k2 < imgHeader.length; k2++) {
                            row3.createCell(k2).setCellValue(imgHeader[k2]);// 设置单元格中表头
                        }
                    }
                    int o2=imgAll-imgSize+1;
                    for (FcInnImg fcInnImg:fcInnImgList){
                        Map map2 = fcInnImg.toMap();
                        row3 = sheet.createRow(o2);// 设置下一行数据
                        for (int j2 = 0; j2 < imgDataMeta.length; j2++) {
                            row3.createCell(j2).setCellValue(map2.get(imgDataMeta[j2]) + "");// 设置每一个单元格的信息
                        }
                        o2++;
                    }
                }


            }

            for (FcInnInfoDto fcInnInfoDto:fcHotelInfoList){
                List<FcRoomTypeDtoInfo> roomTypeInfoList = fcInnInfoDto.getRoomTypeInfoList();
                if (!CollectionUtils.isEmpty(roomTypeInfoList)){
                    roomAll += roomTypeInfoList.size();
                }
                if (!CollectionUtils.isEmpty(roomTypeInfoList)){
                    HSSFRow row2;
                    int roomSize = roomTypeInfoList.size();
                    if (workbook.getSheet("客栈房型列表") == null) {
                        sheet = workbook.createSheet("客栈房型列表");// 动态创建sheet
                        row2 = sheet.createRow(0);// 设置excel第一行
                        for (int k1 = 0; k1 < roomHeader.length; k1++) {
                            row2.createCell(k1).setCellValue(roomHeader[k1]);// 设置单元格中表头
                        }
                    }
                    int o1=roomAll-roomSize+1;
                    for (FcRoomTypeDtoInfo typeDtoInfo:roomTypeInfoList){
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
            workbook.write(response.getOutputStream());// 返回输出流
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }
}