package com.tera.sys.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sys.dao.WorkdayDao;
import com.tera.sys.model.Workday;
import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>WorkdayDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-14 16:31:40<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("workdayService")
public class WorkdayService<T> {

	private final static Logger log = Logger.getLogger(WorkdayService.class);

	@Autowired(required=false)
    private WorkdayDao<T> dao;

	@Transactional
	public void add(T... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(T t : ts ){
			dao.add(t);
		}
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
	
	public List<T> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public T queryByKey(Object id) throws Exception {
		return (T)dao.queryByKey(id);
	}

	public boolean isWorkDay(Date date) throws Exception {
		if (date == null) {
			return false;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//		System.out.println("dayOfWeek:"+dayOfWeek);
		//周一到周五-上班的情况
		if (dayOfWeek >= 1 && dayOfWeek <= 5) {
			boolean result = true;
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("day", date);
			List<Workday> workdayList = (List<Workday>) this.queryList(queryMap);
			for (Workday workday : workdayList) {
				//1 上班 0休息
				if ("0".equals(workday.getWork())) {
					return false;
				}
			}
			return result;
		}
		//周六日-不上班的情况
		if (dayOfWeek == 0 || dayOfWeek >= 6) {
			boolean result = false;
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("day", date);
			List<Workday> workdayList = (List<Workday>) this.queryList(queryMap);
			for (Workday workday : workdayList) {
				//1 上班 0休息
				if ("1".equals(workday.getWork())) {
					return true;
				}
			}
			return result;
		}
		return false;
	}
	
	/**
	 * 根据一个日期计算出num个工作日之后的日期   最后一天 可以不是工作日
	 * @param date
	 * @param num
	 * @return
	 * @throws Exception
	 */
		public Date afterWorkDay(Date date,int num) throws Exception {
			Date dtReturn=date;
			//先让Date是工作日
			/*while (!isWorkDay(dtReturn)) {
				dtReturn = DateUtils.addDay(dtReturn, 1);
			}*/
			
			int gzr=1;
			while (gzr<=num) {
				dtReturn = DateUtils.addDay(dtReturn, 1);
				if (isWorkDay(dtReturn)) {
//					System.out.println(DateUtils.toTimeString(dtReturn) + "是工作日！！");
					gzr++;
				}else if((gzr==num)){
					gzr++;
				}
			}
			return dtReturn;
		}

}
