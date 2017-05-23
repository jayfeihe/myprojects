package com.tera.loan.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.loan.constant.LoanConstants;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.form.LoanQBean;
import com.tera.loan.service.LoanAppService;
import com.tera.loan.service.LoanFirstVerifyService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 17:38:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loan/firstverify")
public class LoanFirstVerifyController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanFirstVerifyController.class);
	
	/**
	 * loanAppFirstVerifyService
	 */
	@Autowired(required=false) //自动注入
	private LoanFirstVerifyService<LoanApp> loanFirstVerifyService;
	
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoanAppService loanAppService;
	
	
	/**
	 * 跳转到借款端申请表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String loanAppQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "loan/firstverify/loanAppQuery";
	}

	/**
	 * 显示借款端申请表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String loanAppList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		/*
		 * 留着这个注释的目的是为了与项目经理进行确认是否的确不需要进行权限控制
		 * 系统登录用户(权限不需要控制，所以注释掉)
		 * String sessionLoginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		 * 登录机构(权限不需要控制，所以注释掉)
		 * Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		 */		
		
		//登录机构
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//系统登录用户
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		 
		 
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
	
		/*
		 * 取得组织机构的id(权限不需要控制，所以注释掉)
		 * queryMap.put("org_id", sessionOrg.getId());
		 */		
		
		//取得流程实例的名称，如"抵押借款流程"
//		queryMap.put("taskProcessName", BpmConstants.PROCESS_NAME_B);
		//取得流程实例的当前状态，如"风险专员初核"
		queryMap.put("bpmStates", new String[]{LoanConstants.PROCESS_STATE_FXZYCH});
		//取得当前登录用户的用户名，如admin
		queryMap.put("userLoginId", loginId);
		
		
		//当前机构id(改为诗献代码而加1111111111111111111111111111111111111111111111 )
		if(queryMap.get("orgId")==null||queryMap.get("orgId").equals("")){
			String orgId=sessionOrg.getOrgId();
			queryMap.put("orgId", sessionOrg.getOrgId());
		}
		
		queryMap.put("states", new String[]{"1", "2","3"});
		
		//取得符合条件的记录的总条数以便分页
		//下面这句是我的查询记录条数
		//int rowsCount = this.loanFirstVerifyService.queryCount(queryMap);
		//下面这句是诗献的查询记录条数
		int rowsCount = this.loanAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		
		//诗献的方法
		 List<LoanApp> loanAppList = this.loanAppService.queryBpmLoanAppList(queryMap);
		 
		//我的查询方法
//		List<LoanApp> loanAppList = this.loanFirstVerifyService.queryList(queryMap);
		 
		pm.setData(loanAppList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "loan/firstverify/loanAppList";
	}

	/**
	 * 跳转到更新借款端申请表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String loanAppUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {	
		
		LoanApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.loanFirstVerifyService.queryByKey(id);
		}
		
		//根据传过来的申请号获取流程实例
		BpmTask task = null;
		//实际上里面只有一个BpmTask对象
		List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(bean.getAppId());
		for (int i = 0;i<bpmTasks.size();i++){
			task = bpmTasks.get(i);
		}
		
		//根据流程实例获取日志的该实例的最后一条记录
		BpmLog lastBpmLog = null;
		
		//如果是总部审批拒件或特殊件审批拒件退回给风险专员
		if("2".equals(bean.getState())){
			List<BpmLog> bpmLogList = null;
			bpmLogList = processService.getProcessHistoryLog(task, "拒件");
			if(bpmLogList.size()>0){
				if(bpmLogList.size()>0){
					lastBpmLog = bpmLogList.get(0);
					map.put("denyReason",lastBpmLog.getLogContent2() );
				}
			}else{
				//bpmLogList = processService.getProcessHistoryLog(task, "总部审核");
				bpmLogList = processService.getProcessHistoryLog(task, "拒件");
				if(bpmLogList.size()>0){
					lastBpmLog = bpmLogList.get(0);
					map.put("denyReason",lastBpmLog.getLogContent2() );
				}
			}
			
		}else
		
		//如果是复核退回给风险专员
		if("3".equals(bean.getState())){
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(task, "复核");
			if(bpmLogList.size()>0){
				lastBpmLog = bpmLogList.get(0);
				map.put("denyReason",lastBpmLog.getLogContent2() );
			}
		} else
		
		//如果是营业部经理正常退回给风险专员或者是总部退回
		if("1".equals(bean.getState())){
			//isDeny：正常件是否是退回的标志，true表示非退回，false表示退回
			boolean isDeny = true;
			List<BpmLog> bpmLogList = null;
			bpmLogList =  processService.getProcessHistoryLog(task.getBizKey());
			for (BpmLog bpmLog : bpmLogList) {
				String logContent1 = bpmLog.getLogContent1();
				if(logContent1 != null && "不通过".equals(bpmLog.getLogContent1().trim())){
					map.put("denyReason",bpmLog.getLogContent2() );
					isDeny = false;
					break;
				}
				
			}
			
			if(isDeny){
				map.put("denyReason","非退回件" );
			}
			/*List<BpmLog> bpmLogList = null;
			bpmLogList =  processService.getProcessHistoryLog(task, "特殊件审核");
			if(bpmLogList.size()>0){
				lastBpmLog = bpmLogList.get(0);
				map.put("denyReason",lastBpmLog.getLogContent2() );
			}else{
				bpmLogList =  processService.getProcessHistoryLog(task, "总部审核");
				if(bpmLogList.size()>0){
					lastBpmLog = bpmLogList.get(0);
					map.put("denyReason",lastBpmLog.getLogContent2() );
				}else{
					bpmLogList =  processService.getProcessHistoryLog(task, "营业部经理审核");
					if(bpmLogList.size()>0){
						lastBpmLog = bpmLogList.get(0);
						map.put("denyReason",lastBpmLog.getLogContent2() );
					}else{
						map.put("denyReason","非退回" );
					}
				}
			}*/
		}
		
