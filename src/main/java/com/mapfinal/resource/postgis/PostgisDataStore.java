package com.mapfinal.resource.postgis;

import com.mapfinal.resource.DataStore;
import com.mapfinal.resource.DataStoreType;
import com.mapfinal.resource.Resource;

public class PostgisDataStore implements DataStore {
	
	private String id;
	private String name;
	
	private String configName;
	private String url;
    private String user;
    private String password;

    private String dbname;
    private String schema;
    
    @Override
	public DataStoreType getType() {
		// TODO Auto-generated method stub
		return DataStoreType.PostGIS;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
    @Override
	public Resource getResource(String name) {
		// TODO Auto-generated method stub
		return null;
	}
    
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

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
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
