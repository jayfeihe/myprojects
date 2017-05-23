package com.hikootech.mqcash.dao;

import java.util.List;

import com.hikootech.mqcash.po.ConfigSmPrinciple;

public interface ConfigSmPrincipleDAO {

	public List<ConfigSmPrinciple> querySmConfigByTargetType(int targetType);
}
