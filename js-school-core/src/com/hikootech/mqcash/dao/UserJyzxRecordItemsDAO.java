package com.hikootech.mqcash.dao;

import java.util.List;

import com.hikootech.mqcash.po.UserJyzxRecordItem;

public interface UserJyzxRecordItemsDAO {

	public void addUserJyzxRecordItem(UserJyzxRecordItem recordItem);
	
	public List<UserJyzxRecordItem> queryUserJyzxRecordItemsByTrxNo(String trxNo);
}
