/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface TrascTestDao {

	/**
	 * 添加
	 * @param map map
	 */
	void addData(Map<String, Object> map);

	/**
	 * 删除
	 */
	void deleteData();
}
