package com.tera.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class SealImageUtil {
	//中间点的位置
	public static final int CENTERX = 182;
	public static final int CENTERY = 182;  
	public static BufferedImage bufferedImageUtil(String centerFlowNo,String uuid){
		return bufferedImageUtil("天瑞融通信息服务（北京）有限公司", 
				uuid,"电子专用章", centerFlowNo, 120, 285);
	}
	public static BufferedImage bufferedImageUtil(String message,
		 String botomMsg,String centerFlowNo){
		return bufferedImageUtil(message,
				botomMsg,  null, centerFlowNo,0, 0);
	}
	public static BufferedImage bufferedImageUtil(String message,
			String botomMsg, String centerName, String centerFlowNo, int width, int height){  
		BufferedImage image = new BufferedImage(705, 705, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		
		Graphics2D g2 = image.createGraphics();
		setAntialiasingOn(g2);
		// ---------- 增加下面的代码使得背景透明 -----------------
		image = g2.getDeviceConfiguration().createCompatibleImage(355, 355, Transparency.TRANSLUCENT);
		g2.dispose();
		g2 = image.createGraphics();
		// ---------- 背景透明代码结束 -----------------
		g2.setColor(Color.RED);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//绘制圆
		int radius = 155;
		Ellipse2D circle = new Ellipse2D.Double();//.Double();
		circle.setFrameFromCenter(CENTERX, CENTERY, CENTERX + radius, CENTERY + radius);
		Stroke s=new BasicStroke(8);
		g2.setStroke(s);
		g2.draw(circle);
		
		//绘制中间的五角星
		Font starFont = new Font("宋体", Font.BOLD, 160);
		g2.setFont(starFont);
//		g2.drawString("★", CENTERX - 80, CENTERY + 40);	
		// 实心五角星变成空心
		g2.drawString("☆", CENTERX - 80, CENTERY + 40);	
		//下方字体
		
//		下方文字
		if(centerName!=null){
			Font fo = new Font("Serif", Font.BOLD, 25);
			g2.setFont(fo);
			g2.drawString(centerName,width, height);
		}
//		中间流水号
		if(centerName!=null){
			Font fo = new Font("Serif", Font.BOLD, 15);
			g2.setFont(fo);
			g2.drawString(centerFlowNo,width+10, height-30);
		}
		//设置字体属性
		//画公司名称
		drawCompany(message, g2, radius);
		//画序列号
		drawBotom(botomMsg, g2, radius);
		return image;
	}

	private static void drawCompany(String message, Graphics2D g2, int radius ) {
		//根据输入字符串得到字符数组
		String[] messages2 = message.split("",0);
		String[] messages = new String[messages2.length-1];
		System.arraycopy(messages2,1,messages,0,messages2.length-1);
		int fontsize = 30;
		Font f = new Font("Serif", Font.BOLD, fontsize);
		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = f.getStringBounds(message, context);
		//输入的字数
		int ilength = messages.length;
		//字符宽度＝字符串长度/字符数
		double char_interval = (bounds.getWidth() / ilength);
		//上坡度
		double ascent = -bounds.getY();

		int first = 0,second = 0;
		boolean odd = false;
		if (ilength%2 == 1)
		{
			first = (ilength-1)/2;
			odd = true;
		}
		else
		{
			first = (ilength)/2-1;
			second = (ilength)/2;
			odd = false;
		}
		
		double radius2 = radius - ascent;
		double x0 = CENTERX;
		double y0 = CENTERY - radius + ascent;
		//旋转角度
		double a = 2*Math.asin(char_interval/(2*radius2));
		
		if (odd)
		{
			g2.setFont(f);
			g2.drawString(messages[first], (float)(x0 - char_interval/2), (float)y0);
			
			//中心点的右边
			for (int i=first+1;i<ilength;i++)
			{
				double aa = (i - first) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(aa);//,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g2.setFont(f2);
				g2.drawString(messages[i], (float)(x0 + ax - char_interval/2* Math.cos(aa)), (float)(y0 + ay - char_interval/2* Math.sin(aa)));
			}
			//中心点的左边
			for (int i=first-1;i>-1;i--)
			{
				double aa = (first - i) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(-aa);//,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g2.setFont(f2);
				g2.drawString(messages[i], (float)(x0 - ax - char_interval/2* Math.cos(aa)), (float)(y0 + ay + char_interval/2* Math.sin(aa)));
			}
			
		}
		else
		{
			//中心点的右边
			for (int i=second;i<ilength;i++)
			{
				double aa = (i - second + 0.5) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(aa);//,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g2.setFont(f2);
				g2.drawString(messages[i], (float)(x0 + ax - char_interval/2* Math.cos(aa)), (float)(y0 + ay - char_interval/2* Math.sin(aa)));
			}
			
			//中心点的左边
			for (int i=first;i>-1;i--)
			{
				double aa = (first - i + 0.5) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(-aa);//,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g2.setFont(f2);
				g2.drawString(messages[i], (float)(x0 - ax - char_interval/2* Math.cos(aa)), (float)(y0 + ay + char_interval/2* Math.sin(aa)));
			}
		}
	}
	private static void drawBotom(String bottomInfo, Graphics2D g2, int radius ) {
		//根据输入字符串得到字符数组
		String[] messages2 = bottomInfo.split("",0);
		String[] messages = new String[messages2.length-1];
		System.arraycopy(messages2,1,messages,0,messages2.length-1);
		int fontsize = 16;
		Font f = new Font("Serif", Font.BOLD, fontsize);
		FontRenderContext context = g2.getFontRenderContext();
		
		Rectangle2D bounds = f.getStringBounds(bottomInfo, context);
		//输入的字数
		int ilength = messages.length;
		//字符宽度＝字符串长度/字符数
		double char_interval = (bounds.getWidth() / ilength);
		//上坡度
		double ascent = -bounds.getY()-5;
		
		int first = 0,second = 0;
		boolean odd = false;
		if (ilength%2 == 1)
		{
			first = (ilength-1)/2;
			odd = true;
		}
		else
		{
			first = (ilength)/2-1;
			second = (ilength)/2;
			odd = false;
		}
		
		double radius2 = radius - ascent;
		double x0 = CENTERX+6;
		double y0 = CENTERY + radius - ascent;
		//旋转角度
		double a = 2*Math.asin(char_interval/(2*radius2));
		if (odd)
		{
			exchange(messages);
			g2.setFont(f);
			g2.drawString(messages[first], (float)(x0 - char_interval/2), (float)y0);
			
			//中心点的左边
			for (int i=first+1;i<ilength;i++)
			{
				double aa = (first-i  ) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(-aa);//,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g2.setFont(f2);
				g2.drawString(messages[i], (float)(x0 + ax - char_interval/2* Math.cos(aa)), (float)(y0 - ay - char_interval/2* Math.sin(aa)));
			}
			//中心点的右边
			for (int i=first-1;i>-1;i--)
			{
				double aa = ( i-first) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(aa);//,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g2.setFont(f2);
				g2.drawString(messages[i], (float)(x0 - ax - char_interval/2* Math.cos(aa)), (float)(y0 - ay - char_interval/2* Math.sin(aa)));
			}
			
		}
		else
		{
			//中心点的右边
			for (int i=second;i<ilength;i++)
			{
				double aa = (i - second + 0.5) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(-aa);
				AffineTransform transform = AffineTransform.getRotateInstance(-aa);//,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g2.setFont(f2);
				g2.drawString(messages[i], (float)(x0 + ax - char_interval/2* Math.cos(aa)), (float)(y0 - ay - char_interval/2* Math.sin(aa)));
			}
			
			//中心点的左边
			for (int i=first;i>-1;i--)
			{
				double aa = (first - i + 0.5) * a;
				double ax = radius2 * Math.sin(aa);
				double ay = radius2 - radius2 * Math.cos(aa);
				AffineTransform transform = AffineTransform.getRotateInstance(aa);//,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g2.setFont(f2);
				g2.drawString(messages[i], (float)(x0 - ax - char_interval/2* Math.cos(aa)), (float)(y0 - ay + char_interval/2* Math.sin(aa)));
			}
		}
	}
	public static void exchange(String a[]) {  
		int x = a.length / 2;  
        for (int i = 0; i < x; i++)  
            swap(a, i, (a.length - i - 1));  
    }  
  
    public static void swap(String a[], int x, int y) {  
    	String tmp = a[x];  
        a[x] = a[y];  
        a[y] = tmp;  
    }  
    public static void setAntialiasingOn(Graphics g)
    {
    Graphics2D g2d=(Graphics2D)g;
    RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHints(renderHints);
    }

    public static void setAntialiasingOff(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHints(renderHints);
    }
}
