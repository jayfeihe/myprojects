package com.greenkoo.record.service;

import java.util.List;

import com.greenkoo.record.model.AdPic;

/**
 * 广告图片下载记录服务接口
 * 
 * @author QYANZE
 *
 */
public interface IAdPicService {

	/**
	 * 下载图片以及数据更新操作
	 * 
	 */
	void adPicProccess();
	
	/**
	 * 更新
	 * 
	 * @param pic
	 * @return
	 * @throws Exception
	 */
	int update(AdPic pic) throws Exception;
	
	/**
	 * 根据记录id查询
	 * 
	 * @param dataId
	 * @return
	 */
	AdPic queryBydataId(String dataId);
	
	/**
	 * 根据状态查询
	 * 
	 * @param status
	 * @return
	 */
	List<AdPic> queryByStatus(int status);
}
