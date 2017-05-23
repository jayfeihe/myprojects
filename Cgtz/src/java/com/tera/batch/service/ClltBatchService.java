package com.tera.batch.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;









import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.batch.dao.ReportAcctDao;
import com.tera.batch.dao.ReportDealsDao;
import com.tera.batch.model.ReportAcct;
import com.tera.batch.model.ReportDeal;
import com.tera.feature.cllt.model.ClltDistr;
import com.tera.feature.cllt.service.IClltDistrService;
import com.tera.interfaces.controller.InterBranchController;
import com.tera.report.model.ReportOverdue;
import com.tera.report.service.IReportOverdueService;
import com.tera.util.DateUtils;
import com.tera.util.StringUtils;

/**
 * 贷后批处理服务
 * @author Jesse
 *
 */
@Service("clltBatchService")
public class ClltBatchService {
	
	@Autowired(required=false)
    private IContractService contractService;
	
	@Autowired(required=false)
    private ICommonHandlerService commonHandlerService;
	
	@Autowired(required=false)
    private IClltDistrService clltDistrService;
	
	@Autowired(required=false)
    private ReportAcctDao  acctDao;
	
	
	@Autowired(required=false)
    private ReportDealsDao  dealDao;
	
	@Autowired(required=false)
    private IReportOverdueService reportOverdueService;
	
	
	private static final Logger log = Logger.getLogger(InterBranchController.class);
	
	
	/**
	 * 催收自动分单
	 * @throws Exception 
	 */
	@Transactional
	public void autoDisCllt() throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info("=====催收自动分单======");
		
		//同步逾期状态，把处于逾期的合同标识为逾期
		List<Contract> listCons=contractService.queryLateNoFlag();
		if (listCons==null||listCons.size()==0) {
			return;
		}
		for (Contract contract : listCons) {
			contract.setIsLate("1");
			contract.setIsLateDis("0");//置为未分配，之后重新查出来再分配
			contractService.update(contract); //更新合同表中的状态，是否逾期，逾期期数
		}
		
		Map<String, Object> map =new HashMap<String, Object>();
		//查询所有的逾期，未分配的数据
		map.put("isLate", "1");
		map.put("isLateDis", "0");
		map.put("state", "2");//合同中的数据
		List<Contract> listDis=contractService.queryList(map);
		String clltUser = "";
		for (Contract contract : listDis) {
			clltUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_CLLT_STAFF, contract.getOrg());
			contract.setIsLateDis("1");
			contractService.update(contract);
			//修改分配表中数据
			map.clear();
			map.put("contractId", contract.getContractId());
			map.put("isCur", "1");
			List<ClltDistr> listDistrs=clltDistrService.queryList(map);
			for (ClltDistr clltDistr : listDistrs) {
				clltDistr.setIsCur("0");
				clltDistr.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			
				clltDistrService.update(clltDistr);
			}
			
