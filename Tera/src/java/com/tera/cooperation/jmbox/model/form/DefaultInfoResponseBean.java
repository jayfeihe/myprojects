package com.tera.cooperation.jmbox.model.form;


/**
 * 违约信息推送 返回 结果对象
 * @author XunXiake
 *
 */
public class DefaultInfoResponseBean {

		private String state;		//消息 状态  200 成功    400 验证失败
		private String message;
		
		
		
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
}
