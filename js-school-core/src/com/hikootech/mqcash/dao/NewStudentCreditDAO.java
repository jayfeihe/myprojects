package com.hikootech.mqcash.dao;

import java.util.List;

import com.hikootech.mqcash.po.DepartmentInfo;
import com.hikootech.mqcash.po.DistrictInfo;

public interface NewStudentCreditDAO {
	
	/** 
	* @Title: queryIllegalCount 
	* @Description: 查询非法姓名配置表 
	* @author yuwei
	* @date 2016年8月29日
	* @param name
	* @return    设定文件 
	* @return 返回类型 int
	*/
	public int queryIllegalCount(String name);
	
	/** 
	* @Title: queryExistSchoolInfo 
	* @Description: 查询该校区是否存在
	* @author yuwei
	* @date 2016年8月29日
	* @param string
	* @return    设定文件 
	* @return 返回类型 DistrictInfo
	*/
	public DistrictInfo queryExistSchoolInfo(String string);
	
	/** 
	* @Title: queryExistDepartmentInfo 
	* @Description: 根据id查询该校区是否存在院系
	* @author yuwei
	* @date 2016年8月29日
	* @param collegeId
	* @return    设定文件 
	* @return 返回类型 List<DepartmentInfo>
	*/
	public List<DepartmentInfo> queryExistDepartmentInfo(int collegeId);
	
}
