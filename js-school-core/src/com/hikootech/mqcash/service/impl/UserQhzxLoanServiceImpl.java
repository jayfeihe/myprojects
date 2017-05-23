package com.hikootech.mqcash.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hikootech.mqcash.dao.UserQhzxLoanDAO;
import com.hikootech.mqcash.po.UserQhzxLoan;
import com.hikootech.mqcash.service.UserQhzxLoanService;

@Service("userQhzxLoanService")
public class UserQhzxLoanServiceImpl implements UserQhzxLoanService {
	@Autowired
	private UserQhzxLoanDAO userQhzxLoanDAO;
	
	@Override
	public void saveQhzxLoaneeList(UserQhzxLoan msc) {
		userQhzxLoanDAO.saveQhzxLoaneeList(msc);
	}

}
