package com.tera.interfaces.model;
/**
 * 债权描述
 * @author Jesse
 *
 */
public class PushDebtDesc {


	private String desc; //M	债权描述　车　房　红木　海鲜
	private String use;// 资金用途　红木　海鲜
	private String pledge;//抵押物描述
	private String source;//还款来源
	private String risk;//风控措施
	private String advice;//出借人或债权人意见
	private String guarantee;//担保公司描述
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getPledge() {
		return pledge;
	}
	public void setPledge(String pledge) {
		this.pledge = pledge;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	
}
