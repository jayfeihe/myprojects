/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.Bulletin;
/**
 * @author Administrator
 *
 */
public interface BulletinDao {
	/**
	 * 获取计数
	 * @param beanMap map
	 * @return 计数
	 */
	//int getMenuCount(Map<String, Object> beanMap);
	/**
	 * 添加
	 * @param bulletin bulletin
	 */
	void addBulletin(Bulletin bulletin);
	/**
	 * 查询
	 * @param map map
	 * @return list
	 */
	List<Bulletin> queryBulletin(Map<String, Object> map);
	/**
	 * 记数
	 * @param map map
	 * @return int
	 */
	int getBulletinCount(Map<String, Object> map);
	/**
	 * 更新
	 * @param bulletin bulletin
	 */
	void updateBulletin(Bulletin bulletin);
	/**
	 * 删除
	 * @param map map
	 */
	void deleteBulletinByIDs(Map<String, Object> map);
	/**
	 * @param id id
	 * @return bulletin
	 */
	Bulletin getBulletinById(Object id);
	/**
	 * @return bulletin
	 */
	Bulletin getBulletinByLast();

}
