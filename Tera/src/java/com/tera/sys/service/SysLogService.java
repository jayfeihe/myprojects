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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sys.dao.SysLogDao;
import com.tera.sys.model.SysLog;
/**
 * sysLog service
 * @author admin
 *
 */
@Service
public class SysLogService {
	/**
	 * 日志DAO
	 */
	@Autowired
	private SysLogDao sysLogDao;

	
	@Transactional
	public void addSysLog(SysLog sysLog) {
		sysLogDao.addSysLog(sysLog);
	}

	
	public int getSysLogCount(Map<String, Object> map) {
		return sysLogDao.getSysLogCount(map);
	}

	
	public List<SysLog> querySysLog(Map<String, Object> map) {
		return sysLogDao.querySysLogList(map);
	}
	
	public List<SysLog> exportSysLog(Map<String, Object> map) {
		return sysLogDao.querySysLogList(map);
	}
}
