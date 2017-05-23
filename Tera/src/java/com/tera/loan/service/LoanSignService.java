package com.tera.loan.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.dao.ContractDao;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.customer.dao.ContactDao;
import com.tera.lend.dao.LendAppDao;
import com.tera.loan.dao.LoanAppCollateralDao;
import com.tera.loan.dao.LoanAppDao;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanAppCollateral;
import com.tera.loan.model.LoanAppContact;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchResult;
import com.tera.match.service.Loan2matchService;
import com.tera.match.service.MatchResultService;
import com.tera.sys.dao.DataDictionaryDao;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.Org;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>LoanApprovalDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-09 15:36:08<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanSignService")
public class LoanSignService {

	private final static Logger log = Logger.getLogger(LoanSpecialService.class);

	@Autowired(required=false)
    private LoanAppDao<LoanApp> loanDao;
	
	@Autowired(required=false)
	private LoanAppCollateralDao<LoanAppCollateral> loanAppCollateralDao;
	
	@Autowired(required=false)
	private ContactDao<LoanAppContact> contactDao;
	@Autowired(required=false)
	private DataDictionaryDao<DataDictionary> dataDictionaryDao;
	
	@Autowired(required=false) //自动注入，流程服务
	private ProcessService processService;
	
	@Autowired(required=false) //自动注入，用户服务
	private UserService userService;
	
	@Autowired(required=false) //自动注入，借款端申请服务
	private LoanAppService<LoanApp> loanAppService;
	
	@Autowired(required=false) //自动注入，借款端撮合服务
	private Loan2matchService<Loan2match> loan2matchService;
	
	@Autowired(required=false) //自动注入，撮合结果服务
	private MatchResultService<MatchResult> matchResultService;
	@Autowired(required=false) //自动注入，合同服务
	private ContractService contractService;
	
