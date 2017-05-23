package com.tera.audit.common.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.audit.common.model.BranchBankBean;
import com.tera.audit.loan.model.BankBranchInfo;
import com.tera.audit.loan.service.IBankBranchInfoService;
import com.tera.util.JsonUtil;

/**
 * 公共Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/common")
public class AuditCommonController {

	private static final Logger log = Logger.getLogger(AuditCommonController.class);
	
//	@Autowired
//	private ParameterService parameterService;

	@Autowired
	private IBankBranchInfoService bankBranchInfoService;
	
	@RequestMapping("/listBranchBank.do")
	public void listBranchBank(String province,String city,String bank_name,HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
//		Parameter bankUrlParam = this.parameterService.queryByParamName(ParameterConstants.BANK_URL);
//		
//		String url = bankUrlParam.getParamValue();
//		url = url.replace("PROVINCE", province);
//		url = url.replace("CITY", city);
//		url = url.replace("BANK_NAME", bank_name);
//		
//		log.info("== 支行url:"+url+"==");
//		
//		DefaultHttpClient client = HttpUtil.getDefaultHttpClient();
//		
//		HttpGet get = new HttpGet(url);
//		try {
//			
//			HttpResponse resp = client.execute(get);
//			
//			int statusCode = resp.getStatusLine().getStatusCode();
//			
//			if (statusCode == HttpStatus.SC_OK) {
//				HttpEntity entity = resp.getEntity();
//				String json = EntityUtils.toString(entity);
//				BranchBankJson bankJson = GsonUtils.getInstance().fromJson(json, BranchBankJson.class);
//				if (bankJson != null) {
//					List<String> datas = bankJson.getData();
//					if (datas != null && datas.size() > 0) {
//						List<BranchBankBean> banks = new ArrayList<BranchBankBean>();
//
//						for (String data : datas) {
//							BranchBankBean temp = new BranchBankBean(data, data);
//							banks.add(temp);
//						}
//						
//						writer.print(JsonUtil.object2json(banks));
//					} 
//				} else {
//					writer.print("[]");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			writer.flush();
//			writer.close();
//		}
		
		//访问支行信息表
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("city", city);
		map.put("bankName", bank_name);
		List<BankBranchInfo> lists=bankBranchInfoService.queryList(map);
		if (lists!=null&&lists.size()>0) {
			List<BranchBankBean> banks = new ArrayList<BranchBankBean>();
			for (BankBranchInfo info : lists) {
				BranchBankBean temp = new BranchBankBean(info.getBankBranch(), info.getBankBranch());
				banks.add(temp);
			}
			writer.print(JsonUtil.object2json(banks));
		}else {
			writer.print("[]");
		}
		
		writer.flush();
		writer.close();
		
		log.info(thisMethodName+":end");
	}
}
