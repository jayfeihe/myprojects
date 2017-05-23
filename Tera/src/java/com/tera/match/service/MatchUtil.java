package com.tera.match.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tera.match.model.Lend2match;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchModel;
import com.tera.match.model.MatchResult;
import com.tera.sys.model.Workday;
import com.tera.sys.service.WorkdayService;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;


public class MatchUtil {
	
	public static final String AutoUser="sysauto";
	
	@Autowired(required=false)
	private WorkdayService<Workday> workdayService;

	/**
	 * 	//添加一条撮合记录，同时更新loan和lend。没有进行loan和lend的开始结束日期判断
	 * 需要保证loan和lend的开始和结束日期都存在才调用此方法
	 * @param matchModel 需要包含lend2match和loan2match两个实体信息
	 * @return
	 * @throws ParseException
	 */
	public static MatchModel addMatchResultOne(MatchModel matchModel) throws ParseException {
		Lend2match lend2match=matchModel.getLend2match();
		Loan2match loan2match=matchModel.getLoan2match();
		MatchResult matchResult=new MatchResult();
		
		Double dbNo=lend2match.getLendAmount()-loan2match.getLoanAmount();
		
		if (dbNo>=0) {
			matchResult.setLendAmount(loan2match.getLoanAmount());
			matchResult.setLoanAmount(loan2match.getLoanAmount());
			lend2match.setLendAmount(dbNo);
			loan2match.setLoanAmount(0);
			
		}else {
			matchResult.setLendAmount(lend2match.getLendAmount());
			matchResult.setLoanAmount(lend2match.getLendAmount());
			loan2match.setLoanAmount(MathUtils.round(loan2match.getLoanAmount()-lend2match.getLendAmount(), 2));
			lend2match.setLendAmount(0);	
		}
		//设置合同的开始日期
		matchResult.setStartDate(DateUtils.getDateNow());
		//比较期限的结束日期，以较早的作为合同的结束日期
		int comp =DateUtils.compareDate(LendGetEndDate(lend2match), LoanGetEndDate(loan2match));
		if (comp>0) {//前者比后者大
			matchResult.setEndDate( LoanGetEndDate(loan2match));
		}else {
			matchResult.setEndDate(LendGetEndDate(lend2match));
		}
		
		
		//matchResult.setState(loan2match.getType());//两个的状态正好对应，1或者2
		matchResult.setFlag("1");
		if (lend2match.getLendAmount()==0) {
			lend2match.setState("2");	
		}
		if (loan2match.getLoanAmount()==0) {
			loan2match.setState("2");
			if ("2".equals(loan2match.getType())){
				loan2match.setState("4");
			}
		}
		
		//赋值
		
		//lend2match.setOperator(AutoUser);
		lend2match.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		//lend2match.setTimes(lend2match.getTimes()+1);
		
		//loan2match.setOperator(AutoUser);
		loan2match.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		//loan2match.setTimes(loan2match.getTimes()+1);
		
		matchResult.setCreateTime(new Timestamp(System.currentTimeMillis()));
		matchResult.setLendAppId(lend2match.getLendAppId());
		matchResult.setLendInterestRate(lend2match.getLendInterestRate());
		matchResult.setLendPeriod(lend2match.getLendPeriod());
		matchResult.setLendProduct(lend2match.getLendProduct());
		matchResult.setLendType(lend2match.getType());
		matchResult.setLoanAppId(loan2match.getLoanAppId());
		matchResult.setLoanInterestRate(loan2match.getLoanInterestRate());
		matchResult.setLoanPeriod(loan2match.getLoanPeriod());
		matchResult.setLoanProduct(loan2match.getLoanProduct());
		matchResult.setLoanType(loan2match.getType());
		matchResult.setMatchTime(new Timestamp(System.currentTimeMillis()));
		matchResult.setMatchType(loan2match.getMatchType());
		matchResult.setOperator(AutoUser);
		matchResult.setUseage(loan2match.getUseage());
		//维护状态
		if ("1".equals(loan2match.getType())) {
			//借款人未签订合同，属于第一次。result状态为1
			matchResult.setState("1");
		}
		if ("2".equals(loan2match.getType())){
			//属于匹配的中间期限
			matchResult.setState("3");//合同中
			matchResult.setActualStartDate(matchResult.getStartDate());
			matchResult.setActualEndDate(matchResult.getEndDate());
			//标识合同签订状态，也认为是签订了有效合同
			lend2match.setContractStartDate(lend2match.getValueDate());
			lend2match.setContractEndDate(lend2match.getEndDate());
		}
		
		//构造返回值
		
		matchModel.setLend2match(lend2match);
		matchModel.setLoan2match(loan2match);
		matchModel.setMatchResult(matchResult);
	
		return matchModel;
	}
	
