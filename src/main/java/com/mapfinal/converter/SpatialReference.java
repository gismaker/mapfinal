package com.mapfinal.converter;

public class SpatialReference {
	
	private String name;
	
	public SpatialReference(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	public static SpatialReference create(int wkid) {
		//未完成
		return new SpatialReference("EPSG:4326");
	}

	public static SpatialReference create(java.lang.String wktext) {
		//未完成
		return new SpatialReference("EPSG:4326");

	}

	public java.lang.String getText() {
		
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// public boolean equals(java.lang.Object o) {}
}
