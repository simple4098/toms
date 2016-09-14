package com.fanqielaile.toms.core.handler;



import com.alibaba.fastjson.JSON;
import com.fanqielaile.toms.dto.homestay.BaseResultDto;
import com.fanqielaile.toms.enums.ResultCode;
import com.fanqielaile.toms.exception.BusinessException;
import com.fanqielaile.toms.exception.SystemException;
import com.fanqielaile.toms.util.URLUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常
 *
 * @author LZQ
 */
@Component
public class CommonExceptionHandler implements HandlerExceptionResolver {
	/**
	 * 日志
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		BaseResultDto result = null;
		if (BusinessException.class.isAssignableFrom(ex.getClass())) {
			// 业务异常
			BusinessException bex = (BusinessException) ex;
			result = new BaseResultDto();
			result.setResultMessage(bex.getMsg());
			//当有返回Code值时候
			if (bex.getCode() != null) {
				result.setResultCode(bex.getCode());
			}
			log.info("CommonExceptionHandler catche the BusinessException : ", bex.getMsg());
		} else if (SystemException.class.isAssignableFrom(ex.getClass())) {
			// 系统异常
			SystemException sex = (SystemException) ex;
			result = new BaseResultDto(ResultCode.SYSTEM_EXCEPTION);
			result.setResultMessage(sex.getMsg());
			log.info("CommonExceptionHandler catche the SystemException : ", sex.getMessage());
		}else{
			// 其他错误
			result = new BaseResultDto(ResultCode.OTHER_EXCEPTION);
			log.error("CommonExceptionHandler catche the System Error, ", ex);
		}

		String requestHeader = request.getHeader("Accept");
		response.setCharacterEncoding("UTF-8");
		boolean isAjaxUrl = request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString());
		if (requestHeader == null || isAjaxUrl) {
			// 返回json格式的数据
			returnRes(request, response, result);
			return new ModelAndView();
		} else {
			//TODO 非ajax请求 赞当ajax请求处理
			//response.setContentType("text/html;charset=UTF-8");
			returnRes(request, response, result);
			return new ModelAndView();
		}
	}

	private String toJSONString(BaseResultDto result) {
		return JSON.toJSONString(result);
	}

	private void returnRes(HttpServletRequest request, HttpServletResponse response, BaseResultDto result) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			StringBuffer responseSb = new StringBuffer();
			log.info("[<--返回][url={}][code={}][msg={}]",request.getServletPath(),result.getResultCode(),result.getResultMessage());
			if (URLUtils.isJsonp(request)) {
				String callback = request.getParameter("callback");
				responseSb.append("(").append(callback).append(toJSONString(result)).append(")");
			} else {
				responseSb.append(toJSONString(result));
			}

			response.getWriter().println(responseSb.toString());
		} catch (Exception e) {
			log.error("Response write exception", e);
		}
	}

}
