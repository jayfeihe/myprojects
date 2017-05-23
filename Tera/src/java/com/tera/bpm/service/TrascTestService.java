/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.dao.TrascTestDao;

/**
 * @author Administrator
 *
 */
@Service
public class TrascTestService {

	/**
	 * mapper
	 */
	@Autowired
	private TrascTestDao mapper;

	/**
	 * 添加
	 * @param code code
	 * @param value value
	 */
	@Transactional
	public void addData(String code, String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("value", value);
		mapper.addData(map);
		int n = 0;
		while (n < 10) {
			if (n == 5) {
				throw new RuntimeException("1234567898765432");
			}
			n++;
		}
//		mapper.addData(map);
	}

	/**
	 * deleteData
	 */
	@Transactional
	public void deleteDataTest() {
		mapper.deleteData();
	}

}
