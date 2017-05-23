package com.hikootech.mqcash.dao;

import com.hikootech.mqcash.po.UserHdBxdjgcxjl;
import com.hikootech.mqcash.po.UserHdDkcfsq;
import com.hikootech.mqcash.po.UserHdGrbzxggcx;
import com.hikootech.mqcash.po.UserHdRequest;
import com.hikootech.mqcash.po.UserHdShanyinSyhmd;
import com.hikootech.mqcash.po.UserHdSxbzxrcx;
import com.hikootech.mqcash.vo.HaoDaiVo;

public interface HaoDaiDao {

	/**  
	 * insertHaoDaiRecord(好贷网插入数据)  
	 * @param haoDaiVo   
	 * void 
	 * @return haoDaiVo
	 * @create time： Sep 15, 2015 4:58:19 PM 
	 * @author：李擎 
	 * @since  1.0.0
	 */
	
	public void insertHaoDaiRecord(HaoDaiVo haoDaiVo);

	public void addhdRequest(UserHdRequest hdRequest);

	public void addhdBxdjgcxjl(UserHdBxdjgcxjl hdBxdjgcxjl);

	public void addhdDkcfsq(UserHdDkcfsq hdDkcfsq);

	public void addhdSyhmd(UserHdShanyinSyhmd hdSyHmd);

	public void addhdSxbzxrcx(UserHdSxbzxrcx hdSxbzxrcx);
	
	public void addGrbzxggcx(UserHdGrbzxggcx hdGrbzxggcx);
}
