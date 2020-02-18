package com.mapfinal.resource;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.event.Event;

import org.locationtech.jts.geom.Envelope;

/**
 * 资源对象： 资源唯一性，同一个资源可被多个layer调用，name是主键
 * <br>资源对象各自管理自己的资源、缓存和存储。
 * @author yangyong
 */
public class ResourceObject {

	//名称，唯一键
	private String name;
	//文件路径 或 网络地址
	private String url;
	//数据类型，Resource.Type
	private Resource.Type type;
	//ResourceObject外接矩形
	private Envelope envelope;
	//空间参考坐标系
	private SpatialReference spatialReference;
	//原位置读取方法
	private ResourceReader reader;
	//目标位置保存方法
	private ResourceWriter writer;
	//ResourceObject被调用次数
	private int reference = 0;
	//缓存
	private ResourceCache cache;
	//缓存文件夹
	private String cacheFolder = "common";
	
	public ResourceObject(String name, String url, Resource.Type type) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.cacheFolder = name;
		this.url = url;
		this.type = type;
		ResourceManager.me().putResource(this);
	}
	
	public void renderBefore(Event event) {
	}
	
	public void renderEnd(Event event) {
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
	public Resource.Type getType() {
		return type;
	}
	public void setType(Resource.Type type) {
		this.type = type;
	}
	public ResourceReader getReader() {
		return reader;
	}
	public void setReader(ResourceReader reader) {
		this.reader = reader;
	}
	public ResourceWriter getWriter() {
		return writer;
	}
	public void setWriter(ResourceWriter writer) {
		this.writer = writer;
	}

	public Envelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}

	public SpatialReference getSpatialReference() {
		return spatialReference;
	}

	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}

	public String getCacheFolder() {
		return cacheFolder;
	}

	public void setCacheFolder(String cacheFolder) {
		this.cacheFolder = cacheFolder;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public ResourceCache getCache() {
		return cache;
	}

	public void setCache(ResourceCache cache) {
		this.cache = cache;
	}
}
