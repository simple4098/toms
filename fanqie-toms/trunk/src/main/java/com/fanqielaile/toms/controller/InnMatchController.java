package com.fanqielaile.toms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fanqie.bean.response.CtripHotelInfo;
import com.fanqie.bean.response.CtripHotelRoomType;
import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqie.util.Pagination;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.FcHotelInfoDto;
import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaInnOtaDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.dto.fc.OtaRatePlanDto;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.exception.CtripDataException;
import com.fanqielaile.toms.exception.RequestCtripException;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.model.fc.FcHotelInfo;
import com.fanqielaile.toms.model.fc.FcRoomTypeInfo;
import com.fanqielaile.toms.model.fc.OtaRatePlan;
import com.fanqielaile.toms.service.CtripHotelInfoService;
import com.fanqielaile.toms.service.CtripHotelRoomTypeService;
import com.fanqielaile.toms.service.CtripRoomTypeMappingService;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.ICompanyService;
import com.fanqielaile.toms.service.ICtripRoomService;
import com.fanqielaile.toms.service.IFcHotelInfoService;
import com.fanqielaile.toms.service.IFcRatePlanService;
import com.fanqielaile.toms.service.IFcRoomTypeFqService;
import com.fanqielaile.toms.service.IInnMatchService;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.service.IOtaInnOtaService;
import com.fanqielaile.toms.service.IOtaRoomPriceService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.service.impl.InnLabelService;
import com.fanqielaile.toms.support.decorator.FrontendPagerDecorator;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.ModelViewUtil;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