	/**
	 * 判断出借人是第几次撮合
	 * @return
	 */
	public static int testLend2Match(Lend2match lend2match){
		if (lend2match.getStartDate()==null&&lend2match.getContractStartDate()==null&&lend2match.getContractEndDate()==null) {
			//第一次撮合
			return 1; 
		}else if (lend2match.getStartDate()!=null&&lend2match.getContractStartDate()==null&&lend2match.getContractEndDate()==null) {
			// 已经撮合过未签过合同
			return 2;
		}else if (lend2match.getContractStartDate()!=null&&lend2match.getContractEndDate()!=null) {
			//已经签订合同
			return 3;
		}else {//有一个为空，可能有问题，如何处理。数据不完整
			return 0;
		}
		
	}
	/**
	 * 判断借款人是第几次撮合
	 * @param loan2match
	 * @return
	 */
	public static int testLoan2Match(Loan2match loan2match){
		if (loan2match.getStartDate()== null && loan2match.getEndDate()== null && loan2match.getContractStartDate()==null && loan2match.getContractEndDate()==null) {
			//第一次撮合
			return 1; 
		}else if (loan2match.getStartDate()!=null&& loan2match.getEndDate()!=null&&loan2match.getContractStartDate()==null&&loan2match.getContractEndDate()==null) {
			// 已经撮合过未签过合同
			return 2;
		}else if (loan2match.getContractStartDate()!=null&&loan2match.getContractEndDate()!=null) {
			//已经签订合同
			return 3;
		}else {//有一个为空，可能有问题，如何处理。数据不完整
			return 0;
		}
		
	}
	
	
	/**
	 * //获得loan的服务截止日期   开始日期为今天
	 */
	public static Date LoanGetEndDate(Loan2match loan2match) throws ParseException{
		int i=testLoan2Match(loan2match);
		if (i==1) {
			Date dateNow=DateUtils.getDateNow();	
			
			Date dt= DateUtils.addMonth(dateNow,loan2match.getLoanPeriod());
			return DateUtils.addDay(dt, -1);
		}else if (i==3) {
			return loan2match.getContractEndDate();
		}
		
		return loan2match.getEndDate();
	}
	

	
	/**
	 * //获得lend的服务截止日期  ，请确保lend的开始时间不为null
	 */
	public static Date LendGetEndDate(Lend2match lend2match) throws ParseException{
		
//		Date dt= DateUtils.addMonth(lend2match.getAppTime(),lend2match.getLendPeriod());
//		//需要再减1天
//		return DateUtils.addDay(dt, -1);
		return lend2match.getEndDate();
	}
	
	
	
