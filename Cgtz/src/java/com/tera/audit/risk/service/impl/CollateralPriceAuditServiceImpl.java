package com.tera.audit.risk.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.risk.dao.CollateralPriceAuditDao;
import com.tera.audit.risk.model.CollateralPriceAudit;
import com.tera.audit.risk.service.ICollateralPriceAuditService;

/**
 * 
 * 核价表服务类
 * <b>功能：</b>CollateralPriceAuditDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-09 18:57:07<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collateralPriceAuditService")
public class CollateralPriceAuditServiceImpl implements ICollateralPriceAuditService {

	private final static Logger log = Logger.getLogger(CollateralPriceAuditServiceImpl.class);

	@Autowired(required=false)
    private CollateralPriceAuditDao dao;
	@Autowired
	private ICollateralService collateralService;

	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.ICollateralPriceAuditService#add(com.tera.audit.risk.model.CollateralPriceAudit)
	 */
	@Override
	@Transactional
	public void add(CollateralPriceAudit... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CollateralPriceAudit obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.ICollateralPriceAuditService#update(com.tera.audit.risk.model.CollateralPriceAudit)
	 */
	@Override
	@Transactional
	public void update(CollateralPriceAudit obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.ICollateralPriceAuditService#updateOnlyChanged(com.tera.audit.risk.model.CollateralPriceAudit)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(CollateralPriceAudit obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.ICollateralPriceAuditService#delete(java.lang.Object)
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
	 * @see com.tera.audit.risk.service.ICollateralPriceAuditService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.ICollateralPriceAuditService#queryList(java.util.Map)
	 */
	@Override
	public List<CollateralPriceAudit> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.ICollateralPriceAuditService#queryByKey(java.lang.Object)
	 */
	@Override
	public CollateralPriceAudit queryByKey(Object id) throws Exception {
		return (CollateralPriceAudit)dao.queryByKey(id);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.ICollateralPriceAuditService#queryByCollateralId(java.lang.String)
	 */
	@Override
	public List<CollateralPriceAudit> queryByCollateralId(String collateralId) {
		return dao.queryByCollateralId(collateralId);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.ICollateralPriceAuditService#queryLatestByCollateralId(java.lang.String)
	 */
	@Override
	public CollateralPriceAudit queryLatestByCollateralId(String collateralId) {
		return dao.queryLatestByCollateralId(collateralId);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.ICollateralPriceAuditService#saveOrUpdate(com.tera.audit.loan.model.Collateral, java.lang.String)
	 */
	@Override
	@Transactional
	public void saveOrUpdate(Collateral col, String loginId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis()); 
		Collateral collateral = this.collateralService.queryByKey(col.getId());
		collateral.setAuditPriceState(col.getAuditPriceState());
		collateral.setAuditPriceRemark(col.getAuditPriceRemark());
		collateral.setLatestPrice(col.getLatestPrice());
		collateral.setUpdateTime(nowTime);
		collateral.setUpdateUid(loginId);
		this.collateralService.update(collateral);
		
		// 保存核价历史
		CollateralPriceAudit pAudit = new CollateralPriceAudit();
		pAudit.setCollateralId(col.getId());
		pAudit.setAmt(col.getLatestPrice());
		pAudit.setResult(col.getAuditPriceState());
		pAudit.setRemark(col.getAuditPriceRemark());
		pAudit.setOperTime(nowTime);
		pAudit.setOperUid(loginId);
		this.add(pAudit);
		
	}
}
