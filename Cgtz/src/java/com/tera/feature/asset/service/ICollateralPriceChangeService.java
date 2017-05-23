package com.tera.feature.asset.service;

import java.util.List;
import java.util.Map;



import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.asset.model.CollateralPriceChange;

/**
 * 
 * 价值变动表服务类
 * <b>功能：</b>CollateralPriceChangeDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-12 11:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface ICollateralPriceChangeService {


	public void add(CollateralPriceChange... objs)  throws Exception;
	
	
	public void update(CollateralPriceChange obj)  throws Exception;
	

	public void updateOnlyChanged(CollateralPriceChange obj)  throws Exception ;
	

	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<CollateralPriceChange> queryList(Map<String, Object> map) throws Exception;

	public CollateralPriceChange queryByKey(Object id) throws Exception ;


	PageList<CollateralPriceChange> queryPageList(Map<String, Object> params);
	
}
