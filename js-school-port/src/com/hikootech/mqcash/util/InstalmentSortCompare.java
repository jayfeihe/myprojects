
		/**
		* 文件名：InstalmentSortCompare.java
		*
		* 版本信息：
		* 日期：2015年9月25日
		* Copyright 足下 Corporation 2015
		* 版权所有
		*
		*/
	
package com.hikootech.mqcash.util;

import java.util.Comparator;

import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dto.InstalmentInfoDTO;

/**
		* 此类描述的是：分期账单用于排序的。。
		* 返回“负数”，意味着“o1比o2小”；返回“零”，意味着“o1等于o2”；返回“正数”，意味着“o1大于o2”。
		* @author:  

		* @version: 2015年9月25日 下午4:54:23
		*/

public class InstalmentSortCompare implements Comparator {

		
	@Override
	public int compare(Object o1, Object o2) {
		 
		InstalmentInfoDTO v1=(InstalmentInfoDTO) o1;
		InstalmentInfoDTO v2=(InstalmentInfoDTO) o2;
		
		
		//两个序号相同
		if((v1.getSort()==v2.getSort())){
			//综合状态都是待还款且剩余天数不一致，则按照剩余天数升序排列，否则按照下单时间进行降序排列
			if(v2.getPlanState()==StatusConstants.INSTALMENT_STATUS_AWAIT_PAY.intValue()&&(v1.getLastDay()==v2.getLastDay())){
				if(v1.getLastDay()>v2.getLastDay()){
					return 1;
				}else{
					return -1;
				} 
			}else{
				
				if(v1.getLoanDateForCompare().compareTo(v2.getLoanDateForCompare())>0){
					return -1;
				}else if(v1.getLoanDateForCompare().compareTo(v2.getLoanDateForCompare())==0){
					return 0;
				}else{
					return 1;
				}
			}
		}else{
			//序号不相同，按照升序排列
			if(v1.getSort()>v2.getSort()){
				return 1;
			}else{
				return -1;
			}
		}
	}

}
