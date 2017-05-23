package com.tera.cooperation.dinxuan.controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.channeltotal.model.ChannelTotal;
import com.tera.channeltotal.service.ChannelTotalService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.cooperation.dinxuan.service.DxJointService;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.form.CreditQBean;
import com.tera.credit.service.CreditAppService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;

/**
 * 鼎轩Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/cooperation/dx/joint")
public class DxJointController {

	private final static Logger log = Logger.getLogger(DxJointController.class);
	
	@Autowired(required=false)
	private DxJointService dxJointService;
	@Autowired(required=false)
	private CreditAppService creditAppService;
	@Autowired(required=false)
	private ContractService contractService;
	@Autowired(required=false)
	private RoleService roleService;
	@Autowired(required=false)
	private ChannelTotalService channelTotalService;

	@RequestMapping("/query.do")
	public String jointQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Map<String, Object> qmap = new HashMap<String, Object>();
		List<ChannelTotal> channelTotalList = channelTotalService.queryList(qmap);
		qmap.put("loginId", user.getLoginId());
		List<Role> partnerRoleList = roleService.getRoleByOrgLoginId(qmap);
		
		int isAdmin = user.getIsAdmin();
		if(isAdmin == 0){
		//取到user，取到user的role，判断role名字是否含有渠道名
			//若有，为具体渠道，query的渠道定死为此渠道//且产品与渠道挂钩
			for (Role role : partnerRoleList) {
				for (ChannelTotal channel : channelTotalList) {
					if(role.getName().indexOf(channel.getChannelCode())>0){
						map.put("partnerCode", channel.getChannelCode());
					}
				}
			}
		}else if(isAdmin == 1){
			//若无，为admin，可选各种渠道//且产品与渠道挂钩
			map.put("partnerCode", "");
		}
		
		log.info(thisMethodName+":end");
		return "cooperation/dx/joint/jointQuery";
	}
	
	@RequestMapping("/list.do")
	public String jointList(CreditQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		queryMap.put("nonStates", new String[]{"0"}); //状态
		if("waitTask".equals(qBean.getStateTask())){
			queryMap.put("states", new String[]{"20"}); 
		}else if("inTask".equals(qBean.getStateTask())){
			queryMap.put("states", new String[]{"24"});  
		}else if("offTask".equals(qBean.getStateTask())){
			queryMap.put("states", new String[]{"23"});  
		}
		
		int rowsCount = this.creditAppService.queryBpmAppAndContractCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CreditApp> creditAppList = this.creditAppService.queryBpmAppAndContractList(queryMap);
		pm.setData(creditAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());	
		
		//为中海软银添加
		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Map<String, Object> rymap = new HashMap<String, Object>();
		List<ChannelTotal> channelTotalList = channelTotalService.queryList(rymap);
		rymap.put("loginId", user.getLoginId());
		List<Role> partnerRoleList = roleService.getRoleByOrgLoginId(rymap);
		//登录人角色名"放款查询员(RY)",如果有ry显示按钮
		int isAdmin = user.getIsAdmin();
		if(isAdmin == 0){
			for (Role role : partnerRoleList) {
				for (ChannelTotal channel : channelTotalList) {
					if(role.getName().indexOf(channel.getChannelCode())>0){
						map.put("partnerCode", channel.getChannelCode());
					}
				}
			}
		}else if(isAdmin == 1){
			map.put("partnerCode", "");
		}
		
		log.info(thisMethodName+":end");
		return "cooperation/dx/joint/jointList";
	}
	
	@RequestMapping("/downloadImage.do")
	public void downloadImage(String appId,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		Map<String, Object> fmap = new HashMap<String, Object>();
		fmap.put("loanAppId", appId);
		Contract contract  = contractService.queryList(fmap).get(0);
		
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=\""+new String(contract.getContractNo().getBytes("GBK"), "iso-8859-1")+".zip\"");
		
		OutputStream out = response.getOutputStream();
		//把 附件 ZIP 放到输出流 里面
		out.write(dxJointService.downloadImage(contract));
		out.flush();
		out.close();	
		
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/save.do")
	public void jointSave(String buttonType, String appId, String msg, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		//app
		Map<String, Object> appMap=new HashMap<String, Object>();
		appMap.put("appId", appId);
		List<CreditApp> appList = creditAppService.queryList(appMap);
		CreditApp app = appList.get(0);
		//contract
		Map<String, Object> conMap=new HashMap<String, Object>();
		conMap.put("loanAppId", appId);
		List<Contract> contractList = contractService.queryList(conMap);
		Contract contract = contractList.get(0);
		contract.setChannelRemark(msg);
		//begin to change
		try{
			if("pass".equals(buttonType)){
//				app.setState("23");
				contract.setChannelState("3");
			}else if("refuse".equals(buttonType)){
				app.setState("24");
				contract.setChannelState("7");
			}
			creditAppService.updateOnlyChanged(app);
			contractService.updateOnlyChanged(contract);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "操作成功。")));
//			writer.print(JsonUtil.object2json(new ResultObj("退回复核操作成功。", null, true)));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "操作失败，程序异常。")));
//			writer.print(JsonUtil.object2json(new ResultObj("操作失败，程序异常。", null, false)));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}	
}
