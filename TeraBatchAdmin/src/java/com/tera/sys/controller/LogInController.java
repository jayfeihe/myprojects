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

import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

/**
 * @author Wallace chu
 *
 */
@Controller
public class LogInController {

	/**
	 * 日志
	 */
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(LogInController.class);

	/**
	 * logInOutService
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * @param request request
	 * @param map map
	 * @return login
	 */
	@RequestMapping("/index.do")
	public String goIndex(HttpServletRequest request, ModelMap map) {
		
		if (request.getSession().getAttribute("loginId") != null ) {
			return "redirect:/batch/";
		}
		map.put("errLoginMsg", request.getParameter("errLoginMsg"));
		log.info("跳转到index.do");
		return "login";
	}
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
	public String goLogin(String loginId,String password,String rand, HttpServletRequest request,
					    HttpServletResponse response) throws Exception {
		
		if (request.getSession().getAttribute("loginId") != null ) {
			return "redirect:/batch/";
		}
		
		String randS = (String) request.getSession().getAttribute("rand");
		if (randS != null && !randS.equalsIgnoreCase(rand)) {
			return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("验证码错误！", "UTF-8");
		}else{
			//登录操作后 作废原先验证码
			char[] yzcodes={'0','1','2','3','4','5','6','7','8','9',
					'a','b','c','d','e','f','j','h','i','g','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
					'A','B','C','D','E','F','J','H','I','G','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
			String sRand=""; 
			Random random = new Random();
			for (int i=0;i<4;i++) { 
				sRand+=yzcodes[random.nextInt(yzcodes.length)];
			}
			request.getSession().setAttribute("rand",sRand); 
		}
		
		User user = userService.checkUser(loginId, password);
		if (null != user ) {
			if(user.getIsAdmin()!=1){
				return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("非系统管理员用户不能登录！", "UTF-8");
			}
			//登录后将用户表示存到session里面
			request.getSession().setAttribute("loginId", loginId);
			request.getSession().setAttribute("user", user);		
			log.info("LogInOutController: 登录成功！");
			return "redirect:/batch/";
		} else {
			return "redirect:index.do?errLoginMsg=" + URLEncoder.encode("用户名和密码错误！", "UTF-8");
		}
	}
	
	@RequestMapping(value = "/batch")
    public String redirectBatchToBatchSlash() {
        return "redirect:/batch/";
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
		
		char[] yzcodes={'0','1','2','3','4','5','6','7','8','9',
				'a','b','c','d','e','f','j','h','i','g','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
				'A','B','C','D','E','F','J','H','I','G','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
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
