/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mapfinal.dispatcher.filter.order;

import java.io.Serializable;


public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private String alias;
	private String propertyName;
	private boolean ascending = true;
	
	public Order(String propertyName, boolean ascending) {
		// TODO Auto-generated constructor stub
		this.setPropertyName(propertyName);
		this.setAscending(ascending);
	}
	
	public Order(String alias, String propertyName, boolean ascending) {
		// TODO Auto-generated constructor stub
		this.setPropertyName(propertyName);
		this.setAscending(ascending);
		this.setAlias(alias);
	}
	
//	public String toSqlString(Criteria criteria) {
//		String _alias = StringKit.notBlank(alias) ? alias : "";
//		_alias = StringKit.isBlank(_alias) && StringKit.notBlank(criteria.getAlias()) ? criteria.getAlias() + "." : _alias + ".";
//		return _alias + propertyName + (ascending ? " asc" : " desc");
//	}
	
	public static Order	asc(String propertyName) {
		return new Order(propertyName, true);
	}
	
	public static Order	asc(String alias, String propertyName) {
		return new Order(alias, propertyName, true);
	}
	
    public static Order	desc(String propertyName) {
    	return new Order(propertyName, false);
    }
	
    public static Order	desc(String alias, String propertyName) {
    	return new Order(alias, propertyName, false);
    }
	
	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
