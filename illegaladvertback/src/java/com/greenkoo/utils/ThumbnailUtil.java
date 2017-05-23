package com.greenkoo.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片缩放工具类
 * 
 * @author QYANZE
 *
 */
public class ThumbnailUtil {

	/**
	 * 指定大小进行缩放 
	 *  
     * 若图片横比200小，高比300小，不变   
     * 若图片横比200小，高比300大，高缩小到300，图片比例不变   
     * 若图片横比200大，高比300小，横缩小到200，图片比例不变   
     * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300   
	 * 
	 * @param width 宽度
	 * @param height 高度
	 * @param sourceImg 原图
	 * @param thumbFile 缩略图
	 * @return
	 */
	public static void createThumbImg(int width, int height, File sourceImg, File thumbFile) {
		try {
			Thumbnails.of(sourceImg).size(width, height).toFile(thumbFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按照比例进行缩放 
	 * 
	 * @param scale 比例值--0.25f
	 * @param sourceImg 原图
	 * @param thumbFile 缩略图
	 * @return
	 */
	public static void createThumbImg(float scale, File sourceImg, File thumbFile) {
		try {
			Thumbnails.of(sourceImg).scale(scale).toFile(thumbFile);;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 指定大小进行缩放 
	 *  
     * 若图片横比200小，高比300小，不变   
     * 若图片横比200小，高比300大，高缩小到300，图片比例不变   
     * 若图片横比200大，高比300小，横缩小到200，图片比例不变   
     * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300   
	 * 
	 * @param width 宽度
	 * @param height 高度
	 * @param sourceImg 原图
	 * @return
	 */
	public static BufferedImage createThumbBufferedImg(int width, int height, File sourceImg) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = Thumbnails.of(sourceImg).size(width, height).asBufferedImage();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}
	
	/**
	 * 按照比例进行缩放 
	 * 
	 * @param scale 比例值--0.25f
	 * @param sourceImg 原图
	 * @return
	 */
	public static BufferedImage createThumbBufferedImg(float scale, File sourceImg) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = Thumbnails.of(sourceImg).scale(scale).asBufferedImage();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}
}
