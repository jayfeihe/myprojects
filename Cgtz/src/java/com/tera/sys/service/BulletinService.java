/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.sys.dao.BulletinDao;
import com.tera.sys.model.Bulletin;
/**
 * @author wy
 *
 */
@Service
public class BulletinService extends MybatisBaseService<Bulletin> {

	/**
	 * bulletin dao
	 */
	@Autowired
	private BulletinDao bulletinDao;

	
	@Transactional
	public void addBulletin(Bulletin bulletin) {
		this.bulletinDao.addBulletin(bulletin);
	}

	
	@Transactional
	public void deleteBulletinByIDs(int[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		this.bulletinDao.deleteBulletinByIDs(map);
	}

	
	public Bulletin getBulletinById(Object id) {
		return this.bulletinDao.getBulletinById(id);
	}

	
	public int getBulletinCount(Map<String, Object> map) {
		return this.bulletinDao.getBulletinCount(map);
	}

	
	public List<Bulletin> queryBulletin(Map<String, Object> map) {
		return this.bulletinDao.queryBulletin(map);
	}

	
	@Transactional
	public void updateBulletin(Bulletin bulletin) {
		this.bulletinDao.updateBulletin(bulletin);
	}
	
	public Bulletin getBulletinByLast() {
		return this.bulletinDao.getBulletinByLast();
	}


	public PageList<Bulletin> queryPageList(Map<String, Object> params) {
		return this.selectPageList(BulletinDao.class, "queryBulletin", params);
	}
}
