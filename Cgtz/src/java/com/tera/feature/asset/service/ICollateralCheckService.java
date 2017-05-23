package com.tera.feature.asset.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.asset.model.CollateralCheck;

/**
 * 
 * 押品检查信息记录
服务类
 * <b>功能：</b>CollateralCheckDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-12 10:11:56<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ICollateralCheckService {

	

	
	public void add(CollateralCheck... objs)  throws Exception ;
	
	
	public void update(CollateralCheck obj)  throws Exception ;
	
	public void updateOnlyChanged(CollateralCheck obj)  throws Exception ;
	

	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<CollateralCheck> queryList(Map<String, Object> map) throws Exception ;

	public CollateralCheck queryByKey(Object id) throws Exception ;


	public PageList<CollateralCheck> queryPageList(Map<String, Object> params);
	
}