	/**
	 * 算法1：过滤金额和期限完全相符的
	 * 根据金额和剩余的期限匹配，匹配好的结果放到matchmode.list<matchmodel>中
	 * 未匹配的不切分，写回各自的list返回
	 * @param matchModel
	 * 参数包含lend和loan两个list
	 * @return  
	 * @throws ParseException 
	 */
	public static MatchModel matchByAmountAndPeriod(MatchModel matchModel) throws ParseException {

		List<Lend2match> listLend=matchModel.getListLend();
		List<Loan2match> listLoan=matchModel.getListLoan();
		//定义一个list<matchModel>存放匹配好的记录
		List<MatchModel> listMatchModels=matchModel.getListMatchModels();
		
		
		for (int i = 0; i <listLend.size(); i++) {
			if (listLend.get(i).getLendAmount()==0) {
				continue;
			}
			for (int j = 0; j <listLoan.size(); j++) {
				if (listLend.get(i).getLendAmount()==listLoan.get(j).getLoanAmount()) {//金额相同
					int com=DateUtils.compareDate(LendGetEndDate(listLend.get(i)),LoanGetEndDate(listLoan.get(j)));
					if (com==0) {//结束日期为同一天
						//匹配成功，余额都为0，都只有一条matchresult记录
						MatchModel m=new MatchModel();
						m.setLend2match(listLend.get(i));
						m.setLoan2match(listLoan.get(j));
						m=addMatchResultOne(m);
						listMatchModels.add(m);//匹配结果列表
						listLend.set(i, m.getLend2match());
						listLoan.set(j, m.getLoan2match());
					}
				}
			}
		}
		//构造返回值
		matchModel.setListLend(listLend);
		matchModel.setListLoan(listLoan);
		matchModel.setListMatchModels(listMatchModels);//匹配好的记录，和addmatchResultOne的返回值是一样的
		
		return matchModel;
	}

	
	/**
	 * 算法2：过滤期限一样，金额可以凑起来，不能切分
	 * 匹配好的结果放到matchmode.list<matchmodel>中
	 * 过滤后的数据写会原来的list传回
	 * @param matchModel
	 * @return
	 * @throws ParseException
	 */
	public static MatchModel matchByPeriod(MatchModel matchModel) throws ParseException {
	
		List<Lend2match> listLend=matchModel.getListLend();
		List<Loan2match> listLoan=matchModel.getListLoan();
		//定义一个list<matchModel>存放匹配好的记录
		List<MatchModel> listMatchModels= matchModel.getListMatchModels();
		
		for (int j = 0; j <listLoan.size(); j++) {
			
			if (listLoan.get(j).getLoanAmount()==0) {
				continue;
			}
			//设置临时存放集合
			double dbSum=0;
			List<Lend2match> listTmp=new ArrayList<Lend2match>();
			for (int i = 0; i <listLend.size(); i++) {
				//结束日期为同一天
				int com=DateUtils.compareDate(LendGetEndDate(listLend.get(i)),LoanGetEndDate(listLoan.get(j)));
				if (com==0 && listLend.get(i).getLendAmount()!=0 && listLend.get(i).getLendAmount()<listLoan.get(j).getLoanAmount()) {
					dbSum+=listLend.get(i).getLendAmount();
					//出借金额小于等于贷款金额
					listTmp.add(listLend.get(i));
				}
			}
			
			if (listTmp==null||dbSum<listLoan.get(j).getLoanAmount()) {
				continue;
			}
			//组合，是否能满足多个出借人正好凑够贷款人的数额
			//先给listtmp排序，金额从小到大
			for (int m = listTmp.size()-1; m >0; m--) {
				for (int n = 0; n < m; n++) {
					if (listTmp.get(n+1).getLendAmount()<listTmp.get(n).getLendAmount()) {
						Lend2match tmp=new Lend2match();
						tmp=listTmp.get(n);
						int index1=listTmp.indexOf(tmp);
						int index2=listTmp.indexOf(listTmp.get(n+1));
						listTmp.set(index1, listTmp.get(n+1));
						listTmp.set(index2, tmp);
					}
				}
			}
			

			//调用拼凑方法，记录匹配记录
			List<Lend2match> listMatch=makeUp(listTmp, listLoan.get(j).getLoanAmount(), 2, 0);
			if (listMatch!=null &&listMatch.size()!=0) {
				//有匹配记录
				Loan2match loanTmp=new Loan2match();
				loanTmp=listLoan.get(j);
				
				for (int k = 0; k < listMatch.size(); k++) {
					MatchModel m=new MatchModel();
					m.setLoan2match(loanTmp);
					m.setLend2match(listMatch.get(k));
					m=addMatchResultOne(m);
					loanTmp=m.getLoan2match();
					listMatchModels.add(m);//匹配结果列表
					//更新两个队列
					int index1=listLend.indexOf(listMatch.get(k));
					listLend.set(index1, m.getLend2match());
				}	
				listLoan.set(j, loanTmp);
			}
		}			
		
		//构造返回值
		matchModel.setListLend(listLend);
		matchModel.setListLoan(listLoan);
		matchModel.setListMatchModels(listMatchModels);//匹配好的记录，和addmatchResultOne的返回值是一样的
		
		return matchModel;
	}
	/**
	 * 算法3：期限一样，出借方的金额可以切分，凑够借款方的金额
	 * 需要确定优先级，先切什么，后切什么
	 * @param matchModel
	 * @return
	 * @throws ParseException 
	 */
	public static MatchModel matchByPeriodAndSplitAmount(MatchModel matchModel) throws ParseException {
		List<Lend2match> listLend=matchModel.getListLend();
		List<Loan2match> listLoan=matchModel.getListLoan();
		//定义一个list<matchModel>存放匹配好的记录
		List<MatchModel> listMatchModels=matchModel.getListMatchModels();
		
		for (int j = 0; j <listLoan.size(); j++) {
			if (listLoan.get(j).getLoanAmount()==0) {
				continue;
			}
			//设置临时存放集合
			List<Lend2match> listTmp=new ArrayList<Lend2match>();
			for (int i = 0; i <listLend.size(); i++) {
				//结束日期为同一天
				int com=DateUtils.compareDate(LendGetEndDate(listLend.get(i)),LoanGetEndDate(listLoan.get(j)));
				if (com==0&&listLend.get(i).getLendAmount()!=0) {
					listTmp.add(listLend.get(i));
				}
			}
			if (listTmp==null) {
				continue;
			}
			//判断是否全部的出借人的金额大于等于借款人的金额
			Double dbTmp=0.00;
			for (int i = 0; i < listTmp.size(); i++) {
				dbTmp+=listTmp.get(i).getLendAmount();
			}
			
			if (dbTmp<listLoan.get(j).getLoanAmount()) {
				continue;
			}
			//listLoan如何排序，暂且未定。
			
			Loan2match loanTmp=listLoan.get(j);
			for (int i = 0; i < listTmp.size(); i++) {
				if (loanTmp.getLoanAmount()<=0) {
					//已经满足
					break;
				}else {
					MatchModel m=new MatchModel();
					m.setLoan2match(loanTmp);
					m.setLend2match(listTmp.get(i));
					m=addMatchResultOne(m);
					loanTmp=m.getLoan2match();
					listMatchModels.add(m);//匹配结果列表
					//更新两个队列
					int index1=listLend.indexOf(listTmp.get(i));
					listLend.set(index1, m.getLend2match());
				}
			}
			listLoan.set(j, loanTmp);
			
			
		}
		
		//构造返回值
		matchModel.setListLend(listLend);
		matchModel.setListLoan(listLoan);
		matchModel.setListMatchModels(listMatchModels);//匹配好的记录，和addmatchResultOne的返回值是一样的
		
		return matchModel;
	}
	
