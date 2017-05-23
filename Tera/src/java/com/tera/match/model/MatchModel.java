package com.tera.match.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 匹配的工具实体，用于传递数据
 * @author Jesse
 *
 */
public class MatchModel {
	
	//出借方信息
	private Lend2match lend2match;
	
	//借款方的信息
	private Loan2match loan2match;
	
	//撮合结果信息
	private MatchResult matchResult;
	
	//出借方的列表
	private List<Lend2match> listLend =new ArrayList<Lend2match>();
	
	//借款方的列表
	private List<Loan2match> listLoan=new ArrayList<Loan2match>();
	
	//撮合结果列表
	private List<MatchResult> listMatchResult=new ArrayList<MatchResult>();
	
	//matchmodel的列表 ，撮合完成之后所有的记录都存在其中的单个model里面
	private List<MatchModel> listMatchModels=new ArrayList<MatchModel>();

	
	
	
	public Lend2match getLend2match() {
		return lend2match;
	}

	public void setLend2match(Lend2match lend2match) {
		this.lend2match = lend2match;
	}

	public Loan2match getLoan2match() {
		return loan2match;
	}

	public void setLoan2match(Loan2match loan2match) {
		this.loan2match = loan2match;
	}

	public MatchResult getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(MatchResult matchResult) {
		this.matchResult = matchResult;
	}

	public List<Lend2match> getListLend() {
		return listLend;
	}

	public void setListLend(List<Lend2match> listLend) {
		this.listLend = listLend;
	}

	public List<Loan2match> getListLoan() {
		return listLoan;
	}

	public void setListLoan(List<Loan2match> listLoan) {
		this.listLoan = listLoan;
	}

	public List<MatchResult> getListMatchResult() {
		return listMatchResult;
	}

	public void setListMatchResult(List<MatchResult> listMatchResult) {
		this.listMatchResult = listMatchResult;
	}

	public List<MatchModel> getListMatchModels() {
		return listMatchModels;
	}

	public void setListMatchModels(List<MatchModel> listMatchModels) {
		this.listMatchModels = listMatchModels;
	}
	
	
	
}
