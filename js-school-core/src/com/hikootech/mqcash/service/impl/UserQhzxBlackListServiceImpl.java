package com.hikootech.mqcash.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.UserQhzxBlackListDAO;
import com.hikootech.mqcash.po.UserQhzxBlackList;
import com.hikootech.mqcash.service.UserQhzxBlackListService;

@Service("userQhzxBlackListService")
public class UserQhzxBlackListServiceImpl implements UserQhzxBlackListService {
 private static Logger logger=LoggerFactory.getLogger(UserQhzxBlackListServiceImpl.class);
	@Autowired
	private UserQhzxBlackListDAO userQhzxBlackListDAO;
		
	
	@Override
	public void addQhzxBlackList(UserQhzxBlackList msc) {
		// TODO Auto-generated method stub
		userQhzxBlackListDAO.addQhzxBlackList(msc);
	}

	@Override
	public int queryQhzxBlackListCountByInfo(UserQhzxBlackList msc) {
		// TODO Auto-generated method stub
		 Integer ret= userQhzxBlackListDAO.queryQhzxBlackListCountByInfo(msc);
		return ret;
	}

	@Override
	public int updateQhzxBlackListByInfo(UserQhzxBlackList msc) {
		// TODO Auto-generated method stub
		return userQhzxBlackListDAO.updateQhzxBlackListByInfo(msc);
	}
	
	@Override
	public void saveQhzxBlackList(UserQhzxBlackList msc){
		 
		try {
			int ret=0; //目前只保存前海信息，不做修改 20160111
			//int ret=this.queryQhzxBlackListCountByInfo(msc);
			if(ret>0){
				this.updateQhzxBlackListByInfo(msc);
			}else{
				this.addQhzxBlackList(msc);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("msc8004报文保存时发生错误:"+e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public void saveQhzxBlackList(Map<String, UserQhzxBlackList> qhMap) {
			 for (String key : qhMap.keySet()) {
				 UserQhzxBlackList msc= qhMap.get(key);
				 try {
					this.saveQhzxBlackList(msc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("seq=====>"+key+"保存时发生错误：",e);
					throw new RuntimeException(e);
				}
			}
	}

}
