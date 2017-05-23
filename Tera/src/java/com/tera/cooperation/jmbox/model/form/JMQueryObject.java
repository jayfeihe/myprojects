package com.tera.cooperation.jmbox.model.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tera.util.ObjectUtils;
import com.tera.util.StringUtils;
public abstract  class JMQueryObject {


	/**
	 * 得到 GET 请求的 参数 字符串 不包含  MD5KEY
	 * @return
	 */
	public String getParams(){
		StringBuffer sbf=new StringBuffer();
		Map<String, Object> beanMap = ObjectUtils.describe(this);
		List<String> nameList=this.getPropertyList();
		//拼接参数
		for (int i = 0; i < nameList.size(); i++) {
			String nameKey=nameList.get(i);
			String value=String.valueOf(beanMap.get(nameKey));
			sbf.append(nameKey);
			sbf.append("=");
			sbf.append(value);
			if(i<nameList.size()-1){
				sbf.append("&");
			}
		}
		return sbf.toString();
	}

	/**
	 * 得到get 请求的参数字符串。 包含 MD5验证Key
	 * @param uKey
	 * @return
	 */
	public String getParamsAndMd5Key(String uKey){
		String prs=getParams();
		prs=prs+"&key="+getMD5Key(uKey);
		return prs;
	}
	/**
	 * 得到当前 对象的 MD5 KEY 根据 约束的 规则 MD5加密
	 * @param uKey  渠道提供的EKY
	 * @return
	 */
	public String getMD5Key(String uKey){
		StringBuffer sbf=new StringBuffer();
		Map<String, Object> beanMap = ObjectUtils.describe(this);
		List<String> nameList=this.getPropertyList();
		//拼接参数
		for (int i = 0; i < nameList.size(); i++) {
			String nameKey=nameList.get(i);
			String value=String.valueOf(beanMap.get(nameKey));
			sbf.append(nameKey);
			sbf.append(value);
		}
		sbf.append(uKey);
		String keyStr=sbf.toString().toUpperCase();
		String md5Key=StringUtils.md5(keyStr).toUpperCase();
		System.out.println("积木盒子查询请求>>> MD5_SRC:"+keyStr+"\nMD5:"+md5Key);
		return md5Key;
	}
	

	/**
	 * 得到 当前类的所有的属性的名字 并且 升序排序
	 * @return
	 */
	public List<String> getPropertyList(){
		List<String> nameList=new ArrayList<String>();
		Class cls =this.getClass();  //com.geocompass.model.STSTBPRPModel
		Field[] fieldlist = cls.getDeclaredFields();
		for (int i = 0; i < fieldlist.length; i++) {
			Field fld = fieldlist[i];
			nameList.add(fld.getName());
		}
		Collections.sort(nameList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				o1=o1.toUpperCase();
				o2=o2.toUpperCase();
				return o1.compareTo(o2);
			}
		});
		return nameList;
	}

}
