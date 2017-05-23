package com.tera.batch.automatch.processor;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.match.dao.Lend2matchDao;
import com.tera.match.dao.Loan2matchDao;
import com.tera.match.dao.MatchResultDao;
import com.tera.match.model.Lend2match;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchModel;
import com.tera.match.model.MatchResult;
import com.tera.match.service.MatchResultService;
import com.tera.match.service.MatchUtil;
import com.tera.payment.service.LoanRepayPlanService;

public class AutoMatchSplitProcessor implements ItemProcessor<Integer, String> {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(AutoMatchSplitProcessor.class);
	
	private Lend2matchDao<Lend2match> daoLend;
	
    private Loan2matchDao<Loan2match> daoLoan;
    
    private MatchResultDao<MatchResult> daoMatch;
    
    private MatchResultService<MatchResult> matchResultService;
    
    private LoanRepayPlanService loanRepayPlanService;
    
    private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;
	/**
     * 对取到的数据进行简单的处理。
     * 
     * @param student
     *            处理前的数据。
     * @return 处理后的数据。
     * @exception Exception
     *                处理是发生的任何异常。
     */
    @Override
    public String process(Integer lm) throws Exception {
    	
    	try {
    		//获取要处理的两个队列
        	
        	Map<String, Object> map=new HashMap<String, Object>();
        	List<Lend2match> listLend=daoLend.queryBasicLockList(map);
        	map.put("type", "2");
        	map.put("loanflag", "C");
        	List<Loan2match> listLoan=daoLoan.queryBasicLockList(map);
            daoLend.updateToLock();
            daoLoan.updateToLock();
        	MatchModel matchModel=new MatchModel();
        	matchModel.setListLend(listLend);
        	matchModel.setListLoan(listLoan);
        	
        	matchModel=matchResultService.handleDate(matchModel);
        	matchModel=MatchUtil.matchByPeriodAndSplitAmount(matchModel);
         	matchModel=MatchUtil.matchByAmountAndSplitPeriod(matchModel);
      	    matchModel=MatchUtil.matchBySplitPeriodAndSplitAmount(matchModel);
     	
        	for (int i = 0; i < matchModel.getListMatchModels().size(); i++) {
        		daoLend.update(matchModel.getListMatchModels().get(i).getLend2match());
        		daoLoan.update(matchModel.getListMatchModels().get(i).getLoan2match());
        		daoMatch.add(matchModel.getListMatchModels().get(i).getMatchResult());
    			
        	
        	}
        	//记录撮合的次数，解锁
        	daoLend.updateMatchTimes();
        	daoLend.updateToUnLock();
        	daoLoan.updateMatchTimes();
        	daoLoan.updateToUnLock();
        	
        	return null;
		} catch (Exception e) {
			// TODO: handle exception
			BatchErrorLog errorLog=new BatchErrorLog();
			errorLog.setErrorMsg(e.getMessage());
			errorLog.setJobName("autoMatchSplitJob");
			errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			batchErrorLogDao.add(errorLog);
			throw e;
		}
    	
    }
    
    
	public MatchResultService<MatchResult> getMatchResultService() {
		return matchResultService;
	}


	public void setMatchResultService(
			MatchResultService<MatchResult> matchResultService) {
		this.matchResultService = matchResultService;
	}


	public Lend2matchDao<Lend2match> getDaoLend() {
		return daoLend;
	}
	public void setDaoLend(Lend2matchDao<Lend2match> daoLend) {
		this.daoLend = daoLend;
	}
	public Loan2matchDao<Loan2match> getDaoLoan() {
		return daoLoan;
	}
	public void setDaoLoan(Loan2matchDao<Loan2match> daoLoan) {
		this.daoLoan = daoLoan;
	}
	public MatchResultDao<MatchResult> getDaoMatch() {
		return daoMatch;
	}
	public void setDaoMatch(MatchResultDao<MatchResult> daoMatch) {
		this.daoMatch = daoMatch;
	}


	public LoanRepayPlanService getLoanRepayPlanService() {
		return loanRepayPlanService;
	}


	public void setLoanRepayPlanService(LoanRepayPlanService loanRepayPlanService) {
		this.loanRepayPlanService = loanRepayPlanService;
	}


	public BatchErrorLogDao<BatchErrorLog> getBatchErrorLogDao() {
		return batchErrorLogDao;
	}


	public void setBatchErrorLogDao(BatchErrorLogDao<BatchErrorLog> batchErrorLogDao) {
		this.batchErrorLogDao = batchErrorLogDao;
	}
	
	
	
}
