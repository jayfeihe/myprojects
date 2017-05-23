package com.tera.audit.law.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.law.dao.ContractDao;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.model.ContractOnline;
import com.tera.audit.law.model.ProjectInfo;
import com.tera.audit.law.service.IContractOnlineService;
import com.tera.audit.law.service.IContractService;
import com.tera.sys.service.MybatisBaseService;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;

/**
 * 
 * 合同信息表服务类
 * <b>功能：</b>ContractDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 11:51:45<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("contractService")
public class ContractServiceImpl extends MybatisBaseService<Contract> implements IContractService {

	private final static Logger log = Logger.getLogger(ContractServiceImpl.class);

	@Autowired(required=false)
    private ContractDao dao;
	
	@Autowired(required=false)
    private IContractOnlineService contractOnlineService;
	

	/* (non-Javadoc)
	 * @see com.tera.audit.law.service.IContractService#add(com.tera.audit.law.model.Contract)
	 */
	@Override
	@Transactional
	public void add(Contract... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Contract obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.law.service.IContractService#update(com.tera.audit.law.model.Contract)
	 */
	@Override
	@Transactional
	public void update(Contract obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.law.service.IContractService#updateOnlyChanged(com.tera.audit.law.model.Contract)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(Contract obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.law.service.IContractService#delete(java.lang.Object)
	 */
	@Override
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.law.service.IContractService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.law.service.IContractService#queryList(java.util.Map)
	 */
	@Override
	public List<Contract> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.law.service.IContractService#queryByKey(java.lang.Object)
	 */
	@Override
	public Contract queryByKey(Object id) throws Exception {
		return (Contract)dao.queryByKey(id);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.law.service.IContractService#queryByContractId(java.lang.String)
	 */
	@Override
	public Contract queryByContractId(String contractId) {
		return dao.queryByContractId(contractId);
	}

	@Override
	public PageList<Contract> queryPageList(Map<String, Object> params) {
		return this.selectPageList(ContractDao.class, "queryList", params);
	}

	@Override
	public List<Contract> queryLateNoFlag() {
	
		return dao.queryLateNoFlag();
	}
	
	@Override
	public List<Contract> queryLateCon(Map<String, Object> map) {
	
		return dao.queryLateCon(map);
	}
	
	@Override
	public List<Contract> querySalesNewLog(Map<String, Object> map) {
		
		return dao.querySalesNewLog(map);
	}
	@Override
	public List<Contract> querySalesNewBill(Map<String, Object> map) {
		
		return dao.querySalesNewBill(map);
	}
	
	public List<Contract> queryInvalidCheck(Map<String, Object> map) {
		
		return dao.queryInvalidCheck(map);
	}
	
	
	/**
	 * 线上系统推送过来的项目信息
	 * 记录到本系统中
	 * @param listinfoInfos
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void  handleOnline(List<ProjectInfo> listInfos) throws Exception {
		//逐条遍历处理
		String loanId;
		List<Contract> listContracts;
		Contract contract=new Contract();
		ContractOnline contractOnline=new ContractOnline();
		Map<String, Object> mapPara=new HashMap<String, Object>() ;
		for (ProjectInfo projectInfo : listInfos) {
			loanId="";
			listContracts=null;
			loanId=projectInfo.getSerial_number(); 
			
			mapPara.put("loanId", loanId);
			listContracts=dao.queryList(mapPara);
			if (listContracts==null||listContracts.size()==0) {
				continue;
			}
			//判断是否已经存在记录
			mapPara.clear();
			mapPara.put("onlineConId", projectInfo.getProject_id().trim());
			List<ContractOnline> listOnlines=contractOnlineService.queryList(mapPara);
			if (listOnlines!=null&&listOnlines.size()>0) {
				continue;
			}
			contract=listContracts.get(0);
			if ("2".equals(contract.getGetLoanWay())) {//债权转让模式
				
				//线上线下关联表添加数据
				contractOnline.setLoanId(loanId);
				contractOnline.setContractId(contract.getContractId());
				contractOnline.setOnlineConId(projectInfo.getProject_id());
				contractOnline.setProjectName(projectInfo.getTitle());
				contractOnline.setOnlineType("");// 类型，暂为空
				contractOnline.setOnlineStartDate(DateUtils.getDate(projectInfo.getOnline_time()));
				contractOnline.setOnlineEndDate(contract.getEndDate());
				contractOnline.setOnlineRateIn(Double.valueOf(projectInfo.getContract_rate()));
				contractOnline.setOnlineRateOut(Double.valueOf(projectInfo.getAnnualized_rate()));
				contractOnline.setRetWay(""); //线上的还款方式，暂为空
				contractOnline.setOnlineAmt(Double.valueOf(projectInfo.getDebt_amount()));//合同金额
				contractOnlineService.add(contractOnline);
				
			}else if ("1".equals(contract.getGetLoanWay())) {//直投模式
				if (!"1".equals(contract.getState())) {//合同状态必须是未生效
					continue;
				}
				//需要注意线下合同的拆分问题
				if (listContracts.size()==1) {//只有一条记录
					//对比合同金额是否相同
					double db=MathUtils.sub(contract.getLoanAmt(), Double.valueOf(projectInfo.getDebt_amount()));
					if (db<0) {//数据有问题，记录到日志中
						//TODO
						continue;
					}
					if (db==0) {
						//相同，无需拆分，一对一
						contractOnline.setLoanId(loanId);
						contractOnline.setContractId(contract.getContractId());
						contractOnline.setOnlineConId(projectInfo.getProject_id());
						contractOnline.setProjectName(projectInfo.getTitle());
						contractOnline.setOnlineType("");// 类型，暂为空
						contractOnline.setOnlineStartDate(DateUtils.getDate(projectInfo.getOnline_time()));
						contractOnline.setOnlineEndDate(contract.getEndDate());
						contractOnline.setOnlineRateIn(Double.valueOf(projectInfo.getContract_rate()));
						contractOnline.setOnlineRateOut(Double.valueOf(projectInfo.getAnnualized_rate()));
						contractOnline.setRetWay(""); //线上的还款方式，暂为空
						contractOnline.setOnlineAmt(Double.valueOf(projectInfo.getDebt_amount()));//合同金额
						contractOnlineService.add(contractOnline);
					}else {
						contract.setState("4");//被拆分
						contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						dao.update(contract);
						//添加一条新的合同记录
						contract.setContractId(contract.getContractId()+"-1");
						contract.setConIndex(1);
						contract.setLoanAmt(Double.valueOf(projectInfo.getDebt_amount()));
						contract.setState("1");//合同未生效状态，放款后生效
						contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						dao.add(contract);
						
						
						contractOnline.setLoanId(loanId);
						contractOnline.setContractId(contract.getContractId());
						contractOnline.setOnlineConId(projectInfo.getProject_id());
						contractOnline.setProjectName(projectInfo.getTitle());
						contractOnline.setOnlineType("");// 类型，暂为空
						contractOnline.setOnlineStartDate(DateUtils.getDate(projectInfo.getOnline_time()));
						contractOnline.setOnlineEndDate(contract.getEndDate());
						contractOnline.setOnlineRateIn(Double.valueOf(projectInfo.getContract_rate()));
						contractOnline.setOnlineRateOut(Double.valueOf(projectInfo.getAnnualized_rate()));
						contractOnline.setRetWay(""); //线上的还款方式，暂为空
						contractOnline.setOnlineAmt(Double.valueOf(projectInfo.getDebt_amount()));//合同金额
						contractOnlineService.add(contractOnline);
					}
					
				}else {//已经被拆分
					//添加一条新的合同记录
					contract.setContractId(contract.getContractId()+"-"+String.valueOf(listContracts.size()-1));
					contract.setLoanAmt(Double.valueOf(projectInfo.getDebt_amount()));
					contract.setConIndex(listContracts.size()-1);
					contract.setState("1");//合同未生效状态，放款后生效
					contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					dao.add(contract);
					
					contractOnline.setLoanId(loanId);
					contractOnline.setContractId(contract.getContractId());
					contractOnline.setOnlineConId(projectInfo.getProject_id());
					contractOnline.setProjectName(projectInfo.getTitle());
					contractOnline.setOnlineType("");// 类型，暂为空
					contractOnline.setOnlineStartDate(DateUtils.getDate(projectInfo.getOnline_time()));
					contractOnline.setOnlineEndDate(contract.getEndDate());
					contractOnline.setOnlineRateIn(Double.valueOf(projectInfo.getContract_rate()));
					contractOnline.setOnlineRateOut(Double.valueOf(projectInfo.getAnnualized_rate()));
					contractOnline.setRetWay(""); //线上的还款方式，暂为空
					contractOnline.setOnlineAmt(Double.valueOf(projectInfo.getDebt_amount()));//合同金额
					contractOnlineService.add(contractOnline);
					
				}
				
			}else {
				continue;
			}
		}
	
	}
	
	
}
