package com.tera.sys.model;

public class ResultObj {
	
	private String state;
	
	private Object result;
	
	private boolean success;
	
	public ResultObj(){
	}
			
	/**
	 * @param state 状态信息
	 * @param result 结果对象
	 * @param success 成功失败
	 */
	public ResultObj(String state, Object result, boolean success){
		this.state = state;
		this.result = result;
		this.success = success;
	}

	public String getState() {
		return state;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


}
