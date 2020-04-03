package com.mapfinal.resource;

import com.mapfinal.event.Event;

/**
 * 非空间数据资源对象： 资源唯一性，同一个资源可被多个对象调用，name是主键
 * <br>资源对象各自管理自己的资源、缓存和存储。
 * @author yangyong
 */
public abstract class ResourceObject implements Resource {

	//名称
	private String name;
	//文件路径 或 网络地址，唯一键
	private String url;
	//ResourceObject外接矩形
	//private Envelope envelope;
	//ResourceObject被调用次数
	private int reference = 0;

	public ResourceObject() {
	}
	
	public ResourceObject(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	@Override
	public void execute(Event event) {
		// TODO Auto-generated method stub
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}
	
	@Override
	public ResourceObject reference() {
		this.reference++;
		return this;
	}
	
	@Override
	public int referenceRelease() {
		// TODO Auto-generated method stub
		this.reference--;
		if(this.reference==0) {
			destroy();
		}
		return this.reference;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
