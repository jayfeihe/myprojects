package com.tera.audit.loan.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.audit.loan.dao.LoanBaseDao;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.Contact;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.audit.loan.service.IBankBranchInfoService;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.IContactService;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.bpm.service.ProcessService;
import com.tera.feature.loanguar.model.LoanGuar;
import com.tera.feature.loanguar.service.ILoanGuarService;
import com.tera.mail.service.MailService;
import com.tera.sys.model.Org;
import com.tera.sys.service.MybatisBaseService;
import com.tera.sys.service.OrgService;
import com.tera.util.MathUtils;
import com.tera.util.StringUtils;

/**
 * 
 * T_LOAN_BASE服务类
 * <b>功能：</b>LoanBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:52:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanBaseService")
public class LoanBaseServiceImpl extends MybatisBaseService<LoanBase> implements ILoanBaseService {

	private final static Logger log = Logger.getLogger(LoanBaseServiceImpl.class);

	@Autowired(required=false)
    private LoanBaseDao dao;
	@Autowired
	private ILoanAppService loanAppService;
	@Autowired
	private IContactService contactService;
	@Autowired
	private ProcessService processService;
	@Autowired
	private ICommonHandlerService commonHandlerService;
	@Autowired
	private ILoanGuarService loanGuarService;
	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private ICollateralService collateralService;
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private IBankBranchInfoService  bankBranchInfoService;

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#add(com.tera.audit.loan.model.LoanBase)
	 */
	@Override
	@Transactional
	public void add(LoanBase... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(LoanBase obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#update(com.tera.audit.loan.model.LoanBase)
	 */
	@Override
	@Transactional
	public void update(LoanBase obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#updateOnlyChanged(com.tera.audit.loan.model.LoanBase)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(LoanBase obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#delete(java.lang.Object)
	 */
	@Override
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#queryList(java.util.Map)
	 */
	@Override
	public List<LoanBase> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#queryByKey(java.lang.Object)
	 */
	@Override
	public LoanBase queryByKey(Object id) throws Exception {
		return (LoanBase)dao.queryByKey(id);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#queryByLoanId(java.lang.Object)
	 */
	@Override
	public LoanBase queryByLoanId(Object loanId) throws Exception {
		return (LoanBase)dao.queryByLoanId(loanId);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#queryBusinessCount(java.util.Map)
	 */
	@Override
	public int queryBusinessCount(Map<String, Object> map)throws Exception {
		return dao.queryBusinessCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#queryBusinessList(java.util.Map)
	 */
	@Override
	public List<LoanBase> queryBusinessList(Map<String, Object> map) throws Exception {
		return dao.queryBusinessList(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#queryPageList(java.util.Map)
	 */
	@Override
	public PageList<LoanBase> queryPageList(Map<String, Object> params) {
		return this.selectPageList(LoanBaseDao.class, "queryBusinessList", params);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanBaseService#loanAppProcess(com.tera.audit.loan.model.form.AppFormBean, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public LoanBaseJsonMsg loanAppProcess(AppFormBean formBean, String loginId, String orgId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		String buttonType = formBean.getButtonType();
		
		// 保存实体
		LoanBase loanBase = this.saveOrUpdate(formBean, loginId, orgId, nowTime);
		
		LoanBaseJsonMsg jsonMsg = new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), true, "成功");
		
		if (!"save".equals(buttonType)) {
			// 流程跳转,状态更新
			jsonMsg = this.workFlow(loanBase, buttonType, loginId, orgId, nowTime);
		}
		return jsonMsg;
	}

	/**
	 * 判断是否诉讼处理完成
	 * @param loanId
	 * @return
	 * @throws Exception
	 */
	private boolean isFinishLaw(String loanId) throws Exception {
		boolean isFinished = true;
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("loanId",loanId);
		// 查询申请诉讼情况
		List<LoanApp> loanApps = this.loanAppService.queryList(queryMap );
		for (LoanApp loanApp : loanApps) {
			if (StringUtils.isNullOrEmpty(loanApp.getLawState())) {
				isFinished = false;
				break;
			}
		}
		
		// 查询担保人诉讼情况
		List<LoanGuar> loanGuars = this.loanGuarService.queryList(queryMap);
		if (loanGuars != null && loanGuars.size() > 0) {
			for (LoanGuar loanGuar : loanGuars) {
				if (StringUtils.isNullOrEmpty(loanGuar.getLawState())) {
					isFinished = false;
					break;
				}
			}
		}
		
		return isFinished;
	}

	/**
	 * 保存更新实体
	 * @param formBean
	 * @param loginId
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private LoanBase saveOrUpdate(AppFormBean formBean, String loginId, String orgId, Timestamp nowTime) throws Exception {

		LoanBase loanBase = formBean.getLoanBase();
		LoanApp appLoan = formBean.getAppTypeLoan();
		LoanApp comLoan = formBean.getComTypeLoan();
		
		Org tmpOrg=orgService.queryByOrgId(orgId);
		String strLoanIdCreate="";
		if (tmpOrg.getCode()!=null) {
			strLoanIdCreate=tmpOrg.getCode();
		}
		//生成申请号 规则：机构代码+日期时间+一位随机数
		 strLoanIdCreate=strLoanIdCreate+DateUtils.format(new Date(), "yyyyMMddHHmmss")+new Random().nextInt(10);

		// 不是续贷进行保存基本信息
		if ("0".equals(loanBase.getIsRenew())  
				|| StringUtils.isNullOrEmpty(loanBase.getIsRenew())) {
			String saleRemark = formBean.getSaleRemark();
			LoanBase tmpLoanBase = this.queryByLoanId(loanBase.getLoanId());
			if (tmpLoanBase != null) {
				// 现在是个人，原先是公司
				if ("01".equals(loanBase.getLoanType()) && "02".equals(tmpLoanBase.getLoanType())) {
					comLoan = appLoan;
				}
				// 现在是公司，原先是个人
				if ("02".equals(loanBase.getLoanType()) && "01".equals(tmpLoanBase.getLoanType())) {
					appLoan = comLoan;
				}
			}
			
			// 个人申请基本信息
			if ("01".equals(loanBase.getLoanType())) {
				if (appLoan != null) {
//					appLoan.setYearIncome(MathUtils.mul(appLoan.getYearIncome(), 10000.00)); // 年收入单位转换
					appLoan.setSaleRemark(saleRemark);
					if (0 == appLoan.getId()) {
						appLoan.setLoanId(strLoanIdCreate);
						appLoan.setMainFlag("1"); // 主借款人
						appLoan.setCreateTime(nowTime);
						appLoan.setUpdateTime(nowTime);
						this.loanAppService.add(appLoan);
						loanBase.setLoanId(appLoan.getLoanId());
					} else {
						appLoan.setUpdateTime(nowTime);
						this.loanAppService.updateOnlyChanged(appLoan);
					}
					
					loanBase.setName(appLoan.getName());
					loanBase.setIdType(appLoan.getIdType());
					loanBase.setIdNo(appLoan.getIdNo());
				}
			}
			
			// 公司申请基本信息
			if ("02".equals(loanBase.getLoanType())) {
				if (comLoan != null) {
//					comLoan.setOrgRegAmt(MathUtils.mul(comLoan.getOrgRegAmt(), 10000.00)); // 注册资本单位转换
//					comLoan.setOrgSalesAmt(MathUtils.mul(comLoan.getOrgSalesAmt(), 10000.00)); // 年销售额单位转换
					comLoan.setSaleRemark(saleRemark);
					if (0 == comLoan.getId()) {
						comLoan.setLoanId(strLoanIdCreate);
						comLoan.setMainFlag("1"); // 主借款人
						comLoan.setCreateTime(nowTime);
						comLoan.setUpdateTime(nowTime);
						this.loanAppService.add(comLoan);
						loanBase.setLoanId(comLoan.getLoanId());
					} else {
						comLoan.setUpdateTime(nowTime);
						this.loanAppService.updateOnlyChanged(comLoan);
					}
					
					loanBase.setName(comLoan.getName());
					loanBase.setIdType(comLoan.getIdType());
					loanBase.setIdNo(comLoan.getIdNo());
				}
			}
			
			// 联系人信息
			List<Contact> contacts = formBean.getContacts();
			if (contacts != null && contacts.size() > 0) {
				for (Contact contact : contacts) {
					if (0 == contact.getId()) {
						// 状态为有效时，添加
						if ("1".equals(contact.getState())) {
							contact.setLoanId(loanBase.getLoanId());
							this.contactService.add(contact);
						}
					} else {
						this.contactService.updateOnlyChanged(contact);
					}
				}
			} 
		}
		
		// 申请业务信息
		if (loanBase != null) {
			loanBase.setUpdateUid(loginId);
			loanBase.setUpdateTime(nowTime);
			if (0 == loanBase.getId()) {
				loanBase.setIsBeyond("0"); // 是否超出审批额度默认为否
				loanBase.setIsRenew("0"); // 是否续贷，默认为否
				loanBase.setState1st("A");
				loanBase.setState2nd("1");
				loanBase.setSalesman(loginId);
				loanBase.setLoanWay(formBean.getLoanWay());
				loanBase.setOrg(orgId);
				loanBase.setInputTime(nowTime);
				this.add(loanBase);
				// 流程启动
				this.processService
						.startProcessInstanceByName(CommonConstant.AUDIT_PROCESS_NAME,loanBase.getLoanId(),loginId);
			} else {
				// 如果改为无共同借款人，删除共同借款人
				if ("0".equals(loanBase.getIsTgth())) {
					this.loanAppService.deleteCommonLoan(loanBase.getLoanId());
				}
				this.updateOnlyChanged(loanBase);
			}
			
			//处理银行，支行信息
			
			bankBranchInfoService.verifyBranch(loanBase.getAcctCity(), loanBase.getAcctBank(), loanBase.getAcctBranch());
			
		}
		
		
		return loanBase;
	}
	
	/**
	 * 流程处理
	 * @param loanBase
	 * @param buttonType
	 * @param operator
	 * @param orgId
	 * @param nowTime
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private LoanBaseJsonMsg workFlow(LoanBase loanBase,String buttonType,String operator,String orgId, Timestamp nowTime) throws Exception {
		
		String nextUser = "";
		String state1st = null,state2nd = null;
		
		// 提交
		if ("submit".equals(buttonType)) {
			// 校验银行账户信息是否规范
			// 个人-开户名与本人姓名必须一致，账户类型必须是个人账户
			if ("01".equals(loanBase.getLoanType())) {
				if (!loanBase.getName().equals(loanBase.getAcctName())) {
					return new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), false, "开户名与本人姓名不一致，提交申请失败！");
				}
				if ("2".equals(loanBase.getExt2())) {
					return new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), false, "账户类型必须是个人账户，提交申请失败！");
				}
			} 
			
			// 公司-开户名与公司名称或者法人姓名必须一致
			if ("02".equals(loanBase.getLoanType())) {
				
				Map<String, Object> queryMap = new HashMap<String,Object>();
				if ("0".equals(loanBase.getIsRenew())) {
					queryMap.put("loanId", loanBase.getLoanId());
				} else if("1".equals(loanBase.getIsRenew())){
					queryMap.put("loanId", loanBase.getOrigLoanId());
				}
				queryMap.put("mainFlag", "1"); // 主借款人
				LoanApp loanApp = this.loanAppService.queryList(queryMap ).get(0);
				
				String legalName = loanApp.getLegalName(); // 法人姓名
				
				if (!(loanBase.getAcctName().equals(loanBase.getName()) 
						|| loanBase.getAcctName().equals(legalName) )) {
					return new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), false, "开户名与公司名或法人姓名不一致，提交申请失败！");
				}
			}

			// 诉讼是否处理完
			boolean isFinished = isFinishLaw(loanBase.getLoanId());
			if (!isFinished) {
				return new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), false, "有未处理的诉讼，提交申请失败！");
			} 
			
			// 获取下一操作人
			 String prevUser = this.commonHandlerService.getPrevUser(loanBase.getLoanId(), CommonConstant.PROCESS_B);
			if (StringUtils.isNotNullAndEmpty(prevUser)) {
				nextUser = prevUser;
			} else {
				nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_BRAN_MGR,orgId);
			}
			
			if (StringUtils.isNotNullAndEmpty(nextUser)) {
				// 跳转到分公司审批
				this.commonHandlerService.workFlow(loanBase.getLoanId(), CommonConstant.PROCESS_B, nextUser, operator, null, null);
				state1st = "B";
				state2nd = "1";
			} else {
				return new LoanBaseJsonMsg(loanBase.getId(),loanBase.getLoanId(),false, CommonConstant.PROCESS_B + "没有相关操作人，提交申请失败！");
			}
			
			// 发送邮件提醒
			String reciveEmail = this.commonHandlerService.getEmailByLoginId(nextUser);
			if (StringUtils.isNotNullAndEmpty(reciveEmail)) {
				this.taskExecutor.execute(new MailService(CommonConstant.PROCESS_B, loanBase.getLoanId(), reciveEmail));
			}
		}

		// 客户放弃
		if ("giveup".equals(buttonType.toLowerCase())) {
			this.commonHandlerService.workFlow(loanBase.getLoanId(), CommonConstant.PROCESS_K, nextUser, operator, "客户放弃", null);
			state1st = "K";
			state2nd = "0";
		}
		 // 判断是否足额，对于整个的申请借贷，包含续贷的
        Map<String, Object> map =new HashMap<String, Object>();
      
        loanBase.setIsEnough("0");
        if ("0".equals(loanBase.getIsRenew())) {
        	map.put("loanId", loanBase.getLoanId());
        	map.put("state", "1");
			List<Collateral> listCollaterals=collateralService.queryList(map);
			double db=0;
			for (Collateral col : listCollaterals) {
				if ("1".equals(col.getIsValueChange())) {
					db=MathUtils.add(db, col.getLatestPrice());
				}else {
					db=MathUtils.add(db, col.getEvalPrice());
				}
			}
			if (MathUtils.sub(db,loanBase.getLoanAmt())>=0) {
				loanBase.setIsEnough("1");
			}
			
		}else {//续贷的
			map.clear();
			map.put("origLoanId", loanBase.getOrigLoanId());
			map.put("loanId", loanBase.getLoanId());
			map.put("state", "1");
			List<Collateral> listCollaterals=collateralService.queryListByLoanId(map);
			double db=0;
			for (Collateral col : listCollaterals) {
				if ("1".equals(col.getIsValueChange())) {
					db=MathUtils.add(db, col.getLatestPrice());
				}else {
					db=MathUtils.add(db, col.getEvalPrice());
				}
				
			}
			if (MathUtils.sub(db,loanBase.getLoanAmt())>=0) {
				loanBase.setIsEnough("1");
			}
		}
		
		// 更新loanBase
		loanBase.setState1st(state1st);
		loanBase.setState2nd(state2nd);
		loanBase.setBranchAuditTime(nowTime);
		loanBase.setUpdateTime(nowTime);
		loanBase.setUpdateUid(operator);
		this.updateOnlyChanged(loanBase);
		
		return new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), true, "成功");
	}
}
