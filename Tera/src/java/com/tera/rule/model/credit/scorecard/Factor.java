package com.tera.rule.model.credit.scorecard;

/**
 * 评分因子 记录评分因子取值情况
 * 
 * @author HanYou
 * 
 */
public class Factor {

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 分数
	 */
	private double score;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