	//查询签约列表数目
	public int queryCount(Map<String, Object> map)throws Exception {
		return loanDao.querySignCount(map);
	}	
	/**生成合同号
	 * @see 生成规则：首字母[X项目,Z咨询通道,F服务]----APP_CHANNEL申请渠道
	 * +字母[机构B，个人P] ------ app_Type客户类型
	 * + 字母[会员类型V(VIP)，P普通]-----P
	 * +8位日期[yyyymmdd]
	 * +4位序列数字dao.getappIdCode()
	 * @data 2014-6-18 14:39:12
	 * @param map
	 * @return String
	 */
	@Transactional
	public String getContractNo(String id){
		LoanApp bean=loanDao.queryByKey(id);
		String appChannel=bean.getAppChannel();
		String appType=bean.getAppType();
		if ("01".equals(appType)) {
			appType="P";
		} else {
			appType="B";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return appChannel+appType+"P"+format.format(new Date())+loanDao.getContractCode();
	}
	
	//查询签约列表
	public List<LoanApp> queryList(Map<String, Object> map) throws Exception {
		//超级管理员登陆，可以查看所有人员的申请信息
		String loginId=(String) map.get("userLoginId");
		if (loginId !=null) {
			User user=userService.getUser(loginId);
			if (user!=null&&user.getIsAdmin()==1) {
				map.remove("userLoginId");
			}
		}
		return loanDao.querySignList(map);
	}
	@Transactional
	public void bpmNext(String loginid,String denyToRole,String auditResult,LoanApp bean,Org org) throws Exception {
		
		//得到当前流程					
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(bean.getAppId());
		BpmTask task=taskList.get(0);
		
		String zhlx=null,netxUser=null;
		if("1".equals(auditResult)){
			if ("自动撮合".equals(task.getState())) {
				//---------------------"自动撮合" or“人工撮合”----签约-----------
				zhlx="签约";
				List<User> users = userService.getUserByOrgAndRoleAndDepart(org.getOrgId(),new String[]{"借款客户经理"},null);
				if (users.size() > 0) {
					User user = users.get(new Random().nextInt(users.size()));
					netxUser=user.getLoginId();
				}
				task.setOperator(loginid);
				//提交流程:"自动撮合" or“人工撮合”----签约
				task = processService.goNext(task, zhlx, netxUser);
				
				//------------------签约----复核-----------------------------
				List<User> userList = userService.getUserByOrgAndRoleAndDepart("86",new String[]{"放款专员"},null);
				if (userList.size() > 0) {
					User user = userList.get(new Random().nextInt(userList.size()));
					netxUser=user.getLoginId();
				}
				//提交流程：签约----复核
				task=processService.goNext(task, "复核",netxUser);
			} else if ("签约".equals(task.getState())) {
				//-----------------------------------------------
				List<User> userList = userService.getUserByOrgAndRoleAndDepart("86",new String[]{"放款专员"},null);
				if (userList.size() > 0) {
					User user = userList.get(new Random().nextInt(userList.size()));
					netxUser=user.getLoginId();
				}
				//提交流程：签约----复核
				task=processService.goNext(task, "复核",netxUser);
			}
			//--------------------财富端流程 自动撮合-->交割-->结束-----------------------------------
			Map<String, Object> beanMap = new HashMap<String, Object>();
			beanMap.put("loanAppId", bean.getAppId());
			List<MatchResult> results = this.matchResultService.queryListInSign(beanMap);
			for (MatchResult matchResult : results) {
				String lendAppId=matchResult.getLendAppId();
				List<BpmTask> lendTaskList = processService.getProcessInstanceByBizKey(lendAppId);
				BpmTask lendTask=lendTaskList.get(0);
				if ("自动撮合".equals(lendTask.getState())) {
					//出借方，从自动撮合到交割
					processService.goNext(lendTask, "交割",BpmConstants.SYSTEM_SYS);
					//跳转到结束
					processService.goNext(lendTask, BpmConstants.SYSTEM_SYS); 
				}
			}
			//更新三个表里的状态
			//1.更新合同表state=1-生效
			Contract contract=new Contract();
			String contractClass="01";//抵押合同类型编号01
			contract=contractService.getContractByAppId(bean.getAppId(),contractClass,"").get(0);
			contract.setState("1");
			contractService.updateOnlyChanged(contract);
			//2.根据申请号更新T_LOAN_2MATCH表状态到3-撮合完已签合同
			Loan2match loan2match=new Loan2match();
			loan2match=loan2matchService.getLoan2matchByAppId(bean.getAppId()).get(0);
			loan2match.setState("3");
			loan2match.setContractAmount(contract.getLoanAmount());
			loan2match.setContractStartDate(contract.getStartDate());
			loan2match.setContractEndDate(contract.getEndDate());
			loan2matchService.updateOnlyChanged(loan2match);
			//3.根据申请号将T_MATCH_RESULT中的状态改为2-合同中，并更新合同号 
			MatchResult matchResult=new MatchResult();
			matchResult=matchResultService.getResultByAppId(bean.getAppId()).get(0);
			matchResult.setContractNo(contract.getContractNo());
			matchResult.setState("2");
			matchResultService.updateOnlyChanged(matchResult);
		}else if ("4".equals(auditResult)) {
			loanAppService.giveupLoanApp(bean.getAppId(), loginid, org.getOrgId());
		}
		log.info(task);
	}
	/**
	 *修改合同
	 * @param contract
	 * @throws Exception 
	 */
	@Transactional
	public void updateContract(Contract contract) throws Exception {
			long dqDate=System.currentTimeMillis();
		
			contract.setUpdateTime(new java.sql.Timestamp(dqDate));
			contractService.update(contract);
	
	}
	/**
	 *添加合同
	 * @param contract
	 * @throws Exception 
	 */
	@Transactional
	public void createContract(Contract contract) throws Exception {

		long dqDate=System.currentTimeMillis();
		
		contract.setCreateTime(new java.sql.Timestamp(dqDate));
		contract.setCreateDate(new java.sql.Timestamp(dqDate));
		contractService.add(contract);
		
	}
	public Boolean IsSign(String appId) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("loanAppId", appId);
		if (contractService.queryList(map).size()>0) {
			return false;
		}
		
		return true;
	}
	public List<LoanAppCollateral> queryCollateralByAppId(String loanAppId, String type) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", loanAppId);
		map.put("type", type);
		return loanAppCollateralDao.queryListByAppId(map); 
		
	}
	public void saveContract(Contract contract) throws Exception {
		//查询是否已有该合同信息
		List<Contract> list=contractService.getContractByAppId(contract.getLoanAppId(), contract.getContractClass(),String.valueOf(contract.getExt1()));
		if (list.size()>0) {
			long dqDate=System.currentTimeMillis();
			contract=list.get(0);
			contract.setUpdateTime(new java.sql.Timestamp(dqDate));
			contractService.update(contract);

		} else {
			long dqDate=System.currentTimeMillis();
			
			contract.setCreateTime(new java.sql.Timestamp(dqDate));
			contract.setCreateDate(new java.sql.Timestamp(dqDate));
			contractService.add(contract);
		}	
		
	}
	public LoanAppCollateral queryCollateralByKey(String diyaId) {
		return loanAppCollateralDao.queryByKey(diyaId);
		
	}
	
	public List<LoanAppContact> querycontactByAppId(String loanAppId, int flag,String contactType) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", loanAppId);
		map.put("collateralId",flag);
		map.put("contactType",contactType);
		return contactDao.queryList(map); 
	}
	public String getUse(String useage) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("keyProp", useage);
		map.put("keyName", "loanusage");
		return dataDictionaryDao.queryList(map).get(0).getKeyValue();
	
	}
	
	
	
}
