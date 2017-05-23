/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.sys.model.Bulletin;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.form.BulletinQBean;
import com.tera.sys.service.BulletinService;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * @author Wy
 *
 */
@Controller

public class BulletinController {
	/**
	 * bulletinService
	 */
	@Autowired
	private BulletinService bulletinService;

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(BulletinController.class);

	/**
	 * 打开添加数据页面
	 * @param request request
	 * @param map map
	 * @return string
	 */
	@RequestMapping("/sys/bulletin/add.do")
	public String sysBulletinAdd(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/bulletin/sysBulletinAdd";
	}
	/**
	 * 添加数据
	 * @param request request
	 * @param response response
	 * @param map map
	 * @return string
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/bulletin/addaction.do")
	public void sysBulletinAddAction(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try{
			Bulletin bulletin = (Bulletin) RequestUtils.getRequestBean(Bulletin.class, request);
			String content = StringUtils.replaceBlank(bulletin.getContent());
			bulletin.setContent(content);
			//如果是
			if ("2".equals(bulletin.getBulState())) {
				bulletin.setPublishTime(new Date());
			}
			this.bulletinService.addBulletin(bulletin);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		}catch(Exception e){
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
//		return "sys/bulletin/sysBulletinQuery";
	}


	/**
	 * 打开查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/sys/bulletin/query.do")
	public String sysBulletinQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/bulletin/sysBulletinQuery";
	}
	/**
	 * 打开数据列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return bulletin list
	 */
	@RequestMapping("/sys/bulletin/list.do")
	public String sysBulletinList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		BulletinQBean bulletinQBean = (BulletinQBean) RequestUtils.getRequestBean(BulletinQBean.class, request);
		Map<String, Object> queryMap = null;
		queryMap = ObjectUtils.describe(bulletinQBean);
		
		pm.init(request, null, bulletinQBean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<Bulletin> bulletins = this.bulletinService.queryPageList(queryMap);
		pm.setData(bulletins);
		pm.initRowsCount(bulletins.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "sys/bulletin/sysBulletinList";
	}
	/**
	 * 打开已公布查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/sys/bulletin/showquery.do")
	public String sysBulletinShowQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/bulletin/sysBulletinShowQuery";
	}
	/**
	 * 打开数据列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return bulletin list
	 */
	@RequestMapping("/sys/bulletin/showlist.do")
	public String sysBulletinShowList(HttpServletRequest request, ModelMap map) throws Exception {
		
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		BulletinQBean bulletinQBean = (BulletinQBean) RequestUtils.getRequestBean(BulletinQBean.class, request);
		Map<String, Object> queryMap = null;
		queryMap = ObjectUtils.describe(bulletinQBean);
		pm.init(request, null, bulletinQBean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<Bulletin> bulletins = this.bulletinService.queryPageList(queryMap);
		pm.setData(bulletins);
		pm.initRowsCount(bulletins.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "sys/bulletin/sysBulletinShowList";
	}
	/**
	 * 打开更新数据页面
	 * @param request request
	 * @param id id
	 * @param map map
	 * @return string
	 */
	@RequestMapping("/sys/bulletin/update.do")
	public String sysBulletinUpdate(int id, HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Bulletin bulletin = this.bulletinService.getBulletinById(id);
		map.put("bulletin", bulletin);
		log.info(thisMethodName+":end");
		return "sys/bulletin/sysBulletinUpdate";
	}
	/**
	 * 更新数据
	 * @param request request
	 * @param response response
	 * @param map map
	 * @return string
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/bulletin/updateaction.do")
	public void sysBulletinUpdateAction(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        try{
		Bulletin bulletin = (Bulletin) RequestUtils.getRequestBean(Bulletin.class, request);
		String content = StringUtils.replaceBlank(bulletin.getContent());
		bulletin.setContent(content);
		//如果是
		if ("2".equals(bulletin.getBulState())) {
			bulletin.setPublishTime(new Date());
		}
		this.bulletinService.updateBulletin(bulletin);
		writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
        }catch(Exception e){
            log.error(thisMethodName+":error",e);
            writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
            writer.flush();
            writer.close();
            throw e;
        }
        writer.flush();
        writer.close();
        log.info(thisMethodName+":end");
//		return "sys/bulletin/sysBulletinQuery";
	}
	/**
	 * 删除数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param ids ids
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/bulletin/delbulletin.do")
	public void sysDelBulletin(String ids, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		int[] idArray = StringUtils.tranStrToIntArray(ids, ",");
		try {
			this.bulletinService.deleteBulletinByIDs(idArray);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
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
	 * 下载数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/bulletin/download.do")
	public void sysDownloadBulletin(int id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Bulletin bulletin = this.bulletinService.getBulletinById(id);

		response.setContentType("application/rtf;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + DateUtils.formatTime(new Date()) + ".html");
		PrintWriter writer = response.getWriter();
		String h = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"></head><body>";
		String f = "</body></html>";
		writer.print(h + bulletin.getContent() + f);
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
}