/**
 * DESC : 不同渠道下的客栈匹配
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
    private IFcRatePlanService fcRatePlanService;
    @Resource
    private IFcRoomTypeFqService fcRoomTypeFqService;
    @Resource
    private ICompanyService companyService;
    @Resource
    private CtripHotelInfoService ctripHotelInfoService;
    @Resource
    private CtripHotelRoomTypeService  ctripHotelRoomTypeService;
    @Resource
    private CtripRoomTypeMappingService ctripRoomTypeMappingService;

    /**
     * 匹配列表
     * @param bangInnDto 绑定客栈参数
     * @param page 当前页
     */
    @RequestMapping("/match")
    public String matchList(Model model,BangInnDto bangInnDto,@RequestParam(defaultValue = "1", required = false) int page){
        UserInfo userInfo = getCurrentUser();
        List<OtaInfoRefDto> infoRefDtoList = otaInfoService.findAllOtaInfo();
        BangInnDto innDto = BangInnDto.userInfoToBangInnDto(userInfo,bangInnDto,infoRefDtoList);
        OtaInfoRefDto otaInfo = otaInfoService.findOtaInfoByCompanyIdAndOtaInnOtaId(userInfo.getCompanyId(), innDto.getOtaInfoId());
        model.addAttribute("infoList", infoRefDtoList);
        model.addAttribute("otaInfoId",innDto.getOtaInfoId());
        if (otaInfo==null){
            OtaInfoRefDto infoRefDto =  otaInfoService.findOtaInfo(innDto.getOtaInfoId());
            return  ModelViewUtil.view(infoRefDto);
        }
        List<InnLabel> innLabels = innLabelService.findLabelsByCompanyId(innDto.getCompanyId());
        List<BangInnDto> bangInns = bangInnService.findOTABangInn(bangInnDto, otaInfo ,new PageBounds(page, defaultRows));
        model.addAttribute("labels", innLabels);
        model.addAttribute("list",bangInns);
        model.addAttribute("bangInnDto",bangInnDto);
        Paginator paginator = ((PageList) bangInns).getPaginator();
        Pagination pagination = PaginationHelper.toPagination(paginator);
        FrontendPagerDecorator pageDecorator = new FrontendPagerDecorator(pagination);
        model.addAttribute("pagination",pagination);
        model.addAttribute("pageDecorator",pageDecorator);
        return ModelViewUtil.otaListView(otaInfo);
    }

    /**
     * 验证不同的渠道的appKey是不是正确的
     * @param otaInfoRefDto appKey appSecret
     */
    @RequestMapping(value = "/vetted",method = RequestMethod.POST)
    @ResponseBody
    public Object vetted(OtaInfoRefDto otaInfoRefDto){
       Result result = new Result();
       UserInfo user = getCurrentUser();
       OtaInfoRefDto infoRefDto =  otaInfoService.findOtaInfo(otaInfoRefDto.getOtaInfoId());
       if (infoRefDto!=null &&user!=null){
          otaInfoRefDto.setCompanyId(user.getCompanyId());
          otaInfoRefDto.setOtaId(otaInfoRefDto.getOtaInfoId());
          ITPService service = infoRefDto.getOtaType().create();
          result =  service.validatedOTAAccuracy(otaInfoRefDto);
       }
       return result;
    }

    /**
     * 保存淘宝这个渠道直连方式
     * @param otaInfoRefDto appKey appSecret
     */
    @RequestMapping(value = "/change_type",method = RequestMethod.POST)
    @ResponseBody
    public Object changeType(OtaInfoRefDto otaInfoRefDto){
       Result result = new Result();
       UserInfo user = getCurrentUser();
       OtaInfoRefDto infoRefDto =  otaInfoService.findOtaInfo(otaInfoRefDto.getOtaInfoId());
       if (infoRefDto!=null && ( OtaType.TB.equals(infoRefDto.getOtaType()) || OtaType.CREDIT.equals(infoRefDto.getOtaType()) ) && user!=null){
           otaInfoRefDto.setCompanyId(user.getCompanyId());
           result = otaInfoService.updateOtaInfoTbType(otaInfoRefDto);
       }
       return result;
    }

    /**
     * 渠道上/下架
     */
    @RequestMapping("/update_inn_ota")
    @ResponseBody
    public Object updateInnOta(String otaInfoId,String innId,Integer sj){
        String companyId = getCurrentUser().getCompanyId();
        Result result = new Result();
        try{
            Company company = companyService.findCompanyByid(companyId);
            OtaInfoRefDto otaInfoRefDto = otaInfoService.findOtaInfoByCompanyIdAndOtaInnOtaId(companyId, otaInfoId);
            BangInn bangInn = bangInnService.findBangInnByCompanyIdAndInnId(companyId, Integer.valueOf(innId));
            ITPService service = otaInfoRefDto.getOtaType().create();
            TBParam tbParam = TomsUtil.toOtaParam(bangInn,company,sj,otaInfoRefDto);
            service.updateOrAddHotel(tbParam,otaInfoRefDto);
            result.setStatus(Constants.SUCCESS200);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(Constants.ERROR400);
        }
        return  result;
    }

    /**
     * 客栈匹配详情
     * @param bangInnDto 绑定客栈参数
     */
    @RequestMapping("/matchDetail")
    public String matchOtaList(Model model,BangInnDto bangInnDto){
        UserInfo userInfo = getCurrentUser();
        String companyId = userInfo.getCompanyId();
        Integer innId =  bangInnDto.getInnId();
        if(innId!=null) {
            BangInn bangInn = bangInnService.findBangInnByCompanyIdAndInnId(companyId, innId);
            try {
                InnDto omsInn = innMatchService.obtOmsInn(bangInn);
                OtaInfoRefDto infoRefDto = otaInfoService.findAllOtaByCompanyAndType(userInfo.getCompanyId(), OtaType.FC);
                OtaInnOtaDto otaInnOtaDto = otaInnOtaService.findOtaInnOtaByOtaId(bangInn.getId(), infoRefDto.getOtaInfoId(), companyId);
                if (otaInnOtaDto != null) {
                    FcHotelInfoDto fcHotelInfoDto = fcHotelInfoService.findFcHotelByHotelId(otaInnOtaDto.getWgHid());
                    //房仓酒店房型信息
                    List<FcRoomTypeInfo> roomTypeInfoDtoList = fcHotelInfoService.finFcRoomTypeByHotelId(otaInnOtaDto.getWgHid());
                    //房型匹配信息
                    List<FcRoomTypeFqDto> fcRoomTypeFq = fcRoomTypeFqService.findFcRoomTypeFq(new FcRoomTypeFqDto(String.valueOf(innId), companyId, infoRefDto.getOtaInfoId()));
                    model.addAttribute("fcHotel", fcHotelInfoDto);
                    model.addAttribute("fcRoomTypeList", roomTypeInfoDtoList);
                    model.addAttribute("matchRoomTypeList", fcRoomTypeFq);
                }
                List<FcHotelInfoDto> hotel = fcHotelInfoService.findFcHotel(bangInn.getInnName());
                model.addAttribute("hotel", hotel);
                Company company = companyService.findCompanyByid(companyId);
                String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.ROOM_TYPE);
                List<RoomTypeInfo> list = InnRoomHelper.getRoomTypeInfo(room_type);
                model.addAttribute("omsRoomTypeList", list);
                model.addAttribute("inn", bangInn);
                model.addAttribute("omsInn", omsInn);
                model.addAttribute("otaInnOtaDto", otaInnOtaDto);
                
                
            } catch (Exception e) {
                log.error("获取oms房型信息异常:" + e);
            }
            return "/match/inn_match_detail";
        }else {
            model.addAttribute("msg","url 异常!客栈id不能为空!");
            return "/error";
        }
    }
    
    
    
    /**
     *  
     * @param model
     * @param bangInnDto
     * @param ctripId  用于页面操作是否提交酒店绑定关系。
     * @return
     */
    @RequestMapping("/ctrip/matchDetail")
    public String matchCtripList(Model model,BangInnDto bangInnDto,String ctripId){
        UserInfo userInfo = getCurrentUser();
        String companyId = userInfo.getCompanyId();
        Integer innId =  bangInnDto.getInnId();
        if(innId!=null) {
            BangInn bangInn = bangInnService.findBangInnByCompanyIdAndInnId(companyId, innId);
            try {
                InnDto omsInn = innMatchService.obtOmsInn(bangInn);
                //  渠道信息
                OtaInfoRefDto infoRefDto = otaInfoService.findAllOtaByCompanyAndType(userInfo.getCompanyId(), OtaType.XC);
                //  查询，h-h的绑定关系
                OtaInnOtaDto otaInnOtaDto = otaInnOtaService.findOtaInnOtaByOtaId(bangInn.getId(), infoRefDto.getOtaInfoId(), companyId);
                if(StringUtils.isEmpty(ctripId)){ //  如果，没有新的绑定关系
                	if (otaInnOtaDto != null) { //  已经关联了酒店，根据子ID查询
                		CtripHotelInfo ctripHotelInfo = ctripHotelInfoService.findCtripHotelInfoByChildHotelId(otaInnOtaDto.getWgHid());
                		//携程酒店房型信息
                		List<CtripHotelRoomType> rooms = ctripHotelRoomTypeService.findByCtripParentHotelId(ctripHotelInfo.getParentHotelId());
                		//房型匹配信息
                		List<CtripRoomTypeMapping> ctripRoomMappings =	ctripRoomTypeMappingService.findRoomTypeMapping(companyId, otaInnOtaDto.getWgHid(),innId.toString());
                		List<CtripRoomTypeMapping> tempAddRoomType = new ArrayList<CtripRoomTypeMapping>();
                		model.addAttribute("fcHotel", ctripHotelInfo);
                        Company company = companyService.findCompanyByid(companyId);
                        String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.ROOM_TYPE);
                        List<RoomTypeInfo> list = InnRoomHelper.getRoomTypeInfo(room_type);
                        assembleAllRoomTypeMapping(ctripRoomMappings,
								tempAddRoomType, list);
                        ctripRoomMappings.addAll(tempAddRoomType);
                		model.addAttribute("fcRoomTypeList", rooms);
                		model.addAttribute("matchRoomTypeList", ctripRoomMappings);
                	}
                }else{
                	//  酒店信息
                	CtripHotelInfo  ctripHotelInfo = ctripHotelInfoService.findCtripHotelInfoByParentHotelId(ctripId);
                	model.addAttribute("fcHotel", ctripHotelInfo);
                	//  所有的母房型
                	List<CtripHotelRoomType> rooms = ctripHotelRoomTypeService.findByCtripParentHotelId(ctripId);
                	model.addAttribute("fcRoomTypeList", rooms);
                }
                List<CtripHotelInfo> hotel =  ctripHotelInfoService.findCtripHotelByName(bangInn.getInnName());
                model.addAttribute("hotel", hotel);
                Company company = companyService.findCompanyByid(companyId);
                String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.ROOM_TYPE);
                List<RoomTypeInfo> list = InnRoomHelper.getRoomTypeInfo(room_type);
                model.addAttribute("omsRoomTypeList", list);
                model.addAttribute("inn", bangInn);
                model.addAttribute("omsInn", omsInn);
                model.addAttribute("otaInnOtaDto", otaInnOtaDto);
            } catch (Exception e) {
            	e.printStackTrace();
                log.error("获取oms房型信息异常:" + e);
                model.addAttribute("msg", e.getMessage());
            }
            return "/match/ctrip/inn_match_detail";
        }else {
            model.addAttribute("msg","url 异常!客栈id不能为空!");
            return "/error";
        }
    }

    /**
     *  组装所有的房型，同时把已经Mapping了建立关系
     * @param ctripRoomMappings  已经匹配的Mapping
     * @param tempAddRoomType 保存为建立Mapping关系的房型
     * @param list   OMS 所有的房型
     */
	private void assembleAllRoomTypeMapping(
			List<CtripRoomTypeMapping> ctripRoomMappings,
			List<CtripRoomTypeMapping> tempAddRoomType, List<RoomTypeInfo> list) {
		if(null!=list && !list.isEmpty()){
			for (RoomTypeInfo roomTypeInfo : list) {
				boolean hasContains = false;
				for (CtripRoomTypeMapping ctripRoomTypeMapping : ctripRoomMappings) {
					if(roomTypeInfo.getRoomTypeId()== Integer.parseInt( ctripRoomTypeMapping.getTomRoomTypeId())){
						hasContains = true;
						roomTypeInfo.setRatePlanCode(ctripRoomTypeMapping.getRatePlanCode());
						roomTypeInfo.setRatePlanCodeName(ctripRoomTypeMapping.getRatePlanCodeName());
						break;
					}
				}
				if(!hasContains){
					CtripRoomTypeMapping crtm = new CtripRoomTypeMapping();
					crtm.setTomRoomTypeName(roomTypeInfo.getRoomTypeName());
					crtm.setCtripRoomTypeName("");
					tempAddRoomType.add(crtm);
				}
			}
		}
	}
    
    

    //房仓客栈搜索
    @RequestMapping("/ajax/searchInn")
    public String searchInn(Model model, BangInnDto bangInnDto, @RequestParam(defaultValue = "1", required = false) int page) {
//        List<FcHotelInfoDto> hotel = fcHotelInfoService.findFcHotel(bangInnDto.getInnName());
        List<FcHotelInfo> hotel = fcHotelInfoService.findFcHotelByPage(bangInnDto.getInnName(), new PageBounds(page, 3));
        model.addAttribute("hotel",hotel);
        //封装分页信息
        Paginator paginator = ((PageList) hotel).getPaginator();
        Pagination pagination = PaginationHelper.toPagination(paginator);
        FrontendPagerDecorator pageDecorator = new FrontendPagerDecorator(pagination);
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageDecorator", pageDecorator);
        model.addAttribute("keyword", bangInnDto.getInnName());
        //当前页
        model.addAttribute("page", page);
        model.addAttribute("maxPage", pagination.getPageCount());
        return "/match/ajax_fc_hotel";
    }
    
    
    /**
     * 
     * @param model
     * @param bangInnDto
     * @param page
     * @return
     */
    @RequestMapping(value="/ajax/searchCtripHotel")
    public String searchCtripHotel(Model model, BangInnDto bangInnDto, @RequestParam(defaultValue = "1", required = false) int page) {
//      List<FcHotelInfoDto> hotel = fcHotelInfoService.findFcHotel(bangInnDto.getInnName());
    //  List<FcHotelInfo> hotel = fcHotelInfoService.findFcHotelByPage(bangInnDto.getInnName(), new PageBounds(page, 3));
    	List<CtripHotelInfo> hotel  =  ctripHotelInfoService.findCtripHotelByPage(bangInnDto.getInnName(),  new PageBounds(page, 3));
    	model.addAttribute("hotel",hotel);
      //封装分页信息
      Paginator paginator = ((PageList) hotel).getPaginator();
      Pagination pagination = PaginationHelper.toPagination(paginator);
      FrontendPagerDecorator pageDecorator = new FrontendPagerDecorator(pagination);
      model.addAttribute("pagination", pagination);
      model.addAttribute("pageDecorator", pageDecorator);
      model.addAttribute("keyword", bangInnDto.getInnName());
      //当前页
      model.addAttribute("page", page);
      model.addAttribute("maxPage", pagination.getPageCount());
      return "/match/ctrip/ajax_ctrip_hotel";
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

    
    //客栈与房仓酒店匹配
    @RequestMapping("/ajax/ctrip/match")
    @ResponseBody
    public Object matchCtripHotel(String innId,String ctripHotelId){
    	Result result = new Result();
    	result.setStatus(Constants.SUCCESS200);
    	result.setMessage(ctripHotelId);
    	CtripHotelInfo hotel =  ctripHotelInfoService.findCtripHotelInfoByParentHotelId(ctripHotelId);
    	if(Boolean.valueOf(hotel.getHasBind())){
    		if(Integer.parseInt(hotel.getId())!=Integer.parseInt(innId)){
    			result.setStatus(Constants.ERROR400);
    			result.setMessage("当前母酒店已经被绑定");
    			return result;
    		}
    	}
    	return  result;
    }
    
    
    //客栈与房仓酒店匹配
    @RequestMapping("/ajax/ctrip/cannelMapping")
    @ResponseBody
    public Object cannelMapping(String innId){
    	Result result = new Result();
    	  UserInfo userInfo = getCurrentUser();
          String companyId = userInfo.getCompanyId();
    	result.setStatus(Constants.SUCCESS200);
    	try {
    		ctripHotelRoomTypeService.cannelMappingAll(companyId, innId, null);
    	} catch (RequestCtripException | JAXBException e) {
    		log.error("异常:",e);
    		result.setStatus(Constants.ERROR400);
    		result.setMessage(e.getMessage());
    	}
		return result;
    }
    
    
    
    //房仓价格计划保存列表
    @RequestMapping("/ajax/ratePlanList")
    public String ratePlanList(Model model,OtaRatePlan otaRatePlan,String innId){
        String companyId = getCurrentUser().getCompanyId();
        otaRatePlan.setCompanyId(companyId);
        List<OtaRatePlan> ratePlan = fcRatePlanService.findFcRatePlan(otaRatePlan);
        model.addAttribute("rateList",ratePlan);
        model.addAttribute("innId",innId);
        return  "/match/rate_list";
    }
    
    //携程价格计划
    @RequestMapping("/ajax/ctrip/ratePlanList")
    public String rateCtripPlanList(Model model,String innId){
        List<OtaRatePlan> ratePlan = fcRatePlanService.selectCtripRatePlan();
        model.addAttribute("rateList",ratePlan);
        model.addAttribute("innId",innId);
        return  "/match/ctrip/rate_list";
    }
    
    
    //携程价格计划
    @RequestMapping("/ajax/ctrip/ratePlanListData")
    @ResponseBody
    public  List<OtaRatePlan> rateCtripPlanListData(){
        return fcRatePlanService.selectCtripRatePlan();
        
    }
    
    
    //更新携程Mapping对应的房价计划
    @RequestMapping("/ajax/ctrip/mapping/update")
    @ResponseBody
    public Result MappingUpdate(String newPlanCode,String newPlanCodeName, String mappingId){
    	Result result = new Result();
    	   String companyId = getCurrentUser().getCompanyId();
    	try {
			ctripRoomTypeMappingService.updateMappingPlanCode(companyId,newPlanCode,newPlanCodeName, mappingId);
			result.setStatus(Constants.SUCCESS200);
		} catch (JAXBException e) {
			log.error(e.getMessage());
    		result.setStatus(Constants.ERROR400);
    		result.setMessage(e.getMessage());
		} catch (RequestCtripException e) {
			log.error(e.getMessage());
			result.setStatus(Constants.ERROR400);
    		result.setMessage(e.getMessage());
		}catch (Exception e) {
        	log.error(e.getMessage());
        	result.setStatus(Constants.ERROR400);
        	result.setMessage(e.getMessage());
        }
    	return result;
    }
    

    //价格计划列表  ajax请求
    @RequestMapping("/ajax/ratePlanJson")
    @ResponseBody
    public Object ratePlanList(OtaRatePlan otaRatePlan){
        Map map = new HashMap();
        String companyId = getCurrentUser().getCompanyId();
        otaRatePlan.setCompanyId(companyId);
        List<OtaRatePlan> ratePlan = fcRatePlanService.findFcRatePlan(otaRatePlan);
        List<OtaRatePlanDto> list = new ArrayList<OtaRatePlanDto>();
        OtaRatePlanDto fc = null;
        for (OtaRatePlan f:ratePlan){
            fc = new OtaRatePlanDto();
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
    public Object saveRatePlanService(OtaRatePlan otaRatePlan){
        String companyId = getCurrentUser().getCompanyId();
        otaRatePlan.setCompanyId(companyId);
        Result result = new Result();
        fcRatePlanService.saveFcRatePlan(otaRatePlan);
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
    
    
    //携程房型与番茄房型匹配
    @RequestMapping("/ajax/ctrip/matchRoomType")
    @ResponseBody
    public Object matchCtripRoomType(String json,String innId,String fcHotelId){
        String companyId = getCurrentUser().getCompanyId();
        Result result = new Result();
        try {
        	List<CtripRoomTypeMapping> crms = ctripHotelRoomTypeService.updateRoomBypeRelation(companyId,json,innId,fcHotelId);
        /*	if(null!=crms && !crms.isEmpty()){
        		log.info("同步OMS数据，上传最新客栈房型绑定关系");
        		Company company = companyService.findCompanyByid(companyId);
        		OtaInfoRefDto dto =  otaInfoService.findAllOtaByCompanyAndType(companyId, OtaType.XC);
        		iCtripRoomService.updateRoomPrice(company, dto, crms, true);
        		log.info("同步OMS数据完成");
        	}*/
        } catch (CtripDataException e) {
        	log.error(e.getMessage());
        	result.setStatus(Constants.ERROR400);
        	result.setMessage(e.getMessage());
        } catch (RequestCtripException e) {
        	log.error("请求携程出错:"+e.getMessage());
        	result.setStatus(Constants.ERROR400);
        	result.setMessage(e.getMessage());
        } catch (JAXBException e) {
        	log.error("数据转换出错:"+e.getMessage());
        	result.setStatus(Constants.ERROR400);
        	result.setMessage(e.getMessage());
        } catch (Exception e) {
        	log.error(e.getMessage());
        	result.setStatus(Constants.ERROR400);
        	result.setMessage(e.getMessage());
        	e.printStackTrace();
        }
        return  result;
    }

    
    
    
    
    //携程房型与番茄房型匹配
    @RequestMapping("/btach/update/mapping")
    @ResponseBody
    public Result matchCtripRoomType(){
        String companyId = getCurrentUser().getCompanyId();
        Result result = new Result();
        try {
        	ctripHotelRoomTypeService.updateBatchRoomMapping(companyId);
        	result.setStatus(Constants.SUCCESS200);
		} catch (Exception e) {
			e.printStackTrace();
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
            log.error("上架异常",e);
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

    //导出
    @RequestMapping("/ajax/export")
    public void excel(BangInnDto bangInnDto,HttpServletResponse response,String fromData){
        String companyId = getCurrentUser().getCompanyId();
        bangInnDto.setCompanyId(companyId);
        OtaInfoRefDto infoRefDto = otaInfoService.findAllOtaByCompanyAndType(companyId, OtaType.FC);
        bangInnDto.setOtaInfoId(infoRefDto.getOtaInfoId());
        List<BangInnDto> bangInns = bangInnService.findFcBangInn(bangInnDto, new PageBounds(Integer.valueOf(fromData), 100));
        try {
            fcHotelInfoService.excel(companyId,bangInns,response);
        } catch (Exception e) {
            log.error("导出excel异常:"+e);
        }

    }

    //众荟导出
    @RequestMapping("/ajax/zhExport")
    public void zhExcel(BangInnDto bangInnDto,HttpServletResponse response,String fromData){
        UserInfo currentUser = getCurrentUser();
        String companyId = getCurrentUser().getCompanyId();
        bangInnDto.setCompanyId(companyId);
        OtaInfoRefDto infoRefDto = otaInfoService.findAllOtaByCompanyAndType(companyId, OtaType.ZH);
        bangInnDto.setOtaInfoId(infoRefDto.getOtaInfoId());
        List<BangInnDto> bangInns = this.bangInnService.findBangInnListByUserInfo(currentUser, new PageBounds(Integer.valueOf(fromData), 100));
        try {
            fcHotelInfoService.zhExcel(companyId,bangInns,response);
        } catch (Exception e) {
            log.error("导出excel异常:"+e);
        }

    }

}
