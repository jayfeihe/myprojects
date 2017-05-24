package com.gome.bi.monitor.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.alibaba.fastjson.JSONObject;

public class BaseController {

	/**
	 * 根据传递报文解析对象
	 * 
	 * @param reqBody 传递报文
	 * @param clz 目标类
	 * @return
	 */
	public <T> T getDO(String reqBody, Class<T> clz) {

		try {
			reqBody = URLDecoder.decode(reqBody, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return JSONObject.parseObject(reqBody, clz);
	}
}
