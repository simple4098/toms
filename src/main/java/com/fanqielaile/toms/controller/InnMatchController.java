package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.fc.FcRatePlanDto;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.model.fc.FcRatePlan;
import com.fanqielaile.toms.model.fc.FcRoomTypeInfo;
import com.fanqielaile.toms.service.*;
import com.fanqielaile.toms.service.impl.InnLabelService;
import com.fanqielaile.toms.support.util.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/11
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/innMatch")
public class InnMatchController extends BaseController {
    private static  final Logger log = LoggerFactory.getLogger(InnMatchController.class);
    @Resource
    private IBangInnService bangInnService;
    @Resource
    private InnLabelService innLabelService;
    @Resource
    private IOtaInfoService otaInfoService;
    @Resource
    private IInnMatchService innMatchService;
    @Resource
    private IFcHotelInfoService fcHotelInfoService;
    @Resource
    private IOtaInnOtaService  otaInnOtaService;
    @Resource
    private IOtaRoomPriceService otaRoomPriceService;
    @Resource
    private IFcRatePlanService fcRatePlanService;
    @Resource
    private IFcRoomTypeFqService fcRoomTypeFqService;

    //匹配列表
    @RequestMapping("/match")
    public String matchList(Model model,BangInnDto bangInnDto,@RequestParam(defaultValue = "1", required = false) int page){
        UserInfo userInfo = getCurrentUser();
        List<OtaInfoRefDto> infoRefDtoList = otaInfoService.findOtaInfoListByCompanyId(userInfo.getCompanyId());
        BangInnDto innDto = BangInnDto.userInfoToBangInnDto(userInfo,bangInnDto,infoRefDtoList);
        List<InnLabel> innLabels = innLabelService.findLabelsByCompanyId(innDto.getCompanyId());
        List<BangInnDto> bangInns = bangInnService.findFcBangInn(bangInnDto, new PageBounds(page, 1));
        model.addAttribute("labels", innLabels);
        model.addAttribute("list",bangInns);
        model.addAttribute("bangInnDto",bangInnDto);
        Paginator paginator = ((PageList) bangInns).getPaginator();
        model.addAttribute("pagination", PaginationHelper.toPagination(paginator));
        model.addAttribute("infoList", infoRefDtoList);
        return "/match/inn_match_list";
    }

    //客栈匹配详情
    @RequestMapping("/matchDetail")
    public String matchOtaList(Model model,BangInnDto bangInnDto){
        UserInfo userInfo = getCurrentUser();
        String companyId = userInfo.getCompanyId();
        Integer innId =  bangInnDto.getInnId();
        BangInn bangInn = bangInnService.findBangInnByCompanyIdAndInnId(companyId,innId);
        try {
            InnDto omsInn = innMatchService.obtOmsInn(bangInn);
            OtaInfoRefDto infoRefDto = otaInfoService.findAllOtaByCompanyAndType(userInfo.getCompanyId(), OtaType.FC);
            OtaInnOtaDto otaInnOtaDto = otaInnOtaService.findOtaInnOtaByOtaId(bangInn.getId(), infoRefDto.getOtaInfoId(), companyId);
            if (otaInnOtaDto!=null){
                FcHotelInfoDto fcHotelInfoDto = fcHotelInfoService.findFcHotelByHotelId(otaInnOtaDto.getWgHid());
                //房仓酒店房型信息
                List<FcRoomTypeInfo> roomTypeInfoDtoList = fcHotelInfoService.finFcRoomTypeByHotelId(otaInnOtaDto.getWgHid());
                //房型匹配信息
                List<FcRoomTypeFqDto> fcRoomTypeFq = fcRoomTypeFqService.findFcRoomTypeFq(new FcRoomTypeFqDto(String.valueOf(innId), companyId, infoRefDto.getOtaInfoId()));
                model.addAttribute("fcHotel", fcHotelInfoDto);
                model.addAttribute("fcRoomTypeList",roomTypeInfoDtoList);
                model.addAttribute("matchRoomTypeList",fcRoomTypeFq);
            }
            List<FcHotelInfoDto> hotel = fcHotelInfoService.findFcHotel(bangInn.getInnName());
            model.addAttribute("hotel",hotel);
            List<RoomTypeInfo> list = otaRoomPriceService.obtOmsRoomInfo(bangInn);
            model.addAttribute("omsRoomTypeList",list);
            model.addAttribute("inn",bangInn);
            model.addAttribute("omsInn",omsInn);
            model.addAttribute("otaInnOtaDto",otaInnOtaDto);
        } catch (Exception e) {
            log.error("获取oms房型信息异常:"+e.getMessage());
        }
        return "/match/inn_match_detail";
    }

    //房仓客栈搜索
    @RequestMapping("/ajax/searchInn")
    public String searchInn(Model model,BangInnDto bangInnDto){
        List<FcHotelInfoDto> hotel = fcHotelInfoService.findFcHotel(bangInnDto.getInnName());
        model.addAttribute("hotel",hotel);
        return "/match/ajax_fc_hotel";
    }