//		map.put("denyReason",lastBpmLog.getLogContent2() );
		map.put("bean", bean);
		return "loan/firstverify/loanAppUpdate";
	}
	
	/**
	 * 申请通过
	 * @param id  申请ID
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/passApplication.do")
	public void passApplication(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try{
			//系统登录用户
			String sessionLoginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			
			LoanApp loan = this.loanFirstVerifyService.queryByKey(id);
			
			//获取登录机构
			Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			String orgId = sessionOrg.getOrgId();
			
			//根据申请号获取该申请实体信息
			LoanApp loanApp =  this.loanFirstVerifyService.queryByKey(id);
			//获取该申请号对应的流程实例（实际上只有一条）
			List<BpmTask> bpmTasks =null;
			bpmTasks = this.processService.getProcessInstanceByBizKey(loanApp.getAppId());
			
			if("1".equals(loanApp.getState())){
				String[] roleNames = new String[]{LoanConstants.PROCESS_STATE_FXZYCH_ROLE_YYBJL};
				//根据当前机构编码和角色名称查询用户
				List<User> users = userService.getUserByOrgAndRoleAndDepart(orgId, roleNames,null);
				//被选中的营业部经理
				User user = null;
				if (users.size() > 0) {
					//随机获取一个用户
					user = users.get(new Random().nextInt(users.size()));
					
				}
				
				//申请流向下一个节点（"营业部经理审核"）
				for(int i=0;i<bpmTasks.size();i++){
					BpmTask bpmTask = bpmTasks.get(i);
					//下面这个设置operator亚红提醒我要去掉
					bpmTask.setOperator(sessionLoginId);
					processService.goNext(bpmTask, LoanConstants.PROCESS_STATE_YYBJLSH, user.getLoginId());
				}
			}else if("3".equals(loanApp.getState())){
				BpmLog lastBpmLog = null;
				
				//List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bpmTasks.get(0).getBizKey(),BpmConstants.PROCESS_STATE_QY);
				List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bpmTasks.get(0).getBizKey(),LoanConstants.PROCESS_STATE_FXZYCH);
				
				if(bpmLogList.size()>0){
					lastBpmLog = bpmLogList.get(0);
				}
				
				processService.goNext(bpmTasks.get(0), LoanConstants.PROCESS_STATE_QY, lastBpmLog.getOperator());
			}
			
			
			writer.print(JsonUtil.object2json(new JsonMsg(true, "审核通过！")));
			
		}catch(Exception e){
			log.error(thisMethodName+":error",e);
			e.printStackTrace();
			writer.print(JsonUtil.object2json(new JsonMsg(false, "审核失败，请联系管理员！")));
		}
		
		log.info(thisMethodName+":end");
	}

	/**
	 * 申请不通过
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/denyApplication.do") 
	public void denyApplication(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		try{
			//取得不通过的原因
			String denyReason = request.getParameter("denyReason");
			//不通过原因转码
			denyReason=URLDecoder.decode(denyReason, "UTF-8");
			
			//系统登录用户
			String sessionLoginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			
			//根据loan_app中的id取得该loan_app
			LoanApp loan = this.loanFirstVerifyService.queryByKey(id);
			
			//根据传过来的申请号获取流程实例
			BpmTask task = null;
			//实际上里面只有一个BpmTask对象
			List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(loan.getAppId());
			for (int i = 0;i<bpmTasks.size();i++){
				task = bpmTasks.get(i);
			}
			
			
			//根据流程实例获取日志的该实例的最后一条记录
			BpmLog lastBpmLog = null;
			
			
			//List<BpmLog> bpmLogList = processService.getProcessHistoryLog(task);
			//修改为查询log表中该任务的状态为“录入申请”的记录的最后一条
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(task, "录入申请");
			if(bpmLogList.size()>0){
				lastBpmLog = bpmLogList.get(0);
			}
			
			
			task.setVariable("logContent1", LoanConstants.PROCESS_STATE_FXZYCH_BTG);
			task.setVariable("logContent2", denyReason);
			//本次事件的实际操作人，即session里面的用户id
			task.setOperator(sessionLoginId);
			
			//谁提交过来的退给谁
			//取得日志表中的OPERATOR
			User user = userService.getUser(lastBpmLog.getOperator());
			//System.out.println(user.getLoginId());调试时使用，无意义，所以注释掉
			task = processService.goNext(task, LoanConstants.PROCESS_STATE_LUSQ, user.getLoginId());

			log.info(task);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "审核不通过成功！")));
		}catch(Exception e){
			log.error(thisMethodName+":error",e);
			e.printStackTrace();
			writer.print(JsonUtil.object2json(new JsonMsg(false, "审核失败，请联系管理员！")));
		}
		log.info(thisMethodName+":end");
		
	}
	
	/**
	 * 复议
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/talkAgainApplication.do") 
	public void talkAgainApplication(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//根据loan_app中的id取得该loan_app
			LoanApp loan = this.loanFirstVerifyService.queryByKey(id);
			//当前登陆的人的ID
			String currentLoginUserID=request.getSession().getAttribute(SysConstants.LOGIN_ID).toString();
			
			if("1".equals(loan.getState())){
				writer.print(JsonUtil.object2json(new JsonMsg(true, "此申请是正常申请，不可复议！")));
				return;
			}else if("2".equals(loan.getState())){
				//实际上里面只有一个BpmTask对象
				List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(loan.getAppId());
				//根据传过来的申请号获取流程实例
				BpmTask task = bpmTasks.get(0);
				//判断该流程实例是否已经复议过，如果复议过则不能再次复议
				List<BpmLog> bpmLogLists = null;
				bpmLogLists = processService.getProcessHistoryLog(task,"申请复议");
				if(bpmLogLists.size()>0){
					writer.print(JsonUtil.object2json(new JsonMsg(true, "该申请已经复议过，不可再次复议")));
					return;
				}
				//取得复议的原因
				String talkAgainReason = request.getParameter("talkAgainReason");
				talkAgainReason = URLDecoder.decode(talkAgainReason, "UTF-8");
				//提交复议流程
				this.loanFirstVerifyService.bpmNextAgain(task,talkAgainReason,currentLoginUserID,loan);
				writer.print(JsonUtil.object2json(new JsonMsg(true, "审核成功！")));
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		log.info(thisMethodName+":end");
	}
	
	
	/**
	 * 放弃
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/quitApplication.do") 
	public void quitApplication(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try{	
			//系统登录用户
			String sessionLoginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			
			//获取登录机构
			Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			String orgId = sessionOrg.getOrgId();
			
			//根据loan_app中的id取得该loan_app
			LoanApp loan = this.loanFirstVerifyService.queryByKey(id);
			
			//根据传过来的申请号获取流程实例
			BpmTask task = null;
			//实际上里面只有一个BpmTask对象
			List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(loan.getAppId());
			for (int i = 0;i<bpmTasks.size();i++){
				task = bpmTasks.get(i);
			}

			//本次事件的实际操作人，即session里面的用户id
			task.setOperator(sessionLoginId);
			
			loanFirstVerifyService.bpmNext(task, LoanConstants.PROCESS_STATE_FQ, sessionLoginId, loan, sessionLoginId, sessionOrg);
			
			//注释掉开始
			  //task = processService.goNext(task, BpmConstants.PROCESS_STATE_FQ, BpmConstants.SYSTEM_SYS);
			  
			  //增加老毕的放弃服务
			  //loanAppService.giveupLoanApp(loan.getAppId(), sessionLoginId, orgId);
			  //log.info(task);
			//注释掉结束
			  
			
			writer.print(JsonUtil.object2json(new JsonMsg(true, "放弃成功！")));
			
		}catch(Exception e){
			log.error(thisMethodName+":error",e);
			e.printStackTrace();
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败！")));
		}
		
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 保存借款端申请表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void loanAppSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			LoanApp bean = (LoanApp) RequestUtils.getRequestBean(LoanApp.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.loanFirstVerifyService.updateOnlyChanged(bean);
			} else { //如果不存在
				this.loanFirstVerifyService.add(bean);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 删除借款端申请表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void loanAppDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.loanFirstVerifyService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(new JsonMsg(false, "关联数据，不能删除！"));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看借款端申请表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String loanAppRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApp bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.loanFirstVerifyService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "sys/firstverify/loanAppRead";
	}

}
