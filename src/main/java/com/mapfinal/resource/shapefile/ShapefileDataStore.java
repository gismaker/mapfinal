package com.mapfinal.resource.shapefile;

import com.mapfinal.kit.StringKit;
import com.mapfinal.resource.DataStore;
import com.mapfinal.resource.DataStoreType;

public class ShapefileDataStore implements DataStore {

	private Shapefile shapefile;
	
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 文件路径 或 网络地址，唯一键
	 */
	private String url;
	/**
	 * 数据类型
	 */
	private DataStoreType type;
	
	private String charsetName;
	
	public ShapefileDataStore(String url, String charsetName) {
		this.url = url;
		this.charsetName = charsetName;
		this.id = StringKit.uuid();
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		shapefile.close();
		shapefile = null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}

	@Override
	public DataStoreType getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public void start() {
		if(shapefile==null) {
			shapefile = new Shapefile(url, charsetName);
		} else {
			shapefile.init(url, charsetName);
		}
	}

	@Override
	public void stop() {
		shapefile.close();
	}

	@Override
	public Shapefile getResource(String name) {
		// TODO Auto-generated method stub
		return shapefile;
	}

	public Shapefile getShapefile() {
		return shapefile;
	}

	public void setShapefile(Shapefile shapefile) {
		this.shapefile = shapefile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setType(DataStoreType type) {
		this.type = type;
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
