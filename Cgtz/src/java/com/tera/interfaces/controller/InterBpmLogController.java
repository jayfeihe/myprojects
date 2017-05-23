package com.tera.interfaces.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.model.BpmLog;
import com.tera.bpm.service.ProcessService;
import com.tera.interfaces.model.AppBpmLogBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.util.DateUtils;

/**
 * 流程记录接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter")
public class InterBpmLogController extends BaseController {

	private static final Logger log = Logger.getLogger(InterBpmLogController.class);
	
	@Autowired
	private ProcessService processService;
	
	
	@RequestMapping("/getBpmLog.do")
	public void logList(String loanId,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		log.info("请求参数：loanId<--------------->"+loanId);
		
		PrintWriter writer = null;

		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			List<BpmLog> bpmLogs = this.processService.getProcessHistoryLogNoDesc(loanId);
			
			String jsonStr = "[]";
			if (bpmLogs != null && bpmLogs.size() > 0) {
				List<AppBpmLogBean> logs = new ArrayList<AppBpmLogBean>();
				for (BpmLog l : bpmLogs) {
					AppBpmLogBean tmpBean = new AppBpmLogBean();
					tmpBean.setNode(l.getState());
					tmpBean.setOperatorName(l.getOperatorName());
					tmpBean.setInTime(DateUtils.formatTime(l.getIntime()));
					tmpBean.setOutTime(DateUtils.formatTime(l.getOuttime()));
					tmpBean.setDecision(l.getDecision());
					tmpBean.setRemark(l.getRemark());
					logs.add(tmpBean);
				}
				
				jsonStr = GsonUtils.getInstance().toJson(logs, List.class);
			}
			
			log.info("== 响应报文："+ jsonStr +" ==");
			
			writer.print(jsonStr);
		} catch (Exception e) {
			writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
		log.info(thisMethodName+":end");
	}
}
