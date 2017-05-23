/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class PageModel  implements Serializable{
	
	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(PageModel.class);

	/**
	 * Constructor
	 */
	public PageModel() {
		super();
	}
	/**
	 * 对应分页条件查询出来的数据
	 */
	@SuppressWarnings("unchecked")
	private Collection data = null;
	/**
	 * 当前页
	 */
	private int curPage = 1;
	/**
	 * 每页的记录数
	 */
	private int pageSize = 20;
	/**
	 * 所有的记录数
	 */
	private int rowsCount;
	/**
	 * 一共多少页
	 */
	private int pageCount;
	/**
	 * 开始行
	 */
	private int rowS;
	/**
	 * 结束行
	 */
	private int rowE;
	/**
	 * url
	 */
	private String url;
	/**
	 * 查询对象
	 */
	private Object model;
	/**
	 * 加载页面目标
	 */
	private String targetDiv = "";


	/**
	 * @param request HttpServletRequest
	 * @param rowsCount int
	 * @param url string
	 * @param model object
	 */
	public void init(HttpServletRequest request, int rowsCount, String url, Object model) {
		if (url != null && !"".equals(url)) {
			this.url = url;
		} else {
			this.url = request.getRequestURL() + "";
		}
		//this.pageSize = parseInt(request.getParameter("pageSize"),1);
		this.targetDiv = request.getParameter("targetDiv");
		this.model = model;
		this.curPage = parseInt(request.getParameter("curPage"), 1);
		this.rowsCount = rowsCount;
		this.pageSize = parseInt(request.getParameter("pageSize"), this.pageSize);

		this.pageCount = rowsCount / pageSize + (rowsCount % pageSize == 0 ? 0 : 1);
		this.rowS = (curPage - 1) * pageSize;
		this.rowE = pageSize;

	}
	
	/**
	 * @param request HttpServletRequest
	 * @param rowsCount int
	 * @param url string
	 * @param model object
	 */
	public void init(HttpServletRequest request, String url, Object model) {
		if (url != null && !"".equals(url)) {
			this.url = url;
		} else {
			this.url = request.getRequestURL() + "";
		}
		this.targetDiv = request.getParameter("targetDiv");
		this.model = model;
		this.curPage = parseInt(request.getParameter("curPage"), 1);
		this.pageSize = parseInt(request.getParameter("pageSize"), this.pageSize);
	}
	
	/**
	 * @return 导航条
	 */
	@SuppressWarnings("unchecked")
	public String getPageNavigation() {
		// 最终返回的分页导航条
		//System.out.println("||||||||||||||||进入||||||||||||||||||||");
		String pageNavigation = "共有" + this.rowsCount + "条数据\n";
		/*
		System.out.println("---------------------");
		System.out.println("rowsCount:"+this.rowsCount);
		System.out.println("pageSize:"+this.pageSize);
		System.out.println("rowS:"+this.rowS);
		System.out.println("rowE:"+this.rowE);
		System.out.println("url:"+this.url);
		System.out.println("pageCount:"+this.pageCount);
		System.out.println("curPage:"+this.curPage);
		System.out.println("---------------------");
		*/
		int curPageNos = this.rowsCount % pageSize == 0 ? this.rowsCount / pageSize : this.rowsCount / pageSize + 1;
		/*
		 * 为了保证一个页面可以有多个分页，对gotoPage函数的名称动态生成
		 */
		String jsFunName = "gotoPage$" + targetDiv;
		// 记录数超过一页,需要分页
		//if (this.rowsCount > pageSize) {
			if (url != null && !"".equals(url)) {

				if (url.indexOf("?") > -1) {
					// 如果url中已经包含了其他的参数,就把curPageNo参数接在后面
					url += "&";
				} else {
					// 如果url中没有别的参数
					url += "?";
				}
				// 生成一个提交页面的函数
				/*
				pageNavigation += "<script>\n";
				pageNavigation += "function gotoPage(page_num,flag){";
				pageNavigation += "if(flag=='go' && page_num > " + curPageNos + "){return;}";
				pageNavigation += "document.getElementById('curPage').value=page_num;\n";
				pageNavigation += "document.getElementById('queryList').submit();\n";
				pageNavigation += "}</script>\n";
				*/

				pageNavigation += "<script>\n";
				pageNavigation += "function " + jsFunName + "(page_num,flag){";
				pageNavigation += "if(flag=='go' && page_num > " + curPageNos + "){return;}";
				pageNavigation += "$('#curPage').attr('value',page_num);\n";
//				pageNavigation += "var pageForm = $('#curPage').parents('form');";
//				pageNavigation += "var params=pageForm.serialize();";
//				pageNavigation += "alert(params);";
//				pageNavigation += "alert(pageForm.attr('action'));";
				pageNavigation += "var params=" + "$('#" + targetDiv + " input').map(function(){  return $(this).attr('name') + '='+ $(this).val();  }).get().join('&');\n";
				pageNavigation += "$.ajax({";
				pageNavigation += "type:'POST',";
//				pageNavigation += "url:pageForm.attr('action'),";
				pageNavigation += "url:'" + url + "',";
				pageNavigation += "data:encodeURI(params+'&targetDiv=" + targetDiv + "'),";
				pageNavigation += "dataType:'html',";
				pageNavigation += "success:function(data){";
				pageNavigation += "$('#" + targetDiv + "').html(data);";
				pageNavigation += "}";
				pageNavigation += "});";
				pageNavigation += "}";
				pageNavigation += "\n</script>\n";
				//System.out.println(pageNavigation);
			}

			pageNavigation += "每页&nbsp;"
					 + "<input id='pageSize' name='pageSize' style='text-align:center;border:1px inset #00008B;	background-color: #FFFFFF;width:30px;' value='" + pageSize + "'/>"
					+ "&nbsp;"
					+ "条&nbsp;&nbsp;"
					+ "当前第" + curPage
					+ "页&nbsp;&nbsp;共" + curPageNos
					+ "页&nbsp;&nbsp;"
					+ "进到<input type='text' id='gotoMyPage' name='gotoMyPage' style='text-align:center;border: 1px inset #00008B;	background-color: #FFFFFF;width:30px;' value='" + curPage + "' /><a href='javascript:void(0)' onclick='javascript:" + jsFunName + "(document.getElementById(\"gotoMyPage\").value,\"go\")'>GO</a>&nbsp;&nbsp;";

			// 如果不是第一页,导航条将包含"首页"和"上一页"的连接
			if (curPage > 1) {
				pageNavigation += "[<a href=\"javascript:void(0);\" onclick=\"" + jsFunName + "(1);return false;\">首页</a>]&nbsp;"
						+ "[<a href=\"javascript:void(0);\" onclick=\"" + jsFunName + "("
						+ (curPage - 1) + ");return false;\">上一页</a>]&nbsp;&nbsp;";
			} else {
				pageNavigation += "[首页]&nbsp;[上一页]&nbsp;&nbsp;";
			}
			// 如果不是最后一页,导航条将包含"末页"和"下一页"
			if (curPage < curPageNos) {
				pageNavigation += "[<a href=\"javascript:void(0);\" onclick=\"" + jsFunName + "("
						+ (curPage + 1)
						+ ");return false;\">下一页</a>]&nbsp;"
						+ "[<a href=\"javascript:void(0);\" onclick=\"" + jsFunName + "("
						+ curPageNos + ");return false;\">末页</a>]";
			} else {
				pageNavigation += "[下一页]&nbsp;[末页]";
			}
		//}

		pageNavigation += "\n";
		pageNavigation += "\n";
		pageNavigation += "<input id='curPage' name='curPage' type='hidden' value=''/>";
		pageNavigation += "\n";
		if (model instanceof Map) {
			Map map = (Map) model;
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = map.get(key);
				pageNavigation += "<input id='" + key + "' name='" + key + "' type='hidden' value='" + (null == value ? "" : value) + "'/>\n";
			}
		} else {
			Field[] fields = model.getClass().getDeclaredFields();
			//System.out.println(fields.length+"-----------");
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getType().equals(int.class)
						|| field.getType().equals(Integer.class)
						|| field.getType().equals(long.class)
						|| field.getType().equals(Long.class)
						|| field.getType().equals(double.class)
						|| field.getType().equals(String.class)
						|| field.getType().equals(boolean.class)) {
					try {
						pageNavigation += "<input id='" + field.getName() + "' name='" + field.getName() + "' type='hidden' value='" + (null == field.get(model) ? "" : field.get(model)) + "'/>\n";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (field.getType().equals(Date.class)) {
					try {
						pageNavigation += "<input id='" + field.getName() + "' name='" + field.getName() + "' type='hidden' value='"
								+ (null == field.get(model) ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) field.get(model))) + "'/>\n";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		log.info("分页模型信息：" + pageNavigation);
		return pageNavigation;
	}


	/**
	 * @return string
	 */
	public String getTargetDiv() {
		return targetDiv;
	}
	/**
	 * @param targetDiv string
	 */
	public void setTargetDiv(String targetDiv) {
		this.targetDiv = targetDiv;
	}
	/**
	 * @return object
	 */
	public Object getModel() {
		return model;
	}
	/**
	 * @param model model
	 */
	public void setModel(Object model) {
		this.model = model;
	}
	/**
	 * @param data Collection
	 */
	@SuppressWarnings("unchecked")
	public void setData(Collection data) {
		this.data = data;
	}

	/**
	 * @param s string
	 * @param defaultValue int
	 * @return int
	 */
	private int parseInt(Object s, int defaultValue) {
		if (s != null && s.toString().matches("\\d+")) {
			return Integer.parseInt(s.toString());
		} else {
			return defaultValue;
		}
	}
	/**
	 * @return start row
	 */
	public int getRowS() {
		return this.rowS;
	}
	/**
	 * @return end row
	 */
	public int getRowE() {
		return this.rowE;
	}
	/**
	 * @return current page num
	 */
	public int getCurPage() {
		return curPage;
	}
	/**
	 * @param curPage current page num
	 */
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	/**
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return int
	 */
	public int getRowsCount() {
		return rowsCount;
	}
	/**
	 * @param rowsCount row count
	 */
	private void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}
	
	/**
	 * 
	 * @param rowsCount
	 */
	public void initRowsCount(int rowsCount){
		this.rowsCount = rowsCount;
		this.pageCount = rowsCount / pageSize + (rowsCount % pageSize == 0 ? 0 : 1);
	}
	
	/**
	 * @return int
	 */
	public int getPageCount() {
		return pageCount;
	}
	/**
	 * @param pageCount page count
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public Collection getData() {
		return data;
	}
	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url ulr
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @param rowS row start
	 */
	public void setRowS(int rowS) {
		this.rowS = rowS;
	}
	/**
	 * @param rowE row end
	 */
	public void setRowE(int rowE) {
		this.rowE = rowE;
	}

}
