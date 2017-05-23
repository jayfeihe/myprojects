package com.tera.feature.asset.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.asset.dao.AssetDao;
import com.tera.feature.asset.model.Asset;
import com.tera.feature.asset.service.IAssetService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 质押、抵押物信息服务类
 * <b>功能：</b>CollateralDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("assetService")
public class AssetServiceImpl extends MybatisBaseService<Asset> implements  IAssetService{

	private final static Logger log = Logger.getLogger(AssetServiceImpl.class);

	@Autowired(required=false)
    private AssetDao dao;

	@Override
	public void add(Asset... objs) {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Asset obj : objs ){
			dao.add(obj);
		}
	}
	
	@Override
	public void update(Asset obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(Asset obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	@Override
	public List<Asset> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public Asset queryByKey(Object id) {
		return (Asset)dao.queryByKey(id);
	}

	/**
	 * 保存更新
	 * @param bean
	 * @param loginId
	 * @param orgId
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void saveOrUpdate(Asset bean, String loginId, String orgId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		// 资产所在地
		String assetAddr = "";
				
		if (Asset.TYPE_HOUSE.equals(bean.getType())) {
			assetAddr = bean.getPrvn() + bean.getCity() + bean.getCtry() + bean.getAddr();
		} else {
			assetAddr = bean.getWarehousePrvn() + bean.getWarehouseCity() + bean.getWarehouseName();
		}
		bean.setAssetAddr(assetAddr);
		bean.setUpdateTime(nowTime);
		bean.setUpdateUid(loginId);
		//如果存在
		if (bean.getId() != 0) {
			this.updateOnlyChanged(bean);
		} else { 
			//如果不存在
			bean.setState("0"); // 未处置
			bean.setCreateTime(nowTime);
			bean.setCreateUid(loginId);
			this.add(bean);
		}
	}

	@Override
	public PageList<Asset> queryPageList(Map<String, Object> params) {
		return this.selectPageList(AssetDao.class, "queryList", params);
	}
	
}