			if (!"".equals(clltUser)) {
				ClltDistr distr=new ClltDistr();
				distr.setContractId(contract.getContractId());
				distr.setClltUid(clltUser);
				distr.setCreateTime(new Timestamp(System.currentTimeMillis()));
				distr.setCreateUid(CommonConstant.SYS_USER);
				distr.setIsCur("1");
				clltDistrService.add(distr);
			}
		}
		
		log.info(thisMethodName+":end");
	}
	
	/**
	 *稽查列表
	 *每周一执行一次，把处理过的清理，增加三种来源的
	 * @throws Exception 
	 */
	@Transactional
	public void  autoCreateCheckTask() throws Exception {
		
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info("=====每周执行一次，稽查======");
		/**
		 * 三种   来源
		 *   1 新逾期  本金逾期2天，利息逾期5天
		 *   2一周之内，业务员新跟进
		 *   3还款发生变化
		 */
		
		//新逾期处理
		//1本金逾期
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("type", "2");
		map.put("date1", DateUtils.addDay(DateUtils.getDateNow(), -9));// 九天前
		map.put("date2", DateUtils.addDay(DateUtils.getDateNow(), -2));// 两天前
	
		List<Contract> listcon1=contractService.queryLateCon(map);
		//2利息逾期
		map.put("type", "1");
		map.put("date1", DateUtils.addDay(DateUtils.getDateNow(), -12));// 12天前
		map.put("date2", DateUtils.addDay(DateUtils.getDateNow(), -5));// 5天前
	
		List<Contract> listcon2=contractService.queryLateCon(map);
		listcon1.removeAll(listcon2);
		listcon1.addAll(listcon2);
		for (Contract contract : listcon1) {
			contract.setIsCheck("1");
			contract.setCheckInTime(new Timestamp(System.currentTimeMillis()));
			contract.setCheckSource("1");//来源
			contract.setCheckReportState("0");
			contract.setIsAccept("0");
			contract.setCheckState("0");
			contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			contractService.update(contract);
		}
		
		//业务员新跟进
		map.remove("type");
		map.put("role", CommonConstant.ROLE_SALESMAN);
		List<Contract> listcon3=contractService.querySalesNewLog(map);
		
		for (Contract contract : listcon3) {
			contract.setIsCheck("1");
			contract.setCheckInTime(new Timestamp(System.currentTimeMillis()));
			contract.setCheckSource("2");//来源
			contract.setCheckReportState("0");
			contract.setIsAccept("0");
			contract.setCheckState("0");
			contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			contractService.update(contract);
		}
		
		//还款变化的
		map.remove("role");
		
		List<Contract> listcon4=contractService.querySalesNewBill(map);
		for (Contract contract : listcon4) {
			contract.setIsCheck("1");
			contract.setCheckInTime(new Timestamp(System.currentTimeMillis()));
			contract.setCheckSource("3");//来源
			contract.setCheckReportState("0");
			contract.setIsAccept("0");
			contract.setCheckState("0");
			contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			contractService.update(contract);
		}
		//清除 超过进入稽查超过七天并且已经处理过的
		
		map.clear();
		map.put("date1", DateUtils.addDay(DateUtils.getDateNow(), -7));
		List<Contract> listInvalid=contractService.queryInvalidCheck(map);
		for (Contract contract : listInvalid) {
			contract.setIsCheck("0");
			contractService.update(contract);
		}
		log.info(thisMethodName+":end");
	}
	
	/**
	 *  每天晚上记录一次，统计各个分公司的存量情况
	 */
	@Transactional
	public synchronized void recordAcct() {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info("=====统计各个分公司存量======");
		//获取当前日期
		String dateNow=DateUtils.formatDate(DateUtils.getDateNow());
		//清空当前日期的所有数据
		acctDao.deleteByDate(dateNow);
		//查询未到期未收回的
		List<ReportAcct> listAccts =acctDao.queryNor(dateNow);
		for (ReportAcct reportAcct : listAccts) {
			reportAcct.setAllAmt(reportAcct.getNorAmt()+reportAcct.getOverAmt());
			reportAcct.setAllNum(reportAcct.getNorNum()+reportAcct.getOverNum());
			reportAcct.setCreateDate(DateUtils.getDateNow());
			acctDao.add(reportAcct);
		}
		
		//查询到期未收回的
		
		List<ReportAcct> listOver =acctDao.queryOver(dateNow);
		Map<String, Object> map=new HashMap<String, Object>();
		List<ReportAcct> listTmp;
		ReportAcct acctTmp;
		for (ReportAcct reportAcct : listOver) {
			
			map.put("orgId", reportAcct.getOrgId());
			map.put("product", reportAcct.getProduct());
			map.put("createDate", reportAcct.getCreateDateStr());
			listTmp=null;
			listTmp=acctDao.queryList(map);
			if (listTmp==null||listTmp.size()==0) {
				//插入新纪录
				reportAcct.setCreateDate(DateUtils.getDateNow());
				reportAcct.setAllAmt(reportAcct.getNorAmt()+reportAcct.getOverAmt());
				reportAcct.setAllNum(reportAcct.getNorNum()+reportAcct.getOverNum());
				acctDao.add(reportAcct);
			}else {
				acctTmp=listTmp.get(0);
				acctTmp.setOverAmt(reportAcct.getOverAmt());
				acctTmp.setOverNum(reportAcct.getOverNum());
				acctTmp.setAllAmt(reportAcct.getNorAmt()+reportAcct.getOverAmt());
				acctTmp.setAllNum(reportAcct.getNorNum()+reportAcct.getOverNum());
				acctDao.update(acctTmp);
			}
			
		}
		//转贷纪录
		List<ReportAcct> listTran =acctDao.queryTran();
		for (ReportAcct reportAcct : listTran) {

			map.put("orgId", reportAcct.getOrgId());
			map.put("product", reportAcct.getProduct());
			map.put("createDate", reportAcct.getCreateDateStr());
			listTmp=null;
			listTmp=acctDao.queryList(map);
			if (listTmp!=null&&listTmp.size()>0) {
				acctTmp=listTmp.get(0);
				acctTmp.setTranAmt(reportAcct.getTranAmt());
				acctTmp.setTranNum(reportAcct.getTranNum());
				acctTmp.setTranFewAmt(reportAcct.getTranFewAmt());
				acctTmp.setTranMoreAmt(reportAcct.getTranMoreAmt());
				acctTmp.setTranRate(acctTmp.getTranAmt()/acctTmp.getAllAmt());
				acctDao.update(acctTmp);
			}
			
		}
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 每月1号统计上个月的新增和续贷的笔数，金额，记录
	 */
	@Transactional
	public synchronized void createMonthAcct() {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info("=====每天统计分公司新增和续贷======");
		//获取上个月的月份
		Date dt=DateUtils.addMonth(DateUtils.getDateNow(), -1);
		String strMonth=DateUtils.formatDate(dt, "yyyy-MM");
		
		//删除之前的统计记录
		dealDao.deleteByMon(strMonth);
		
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("mon", strMonth);
		map.put("xz", "1");//新增
		List<ReportDeal> listxz =dealDao.queryMonInfo(map);
		
		for (ReportDeal reportDeal : listxz) {
			reportDeal.setMon(strMonth);
			reportDeal.setType("新增");
			reportDeal.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dealDao.add(reportDeal);
		}
		
		map.remove("xz");
		map.put("xd", "1");//续贷
		List<ReportDeal> listxd =dealDao.queryMonInfo(map);
		for (ReportDeal reportDeal : listxd) {
			reportDeal.setMon(strMonth);
			reportDeal.setType("续贷");
			reportDeal.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dealDao.add(reportDeal);
		}
		log.info(thisMethodName+":end");
	}
	/**
	 *  每天晚上记录一次，统计各个分公司当天的逾期情况
	 */
	@Transactional
	public synchronized void orgOverdue() {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info("=====统计各个分公司逾期数据======");
		//获取当前日期
		Date createDate=DateUtils.getDateNow();
		//统计当天逾期信息
		//如果当天数据没有，插入当天数据(查询当天的数据结果为0)
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.clear();
		queryMap.put("createDate",createDate);
		int allListNum=0;
		try{
			allListNum=this.reportOverdueService.queryCount(queryMap);
			queryMap.clear();
			if(allListNum==0){
				//获得当天逾期数据
				List<ReportOverdue> overList=null;
				overList=this.reportOverdueService.queryOverdueStatics(queryMap);
				//插入当天数据
				for (ReportOverdue reportOverdue : overList) {
					reportOverdue.setCreateDate(createDate);
					this.reportOverdueService.add(reportOverdue);
				}
			}				
		}catch(Exception e){
			e.printStackTrace();
		}
	    log.info(thisMethodName+":end");
	}
	
}

