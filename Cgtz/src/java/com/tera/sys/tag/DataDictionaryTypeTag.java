package com.tera.sys.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.tera.sys.model.DataDictionary;

public class DataDictionaryTypeTag extends RequestContextAwareTag {

	private static final long serialVersionUID = 1L;

	private String name = "";// 字段名称
	private String value = "";// 字段名称
	private String keyName = "";// 字典名称
	private String isOnChange = "";// 是否需要onChange方法：true、false
	private String linkageName = "";// 联动字段名称
	private String linkageKeyName = "";// 联动字典名称

	public String getName() {
		return name == null ? "" : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKeyName() {
		return keyName == null ? "" : keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getIsOnChange() {
		return isOnChange;
	}

	public void setIsOnChange(String isOnChange) {
		this.isOnChange = isOnChange;
	}

	public String getLinkageName() {
		return linkageName == null ? "" : linkageName;
	}

	public void setLinkageName(String linkageName) {
		this.linkageName = linkageName;
	}

	public String getLinkageKeyName() {
		return linkageKeyName == null ? "" : linkageKeyName;
	}

	public void setLinkageKeyName(String linkageKeyName) {
		this.linkageKeyName = linkageKeyName;
	}

	@Override
	protected int doStartTagInternal() throws Exception {

		try {
			// 组装数据字典下拉框
			StringBuilder str = new StringBuilder();
			
			str.append("<input id=\"");
			str.append(name);
			str.append("\" name=\"");
			str.append(name);
			str.append("\" value=\"");
			str.append(value);
			str.append("\" type=\"text\" editable=\"false\" class=\"easyui-combobox\"/>");
			str.append("<script type=\"text/javascript\">");
			str.append("$(document).ready(function() {");
//			str.append("var url = \"sys/datadictionary/listjason.do?keyName=");
//			str.append(keyName);
//			str.append("\";");
//			if ("".equals(keyName)) {
//				str.append("url = \"\";");
//			}
			str.append("$(\"#");
			str.append(name);
			str.append("\").combobox(\"clear\");");
			str.append("$(\"#");
			str.append(name);
			str.append("\").combobox({");
//			str.append("url: url,");
			str.append("data:");
			
			Map<String, List<DataDictionary>> dictMap = (Map<String, List<DataDictionary>>) this
					.getRequestContext().getWebApplicationContext()
					.getServletContext().getAttribute("DATADICTS");

			List<DataDictionary> list = dictMap.get(keyName);
			List<DataDictionary> list2=new ArrayList<DataDictionary>();
			DataDictionary data=new DataDictionary();
			data.setKeyProp("");
			data.setKeyValue("请选择");
			list2.add(data);
			list2.addAll(list);
			JSON js = JSONArray.fromObject(list2);
			
			str.append(js.toString());
			
			str.append(",");
			str.append("valueField:'keyProp',");
			str.append("textField:'keyValue'");
			if ("true".equals(isOnChange)) {
				str.append(",");
				str.append("onChange: function(newValue, oldValue){");
				str.append("$(\"#");
				str.append(linkageName);
				str.append("\").combobox(\"clear\");");
				str.append("var url_ = \"sys/datadictionary/listjason.do?keyName=");
				str.append(linkageKeyName);
				str.append("&parentKeyProp=\" + encodeURI(newValue);");
				str.append("$('#");
				str.append(linkageName);
				str.append("').combobox('reload',url_);");
				str.append("}");
			}
			str.append("});");
			str.append("});");
			str.append("</script>");

			pageContext.getOut().println(str.toString());

		} catch (Exception ex) {
		}
		return EVAL_PAGE;
	}

}
