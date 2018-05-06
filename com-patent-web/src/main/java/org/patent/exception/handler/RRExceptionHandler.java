package org.patent.exception.handler;


import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.patent.utils.ApiRRException;
import org.patent.utils.ApiResultCode;
import org.patent.utils.ApiResult;


/**
 * API异常处理器
 */
@Component
public class RRExceptionHandler implements HandlerExceptionResolver{

	private Logger logger = LoggerFactory.getLogger(RRExceptionHandler.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) {
		try {
			String json = "";
			ApiResult api = new ApiResult();
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			if(ex instanceof ApiRRException){
				api.put("resultCode", ((ApiRRException) ex).getResultCode());
				api.put("resultMsg", ((ApiRRException) ex).getResultMsg());
				api.put("errorCode", ((ApiRRException) ex).getErrorCode());
				json = JSON.toJSONString(api);
			}else{
				api.put("resultCode", ApiResultCode.FAILUE_CODE);
				api.put("resultMsg", ApiResultCode.FAILUE_MSG);
				api.put("errorCode", ApiResultCode.FAILUE_ERROR_CODE);
				json = JSON.toJSONString(api);
				logger.error("UNKONW ERROR:",ex);
			}
			response.getWriter().print(json);
			response.getWriter().close();
			logger.info("异常信息ex：" + ex);
		} catch (Exception e) {
			logger.error("RRExceptionHandler异常处理失败", e);
		}
		return new ModelAndView();
	}
}
