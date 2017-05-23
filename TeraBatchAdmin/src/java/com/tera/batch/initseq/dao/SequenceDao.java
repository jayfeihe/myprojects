package com.tera.batch.initseq.dao;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.tera.batch.initseq.model.Sequence;

/**
 * 
 * <br>
 * <b>功能：</b>BatchErrorLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-07-11 16:12:19<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface SequenceDao {	
		
	
	public void update(Sequence t);

	public List<Sequence> queryList(Map<String, Object> map);

}