	/**
	 * 算法4：金额相同，期限可以不一样。
	 * @param matchModel
	 * @return
	 * @throws ParseException
	 */
	public static MatchModel matchByAmountAndSplitPeriod(MatchModel matchModel) throws ParseException {
		List<Lend2match> listLend=matchModel.getListLend();
		List<Loan2match> listLoan=matchModel.getListLoan();
		//定义一个list<matchModel>存放匹配好的记录
		List<MatchModel> listMatchModels=matchModel.getListMatchModels();
		
		for (int i = 0; i <listLend.size(); i++) {
			if (listLend.get(i).getLendAmount()==0) {
				continue;
			}
			for (int j = 0; j <listLoan.size(); j++) {
				if (listLoan.get(j).getLoanAmount()==0) {
					continue;
				}
				if (listLend.get(i).getLendAmount()==listLoan.get(j).getLoanAmount()) {//金额相同
					
					//匹配成功，余额都为0，都只有一条matchresult记录
					MatchModel m=new MatchModel();
					m.setLend2match(listLend.get(i));
					m.setLoan2match(listLoan.get(j));
					m=addMatchResultOne(m);
					listMatchModels.add(m);//匹配结果列表
					listLend.set(i, m.getLend2match());
					listLoan.set(j, m.getLoan2match());
				}
			}
		}
		
		//构造返回值
		matchModel.setListLend(listLend);
		matchModel.setListLoan(listLoan);
		matchModel.setListMatchModels(listMatchModels);//匹配好的记录，和addmatchResultOne的返回值是一样的
		
		return matchModel;
	}
	
