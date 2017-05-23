package com.greenkoo.sys.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.sys.model.SysOperationRecord;
import com.greenkoo.sys.model.SysUser;
import com.greenkoo.sys.service.ISysOperationRecordService;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.IpUtil;
import com.greenkoo.utils.StringUtil;


public class SysOperationInterceptor implements HandlerInterceptor {

	@Autowired
	private  ISysOperationRecordService sysOperationRecordService;
	
	private static Logger log = LoggerFactory.getLogger(SysOperationInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		try {
			// 获取用户所在ip
			String ipAddress = IpUtil.getRemoteHost(request);
			String url = request.getRequestURI();
			Map<String, String[]> reqMap = request.getParameterMap();
			log.info("进入操作记录拦截器：" + url);
			String params="";
			List<String> paramsList = new ArrayList<String>();
			if (reqMap != null&&reqMap.keySet()!=null&&reqMap.keySet().size()>0) {
				for (String key : reqMap.keySet()) {
					if ("rnd".equals(key) || "menuIdParam".equals(key)) {
						continue;
					}
					String val = request.getParameter(key);
					if("password".equalsIgnoreCase(key)||"pwd".equalsIgnoreCase(key)){
						val="******";
					}
					paramsList.add("(" + key + ":" + val + ")");
				}
				
				if(paramsList.size()>0){
					params = StringUtils.collectionToDelimitedString(paramsList, "||");
				}
			}
			
			SysUser user = (SysUser) request.getSession().getAttribute(CommonConstants.LOGIN_USER);
			//只有用户信息存在在session中或者存在请求attr中时，才会记录操作记录
			if(user!=null){
				//另：需要记录日志操作的方法必须在方法内添加操作描述至attr内  
				
				// 记录标识（有的话进行操作记录）
				String flag = (String) request.getAttribute(CommonConstants.OPERATION_RECORD_FLAG);
				
				if(StringUtil.isNotEmpty(flag) 
						&& flag.equals(CommonConstants.SUCCESS_FLAG)){
					// 操作描述
					String operateDesc = (String) request.getAttribute(CommonConstants.OPERATION_RECORD_DESC);
					// 操作类型
					int operateType = (int) request.getAttribute(CommonConstants.OPERATION_RECORD_TYPE);
					
					SysOperationRecord sysRecord=new SysOperationRecord(
							user.getAccount(), user.getNickName(), ipAddress,
							null,operateDesc, url,params,operateType,DateUtil.getCurDate());
					
					this.sysOperationRecordService.add(sysRecord);
					
					request.removeAttribute(CommonConstants.OPERATION_RECORD_FLAG);
					request.removeAttribute(CommonConstants.OPERATION_RECORD_DESC);
					request.removeAttribute(CommonConstants.OPERATION_RECORD_TYPE);
				}
			}
			
		} catch (Exception e) {
			log.error("记录后台操作日志发生错误",e);
		}
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2) throws Exception {
		return true;
	}
}
