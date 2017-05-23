package com.hikootech.mqcash.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * 验证码图片生成器
 * 
 * @author WuZhengFei
 * 
 */
public class IdentifyingCode {
	/**
	 * 验证码图片的宽度。
	 */
	private int width = 80;
	/**
	 * 验证码图片的高度。
	 */
	private int height = 40;
	/**
	 * 验证码的数量。
	 */
	private Random random = new Random();
	private static String[] randomChar = new String[] {  "2", "3",
			"4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G",
			"H",   "J", "K",  "M", "N", "U", "T", "S",  "X", "V",
			"P", "Q", "R", "W", "Y", "Z" };
	public IdentifyingCode() {
	}

	/**
	 * 生成随机颜色
	 * 
	 * @param fc
	 *            前景色
	 * @param bc
	 *            背景色
	 * @return Color对象，此Color对象是RGB形式的。
	 */
	public Color getRandomColor(int fc, int bc) {
		if (fc > 255)
			fc = 200;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 绘制干扰线
	 * 
	 * @param g
	 *            Graphics2D对象，用来绘制图像
	 * @param nums
	 *            干扰线的条数
	 */
	public void drawRandomLines(Graphics2D g, int nums) {
		g.setColor(this.getRandomColor(160, 200));
		for (int i = 0; i < nums; i++) {
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(12);
			int y2 = random.nextInt(12);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	/**
	 * 获取随机字符串， 此函数可以产生由大小写字母，汉字，数字组成的字符串
	 * 
	 * @param length
	 *            随机字符串的长度
	 * @return 随机字符串
	 */
	public String drawRandomString(int length, Graphics2D g) {
		StringBuffer strbuf = new StringBuffer();
		String temp = "";
		//int itmp = 0;
		for (int i = 0; i < length; i++) {
			temp=randomChar[random.nextInt(randomChar.length)];
			/*switch (random.nextInt(5)) {
			case 1: // 生成A～Z的字母
				itmp = random.nextInt(26) + 65;
				temp = String.valueOf((char) itmp);
				break;
			case 2:
				itmp = random.nextInt(26) + 97;
				temp = String.valueOf((char) itmp);
			
			case 3: // 生成汉字
				String[] rBase = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
						"9", "a", "b", "c", "d", "e", "f" };
				int r1 = random.nextInt(3) + 11; // 生成第1位的区码
				String strR1 = rBase[r1]; // 生成11～14的随机数
				int r2; // 生成第2位的区码
				if (r1 == 13)
					r2 = random.nextInt(7); // 生成0～7的随机数
				else
					r2 = random.nextInt(16); // 生成0～16的随机数
				String strR2 = rBase[r2];
				int r3 = random.nextInt(6) + 10; // 生成第1位的位码
				String strR3 = rBase[r3];
				int r4; // 生成第2位的位码
				if (r3 == 10)
					r4 = random.nextInt(15) + 1; // 生成1～16的随机数
				else if (r3 == 15)
					r4 = random.nextInt(15); // 生成0～15的随机数
				else
					r4 = random.nextInt(16); // 生成0～16的随机数
				String strR4 = rBase[r4];
				// 将生成的机内码转换成数字
				byte[] bytes = new byte[2];
				String strR12 = strR1 + strR2; // 将生成的区码保存到字节数组的第1个元素中
				int tempLow = Integer.parseInt(strR12, 16);
				bytes[0] = (byte) tempLow;
				String strR34 = strR3 + strR4; // 将生成的区码保存到字节数组的第2个元素中
				int tempHigh = Integer.parseInt(strR34, 16);
				bytes[1] = (byte) tempHigh;
				try {
					temp = new String(bytes, "utf-8"); // 根据字节数组生成汉字
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			default:
				itmp = random.nextInt(10) + 48;
				temp = String.valueOf((char) itmp);
				break;
			}*/
			Color color = new Color(20 + random.nextInt(20),
					20 + random.nextInt(20), 20 + random.nextInt(20));
			g.setColor(color);
			// 想文字旋转一定的角度
			AffineTransform trans = new AffineTransform();
//			trans.rotate(random.nextInt(45) * 3.14 / 180, 15 * i + 8, 7);
			// 缩放文字
//			float scaleSize = random.nextFloat() + 0.8f;
//			if (scaleSize > 1f)
//				scaleSize = 1f;
//			trans.scale(scaleSize, scaleSize);
//			g.setTransform(trans);
			// 验证码的第i个值,该值的x轴位置（越大越靠），该值的y轴位置（越大越靠下）
			g.drawString(temp,13*i+15, 24);
			strbuf.append(temp);
		}
		//g.drawString(strbuf.toString(),15 , 24);

		g.dispose();
		return strbuf.toString();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
