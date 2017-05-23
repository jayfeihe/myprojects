package com.tera.audit.risk.service;

import java.util.List;
import java.util.Map;

import com.tera.audit.loan.model.Collateral;
import com.tera.audit.risk.model.CollateralPriceAudit;

public interface ICollateralPriceAuditService {

	void add(CollateralPriceAudit... objs) throws Exception;

	void update(CollateralPriceAudit obj) throws Exception;

	void updateOnlyChanged(CollateralPriceAudit obj) throws Exception;

	void delete(Object... ids) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<CollateralPriceAudit> queryList(Map<String, Object> map) throws Exception;

	CollateralPriceAudit queryByKey(Object id) throws Exception;

	/**
	 * 根据抵押物ID获取核价历史信息
	 * @param collateralId
	 * @return
	 */
	List<CollateralPriceAudit> queryByCollateralId(String collateralId);

	/**
	 * 根据抵押物ID获取最新核价历史信息
	 * @param collateralId
	 * @return
	 */
	CollateralPriceAudit queryLatestByCollateralId(String collateralId);

	/**
	 * 更新抵押物核价信息,保存核价历史
	 * @param col
	 * @param loginId
	 * @throws Exception
	 */
	void saveOrUpdate(Collateral col, String loginId) throws Exception;

}