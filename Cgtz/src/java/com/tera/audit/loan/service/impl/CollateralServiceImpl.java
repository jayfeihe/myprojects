package com.tera.audit.loan.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.dao.CollateralDao;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.form.CollateralJsonMsg;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.feature.warehouse.model.Warehouse;
import com.tera.feature.warehouse.service.IWarehouseService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 质押、抵押物信息服务类
 * <b>功能：</b>CollateralDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collateralService")
public class CollateralServiceImpl extends MybatisBaseService<Collateral> implements ICollateralService {

	private final static Logger log = Logger.getLogger(CollateralServiceImpl.class);

	@Autowired(required=false)
    private CollateralDao dao;
	@Autowired(required=false)
	private IWarehouseService warehouseService;

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ICollateralService#add(com.tera.audit.loan.model.Collateral)
	 */
	@Override
	@Transactional
	public void add(Collateral... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Collateral obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ICollateralService#update(com.tera.audit.loan.model.Collateral)
	 */
	@Override
	@Transactional
	public void update(Collateral obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ICollateralService#updateOnlyChanged(com.tera.audit.loan.model.Collateral)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(Collateral obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ICollateralService#updatePriceInfo(com.tera.audit.loan.model.Collateral)
	 */
	@Override
	@Transactional
	public void updatePriceInfo(Collateral obj) throws Exception {
		dao.updatePriceInfo(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ICollateralService#delete(java.lang.Object)
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
	 * @see com.tera.audit.loan.service.ICollateralService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ICollateralService#queryList(java.util.Map)
	 */
	@Override
	public List<Collateral> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	
	@Override
	public List<Collateral> queryListByLoanId(Map<String, Object> map) {
		return this.dao.queryListByLoanId(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ICollateralService#queryPageList(java.util.Map)
	 */
	@Override
	public PageList<Collateral> queryPageList(Map<String, Object> params) {
		return this.selectPageList(CollateralDao.class, "queryListByLoanId", params);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ICollateralService#queryByKey(java.lang.Object)
	 */
	@Override
	public Collateral queryByKey(Object id) throws Exception {
		return (Collateral)dao.queryByKey(id);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ICollateralService#saveOrUpdate(com.tera.audit.loan.model.Collateral, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public CollateralJsonMsg saveOrUpdate(Collateral bean, String loginId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		// 资产所在地
		String assetAddr = "";
				
		if (Collateral.TYPE_HOUSE.equals(bean.getType())) {
			assetAddr = bean.getPrvn() + bean.getCity() + bean.getCtry() + bean.getAddr();
		} else {
			Warehouse warehouse = this.warehouseService.queryByKey(bean.getWarehouseId());
			assetAddr = warehouse.getPrvn() + warehouse.getCity() + bean.getWarehouseName();
		}
		bean.setAssetAddr(assetAddr);
		bean.setUpdateTime(nowTime);
		bean.setUpdateUid(loginId);
		//如果存在
		if (bean.getId() != 0) {
			//保留以前的数据
			Collateral coll=dao.queryByKey(bean.getId());
			bean.setAuditPriceState(coll.getAuditPriceState());
			bean.setAuditPriceRemark(coll.getAuditPriceRemark());
			bean.setAuditPriceTime(coll.getAuditPriceTime());
			bean.setAuditPriceUid(coll.getAuditPriceUid());
			
			bean.setIsValueChange(coll.getIsValueChange());
			bean.setChangeRemark(coll.getChangeRemark());
			bean.setChangeTime(coll.getChangeTime());
			bean.setChangeUid(coll.getChangeUid());
			bean.setLatestCheck(coll.getLatestCheck());
			bean.setCheckRemark(coll.getCheckRemark());
			bean.setCheckTime(coll.getCheckTime());
			bean.setCheckUid(coll.getCheckUid());
			bean.setSellAmt(coll.getSellAmt());
			bean.setSellInputUid(coll.getSellInputUid());
			bean.setSellOrg(coll.getSellOrg());
			bean.setSellRemark(coll.getSellRemark());
			bean.setSellTime(coll.getSellTime());
			bean.setSellWay(coll.getSellWay());
			bean.setState(coll.getState()); // 
		
			this.updateOnlyChanged(bean);
		} else { 
			//如果不存在
			bean.setAuditPriceState("0"); // 最新核价结果，默认未处理
			bean.setIsValueChange("0"); // 价值是否变动，默认否
			bean.setState("1"); // 处置状态，默认正常
			bean.setLatestCheck("0");
			bean.setCreateTime(nowTime);
			bean.setCreateUid(loginId);
			this.add(bean);
		}
		
		return new CollateralJsonMsg(bean.getId(), bean.getLoanId(), bean.getType(), true, "成功");
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ICollateralService#clearPriceInfo(java.lang.String, java.lang.String, java.sql.Timestamp)
	 */
	@Override
	@Transactional
	public void clearPriceInfo(String loanId,String loginId,Timestamp nowTime) throws Exception {
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("loanId", loanId);
		List<Collateral> collateralList = this.queryList(queryMap);
		if (collateralList != null && collateralList.size() > 0) {
			for (Collateral col : collateralList) {
				col.setAuditPriceState("0"); // 设为未处理
				col.setAuditPriceRemark(null);
				col.setAuditPriceUid(null);
				col.setAuditPriceTime(null);
				col.setUpdateTime(nowTime);
				col.setUpdateUid(loginId);
				this.updatePriceInfo(col);
			}
		}
		
	}
}
