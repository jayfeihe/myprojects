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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Menu;
import com.tera.sys.model.Org;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.User;
import com.tera.sys.service.LogInOutService;
import com.tera.sys.service.MenuService;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.SysLogService;
import com.tera.sys.service.UserExtService;
import com.tera.util.NetUtils;

/**
 * @author Wallace chu
 *
 */
@Controller
public class LogInOutController {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(LogInOutController.class);

	/**
	 * logInOutService
	 */
	@Autowired
	private LogInOutService logInOutService;
	/**
	 * menuserivce
	 */
	@Autowired
	private MenuService menuService;
	/***/
	@Autowired
	private SysLogService sysLogService;

	@Autowired(required=false) //自动注入
	private OrgService orgService;
	
	@Autowired(required=false) //自动注入
	private UserExtService userExtService;

	/**
	 * 登录验证
	 * @param loginid 用户名
	 * @param password 密码
	 * @param orgId 机构代码
	 * @param request request
	 * @param response response
	 * @return String
	 * @throws Exception Exception
	 */
	@RequestMapping("/login.do")
	public String goLogin(String loginid, String password,String rand, HttpServletRequest request,
					    HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		System.out.println("LogInOutController: " + "-loginid:" + loginid + "-password:" + password);
		if (loginid == null || "".equals(loginid)) {
			return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("用户名不能为空！", "UTF-8");
		}
		if (password == null || "".equals(password)) {
			return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("密码不能为空！", "UTF-8");
		}
//		if (orgId == null || "".equals(orgId)) {
//			return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("机构不能为空！", "UTF-8");
//		}
		if (rand == null || "".equals(rand)) {
			return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("验证码不能为空！", "UTF-8");
		}
		String randS = (String) request.getSession().getAttribute("rand");
		if (randS != null && !randS.equalsIgnoreCase(rand)) {
			return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("验证码不正确！", "UTF-8");
		}else{
			//登录操作后 作废原先验证码
			char[] yzcodes={'0','2','3','4','5','6','7','8','9',
					'a','b','c','d','e','f','j','h','g','k','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
					'A','B','C','D','E','F','J','H','G','K','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
			String sRand=""; 
			Random random = new Random();
			for (int i=0;i<4;i++) { 
				sRand+=yzcodes[random.nextInt(yzcodes.length)];
			}
			request.getSession().setAttribute("rand",sRand); 
		}
		
		User user = logInOutService.checkUser(loginid, password);
		if (null != user) {
			if(!loginid.equals(user.getLoginId())){
				return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("用户名错误，请检查大小写", "UTF-8");
			}
			//挂起修改判断条件，挂起可以登录，只是不接收分单
//			if(!"1".equals(user.getState())){
			if("0".equals(user.getState())){
				return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("该用户已被禁用！", "UTF-8");
			}
			//登录后将用户表示存到session里面
			request.getSession().setAttribute(SysConstants.LOGIN_ID, user.getLoginId());
			request.getSession().setAttribute(SysConstants.LOGIN_USER, user);
			String orgId="";
			if (user.getIsAdmin()==1) {
				orgId="86";
			}else {
				orgId=userExtService.queryByKey(loginid).getOrgId();
			}
			Org org = orgService.queryByOrgId(orgId);
			request.getSession().setAttribute(SysConstants.LOGIN_ORG, org);

			String ipAddress = NetUtils.getIpAddr(request);
			request.getSession().setAttribute(SysConstants.LOGIN_IP, ipAddress);
			//所有菜单
			List<Menu> menus =new ArrayList<Menu>();
			if (1 == user.getIsAdmin()) {
				System.out.println("is admin true-------------");
				List<Menu> allMenus = new ArrayList<Menu>();
				menus=menuService.getAvailableMenu();
				menuService.sort(menuService.getAllAvailableMenu(menus), allMenus, null, 0);
				request.getSession().setAttribute(SysConstants.LOGIN_MENUS, allMenus);
			} else {
				System.out.println("false-------------");
				//用户所有的menus
				List<Menu> userMenus = new ArrayList<Menu>();
				menus=menuService.getMenuByOrgAndUser(user.getLoginId(),org.getOrgId());
				menuService.sort(menuService.getAllAvailableMenu(menus), userMenus, null, 0);
				request.getSession().setAttribute(SysConstants.LOGIN_MENUS, userMenus);
			}
			this.sysLogService.addSysLog(new SysLog(ipAddress, user, orgId, "登录"));
			log.info("LogInOutController: 登录成功！");
			log.info(thisMethodName+":end");
			return "redirect:frame.do";
		} else {
			return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("用户名和密码错误！", "UTF-8");
		}
	}

	/**
	 * @param request request
	 * @param response response
	 * @param map map
	 * @return string
	 */
	@RequestMapping("/logout.do")
	public String goMainTabs(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
//		String ipAddress = NetUtils.getIpAddr(request);
//		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		request.getSession().removeAttribute(SysConstants.LOGIN_ID);
		request.getSession().invalidate();
		log.info(thisMethodName+":end");
		return "redirect:index.do";
	}
	
	/**
	 * @param request request
	 * @param response response
	 * @param map map
	 * @return string
	 * @throws IOException 
	 */
	@RequestMapping("/randomImg.do")
	public void randomImg(HttpServletRequest request, HttpServletResponse response ) throws IOException {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		OutputStream out=response.getOutputStream();
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0); 
		int width=60, height=20; 
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
		Graphics g = image.getGraphics(); 
		Random random = new Random(); 
		g.setColor(getRandColor(200,250)); 
		g.fillRect(0, 0, width, height); 
		g.setFont(new Font("Times New Roman",Font.PLAIN,18)); 
		g.setColor(getRandColor(160,200)); 
		for (int i=0;i<200;i++) { 
			int x = random.nextInt(width); 
			int y = random.nextInt(height); 
			int xl = random.nextInt(12); 
			int yl = random.nextInt(12); 
			g.drawLine(x,y,x+xl,y+yl); 
		} 
		
		char[] yzcodes={'0','2','3','4','5','6','7','8','9',
				'a','b','c','d','e','f','j','h','g','k','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
				'A','B','C','D','E','F','J','H','G','K','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		String sRand=""; 
		for (int i=0;i<4;i++) { 
			String rand=String.valueOf(yzcodes[random.nextInt(yzcodes.length)]);
			sRand+=rand; 
			g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110))); 
			g.drawString(rand,13*i+6,16); 
		}
		request.getSession().setAttribute("rand",sRand); 
		
		// 将认证码存入SESSION 
		request.getSession().setAttribute("rand",sRand); 
		g.dispose(); 
		ImageIO.write(image, "JPEG", out); 
		out.flush();
		out.close();
		log.info(thisMethodName+":end");
	}
	Color getRandColor(int fc,int bc) { 
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Random random = new Random(); 
		if(fc>255) fc=255; 
		if(bc>255) bc=255; 
		int r=fc+random.nextInt(bc-fc); 
		int g=fc+random.nextInt(bc-fc); 
		int b=fc+random.nextInt(bc-fc); 
		log.info(thisMethodName+":end");
		return new Color(r,g,b); 
	} 
	
	
	
	
}
