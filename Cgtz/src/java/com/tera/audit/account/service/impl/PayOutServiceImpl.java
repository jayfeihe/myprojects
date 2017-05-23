package com.tera.audit.account.service.impl;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.dao.PayOutDao;
import com.tera.audit.account.model.BillBase;
import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.account.model.form.PayOutQBean;
import com.tera.audit.account.service.IBillBaseService;
import com.tera.audit.account.service.IPayOutService;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.service.MybatisBaseService;

@Service("payOutService")
public class PayOutServiceImpl extends MybatisBaseService<PayOutQBean> implements IPayOutService {

	@Autowired
	private IBillBaseService billBaseService;

	@Override
	public PageList<PayOutQBean> queryPageList(Map<String, Object> params) {
		return this.selectPageList(PayOutDao.class, "queryList", params);
	}
	
	@Override
	public JsonMsg payOut(AccountFormBean bean, String loginId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		String loanId = bean.getLoanId();
		String contractId = bean.getContractId();
		double amt = bean.getAmt();
		String remark = bean.getRemark();
		String proof = bean.getProof();
		int payOutNum = bean.getPayOutNum();
		
		// 公司账户记账
		BillBase base = new BillBase();
		base.setLoanId(loanId);
		base.setContractId(contractId);
		base.setAmt(amt);
		base.setNum(payOutNum);// 垫付期数
		base.setProof(proof);
		base.setRemark(remark);
		base.setType("2"); // 付
		base.setSubject("4"); // 付线上垫付
		base.setState("1");
		base.setCreateTime(nowTime);
		base.setCreateUid(loginId);
		this.billBaseService.add(base);
		
		return new JsonMsg(true, "垫付成功");
	}
}
