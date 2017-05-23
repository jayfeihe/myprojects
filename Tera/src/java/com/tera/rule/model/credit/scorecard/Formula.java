package com.tera.rule.model.credit.scorecard;

import java.util.ArrayList;
import java.util.List;

public class Formula {

	/**
	 * 评分因子集合
	 */
	private List<Factor> list = new ArrayList<Factor>();
	
	/**
	 * 分数
	 */
	private double score;
	
	/**
	 * 分数
	 */
	private String level;
	

	/**
	 * 添加评分因子
	 * 
	 * @param name
	 *            因子名称
	 * @param score
	 *            分数
	 */
	public void addFactor(String name, double score) {
		Factor factor = new Factor();
		factor.setName(name);
		factor.setScore(score);

		list.add(factor);
	}

	/**
	 * 获得评分总分数
	 * @return
	 */
	public Double getScore() {
		Double in = 0.0;
		if (list != null && !list.isEmpty()) {
			for (Factor factor : list) {
				in += factor.getScore();
			}
		} else {
			return null;
		}
		return in;
	}

	public List<Factor> getList() {
		return list;
	}

	public void setList(List<Factor> list) {
		this.list = list;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
