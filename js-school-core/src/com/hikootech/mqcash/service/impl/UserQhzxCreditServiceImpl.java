package com.hikootech.mqcash.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.UserQhzxCreditDAO;
import com.hikootech.mqcash.po.UserQhzxCredit;
import com.hikootech.mqcash.service.UserQhzxCreditService;

@Service("UserQhzxCreditService")
public class UserQhzxCreditServiceImpl implements UserQhzxCreditService {

	private static Logger logger=LoggerFactory.getLogger(UserQhzxCreditServiceImpl.class);
	@Autowired
	private UserQhzxCreditDAO userQhzxMsc8005DAO;
	
	@Override
	public void addQhzxCredit(UserQhzxCredit msc) {
		// TODO Auto-generated method stub
		userQhzxMsc8005DAO.addQhzxCredit(msc);
	}

	@Override
	public int queryQhzxCreditByInfo(UserQhzxCredit msc) {
		// TODO Auto-generated method stub
		 Integer ret= userQhzxMsc8005DAO.queryQhzxCreditByInfo(msc);
		return ret;
	}

	@Override
	public int updateQhzxCreditByInfo(UserQhzxCredit msc) {
		return userQhzxMsc8005DAO.updateQhzxCreditByInfo(msc);
	}
	
	@Override
	public void saveQhzxCreditList(UserQhzxCredit msc){
		 
			try {
				int ret=0; //目前只保存前海信息，不做修改 20160111
				//int ret=this.queryQhzxCreditByInfo(msc);
				if(ret>0){
					this.updateQhzxCreditByInfo(msc);
				}else{
					this.addQhzxCredit(msc);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("msc8005报文保存时发生错误:"+e.getMessage());
				e.printStackTrace();
			}
			
		
	}

	@Override
	public void saveQhzxCreditList(Map<String, UserQhzxCredit> qhMap) {
		 for (String key : qhMap.keySet()) {
			 UserQhzxCredit msc= qhMap.get(key);
			 try {
				this.saveQhzxCreditList(msc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("seq=====>"+key+"保存时发生错误："+e.getMessage());
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
	}

}
