package com.tera.car.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tera.sys.service.MybatisBaseService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import com.tera.car.dao.CarInfoDao;
import com.tera.car.model.CarInfo;

/**
 * 
 * 车辆信息表服务类
 * <b>功能：</b>CarInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-11 17:13:38<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carInfoService")
public class CarInfoService extends MybatisBaseService<CarInfo> {

	private final static Logger log = Logger.getLogger(CarInfoService.class);

	@Autowired(required=false)
    private CarInfoDao dao;

	@Transactional
	public void add(CarInfo... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CarInfo obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CarInfo obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CarInfo obj)  throws Exception {
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
	
	public List<CarInfo> queryList(Map<String, Object> map) {
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
	public PageList<CarInfo> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(CarInfoDao.class, "queryList", params);
	}

	public CarInfo queryByKey(Object id) throws Exception {
		return (CarInfo)dao.queryByKey(id);
	}
	
}
