package com.tera.collection.phone.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.collection.phone.service.CollectionBatchService;

@Controller
@RequestMapping("/collectionTest")
public class CollectionTest {

	private final static Logger log = Logger.getLogger(CollectionResultController.class);
	
	@Autowired(required=false) 
	private CollectionBatchService batchService;
	
	@RequestMapping("/ini.do")
	public void collectionBasicIni(HttpServletRequest request, ModelMap map) throws Exception {
		batchService.collectionBasicIni();
	}
	
	@RequestMapping("/autodis.do")
	public void collectionAutoDistribution(HttpServletRequest request, ModelMap map) throws Exception {
		batchService.autoDistribution();
	}
	
	@RequestMapping("/afterpay.do")
	public void afterpay(HttpServletRequest request, ModelMap map,String contractNo) throws Exception {
		batchService.handleCollectionAfterPayment(contractNo);
	}
	
	@RequestMapping("/sync.do")
	public void sync(HttpServletRequest request, ModelMap map,String contractNo) throws Exception {
		batchService.syncCollectionBaseData(contractNo);
	}
	
	@RequestMapping("/way.do")
	public void way(HttpServletRequest request, ModelMap map,String contractNo) throws Exception {
		batchService.handleCollectionWay();
	}
	
}
