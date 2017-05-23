package com.tera.interfaces.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.audit.common.model.BranchBankBean;
import com.tera.audit.loan.model.BankBranchInfo;
import com.tera.audit.loan.service.IBankBranchInfoService;
import com.tera.feature.warehouse.model.Warehouse;
import com.tera.feature.warehouse.service.IWarehouseService;
import com.tera.interfaces.model.AppDictBean;
import com.tera.interfaces.model.AppWarehouseBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.JsonMsg;
import com.tera.util.JsonUtil;
import com.tera.util.StringUtils;

/**
 * 接口字典数据Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter")
public class InterDictController extends BaseController {

	private static final Logger log = Logger.getLogger(InterDictController.class);
	
//	@Autowired
//	private ParameterService parameterService;
	@Autowired
	private IWarehouseService warehouseService;
	@Autowired
	private IBankBranchInfoService bankBranchInfoService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/dict.do")
	public void bank(String key,String parentValue, HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		log.info("请求参数：key<--------------->"+key);
		log.info("请求参数：parentKey<--------------->"+parentValue);
		
		PrintWriter writer = null;
		
		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			Map<String, List<DataDictionary>> dictMap = (Map<String, List<DataDictionary>>) request
					.getSession().getServletContext().getAttribute("DATADICTS");
			List<DataDictionary> list = null;
			
			if (StringUtils.isNullOrEmpty(parentValue)) {
				list = dictMap.get(key.toLowerCase());
			} else {
				list = dictMap.get((key+","+parentValue).toLowerCase());
			}
			
			List<AppDictBean> dicts = new ArrayList<AppDictBean>(list.size());
			if (list != null) {
				for (DataDictionary dict : list) {
					AppDictBean tmpDict = new AppDictBean(dict.getKeyProp(), dict.getKeyValue());
					dicts.add(tmpDict);
				}
			}
			
			String jsonStr = "";
			
			jsonStr = GsonUtils.getInstance().toJson(dicts, List.class);
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
	
	@RequestMapping("/branchBank.do")
	public void listBranchBank(String province,String city,String bankName,HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
//		log.info("请求参数：province<--------------->"+province);
//		log.info("请求参数：city<--------------->"+city);
//		log.info("请求参数：bankName<--------------->"+bankName);
//		
//		Parameter bankUrlParam = this.parameterService.queryByParamName(ParameterConstants.BANK_URL);
//		
//		String url = bankUrlParam.getParamValue();
//		url = url.replace("PROVINCE", province);
//		url = url.replace("CITY", city);
//		url = url.replace("BANK_NAME", bankName);
		
//		log.info("== 支行url:"+url+"==");
		
//		DefaultHttpClient client = HttpUtil.getDefaultHttpClient();
//		
//		HttpGet get = new HttpGet(url);
//		
//		HttpResponse resp = client.execute(get);
//		int statusCode = resp.getStatusLine().getStatusCode();
//		
//		if (statusCode == HttpStatus.SC_OK) {
//			HttpEntity entity = resp.getEntity();
//			String json = EntityUtils.toString(entity);
//			BranchBankJson bankJson = GsonUtils.getInstance().fromJson(json, BranchBankJson.class);
//			if (bankJson != null) {
//				List<String> datas = bankJson.getData();
//				if (datas != null && datas.size() > 0) {
//					List<AppDictBean> dicts = new ArrayList<AppDictBean>(datas.size());
//
//					for (String data : datas) {
//						AppDictBean tmpDict = new AppDictBean(data, data);
//						dicts.add(tmpDict);
//					}
//					response.setContentType("application/json;charset=UTF-8");
//					PrintWriter writer = response.getWriter();
//					writer.print(JsonUtil.object2json(dicts));
//					writer.flush();
//					writer.close();
//				} 
//			}
//		}
		
		//访问支行信息表
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("city", city);
		map.put("bankName", bankName);
		List<BankBranchInfo> lists=bankBranchInfoService.queryList(map);
		if (lists!=null&&lists.size()>0) {
			List<BranchBankBean> banks = new ArrayList<BranchBankBean>();
			for (BankBranchInfo info : lists) {
				BranchBankBean temp = new BranchBankBean(info.getBankBranch(), info.getBankBranch());
				banks.add(temp);
			}
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print(JsonUtil.object2json(banks));
			writer.flush();
			writer.close();
		}
		
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/warehouse.do")
	public void listWarehouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Warehouse> warehouses = this.warehouseService.queryList(null);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		List<AppWarehouseBean> appWarehouse = new ArrayList<AppWarehouseBean>(warehouses.size());
		if (warehouses != null && warehouses.size() > 0) {
			for (Warehouse warehouse : warehouses) {
				appWarehouse.add(new AppWarehouseBean(warehouse.getId(), warehouse.getName()));
			}
		}
		
		writer.print(JsonUtil.object2json(appWarehouse));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
}
