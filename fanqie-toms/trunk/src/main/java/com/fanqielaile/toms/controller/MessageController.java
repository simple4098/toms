package com.fanqielaile.toms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.init.Jackson2ResourceReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fanqie.util.JacksonUtil;
import com.fanqie.util.Pagination;
import com.fanqie.util.StringUtil;
import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.UserInfoDto;
import com.fanqielaile.toms.dto.xl.ChangePriceMessageDto;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.MessageParam;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IXlMessageService;
import com.fanqielaile.toms.support.decorator.FrontendPagerDecorator;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.Constants;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
/**
 * 消息管理
 */
@Controller
@RequestMapping(Constants.MESSAGE)
public class MessageController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private IXlMessageService xlMessageService;
	 /**
     * 查询某一段时间是否有未读改价消息,返回该段时间里未读消息的总条数
     *
     * @param model
     * @param param
     * 			from to
     */
    @RequestMapping("query_change_price_message")
    @ResponseBody
    public Object queryChangePriceMessage(Model model, @Valid MessageParam param,BindingResult result) {
    	logger.debug("*********进入查询某一段时间是否有未读改价消息方法*************");
    	logger.debug("传入参数:"+JacksonUtil.obj2json(param));
    	UserInfo user;
    	if(result.hasErrors()){
    		model.addAttribute(Constants.STATUS,400);
    		model.addAttribute(Constants.MESSAGE,result.getAllErrors().get(0).getDefaultMessage());
    		return model;
    	}
    	try {
			user = getCurrentUser();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("未获取到该用户的登录信息");
			model.addAttribute(Constants.STATUS,400);
			model.addAttribute(Constants.MESSAGE,e.getMessage());
			return model;
		}
    	try {
			Integer count=xlMessageService.getNoReadMessage(param,user);
			model.addAttribute("count",count);
			model.addAttribute(Constants.STATUS, 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("查询某一段时间是否有未读改价消息出错");
			model.addAttribute(Constants.STATUS,500);
			model.addAttribute(Constants.MESSAGE,"获取某段时间内的未读改价消息出错");
		}
    	return model;
    }
    /**
     * 查询所有的未读改价消息,返回未读消息的总条数
     *
     * @param model
     */
    @RequestMapping("query_not_read_count")
    public void queryNotReadCount(Model model) {
    	logger.debug("*********进入查询所有的未读改价消息条数方法*************");
    	UserInfo user;
    	MessageParam param=new MessageParam();
		try {
			user = getCurrentUser();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("未获取到该用户的登录信息");
			model.addAttribute(Constants.STATUS,400);
			model.addAttribute(Constants.MESSAGE,e.getMessage());
			return ;
		}
    	try {
			Integer count=xlMessageService.getNoReadMessage(param,user);
			model.addAttribute("count",count);
			model.addAttribute(Constants.STATUS, 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("查询所有未读改价消息出错");
			model.addAttribute(Constants.STATUS,500);
			model.addAttribute(Constants.MESSAGE,"获取所有未读改价消息出错");
		}
    }
    /**
     * 查询改价消息列表,将当前页未读改价消息置为已读
     *
     * @param model
     */
    @RequestMapping("query_change_price_list")
    public void queryChangePriceList(Model model,Integer page,Integer rows) {
    	logger.debug("*********进入查询改价消息列表数方法*************");
    	logger.debug("传入参数：page:"+page+",rows:"+rows);
    	UserInfo user;
    	MessageParam param=new MessageParam();
		try {
			user = getCurrentUser();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("未获取到该用户的登录信息");
			model.addAttribute(Constants.STATUS,400);
			model.addAttribute(Constants.MESSAGE,e.getMessage());
			return ;
		}
		if(page == null || page < 1 ){
			page=1;
		}
		if(rows == null || rows <1){
			rows=defaultRows;
		}
    	try {
    		//获取改价信息列表
    		List<ChangePriceMessageDto> list=xlMessageService.getChangePriceList(param,user,new PageBounds(page, rows,Order.formString("create_date.desc")));
    		//将展示的改价消息置为已读
    		xlMessageService.setChangPriceMsgStatus(list);
    		model.addAttribute(Constants.DATA,list);
			//分页对象
            Paginator paginator = ((PageList) list).getPaginator();
            Pagination pagination = PaginationHelper.toPagination(paginator);
            FrontendPagerDecorator pageDecorator = new FrontendPagerDecorator(pagination);
			model.addAttribute("pagination",pagination);
			model.addAttribute("pageDecorator",pageDecorator);
			model.addAttribute(Constants.STATUS, 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("查询所有未读改价消息出错");
			model.addAttribute(Constants.STATUS,500);
			model.addAttribute(Constants.MESSAGE,"获取改价消息列表出错");
		}
    }
	/**
     * 将所有未读改价消息置为已读
     *
     * @param model
     * @param code
     */
    @RequestMapping("change_message_status")
    public void changeMessageStatus(Model model) {
    	logger.debug("*********进入更新改价消息的是否已读状态方法*************");
    	UserInfo user;
    	MessageParam param=new MessageParam();
		try {
			user = getCurrentUser();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("未获取到该用户的登录信息");
			model.addAttribute(Constants.STATUS,400);
			model.addAttribute(Constants.MESSAGE,e.getMessage());
			return ;
		}
    	try {
			xlMessageService.setChangPriceMsgStatus(param,user);
			model.addAttribute(Constants.STATUS, 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("查询所有未读改价消息出错");
			model.addAttribute(Constants.STATUS,500);
			model.addAttribute(Constants.MESSAGE,"获取所有未读改价消息出错");
		}
    }
}
