package com.mapfinal.converter;

import com.alibaba.fastjson.JSONObject;

public interface JsonStore {

	void fromJson(JSONObject jsonObject);
	
	JSONObject toJson();
}