    //客栈与房仓酒店匹配
    @RequestMapping("/ajax/match")
    @ResponseBody
    public Object match(String innId,String fcHotelId){
        String companyId = getCurrentUser().getCompanyId();
        Result result = new Result();
        try{
            fcHotelInfoService.updateMatchInn(companyId, innId, fcHotelId);
            result.setStatus(Constants.SUCCESS200);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(Constants.ERROR400);
        }
        return  result;
    }

    //房仓价格计划保存列表
    @RequestMapping("/ajax/ratePlanList")
    public String ratePlanList(Model model,FcRatePlan fcRatePlan,String innId){
        String companyId = getCurrentUser().getCompanyId();
        fcRatePlan.setCompanyId(companyId);
        List<FcRatePlan> ratePlan = fcRatePlanService.findFcRatePlan(fcRatePlan);
        model.addAttribute("rateList",ratePlan);
        model.addAttribute("innId",innId);
        return  "/match/rate_list";
    }

    //价格计划列表  ajax请求
    @RequestMapping("/ajax/ratePlanJson")
    @ResponseBody
    public Object ratePlanList(FcRatePlan fcRatePlan){
        Map map = new HashMap();
        String companyId = getCurrentUser().getCompanyId();
        fcRatePlan.setCompanyId(companyId);
        List<FcRatePlan> ratePlan = fcRatePlanService.findFcRatePlan(fcRatePlan);
        List<FcRatePlanDto> list = new ArrayList<FcRatePlanDto>();
        FcRatePlanDto fc = null;
        for (FcRatePlan f:ratePlan){
            fc = new FcRatePlanDto();
            BeanUtils.copyProperties(f,fc);
            fc.setBedTypeValue(f.getBedType().getDesc());
            fc.setCurrencyValue(f.getCurrency().getValue());
            fc.setPayMethodValue(f.getPayMethod().getDesc());
            list.add(fc);
        }

        map.put("rateList",list);
        return  map;
    }

    /**
     * 更新匹配房型的 价格计划 - 如果此价格计划房仓上没有要同步到房仓
     * @param fcRoomTypeFqId 房型匹配id
     * @param ratePlanId 价格计划id
     */
    @RequestMapping("/ajax/saveFcRoomTypeFqRatePlan")
    @ResponseBody
    public Object saveFcRoomTypeFqRatePlan(String fcRoomTypeFqId,String ratePlanId){
        Result result = new Result();
        try {
            //todo
            fcRoomTypeFqService.updateRoomTypeRatePlan(fcRoomTypeFqId,ratePlanId);
            result.setStatus(Constants.SUCCESS200);
        } catch (Exception e) {
            log.error("更新匹配房型价格计划异常:"+e);
            result.setMessage(e.getMessage());
            result.setStatus(Constants.ERROR400);
        }
        return  result;
    }

    //房仓价格计划保存
    @RequestMapping("/ajax/saveRatePlan")
    @ResponseBody
    public Object saveRatePlanService(FcRatePlan fcRatePlan){
        String companyId = getCurrentUser().getCompanyId();
        fcRatePlan.setCompanyId(companyId);
        Result result = new Result();
        fcRatePlanService.saveFcRatePlan(fcRatePlan);
        return  result;
    }

    //房仓价格计划保存
    @RequestMapping("/ajax/delRatePlan")
    @ResponseBody
    public Object delRatePlan(String ratePlanId,String innId){
        Result result = new Result();
        String companyId = getCurrentUser().getCompanyId();

        try {
            fcRatePlanService.deletedRatePlan(companyId,ratePlanId);
            result.setStatus(Constants.SUCCESS200);
        }catch (Exception e) {
            log.error("删除价格计划:"+e.getMessage());
            result.setMessage(e.getMessage());
            result.setStatus(Constants.ERROR400);
        }
        return result;
        //return  "redirect:/innMatch/matchDetail?innId="+innId;
    }
    //房仓房型与番茄房型匹配
    @RequestMapping("/ajax/matchRoomType")
    @ResponseBody
    public Object matchRoomType(String json,String innId,String fcHotelId){
        String companyId = getCurrentUser().getCompanyId();
        Result result = new Result();
        try{
            fcRoomTypeFqService.updateMatchRoomType(companyId,innId,fcHotelId,json);
            result.setStatus(Constants.SUCCESS200);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(Constants.ERROR400);
        }
        return  result;
    }

    //上架
    @RequestMapping("/ajax/sjMatchRoomType")
    @ResponseBody
    public Object sjMatchRoomType(String matchRoomTypeId){
        String companyId = getCurrentUser().getCompanyId();
        Result result = new Result();
        try{
            fcRoomTypeFqService.updateSjMatchRoomType(companyId, matchRoomTypeId);
            result.setStatus(Constants.SUCCESS200);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(Constants.ERROR400);
        }
        return  result;
    }

    //下架
    @RequestMapping("/ajax/xjMatchRoomType")
    @ResponseBody
    public Object xjMatchRoomType(String fqRoomTypeFcId){
        String companyId = getCurrentUser().getCompanyId();
        Result result = new Result();
        try{
            fcRoomTypeFqService.updateXjMatchRoomType(companyId, fqRoomTypeFcId);
            result.setStatus(Constants.SUCCESS200);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(Constants.ERROR400);
        }
        return  result;
    }

}
