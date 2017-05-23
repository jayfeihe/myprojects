package com.hikootech.mqcash.service;

import java.util.List;

import com.hikootech.mqcash.po.ConfigSmPrinciple;

public interface ConfigSmPrincipleService {

	public List<ConfigSmPrinciple> querySmConfigByTargetType(int targetType);
}
