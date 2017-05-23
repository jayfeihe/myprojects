package com.greenkoo.record.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.record.dao.AdPicDao;
import com.greenkoo.record.model.AdPic;
import com.greenkoo.record.model.DataRecord;
import com.greenkoo.record.service.IAdPicService;
import com.greenkoo.record.service.IDataRecordService;
import com.greenkoo.utils.Base64Util;
import com.greenkoo.utils.ConfigUtil;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.HttpUtil;
import com.greenkoo.utils.SecurityUtil;
import com.greenkoo.utils.StringUtil;
import com.greenkoo.utils.ThumbnailUtil;

@Service("adPicService")
public class AdPicServiceImpl implements IAdPicService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String photoRootPath = ConfigUtil.getProperty("photo_root_path");
	private final String thumb_width = ConfigUtil.getProperty("thumb_width");
	private final String thumb_height = ConfigUtil.getProperty("thumb_height");
	/**
	 * 没有图片时，娶默认图片信息
	 */
	private final String default_adpic = ConfigUtil.getProperty("default_adpic");
	private final String default_width = ConfigUtil.getProperty("default_width");
	private final String default_height = ConfigUtil.getProperty("default_height");
	
	@Autowired
	private AdPicDao dao;
	
	@Autowired
	private IDataRecordService dataRecordService;

	@Transactional
	@Override
	public void adPicProccess() {
		
		//数据库保存目录
		String picDBDir = DateUtil.getCurDateStr("yyyyMMdd") + File.separator;
		// 图片本地存储目录
		String picLocalDir = photoRootPath + File.separator + picDBDir;
		
		// 查询所有初始化的下载数据 
		List<AdPic> initPics = this.queryByStatus(AdPic.STATUS_INIT);
		// 根据图片地址去下载
		if (initPics != null && initPics.size() > 0) {
			// 创建更新实体
			AdPic upPic = new AdPic();
			DataRecord upRecord = new DataRecord();
			for (AdPic adPic : initPics) {
				
				upPic.setDataId(adPic.getDataId());
				
				// 网络图片地址为空，设置默认图片属性值
				if (StringUtil.isEmpty(adPic.getPicUrl())) {
					// 无网络图片进行下载
					try {
						upRecord.setId(adPic.getDataId());
						upRecord.setAdpicUrl(default_adpic + ".jpg") ;
						upRecord.setAdpicWidth(Integer.parseInt(default_width));
						upRecord.setAdpicHeight(Integer.parseInt(default_height));
						upRecord.setThumbWidth(Integer.parseInt(default_width));
						upRecord.setThumbHeight(Integer.parseInt(default_height));
						this.dataRecordService.update(upRecord);
						
					} catch (NumberFormatException e1) {
						logger.error("default_width或default_height类型转换错误，" + e1.getMessage(), e1);
					} catch (Exception e1) {
						logger.error("更新数据异常，" + e1.getMessage(), e1);
					} finally {
						try {
							upPic.setStatus(AdPic.STATUS_SUCCESS);
							this.update(upPic);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					continue;
				}
				
				// 有网络图片进行下载
				try {
					// 图片名称
					String picName = System.currentTimeMillis() + String.format("%03d", new Random().nextInt(1000) + 1);
					String picPath = picLocalDir + picName + ".jpg";
					// 从网络下载图片到本地
					File sourceImg = this.downloadImg(adPic.getPicUrl(), picPath);
					
					if (sourceImg.exists()) {
						// 更新图片表数据
						upPic.setStatus(AdPic.STATUS_SUCCESS);
						
						// 获取图片宽高属性
						BufferedImage origImage = ImageIO.read(sourceImg);
						int adpicWidth = origImage.getWidth();
						int adpicHeight = origImage.getHeight();
						
						// 生成缩略图
						String thumbPath = picLocalDir + picName + "_thumb.jpg";
						BufferedImage thumbBufferedImg = this.createThumbImg(thumbPath, sourceImg, adpicWidth, adpicHeight);
						int thumbImgWidth = thumbBufferedImg.getWidth();
						int thumbImgHeight = thumbBufferedImg.getHeight();
						
						// 更新创意表数据
						upRecord.setId(adPic.getDataId());
						upRecord.setAdpicUrl(picDBDir + picName + ".jpg");
						upRecord.setAdpicWidth(adpicWidth);
						upRecord.setAdpicHeight(adpicHeight);
						upRecord.setThumbWidth(thumbImgWidth);
						upRecord.setThumbHeight(thumbImgHeight);
						this.dataRecordService.update(upRecord);
						
						// 生成图片
						ImageIO.write(thumbBufferedImg, "jpg", this.getFile(thumbPath));
						
						
					} else {
						logger.warn("根据地址【"+adPic.getPicUrl()+"】获取图片失败");
						upPic.setStatus(AdPic.STATUS_FAIL);
					}
					
				} catch (IOException e) {
					upPic.setStatus(AdPic.STATUS_FAIL);
					logger.error("生成图片输入流错误，" + e.getMessage(), e);
				} catch (Exception e) {
					logger.error("更新数据异常，" + e.getMessage(), e);
				} finally {
					try {
						this.update(upPic);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 通过网络地址获取图片
	 * 
	 * @param picUrl 网络图片地址
	 * @param picLocalPath 本地图片存储地址
	 * @return
	 * @throws IOException
	 */
	private File downloadImg(String picUrl, String picLocalPath) throws IOException {
		// 获取图片流
		CloseableHttpClient client = HttpUtil.getHttpClient();
		HttpGet get = new HttpGet(picUrl);
		CloseableHttpResponse response = client.execute(get);
		InputStream inputStream = response.getEntity().getContent();
		if (inputStream != null) {
			File pic = new File(picLocalPath);
			FileUtils.copyInputStreamToFile(inputStream, pic);
			return pic;
		}
		
		response.close();
		client.close();
		
		return null;
	}
	
	/**
	 * 通过流获取图片
	 * 
	 * @param encodeAdPic
	 * @param adpicUrl
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private File getPicByStream(String encodeAdPic, String picLocalPath) throws IOException {
		File pic = this.getFile(picLocalPath);
		String adPic = SecurityUtil.URLDecode(encodeAdPic, "UTF-8");
		byte[] picByte = Base64Util.decodeStringToBase64(adPic);
		FileUtils.writeByteArrayToFile(pic, picByte);
		return pic;
	}
	
	/**
	 * 生成缩略图
	 * 
	 * @param picDir 图片目录
	 * @param picName 图片名称
	 * @param sourceImg 原图
	 * @param adpicWidth 压缩宽度
	 * @param adpicHeight 压缩高度
	 */
	private BufferedImage createThumbImg(String thumbPath, File sourceImg, int adpicWidth, int adpicHeight)
			throws Exception {
		int thumbWidth = adpicWidth, thumbHeight = adpicHeight;
		if (StringUtil.isNotEmpty(thumb_width) && Integer.parseInt(thumb_width) < adpicWidth) 
			thumbWidth = Integer.parseInt(thumb_width);
		if (StringUtil.isNotEmpty(thumb_height) && Integer.parseInt(thumb_height) < adpicHeight) 
			thumbHeight = Integer.parseInt(thumb_height);
		
		// 按大小压缩
//		File thumbFile = this.getFile(thumbPath);
//		ThumbnailUtil.createThumbImg(thumbWidth, thumbHeight, sourceImg, thumbFile);
		BufferedImage thumbBufferedImg = ThumbnailUtil.createThumbBufferedImg(thumbWidth, thumbHeight, sourceImg);
		return thumbBufferedImg;
	}
	
	private File getFile(String path) {
		try {
			File f = new File(path);
			if (!f.getParentFile().exists())
				f.getParentFile().mkdirs();
			if (!f.exists())
				f.createNewFile();
			return f;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Transactional
	@Override
	public int update(AdPic pic) throws Exception {
		try {
			return this.dao.update(pic);
		} catch (Exception e) {
			logger.error("更新图片下载信息异常，" + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public AdPic queryBydataId(String dataId) {
		return this.dao.queryBydataId(dataId);
	}

	@Override
	public List<AdPic> queryByStatus(int status) {
		return this.dao.queryByStatus(status);
	}
}
