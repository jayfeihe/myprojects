/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package ${bizzPackage}.${modelPackage}.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ${bizzPackage}.${modelPackage}.model.${className};
import ${bizzPackage}.${modelPackage}.service.${className}Service;

import com.tera.sys.model.PageModel;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.controller.BaseController;

/**
 * 
 * ${tableComment}控制器
 * <b>功能：</b>${className}Controller<br>
 * <b>作者：</b>${author}<br>
 * <b>日期：</b>${createDate}<br>
 * <b>版权所有：<b>${copyRight}<br>
 */
@Controller
@RequestMapping("/${jspPath}")
public class ${className}Controller extends BaseController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(${className}Controller.class);
	
	/**
	 * ${className}Service
	 */
	@Autowired(required=false) //自动注入
	private ${className}Service ${lowerName}Service;
	
	/**
	 * 跳转到${tableComment}的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String ${lowerName}Query(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "${jspPath}/${lowerName}Query";
	}

	/**
	 * 显示${tableComment}的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String ${lowerName}List(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(${className}.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request,null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<${className}> ${lowerName}List = this.${lowerName}Service.queryList(queryMap);
		pm.setData(${lowerName}List);
		pm.initRowsCount(${lowerName}List.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "${jspPath}/${lowerName}List";
	}

	/**
	 * 跳转到更新${tableComment}的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String ${lowerName}Update(String ${primaryProp}, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		${className} bean = null;
		//如果存在
		if (null != ${primaryProp} && !"".equals(${primaryProp})) {
			bean  = this.${lowerName}Service.queryByKey(${primaryProp});
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "${jspPath}/${lowerName}Update";
	}

	/**
	 * 保存${tableComment}数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void ${lowerName}Save(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			${className} bean = (${className}) RequestUtils.getRequestBean(${className}.class, request);
			//如果存在
			if (bean.get$!{UprimaryProp}() != 0) {
				bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				this.${lowerName}Service.updateOnlyChanged(bean);
			} else { //如果不存在
				bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
				bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				this.${lowerName}Service.add(bean);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 删除${tableComment}数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void ${lowerName}Delete(String[] ${primaryProp}, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.${lowerName}Service.delete(${primaryProp});
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "关联数据，不能删除！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看${tableComment}的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String ${lowerName}Read(String ${primaryProp}, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		${className} bean = null;
		// 如果存在
		if (null != ${primaryProp} && !"".equals(${primaryProp})) {
			bean = this.${lowerName}Service.queryByKey(${primaryProp});
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "${jspPath}/${lowerName}Read";
	}

}
