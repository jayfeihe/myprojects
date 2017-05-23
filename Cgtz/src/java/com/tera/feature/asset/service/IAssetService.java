package com.tera.feature.asset.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.asset.model.Asset;

/**
 * 
 * 质押、抵押物信息接口类
 * <b>功能：</b>CollateralDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface IAssetService {

	

	
	public void add(Asset... objs);
	
	
	public void update(Asset obj) throws Exception;
	
	
	public void updateOnlyChanged(Asset obj) throws Exception;
	
	@Transactional
	public void delete(Object... ids) throws Exception;
	
	public int queryCount(Map<String, Object> map) throws Exception;
	
	public List<Asset> queryList(Map<String, Object> map) throws Exception;

	public Asset queryByKey(Object id);

	/**
	 * 保存更新
	 * @param bean
	 * @param loginId
	 * @param orgId
	 * @throws Exception 
	 * @throws Exception
	 */
	public void saveOrUpdate(Asset bean, String loginId, String orgId) throws Exception;

	public PageList<Asset> queryPageList(Map<String, Object> params);
}
