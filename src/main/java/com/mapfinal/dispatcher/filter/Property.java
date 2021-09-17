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
package com.mapfinal.dispatcher.filter;

import java.util.List;

import com.mapfinal.dispatcher.filter.expression.PropertyExpression;
import com.mapfinal.dispatcher.query.ConditionMode;

/**
 * Property实例是获得一个条件的另外一种途径。你可以通过调用Property.forName() 创建一个
Property。
Property age = Property.forName("age");
List cats = sess.createCriteria(Cat.class)
.add( Restrictions.disjunction()
.add( age.isNull() )
.add( age.eq( new Integer(0) ) )
.add( age.eq( new Integer(1) ) )
.add( age.eq( new Integer(2) ) )
) )
.add( Property.forName("name").in( new String[] { "Fritz", "Izi", "Pk" } ) )
.list();
 * @author 孤竹行
 *
 */
public class Property {
	
	private String typeName;
	private String propertyName;
	private String dataType;
	
	public Property(String name) {
		// TODO Auto-generated constructor stub
		this.propertyName = name;
	}
	
	public Property(String typeName, String propertyName) {
		// TODO Auto-generated constructor stub
		this.typeName = typeName;
		this.propertyName = propertyName;
	}
	
	public Property(String typeName, String propertyName, String dataType) {
		// TODO Auto-generated constructor stub
		this.typeName = typeName;
		this.propertyName = propertyName;
		this.dataType = dataType;
	}
	
	public static Property forName(String name) {
		return new Property(name);
	}
	
	public static Property forName(String typeName, String propertyName) {
		return new Property(typeName, propertyName);
	}
	
	public Expression between(Object lo, Object hi) {
		return new PropertyExpression(ConditionMode.BETWEEN, this, lo, hi);
	}

	public Expression eq(Object value) {
		return new PropertyExpression(ConditionMode.EQUAL, this, value);
	}

	public Expression ge(Object value) {
		return new PropertyExpression(ConditionMode.GREATER_EQUAL, this, value);
	}

	public Expression gt(Object value) {
		return new PropertyExpression(ConditionMode.GREATER_THEN, this, value);
	}

	public Expression ilike(Object value) {
		return new PropertyExpression(ConditionMode.IFUZZY, this, value);
	}
	
	public Expression ilikeLeft(Object value) {
		return new PropertyExpression(ConditionMode.IFUZZY_LEFT, this, value);
	}
	
	public Expression ilikeRight(Object value) {
		return new PropertyExpression(ConditionMode.IFUZZY_RIGHT, this, value);
	}
	
	public Expression inotlike(Object value) {
		return new PropertyExpression(ConditionMode.NOT_IFUZZY, this, value);
	}
	
	public Expression inotlikeLeft(Object value) {
		return new PropertyExpression(ConditionMode.NOT_IFUZZY_LEFT, this, value);
	}
	
	public Expression inotlikeRight(Object value) {
		return new PropertyExpression(ConditionMode.NOT_IFUZZY_RIGHT, this, value);
	}

	public Expression query(String value, ConditionMode type) {
		return new PropertyExpression(type, this, value);
	}

	public Expression in(List<?> values) {
		return new PropertyExpression(ConditionMode.IN, this, values);
	}
	
	public Expression in(Object[] values) {
		return new PropertyExpression(ConditionMode.IN, this, values);
	}

	public Expression isEmpty() {
		return new PropertyExpression(ConditionMode.EMPTY, this);
	}

	public Expression isNotEmpty() {
		return new PropertyExpression(ConditionMode.NOT_EMPTY, this);
	}

	public Expression isNotNull() {
		return new PropertyExpression(ConditionMode.NOT_NULL, this);
	}

	public Expression isNull() {
		return new PropertyExpression(ConditionMode.ISNULL, this);
	}

	public Expression le(Object value) {
		return new PropertyExpression(ConditionMode.LESS_EQUAL, this, value);
	}

	public Expression like(Object value) {
		return new PropertyExpression(ConditionMode.FUZZY, this, value);
	}
	
	public Expression notlike(Object value) {
		return new PropertyExpression(ConditionMode.NOT_FUZZY, this, value);
	}

	public Expression lt(Object value) {
		return new PropertyExpression(ConditionMode.LESS_THEN, this, value);
	}

	public Expression ne(Object value) {
		return new PropertyExpression(ConditionMode.NOT_EMPTY, this, value);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