	/**
	 * 算法5：金额和期限都不同，逐一进行匹配处理。切分金额和日期
	 * @param matchModel
	 * @return
	 * @throws ParseException
	 */
	public static MatchModel matchBySplitPeriodAndSplitAmount(MatchModel matchModel) throws ParseException {
		List<Lend2match> listLend=matchModel.getListLend();
		List<Loan2match> listLoan=matchModel.getListLoan();
		//定义一个list<matchModel>存放匹配好的记录
		List<MatchModel> listMatchModels=matchModel.getListMatchModels();
		//list排序问题
		for (int j = 0; j <listLoan.size(); j++) {
			Double dbTmp=0.00;
			for (int k = 0; k < listLend.size(); k++) {
				dbTmp+=listLend.get(k).getLendAmount();
			}
			
			if (dbTmp<listLoan.get(j).getLoanAmount()) {
				continue;
			}
			
			for (int i = 0; i <listLend.size(); i++){
				if (listLoan.get(j).getLoanAmount()==0) {
					//贷款人的钱已经全部满足了
					break;
				}
				if (listLend.get(i).getLendAmount()==0) {
					continue;
				}
				MatchModel m=new MatchModel();
				m.setLend2match(listLend.get(i));
				m.setLoan2match(listLoan.get(j));
				m=addMatchResultOne(m);
				listMatchModels.add(m);//匹配结果列表
				listLend.set(i, m.getLend2match());
				listLoan.set(j, m.getLoan2match());
				
			}
		}
		
		//构造返回值
		matchModel.setListLend(listLend);
		matchModel.setListLoan(listLoan);
		matchModel.setListMatchModels(listMatchModels);//匹配好的记录，和addmatchResultOne的返回值是一样的
		
		return matchModel;
	}
	
	/**
	 * 从list中找出金额可以凑出db的多个lend2match
	 * list需要根据金额从小到大排序
	 * @param listLend  list,需要凑的金额，几个出借人组合数量(第一次从2开始),开始的序号（第一次为0）
	 * @param db
	 */
	
