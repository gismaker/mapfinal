package com.mapfinal.example.converter;

public class TestStringHelper {

	public static void main(String[] args) {
		String url = "D:/lambkit/arcgis/cache/_alllayers/{z}-{x}-{y}.bundle";
		String upath = url.substring(url.lastIndexOf("/")+1);
		System.out.println("upath: " + upath);
		
		String name = "0008e23";
		String fn = name.substring(name.length()-4);
		System.out.println("fn: " + fn);
	}
}
