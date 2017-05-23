package com.greenkoo.sys.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.sys.model.SysJsonMsg;
import com.greenkoo.sys.model.SysOperationRecord;
import com.greenkoo.sys.model.SysUser;
import com.greenkoo.sys.service.ISysOperationRecordService;
import com.greenkoo.sys.service.ISysUserService;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.IpUtil;
import com.greenkoo.utils.SecurityUtil;
import com.greenkoo.utils.StringUtil;

/**
 * 登录Controller
 * 
 * @author QYANZE
 *
 */
@Controller
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysOperationRecordService sysOperationRecordService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/loginAuth", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public SysJsonMsg loginAuth(String account, String pwd, String code, HttpServletRequest request) {
		try {
			// 解密账户
			String accountDecode = SecurityUtil.BASE64Decode(account);
			
			if (StringUtil.isEmpty(accountDecode)) {
				return new SysJsonMsg(false, "账户不能为空！");
			}
			if (StringUtil.isEmpty(pwd)) {
				return new SysJsonMsg(false, "密码不能为空！");
			}
			
			// 验证验证码
			String randCode = (String) request.getSession().getAttribute("rand");
			if (code == null || !code.equalsIgnoreCase(randCode)) {
				return new SysJsonMsg(false, "验证码错误！"); 
			}
			
			//验证账号
			SysUser user = this.sysUserService.queryByAccountAndPwd(accountDecode, pwd);
			if (user == null) {
				return new SysJsonMsg(false, "账户或密码错误！"); 
			}
			
			if (CommonConstants.STATUS_OFF == user.getStatus()) {
				return new SysJsonMsg(false, "账户不可用！"); 
			}
			
			// 将登录账户信息放入session中
			request.getSession().setAttribute(CommonConstants.LOGIN_USER, user);
			
			// 保存操作日志
			request.setAttribute(CommonConstants.OPERATION_RECORD_FLAG, CommonConstants.SUCCESS_FLAG);
			request.setAttribute(CommonConstants.OPERATION_RECORD_DESC, "登录系统成功");
			request.setAttribute(CommonConstants.OPERATION_RECORD_TYPE, 90);
			
			return new SysJsonMsg(true, "登录成功"); 
		} catch (Exception e) {
			logger.error("系统出错！" + e.getMessage(), e);
			return new SysJsonMsg(false, "系统出错！"); 
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUser loginUser = (SysUser) session.getAttribute(CommonConstants.LOGIN_USER);
		if (loginUser != null) {
			// 清除session
			session.removeAttribute(CommonConstants.LOGIN_USER);
			session.invalidate();
			
			// 保存操作日志
			try {
				SysOperationRecord sysRecord=new SysOperationRecord(
						loginUser.getAccount(), loginUser.getNickName(), IpUtil.getRemoteHost(request),
						null,"登出系统成功", "/logout","{}",90,DateUtil.getCurDate());
				
				this.sysOperationRecordService.add(sysRecord);
			} catch (Exception e) {
				logger.error("登出记录操作日志出错，" + e.getMessage(), e);
			}
		}
		return "redirect:login";
	}
	
	@RequestMapping("/randomImg")
	public void randomImg(HttpServletRequest request, HttpServletResponse response ) throws IOException {
		OutputStream out=response.getOutputStream();
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0); 
		int width=80, height=40; 
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
		Graphics g = image.getGraphics(); 
		Random random = new Random(); 
		g.setColor(getRandColor(200,250)); 
		g.fillRect(0, 0, width, height); 
		g.setFont(new Font("行楷", Font.ITALIC, 22)); // new Font("Times New Roman",Font.PLAIN,18) 
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
//			g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110))); 
//			g.drawString(rand,9*i+15,24); 
		}
		
		// 将认证码存入SESSION 
		request.getSession().setAttribute("rand",sRand); 
		
		// 设置间距
		double rate=1.2d;
		int x=(int)(width/2-rate*g.getFontMetrics().stringWidth(sRand)/2);  
        int y=height/2+g.getFontMetrics().getHeight()/3;  
        int orgStringWight=g.getFontMetrics().stringWidth(sRand);  
        int orgStringLength=sRand.length();  
        String tempStr= "";
		while (sRand.length() > 0) {
			tempStr = sRand.substring(0, 1);
			sRand = sRand.substring(1, sRand.length());
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(tempStr, x, y);
			x = (int) (x + (double) orgStringWight / (double) orgStringLength * rate);
		}
        
		g.dispose(); 
		ImageIO.write(image, "JPEG", out); 
		out.flush();
		out.close();
	}
	Color getRandColor(int fc,int bc) { 
		Random random = new Random(); 
		if(fc>255) fc=255; 
		if(bc>255) bc=255; 
		int r=fc+random.nextInt(bc-fc); 
		int g=fc+random.nextInt(bc-fc); 
		int b=fc+random.nextInt(bc-fc); 
		return new Color(r,g,b); 
	} 
}
