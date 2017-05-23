package com.tera.loanconsult.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.loanconsult.dao.LoanConsultDao;
import com.tera.loanconsult.model.LoanConsult;
import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>LoanConsultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-30 14:50:36<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanConsultService")
public class LoanConsultService<T> {

	private final static Logger log = Logger.getLogger(LoanConsultService.class);

	@Autowired
    private LoanConsultDao<T> dao;

	@Transactional
	public void add(T t)  throws Exception {
		dao.add(t);
	}
	
	@Transactional
	public void update(T t)  throws Exception {
		dao.update(t);
	}

	@Transactional
	public void updateOnlyChanged(T t)  throws Exception {
		dao.updateOnlyChanged(t);
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

	@SuppressWarnings("unchecked")
	public List<T> queryList(Map<String, Object> map) throws Exception {

		List<LoanConsult> loanConsultList = (List<LoanConsult>) dao.queryList(map);
		for (LoanConsult loanConsult : loanConsultList) {
			Date startTime = loanConsult.getStartTime(); //开始日期
			Date endTime = loanConsult.getEndTime(); //结束日期
			String days = "";
			if (startTime != null && endTime != null) {
				days = DateUtils.getDayRange(startTime, endTime) + "";
			}
			if (loanConsult.getType().equals("02")) {
				loanConsult.setType("机构");
			}
			if (loanConsult.getType().equals("01")) {
				loanConsult.setType("个人");
			}
			loanConsult.setDays(days);
		}
		return (List<T>) loanConsultList;
	}

	public T queryByKey(Object id) throws Exception {
		return (T)dao.queryByKey(id);
	}
	
}
