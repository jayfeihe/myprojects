package com.greenkoo.dmp.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

@Document(collection = "network_app")
public class NetworkApp {
	
	@Id
	@JSONField(serialize=false)
	private String id;
	@JSONField(ordinal=1)
	private String device_id;
	@JSONField(ordinal=2)
	private List<JSONObject> apptags;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public List<JSONObject> getApptags() {
		return apptags;
	}
	public void setApptags(List<JSONObject> apptags) {
		this.apptags = apptags;
	}
}
