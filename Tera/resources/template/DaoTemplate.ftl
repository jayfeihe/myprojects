package ${bizzPackage}.${modelPackage}.dao;

import java.util.List;
import java.util.Map;

import ${bizzPackage}.${modelPackage}.model.${className};

/**
 * 
 * ${tableComment}DAO
 * <b>功能：</b>${className}Dao<br>
 * <b>作者：</b>${author}<br>
 * <b>日期：</b>${createDate}<br>
 * <b>版权所有：<b>${copyRight}<br>
 */
public interface ${className}Dao {
		
	public void add(${className} obj);	
	
	public void update(${className} obj);
	
	public void updateOnlyChanged(${className} obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<${className}> queryList(Map<String, Object> map);

	public ${className} queryByKey(Object obj);

}
