package com.mapfinal.example.converter;

import com.alibaba.fastjson.JSONObject;
import com.mapfinal.kit.ColorKit;
import com.mapfinal.render.style.SimpleLineSymbol;

public class TestJsonStore {

	public static void main(String[] args) {
		SimpleLineSymbol style = new SimpleLineSymbol(2222, 5);
		JSONObject jobj = style.toJson();
		System.out.println(jobj.toJSONString());
		
		SimpleLineSymbol lineStyle = new SimpleLineSymbol(ColorKit.RED);
		lineStyle.fromJson(jobj);
		System.out.println(lineStyle.toJson().toJSONString());
	}
}