	public static List<Lend2match> makeUp(List<Lend2match> listLend,Double db ,int num,int index) {
		if (num>listLend.size()) {
			//数量超出，整个list中没有合适的组合
			return null;
		}
		//记录合适的makeup
		List<Lend2match> listMakeUp=new ArrayList<Lend2match>();
		
		List<Lend2match> listReturn=new ArrayList<Lend2match>();
		
		double dbMakeUp=0;
		//计算出最小的几个值，预留一个空缺
		for (int i = 0; i <num-1; i++) {
			dbMakeUp+=listLend.get(i+index).getLendAmount();
			listMakeUp.add(listLend.get(i+index));//先存起来记录
		}
		Double tmp=dbMakeUp;
		//记录要舍去的list
		List<Lend2match> listDel=new ArrayList<Lend2match>();
		
		for (int i = num+index-1; i < listLend.size(); i++) {
			dbMakeUp+=listLend.get(i).getLendAmount();
			if (dbMakeUp==db) {//正好相等,记录并跳出
				listMakeUp.add(listLend.get(i));
				listReturn=listMakeUp;
				return listReturn;   //只取第一次匹配合适的记录
			}
			if (dbMakeUp>db) {//超出，舍去最大的那个记录
				listDel.add(listLend.get(i));
			}
			dbMakeUp=tmp;//值还原
		}
		//删除要舍去的
		for (Lend2match t:listDel) {
			listLend.remove(t);
		}
		
		//组合遍历
		List<Lend2match> listRec=new ArrayList<Lend2match>();
		listRec=traversal(listLend, listRec, db, num, index);
		if (listRec!=null&&listRec.size()!=0) {
			listReturn=listRec;
		}
		
		
		index++;
		//需要根据新的list，从序号为0开始依次作为组合中最小的
		if (index+num+1>listLend.size()) {
			num++;
			index=0;
		}
		if (listReturn==null||listReturn.size()==0) {
			 //继续迭代，增加组合的数量
			listReturn=makeUp(listLend, db, num,index);
		}
	   
		return listReturn;
	}
	/**
	 * 组合遍历
	 * @param listLend
	 * @param listReturn
	 * @param db
	 * @param num
	 * @param index
	 * @return
	 */
	
	public static List<Lend2match> traversal(List<Lend2match> listLend,List<Lend2match> listReturn,Double db ,int num,int index){
		Double dbTmp=db;
		for (int i = index; i < listLend.size()-1-num; i++) {
			
			db=db-listLend.get(index).getLendAmount();
			if (db==0) {//正好匹配
				listReturn.add(listLend.get(index));
			}else if (db>0) {
				int index1=i+1;
				int num1=num-1;//去寻找下一个合适的数，故数量减1
				if (num1==0) {
					return null;
				}
				listReturn=traversal(listLend,listReturn, db, num1, index1);
				if (listReturn!=null&&listReturn.size()!=0) {
					listReturn.add(listLend.get(index));
				}else {
					listReturn=null;
				}
			}else if (db<0) {
				listReturn=null;
			}	
			db=dbTmp;
		}
		return listReturn;
	}
	
//	public static List<Lend2match> makeUp(List<Lend2match> listLend,Double db ,int num,int index) {
//		if (num>listLend.size()) {
//			//数量超出，整个list中没有合适的组合
//			return null;
//		}
//		//记录合适的makeup
//		List<Lend2match> listMakeUp=new ArrayList<Lend2match>();
//		
//		List<Lend2match> listReturn=new ArrayList<Lend2match>();
//		
//		double dbMakeUp=0;
//		//计算出最小的几个值，预留一个空缺
//		for (int i = 0; i <num-1; i++) {
//			dbMakeUp+=listLend.get(i+index).getLendAmount();
//			listMakeUp.add(listLend.get(i+index));//先存起来记录
//		}
//		Double tmp=dbMakeUp;
//		//记录要舍去的list
//		List<Lend2match> listDel=new ArrayList<Lend2match>();
//		
//		for (int i = num+index-1; i < listLend.size(); i++) {
//			dbMakeUp+=listLend.get(i).getLendAmount();
//			if (dbMakeUp==db) {//正好相等,记录并跳出
//				listMakeUp.add(listLend.get(i));
//				listReturn=listMakeUp;
//				return listReturn;   //只取第一次匹配合适的记录
//			}
//			if (dbMakeUp>db) {//超出，舍去最大的那个记录
//				listDel.add(listLend.get(i));
//			}
//			dbMakeUp=tmp;//值还原
//		}
//		//删除要舍去的
//		for (Lend2match t:listDel) {
//			listLend.remove(t);
//		}
//		
//		index++;
//		//需要根据新的list，从序号为0开始依次作为组合中最小的
//		if (index+num+1>listLend.size()) {
//			num++;
//			index=0;
//		}
//		if (listReturn==null||listReturn.size()==0) {
//			 //继续迭代，增加组合的数量
//			listReturn=makeUp(listLend, db, num,index);
//		}
//	   
//		return listReturn;
//	}
//	

}
