package com.greenkoo.inter.model;

/**
 * 响应dto
 * 
 * @author QYANZE
 *
 */
public class AdvertInterRespDto {

	private String result; // 0：成功 非0：失败  --详细见处理结果码

	private String sign; // 签名

	public AdvertInterRespDto() {
		super();
	}

	public AdvertInterRespDto(String result) {
		super();
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
