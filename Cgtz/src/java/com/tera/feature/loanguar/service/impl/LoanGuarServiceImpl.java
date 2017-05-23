package com.tera.feature.loanguar.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.loanguar.dao.LoanGuarDao;
import com.tera.feature.loanguar.model.LoanGuar;
import com.tera.feature.loanguar.service.ILoanGuarService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * T_LOAN_GUAR服务类
 * <b>功能：</b>LoanGuarDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-06 13:51:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanGuarService")
public class LoanGuarServiceImpl extends MybatisBaseService<LoanGuar> implements ILoanGuarService{

	private final static Logger log = Logger.getLogger(LoanGuarServiceImpl.class);

	@Autowired(required=false)
    private LoanGuarDao dao;
	@Override
	@Transactional
	public void add(LoanGuar... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(LoanGuar obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(LoanGuar obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(LoanGuar obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
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
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	@Override
	public List<LoanGuar> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public LoanGuar queryByKey(Object id) throws Exception {
		return (LoanGuar)dao.queryByKey(id);
	}
	
	@Override
	public PageList<LoanGuar> queryPageList(Map<String, Object> params) {
		return this.selectPageList(LoanGuarDao.class, "queryListByLoanId", params);
	}
}
