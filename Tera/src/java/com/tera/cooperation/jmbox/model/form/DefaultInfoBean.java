package com.tera.cooperation.jmbox.model.form;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import com.tera.cooperation.jmbox.constant.JMLendingConstant;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.StringUtils;


/**
 * 发送违约信息   请求发送接口 信息 类
 * @author XunXiake
 *
 */
public class DefaultInfoBean extends JMQueryObject {


	private String cid;			//JM提供的 客户 ID
	private long timeStamp;		//时间戳 用来加密使用
	private String customers;	//违约 客户信息

	{
		timeStamp=System.currentTimeMillis();
	}
	
	public DefaultInfoBean(String cid,List<DefaultCustomersInfo> infos){
		this.cid=cid;
		setCustomersJSON(infos);
	}
	/*public DefaultInfoBean(String cid,String customers){
		this.cid=cid;
		this.customers=customers;
	}*/
	
	
	public void setCustomersJSON(List<DefaultCustomersInfo> infos){
//		customers=JsonUtil.list2json(infos);
		
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			private String format ="yyyy-MM-dd HH:mm:ss";
			
		   @Override  
		    public Object processArrayValue(Object paramObject,  
		            JsonConfig paramJsonConfig) {  
		        return process(paramObject);  
		    }  
		  
		    @Override  
		    public Object processObjectValue(String paramString, Object paramObject,  
		            JsonConfig paramJsonConfig) {  
		        return process(paramObject);  
		    }  
		    private Object process(Object value){  
		        if(value instanceof Date){    
		            SimpleDateFormat sdf = new SimpleDateFormat(format);    
		            return sdf.format(value);  
		        }    
		        return value == null ? "" : value.toString();    
		    }  
		}); 
		
		JSONArray jsonarray = JSONArray.fromObject(infos,jsonConfig);
		customers=jsonarray.toString();
	}
	
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCustomers() {
		return customers;
	}

	/*public void setCustomers(String customers) {
		this.customers = customers;
	}*/

	public long getTimeStamp() {
		return timeStamp;
	}
	
	/*public void setTimeStamp(long timeStamp) {
		this.timeStamp=timeStamp ;
	}*/

	
	
	
	
	
	
	


	/**
	 * 得到当前 对象的 MD5 KEY 根据 约束的 规则 MD5加密
	 * @param uKey  渠道提供的EKY
	 * @return
	 */
	@Override
	public String getMD5Key(String uKey){
		
		StringBuffer sbf=new StringBuffer();
		
		sbf.append(this.cid);
		sbf.append(this.timeStamp);
		sbf.append(this.customers);
		sbf.append(uKey);
		String keyStr=sbf.toString().toUpperCase();
		String md5Key=StringUtils.md5(keyStr).toUpperCase();
		
		System.out.println("积木盒子违约信息推送请求>>> MD5_SRC:"+keyStr+"\nMD5:"+md5Key);
		
		return md5Key;
	}
	
	/**
	 * 得到 GET 请求的 参数 字符串 不包含  MD5KEY
	 * @return
	 */
	@Override
	public String getParams(){
		StringBuffer sbf=new StringBuffer();
		Map<String, Object> beanMap = ObjectUtils.describe(this);
		List<String> nameList=this.getPropertyList();
		//拼接参数
		for (int i = 0; i < nameList.size(); i++) {
			String nameKey=nameList.get(i);
			String value=String.valueOf(beanMap.get(nameKey));
			try {
				value=URLEncoder.encode(value,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("积木盒子违约信息推送请求，参数转换异常： nameKey："+nameKey+"value："+value+"。\nerror"+e);
			}
			sbf.append(nameKey);
			sbf.append("=");
			sbf.append(value);
			if(i<nameList.size()-1){
				sbf.append("&");
			}
		}
		return sbf.toString();
	}

	
	
	/*public static void main(String[] ager){
		String spid =JMLendingConstant.DUEINFO_ID;
		String userKey = JMLendingConstant.DUEINFO_KEY;
		
		String json="[{\"projectId\":\"11111\",\"customerName\":\"客户姓名\",\"idCard\":\"111111111111\",\"moneyOverDue\":6029.79,\"interestOverDue\":668.21,\"moneyLeave\":69604.92,\"payDate\":\"2015-03-06\",\"loanBackDate\":\"2015-2-16 18:45:45\",\"dueDays\":29,\"reason\":\"\",\"overdueNumber\":1,\"lastModifyDate\":\"2015-04-04 01:23:13\"},{\"projectId\":\"11112\",\"customerName\":\"客户姓名2\",\"idCard\":\"111111111111\",\"moneyOverDue\":6029.79,\"interestOverDue\":668.21,\"moneyLeave\":69604.92,\"payDate\":\"2015-03-06\",\"loanBackDate\":\"2015-2-16 18:45:45\",\"dueDays\":29,\"reason\":\"\",\"overdueNumber\":1,\"lastModifyDate\":\"2015-04-04 01:23:13\"},{\"projectId\":\"11113\",\"customerName\":\"客户姓名3\",\"idCard\":\"111111111111\",\"moneyOverDue\":6029.79,\"interestOverDue\":668.21,\"moneyLeave\":69604.92,\"payDate\":\"2015-03-06\",\"loanBackDate\":\"2015-2-16 18:45:45\",\"dueDays\":29,\"reason\":\"\",\"overdueNumber\":1,\"lastModifyDate\":\"2015-04-04 01:23:13\"}]";
		
		SendOverDueInfoBean info = new SendOverDueInfoBean(spid,json);
		System.out.println(info.getMD5Key(userKey));
		System.out.println(info.getParams());
		System.out.println(info.getParamsAndMd5Key(userKey));
	}*/
	
	
	
	
	
}
