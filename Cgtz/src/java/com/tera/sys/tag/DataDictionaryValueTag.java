package com.tera.sys.tag;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.tera.sys.model.DataDictionary;

public class DataDictionaryValueTag extends RequestContextAwareTag {

	private static final long serialVersionUID = 1L;

	private String keyProp = "";// 字典属性
	private String keyName = "";// 字典名称

	public String getKeyProp() {
		if (this.keyProp == null) {
			return "";
		}
		return keyProp;
	}

	public void setKeyProp(String keyProp) {
		this.keyProp = keyProp;
	}

	public String getKeyName() {
		if (this.keyName == null) {
			return "";
		}
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		try {
			Map<String, List<DataDictionary>> dictMap = (Map<String, List<DataDictionary>>) this
					.getRequestContext().getWebApplicationContext()
					.getServletContext().getAttribute("DATADICTS");

			List<DataDictionary> list = dictMap.get(keyName);
			
			// 得到数据字典值
			DataDictionary dataDictionary = null;
			if(list!=null&&!list.isEmpty()){
				dataDictionary = list.get(0);
				for (DataDictionary dd : list) {
					if(keyProp.equals(dd.getKeyProp())){
						dataDictionary = dd;
					}
				}
			}
			if (dataDictionary != null) {
				pageContext.getOut().print(dataDictionary.getKeyValue());
			} else {
				if (this.getKeyProp() != null) {
					if (this.getKeyProp().length() == 32) {
						pageContext.getOut().print("");
					} else {
						pageContext.getOut().print(this.getKeyProp());
					}
				}
			}
			
		} catch (Exception ex) {
		}
		return super.EVAL_PAGE;
	}

}
