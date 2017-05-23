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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.bpm.dao.BpmParameterDao;
import com.tera.bpm.model.BpmParameter;
/**
 * @author Administrator
 */
@Service
public class BpmParameterService {
	
	/**
	 * bpmParameterDao
	 */
	@Autowired(required=false)
	BpmParameterDao bpmParameterDao;
	
	
	public void add(BpmParameter parameter) {
		bpmParameterDao.add(parameter);
	}
	
	public void update(BpmParameter parameter) {
		bpmParameterDao.update(parameter);
	}
			
	public void delete(Object obj) {
		bpmParameterDao.delete(obj);
	}
	
	public List<BpmParameter> getBpmParameter(Map<String, Object> map) {
		return bpmParameterDao.getBpmParameter(map);
	}


}
