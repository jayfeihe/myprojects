package com.hikootech.mqcash.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.GenAreaDAO;
import com.hikootech.mqcash.po.GenArea;
import com.hikootech.mqcash.service.GenAreaService;

@Service("genAreaService")
public class GenAreaServiceImpl implements GenAreaService {

	private static Logger log=LoggerFactory.getLogger(GenAreaServiceImpl.class);
	@Autowired
	public  GenAreaDAO genAreaDAO;
	
	@Override
	public List<GenArea> getAreaList(String parentCode) {
		 
		try {
			return genAreaDAO.getAreaList(parentCode);
		} catch (Exception e) {
			log.error("根据父级节点查询地区信息",e);
			throw new RuntimeException("根据父级节点查询地区信息",e);
		}
	}

}
