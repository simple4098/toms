/*package com.fanqielaile.toms.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.enums.ResultCode;
import com.fanqielaile.toms.exception.BusinessException;
import com.fanqielaile.toms.model.minsu.bo.RequestBean;
import com.fanqielaile.toms.util.CommonUtil;
import com.fanqielaile.toms.util.PassWordUtil;


public class OMSSecurityInterceptor  implements HandlerInterceptor   {
	private MemcachedCacheManager cached= SpringContextHolder.getBean(MemcachedCacheManager.class);
	protected transient final Logger log  =  Logger.getLogger(this.getClass());
//	private String loginUri;
//	private List<String> noFiltUris;

	
	@Override
	public boolean  preHandle(HttpServletRequest request,  HttpServletResponse response, 
			Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        //需要过滤的代码         
        String timestamp = request.getParameter("timestamp");
        String signature = request.getParameter("signature");
        String otaId = request.getParameter("otaId");
        log.info("请求拦截开始,请求参数为 ,timestamp:"+timestamp+";signature:"+signature+";otaId"+otaId);
        RequestBean qBean =  new RequestBean();
        qBean.setOtaId(otaId);
        qBean.setSignature(signature);
        qBean.setTimestamp(timestamp);
        Map<String,Object> requestMap  = new HashMap<>();
        
    
        if(checkApiRequest(qBean,requestMap)){
        //	filter.doFilter(servletRequest, servletResponse); 
        	return true;
        }else{
        	response.setContentType("application/json;charset=UTF-8");
        	response.getWriter().print(JSONObject.toJSON(requestMap));
        	return false;
        }
	}
	
	public boolean checkApiRequest(RequestBean qBean,Map<String,Object> requestMap){
		boolean sign = true;
		if(!StringUtils.isEmpty(qBean.getOtaId()) //参数不能为空
				 && !StringUtils.isEmpty(qBean.getSignature()) 
				 && !StringUtils.isEmpty(qBean.getTimestamp())){
			long time=0;
			TomatoOmsOtaInfo channelBean = new TomatoOmsOtaInfo();
			try{
				 time = Long.valueOf(qBean.getTimestamp());
				 channelBean.setOtaId(Integer.valueOf(qBean.getOtaId()));
			} catch (NumberFormatException e) {
		    	throw new BusinessException(ResultCode.PARAM_ERROR.getMessage(),ResultCode.PARAM_ERROR.getCode());
				return sign;
			} 
			// 是否超时

			long starttmil =System.currentTimeMillis()-(5*DateUtil.MILLION_SECONDS_OF_MINUTE);
  			long endtmil =System.currentTimeMillis()+(5*DateUtil.MILLION_SECONDS_OF_MINUTE);

			if(time<=starttmil || time>=endtmil){ //请求超时
				CommonUtil.setInfo(requestMap,Constants.CODE_412_MESSAGE,Constants.HTTP_412,null);
		    	sign =false;
			}else{				
				try {//sign 是否合法
					//channelBean = (TomatoOmsOtaInfo) cached.getCache(Constants.OMS_CACHE_NAMESPACE+Constants.OMS_CACHE_OTA).get(qBean.getOtaId(),TomatoOmsOtaInfo.class);
					if(channelBean==null){
						CacheServiceImpl cacheServiceImpl= SpringContextHolder.getBean(CacheServiceImpl.class);
						cacheServiceImpl.putOtaInfo(new TomatoOmsOtaInfo());
						channelBean = (TomatoOmsOtaInfo) cached.getCache(Constants.OMS_CACHE_NAMESPACE+Constants.OMS_CACHE_OTA).get(qBean.getOtaId(),TomatoOmsOtaInfo.class);
					}
					
					if(channelBean != null){
						String key =channelBean.getOtaId()+""+time+channelBean.getUserCode()+channelBean.getUserPassword();
						log.info("获取的 key:"+key);
						key = PassWordUtil.getMd5Pwd(key);
						if(!key.equals(qBean.getSignature())){
							CommonUtil.setInfo(requestMap,Constants.CODE_413_MESSAGE,Constants.HTTP_413,null);
							sign =false;
						}
					}else{
						CommonUtil.setInfo(requestMap,Constants.CODE_411_MESSAGE,Constants.HTTP_411,null);
						sign =false;
					}
				} catch (Exception e) {	
					CommonUtil.setInfo(requestMap,"系统错误",Constants.HTTP_500,null);
					sign =false;
					log.error("拦截系统异常!e"+e);
				}
			}
			
			//秘钥是不对
	    }else{
	    	requestMap.put(Constants.STATUS,Constants.HTTP_411);
	    	requestMap.put(Constants.MESSAGE,Constants.CODE_411_MESSAGE);
	    	sign = false;
	    	
	    }
		log.debug(requestMap);
		return sign;
		
	}

	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	
	private void setErrorInfo(Map<String, Object> map,String msg,String code){
	}


}
*/