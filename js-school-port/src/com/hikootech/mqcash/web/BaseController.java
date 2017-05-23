package com.hikootech.mqcash.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.util.IdentifyingCode;
import com.hikootech.mqcash.util.Page;
import com.hikootech.mqcash.util.UserUtils;

/**
 * 2014年1月16日
 * com.jiexun.pos.webBaseController.java
 * @author yuwei
 * 基控制器
 */
public class BaseController {
	
	@Autowired
	private HttpServletRequest request;
 
	
	protected Page pv ;
	
	public BaseController() {
	}

	public UserInfo getUserInfo(){
		UserInfo userInfo = UserUtils.getUserInfoFromCache(getRequest().getSession());
		return userInfo;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	
	
	/**
	 * 根据请求总页数，初始化分页VO
	 * @param total
	 */
	public void initPV(int total){
		
		//这里取得每页显示条数，以及当前请求页数
		String pageNo = request.getParameter("start") ;// 当前页数
		int pageSize = Page.PAGESIZE;// 每页大小
		int pageNoInt=1;
		try {
			pageNoInt=Integer.parseInt(pageNo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		pv = new Page(pageSize, pageNoInt, total);
	}

	public Page getPv() {
		return pv;
	}

	public void setPv(Page pv) {
		this.pv = pv;
	}
	
	
		/**constructValidationCode
		* 此方法描述的是：生成验证码的函数
		* @author: zhaohefei
		* @version: 2015年10月17日 下午2:56:33
		*/
		
	public  List<Object> constructValidationCode(){

		
		IdentifyingCode idCode = new IdentifyingCode();
		BufferedImage image = new BufferedImage(idCode.getWidth(), idCode.getHeight(), BufferedImage.TYPE_INT_BGR);
		Graphics2D g = image.createGraphics();
		// 定义字体样式
		Font myFont = new Font("行楷", Font.ITALIC, 22);
		// 设置字体
		g.setFont(myFont);

//		g.setColor(idCode.getRandomColor(200, 250));
//		// 绘制背景
//		g.fillRect(0, 0, idCode.getWidth(), idCode.getHeight());
//
//		g.setColor(idCode.getRandomColor(180, 200));
//		idCode.drawRandomLines(g, 160);

		g.setColor(Color.WHITE);
		// 绘制背景
		g.fillRect(0, 0, idCode.getWidth(), idCode.getHeight());

		g.setColor(Color.WHITE);
		idCode.drawRandomLines(g, 0);
		String validationCode = idCode.drawRandomString(4, g);
		g.dispose();
		List<Object> list=new ArrayList<Object>();
		list.add(validationCode);
		list.add(image);
		return list;
	}
	
	
}
