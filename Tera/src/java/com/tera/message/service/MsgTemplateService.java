package com.tera.message.service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.message.dao.MsgTemplateDao;
import com.tera.message.model.MsgTemplate;

/**
 * 
 * 模板表服务类
 * <b>功能：</b>TemplateDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-30 11:58:07<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("msgTemplateService")
public class MsgTemplateService {

	private final static Logger log = Logger.getLogger(MsgTemplateService.class);

	@Autowired(required=false)
    private MsgTemplateDao dao;

	@Transactional
	public void add(MsgTemplate... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(MsgTemplate obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(MsgTemplate obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(MsgTemplate obj)  throws Exception {
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
	
	public List<MsgTemplate> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public MsgTemplate queryByKey(Object id) throws Exception {
		return (MsgTemplate)dao.queryByKey(id);
	}
	
	/**
	 * 获取模板集合
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, MsgTemplate> getTemplateMap() throws Exception {
		List<MsgTemplate> list = dao.queryList(null);
		Map<Integer, MsgTemplate> map = new HashMap<Integer, MsgTemplate>();
		if(list!=null&&!list.isEmpty()){
			for (MsgTemplate template : list) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, template.getRemindTime().getHours());
				cal.set(Calendar.MINUTE, template.getRemindTime().getMinutes());
				cal.set(Calendar.SECOND, template.getRemindTime().getSeconds());
				template.setRemindTime(new Timestamp(cal.getTimeInMillis()));
				map.put(template.getId(), template);
			}
		}
		return map;
	}
	
	/**
	 * 获取短信内容
	 * @param id 模板ID
	 * @param obj 模板参数
	 * @return
	 * @throws Exception
	 */
	public String getMsgContent(int id, Object... obj) throws Exception {
		MsgTemplate template = queryByKey(id);
		return getMsgContent(template, obj);
	}

	/**
	 * 获取短信内容
	 * @param template 模板对象
	 * @param obj 模板参数
	 * @return
	 * @throws Exception
	 */
	public String getMsgContent(MsgTemplate template, Object... obj) throws Exception {
		String str = "";
		if (template != null) {
			str = MessageFormat.format(template.getContent(), obj);
		} else {
			log.info("模板对象为空");
		}
		return str;
	}

	/**
	 * 根据模板类型查询获取有效的模板对象
	 * @param type 模板类型
	 * @return
	 * @throws Exception
	 */
	public MsgTemplate queryByType(Object type) throws Exception {
		return (MsgTemplate)dao.queryByType(type);
	}
}
