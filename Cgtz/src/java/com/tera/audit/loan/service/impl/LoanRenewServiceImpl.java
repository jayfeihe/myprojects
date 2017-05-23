package com.tera.audit.loan.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.loan.dao.LoanRenewDao;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.audit.loan.model.form.LoanRenewQBean;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.loan.service.ILoanRenewService;
import com.tera.bpm.service.ProcessService;
import com.tera.sys.model.Org;
import com.tera.sys.service.MybatisBaseService;
import com.tera.sys.service.OrgService;


/**
 * 
 * T_LOAN_BASE服务类
 * <b>功能：</b>LoanBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:52:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanRenewService")
public class LoanRenewServiceImpl extends MybatisBaseService<LoanRenewQBean> implements ILoanRenewService {

	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private OrgService orgService;
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanRenewService#queryPageList(java.util.Map)
	 */
	@Override
	public PageList<LoanRenewQBean> queryPageList(Map<String, Object> params) {
		return this.selectPageList(LoanRenewDao.class, "queryList", params);
	}

	@Override
	@Transactional
	public LoanBaseJsonMsg loanRenewProcess(AppFormBean formBean, String loginId, String orgId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
//		String buttonType = formBean.getButtonType();
		
		// 保存实体
		LoanBase loanBase = this.saveOrUpdate(formBean, loginId, orgId, nowTime);
		
		LoanBaseJsonMsg jsonMsg = new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), true, "成功");
		
		/*if (!"save".equals(buttonType)) {
			// 流程跳转,状态更新
			jsonMsg = this.workFlow(loanBase, buttonType, loginId, orgId, nowTime);
		}*/
		
		return jsonMsg;
	}

	/**
	 * 保存续贷申请
	 * @param formBean
	 * @param loginId
	 * @param orgId
	 * @param nowTime
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private LoanBase saveOrUpdate(AppFormBean formBean, String loginId, String orgId, Timestamp nowTime) throws Exception {
		
		Org tmpOrg=orgService.queryByOrgId(orgId);
		String strLoanIdCreate="";
		if (tmpOrg.getCode()!=null) {
			strLoanIdCreate=tmpOrg.getCode();
		}
		//生成申请号 规则：机构代码+日期时间+一位随机数
		 strLoanIdCreate=strLoanIdCreate+DateUtils.format(new Date(), "yyyyMMddHHmmss")+new Random().nextInt(10);
		LoanBase loanBase = formBean.getLoanBase();
		// 原来的申请
		LoanBase origLoanBase = this.loanBaseService.queryByLoanId(loanBase.getOrigLoanId());
		// 原申请续贷标志改为被续贷
		origLoanBase.setIsRenew("2");
		origLoanBase.setUpdateTime(nowTime);
		origLoanBase.setUpdateUid(loginId);
		this.loanBaseService.update(origLoanBase);
		
		loanBase.setUpdateTime(nowTime);
		loanBase.setUpdateUid(loginId);
//		if (0 == loanBase.getId()) {
			loanBase.setLoanId(strLoanIdCreate);
			loanBase.setLoanWay(origLoanBase.getLoanWay());
			loanBase.setLoanType(origLoanBase.getLoanType());
			loanBase.setName(origLoanBase.getName());
			loanBase.setIdType(origLoanBase.getIdType());
			loanBase.setIdNo(origLoanBase.getIdNo());
			loanBase.setProduct(origLoanBase.getProduct());
			loanBase.setLoanUse(origLoanBase.getLoanUse());
			loanBase.setAcctName(origLoanBase.getAcctName());
			loanBase.setExt2(origLoanBase.getExt2());
			loanBase.setAcctPrvn(origLoanBase.getAcctPrvn());
			loanBase.setAcctCity(origLoanBase.getAcctCity());
			loanBase.setAcctBank(origLoanBase.getAcctBank());
			loanBase.setAcctBranch(origLoanBase.getAcctBranch());
			loanBase.setAcctCode(origLoanBase.getAcctCode());
			loanBase.setIsTgth(origLoanBase.getIsTgth());
			loanBase.setIsRenew("1"); // 是续贷申请
			loanBase.setRenewNum(this.getRenewNum(origLoanBase.getLoanId()));
			loanBase.setOrigLoanId(origLoanBase.getLoanId());
			loanBase.setInputTime(nowTime);
			loanBase.setSalesman(loginId);
			loanBase.setOrg(orgId);
			loanBase.setIsBeyond("0"); // 是否超出审批额度默认为否
			loanBase.setState1st("A");
			loanBase.setState2nd("1");
			this.loanBaseService.add(loanBase);
			
			// 流程启动
			this.processService.startProcessInstanceByName(CommonConstant.AUDIT_PROCESS_NAME, loanBase.getLoanId(), loginId);
//		} else {
//			this.loanBaseService.updateOnlyChanged(loanBase);
//		}
		return loanBase;
	}

	/**
	 * 流程操作
	 * @param loanBase
	 * @param buttonType
	 * @param operator
	 * @param orgId
	 * @param nowTime
	 * @return
	 * @throws Exception
	 */
	/*@Transactional
	private LoanBaseJsonMsg workFlow(LoanBase loanBase, String buttonType, String operator, String orgId,
			Timestamp nowTime) throws Exception {
		String nextUser = "";
		String state1st = null,state2nd = null;
		
		// 提交
		if ("submit".equals(buttonType)) {
			// 获取下一操作人
			 String prevUser = this.commonHandlerService.getPrevUser(loanBase.getLoanId(), CommonConstant.PROCESS_B);
			if (StringUtils.isNotNullAndEmpty(prevUser)) {
				nextUser = prevUser;
			} else {
				nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_BRAN_MGR, loanBase.getOrg());
			}
			
			if (StringUtils.isNotNullAndEmpty(nextUser)) {
				// 跳转到分公司审批
				this.commonHandlerService.workFlow(loanBase.getLoanId(), CommonConstant.PROCESS_B, nextUser, operator, null, null);
				state1st = "B";
				state2nd = "1";
			} else {
				return new LoanBaseJsonMsg(loanBase.getId(),loanBase.getLoanId(),false, CommonConstant.PROCESS_B + "没有相关操作人，提交申请失败！");
			}
		}

		// 客户放弃
		if ("giveUp".equals(buttonType)) {
			this.commonHandlerService.workFlow(loanBase.getLoanId(), CommonConstant.PROCESS_K, nextUser, operator, "客户放弃", null);
			state1st = "K";
			state2nd = "0";
		}
		
		// 更新loanBase
		loanBase.setState1st(state1st);
		loanBase.setState2nd(state2nd);
		loanBase.setBranchAuditTime(nowTime);
		loanBase.setUpdateTime(nowTime);
		loanBase.setUpdateUid(operator);
		this.loanBaseService.updateOnlyChanged(loanBase);
		
		return new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), true, "成功");
	}*/
	
	/**
	 * 获取续贷次数
	 * @param origLoanId
	 * @return
	 * @throws Exception
	 */
	private int getRenewNum(String origLoanId) throws Exception {
		int renewNum = 1;
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("origLoanId", origLoanId);
		List<LoanBase> renewBases = this.loanBaseService.queryList(queryMap);
		if (renewBases != null) {
			renewNum = renewBases.size() + 1;
		}
		return renewNum;
	}
}
