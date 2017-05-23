package com.hikootech.mqcash.dto;

/**
 * 获取二维码传输实体
 * 
 * @author QYANZE
 *
 */
public class WxUnifiedOrderDTO {

	private String body;             // 商品描述     
	private String detail;           // 商品详情-非必需     
	private String attach;           // 附加数据 -非必需  
	private String out_trade_no;     // 商户订单号   
	private String fee_type;         // 货币类型-非必需     
	private int total_fee;         	 // 总金额       
	private String time_start;       // 交易起始时间-非必需 
	private String time_expire;      // 交易结束时间-非必需 
	private String goods_tag;        // 商品标记-非必需     
	private String product_id;       // 商品ID-NATIVE(扫一扫支付)必需       
	private String openid;           // 用户标识-非必需     
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	public String getGoods_tag() {
		return goods_tag;
	}
	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
