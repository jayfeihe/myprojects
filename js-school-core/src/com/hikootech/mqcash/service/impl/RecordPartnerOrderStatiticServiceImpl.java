package com.hikootech.mqcash.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.RecordPartnerOrderStatiticDAO;
import com.hikootech.mqcash.dto.RecordPartnerOrderStatisticDTO;
import com.hikootech.mqcash.service.RecordPartnerOrderStatiticService;

@Service("recordPartnerOrderStatiticService")
public class RecordPartnerOrderStatiticServiceImpl implements RecordPartnerOrderStatiticService {
		
	@Autowired
	private RecordPartnerOrderStatiticDAO recordPartnerOrderStatiticDAO ;
	
	@Override
	public RecordPartnerOrderStatisticDTO queryRecordPartnerOrderStatistic(RecordPartnerOrderStatisticDTO recordPartnerOrderStatistic)throws Exception {
			
		return recordPartnerOrderStatiticDAO.queryRecordPartnerOrderStatistic(recordPartnerOrderStatistic);
	}

	@Override
	public int insertRecordPartnerOrderStatistic( RecordPartnerOrderStatisticDTO recordPartnerOrderStatistic) throws Exception {
		return recordPartnerOrderStatiticDAO.insertRecordPartnerOrderStatistic(recordPartnerOrderStatistic);
	}

	@Override
	public String queryPartner2Id(String partnerId) {
		return recordPartnerOrderStatiticDAO.queryPartner2Id(partnerId);
	}

}
