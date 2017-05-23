package com.tera.audit.loan.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.form.CollateralJsonMsg;

public interface ICollateralService {

	void add(Collateral... objs) throws Exception;

	void update(Collateral obj) throws Exception;

	void updateOnlyChanged(Collateral obj) throws Exception;

	void updatePriceInfo(Collateral obj) throws Exception;

	void delete(Object... ids) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<Collateral> queryList(Map<String, Object> map) throws Exception;
	
	PageList<Collateral> queryPageList(Map<String, Object> params);

	Collateral queryByKey(Object id) throws Exception;

	/**
	 * 保存更新
	 * @param bean
	 * @param loginId
	 * @param orgId
	 * @return 
	 * @throws Exception
	 */
	CollateralJsonMsg saveOrUpdate(Collateral bean, String loginId) throws Exception;

	/**
	 * 清除抵押物表中核价信息
	 * @param loanId
	 * @param loginId
	 * @param nowTime
	 * @throws Exception
	 */
	void clearPriceInfo(String loanId, String loginId, Timestamp nowTime) throws Exception;

	List<Collateral> queryListByLoanId(Map<String, Object> map);

}