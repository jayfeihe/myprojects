package ${bizzPackage}.${modelPackage}.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tera.sys.service.MybatisBaseService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import ${bizzPackage}.${modelPackage}.dao.${className}Dao;
import ${bizzPackage}.${modelPackage}.model.${className};

/**
 * 
 * ${tableComment}服务类
 * <b>功能：</b>${className}Dao<br>
 * <b>作者：</b>${author}<br>
 * <b>日期：</b>${createDate}<br>
 * <b>版权所有：<b>${copyRight}<br>
 */
@Service("$!{lowerName}Service")
public class ${className}Service extends MybatisBaseService<${className}> {

	private final static Logger log = Logger.getLogger(${className}Service.class);

	@Autowired(required=false)
    private ${className}Dao dao;

	@Transactional
	public void add(${className}... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(${className} obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(${className} obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(${className} obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/**
	 * 分页查询
	 * @param params
	 * 				key
	 * 					curPage   当前页数
	 * 					pageSize  每页条数
	 * @return
	 */
	public PageList<${className}> queryList(Map<String, Object> params) throws Exception {
		return this.selectPageList(${className}Dao.class, "queryList", params);
	}

	public ${className} queryByKey(Object id) throws Exception {
		return (${className})dao.queryByKey(id);
	}
	
}
