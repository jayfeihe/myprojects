package com.tera.interfaces.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.audit.law.model.OnlineInfo;
import com.tera.audit.law.model.ProjectInfo;
import com.tera.audit.law.service.IContractService;
import com.tera.interfaces.constants.InterConstants;
import com.tera.interfaces.model.RetMsg;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.util.PasswordUtil;

/**
 * 线上系统对接接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/Online")
public class InterOnlineController extends BaseController {

	private static final Logger log = Logger.getLogger(InterOnlineController.class);
	
	@Autowired
	private IContractService contractService;
	
	
	@RequestMapping("/sendProjectInfo.do")
	public void sendInfo(@RequestBody String strJson,String cTime,String sign,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		log.info("请求参数：json<--------------->"+strJson);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			String strSign = PasswordUtil.md5(InterConstants.SECRET_ID + cTime
					+ strJson);
			if (!strSign.equals(sign)) {//签名认证不通过
				writer.print(GsonUtils.getInstance().toJson(new RetMsg("1111","签名认证不通过")));
				return ;
			}
			OnlineInfo onlineInfo=GsonUtils.getInstance().fromJson(strJson, OnlineInfo.class);
			//验证签名是否正确
			
			List<ProjectInfo> listInfos=onlineInfo.getData();
			if(listInfos!=null&&listInfos.size()>0){
				contractService.handleOnline(listInfos);
			}
		
			writer.print(GsonUtils.getInstance().toJson(new RetMsg("0000","已成功处理！")));
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			writer.print(GsonUtils.getInstance().toJson(new RetMsg("1111",str)));
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
		
		log.info(thisMethodName+":end");
	}
	

}
