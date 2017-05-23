package com.tera.house.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tera.sys.service.MybatisBaseService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import com.tera.house.dao.HouseInfoDao;
import com.tera.house.model.HouseInfo;

/**
 * 
 * 房产信息表服务类
 * <b>功能：</b>HouseInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-20 17:01:08<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseInfoService")
public class HouseInfoService extends MybatisBaseService<HouseInfo> {

	private final static Logger log = Logger.getLogger(HouseInfoService.class);

	@Autowired(required=false)
    private HouseInfoDao dao;

	@Transactional
	public void add(HouseInfo... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(HouseInfo obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(HouseInfo obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(HouseInfo obj)  throws Exception {
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
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<HouseInfo> queryList(Map<String, Object> map ) {
		return dao.queryList(map);
	}
	
	/**
	 * 分页查询
	 * @param params
	 * 				key
	 * 					curPage   当前页数
	 * 					pageSize  每页条数
	 * @return
	 */
	public PageList<HouseInfo> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(HouseInfoDao.class, "queryList", params);
	}

	public HouseInfo queryByKey(Object id) throws Exception {
		return (HouseInfo)dao.queryByKey(id);
	}
	
}
