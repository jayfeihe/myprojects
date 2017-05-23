package com.tera.audit.judge.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.judge.dao.JudgeAdvDao;
import com.tera.audit.judge.model.JudgeAdv;
import com.tera.audit.judge.service.IJudgeAdviceService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.sys.model.JsonMsg;


/** 评审会意见Service
 * @author QYANZE
 *
 */
@Service("judgeAdviceService")
public class JudgeAdviceServiceImpl implements IJudgeAdviceService {

	@Autowired
	private ILoanBaseService loanBaseService;

	@Autowired(required=false)
    private JudgeAdvDao dao;

	/* (non-Javadoc)
	 * @see com.tera.audit.judge.service.IJudgeAdviceService#add(com.tera.audit.judge.model.JudgeAdv)
	 */
	@Override
	@Transactional
	public void add(JudgeAdv... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(JudgeAdv obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.judge.service.IJudgeAdviceService#update(com.tera.audit.judge.model.JudgeAdv)
	 */
	@Override
	@Transactional
	public void update(JudgeAdv obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.judge.service.IJudgeAdviceService#updateOnlyChanged(com.tera.audit.judge.model.JudgeAdv)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(JudgeAdv obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.judge.service.IJudgeAdviceService#delete(java.lang.Object)
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
	 * @see com.tera.audit.judge.service.IJudgeAdviceService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.judge.service.IJudgeAdviceService#queryList(java.util.Map)
	 */
	@Override
	public List<JudgeAdv> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.judge.service.IJudgeAdviceService#queryByKey(java.lang.Object)
	 */
	@Override
	public JudgeAdv queryByKey(Object id) throws Exception {
		return (JudgeAdv)dao.queryByKey(id);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.judge.service.IJudgeAdviceService#saveJudgeAdv(com.tera.audit.judge.model.JudgeAdv)
	 */
	@Override
	@Transactional
	public void saveJudgeAdv(JudgeAdv jAdv) throws Exception {
		jAdv.setAuditState("1"); //未处理
		jAdv.setState("1"); //有效
		this.add(jAdv);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.judge.service.IJudgeAdviceService#getNextNum()
	 */
	@Override
	public int getNextNum(String loanId) {
		int num = 1;
		Integer tmpNum = dao.getNum(loanId);
		if (tmpNum != null) {
			num = tmpNum + 1;
		} 
		return num;
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.judge.service.IJudgeAdviceService#operateProcess(com.tera.audit.judge.model.JudgeAdv, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public JsonMsg operateProcess(JudgeAdv bean, String loginId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		// 更新评审会意见
		bean.setAuditState("2"); // 已处理
		bean.setAuditTime(nowTime);
		bean.setAuditUid(loginId);
		this.updateOnlyChanged(bean);
		
		String loanId = bean.getLoanId();
		// 判断是否都处理完毕
		boolean isFinished =  this.isAllFinished(loanId,loginId);
		
		if (isFinished) {
			LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
			// 更新loanBase信息
			loanBase.setState1st("E");
			loanBase.setState2nd("2");
			loanBase.setUpdateTime(nowTime);
			loanBase.setUpdateUid(loginId);
			this.loanBaseService.updateOnlyChanged(loanBase);
		}
		return new JsonMsg(true,"成功");
	}

	/**
	 * 判断是否全部评审处理过
	 * @param loanId
	 * @param loginId
	 * @return
	 * @throws Exception
	 */
	private boolean isAllFinished(String loanId,String loginId) throws Exception {
		boolean isFinished = false;
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("loanId", loanId);
		queryMap.put("num", this.getNextNum(loanId)-1);
		List<JudgeAdv> advs = this.queryList(queryMap );
		if (advs != null && advs.size() > 0) {
			for (JudgeAdv adv : advs) {
				if (loginId.equals(adv.getAuditUid())) {
					isFinished = true;
					continue;
				} else {
					if (!"2".equals(adv.getAuditState())) {
						isFinished = false;
						break;
					} else {
						isFinished = true;
					}
				}
			}
		}
		return isFinished;
	}
}
