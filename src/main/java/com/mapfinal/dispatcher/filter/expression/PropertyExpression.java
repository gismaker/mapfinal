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
package com.mapfinal.dispatcher.filter.expression;

import java.io.Serializable;
import java.util.List;

import com.mapfinal.dispatcher.filter.Expression;
import com.mapfinal.dispatcher.filter.FilterVisitor;
import com.mapfinal.dispatcher.filter.Property;
import com.mapfinal.dispatcher.query.ConditionMode;

public class PropertyExpression implements Expression, Serializable {

	private static final long serialVersionUID = 1L;
	private ConditionMode type;
	private Property property;
	private Object value;
	private Object secondValue;
    private boolean noValue;
    private boolean singleValue;
    private boolean betweenValue;
    private boolean listValue;
	
	public PropertyExpression(ConditionMode type, Property property) {
        this.type = type;
        this.property = property;
        //this.typeHandler = null;
        this.noValue = true;
    }

    public PropertyExpression(ConditionMode type, Property property, Object value) {//String typeHandler, String valueType
        this.type = type;
        this.value = value;
        this.property = property;
        //this.typeHandler = typeHandler;
        if (value instanceof List<?>) {
            this.listValue = true;
        } else {
            this.singleValue = true;
        }
        //this.valueType = valueType;
    }
    
//    public PropertyExpression(ConditionMode type, Property property, Object value, String typeHandler) {
//        this(type, property, value, typeHandler, null);
//    }

//    public PropertyExpression(ConditionMode type, Property property, Object value) {
//        this(type, property, value, null);
//    }

    public PropertyExpression(ConditionMode type, Property property, Object value, Object secondValue) {//, String typeHandler, String valueType
        this.type = type;
        this.property = property;
        this.value = value;
        this.secondValue = secondValue;
        //this.typeHandler = typeHandler;
        this.betweenValue = true;
//        this.valueType = valueType;
//        if(this.valueType == null && value instanceof String) {
//        	this.valueType = "String";
//        }
    }
    
//    public PropertyExpression(ConditionMode type, Property property, Object value, Object secondValue, String typeHandler) {
//    	this(type, property, value, secondValue, typeHandler, null);
//    }
//
//    public PropertyExpression(ConditionMode type, Property property, Object value, Object secondValue) {
//        this(type, property, value, secondValue, null);
//    }
	
	@Override
	public boolean evaluate(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void accept(FilterVisitor visitor, Object extraData) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	@Override
	public SqlParas getSqlParas(Criteria criteria) {
		// TODO Auto-generated method stub
		SqlParas csql = new SqlParas();
		StringBuilder sb = new StringBuilder();
		String alias = StringKit.notBlank(criteria.getAlias()) ? criteria.getAlias() + "." : "";
		if(!propertyName.contains(".")) sb.append(alias);
		sb.append(propertyName);
		switch (type) {
		case EQUAL:
			sb.append(" = ");
			break;
		case NOT_EQUAL:
			sb.append(" <> ");
			break;
		case GREATER_EQUAL:
			sb.append(" >= ");
			break;
		case GREATER_THEN:
			sb.append(" > ");
			break;
		case LESS_EQUAL:
			sb.append(" <= ");
			break;
		case LESS_THEN:
			sb.append(" < ");
			break;
		default:
			sb.append(" = ");
			break;
		}
		//sb.append(alias);
		sb.append(otherPropertyName);
		csql.setSql(sb.toString());
		return csql;
	}
	*/
	public ConditionMode getType() {
		return type;
	}

	public void setType(ConditionMode type) {
		this.type = type;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(Object secondValue) {
		this.secondValue = secondValue;
	}

	public boolean isNoValue() {
		return noValue;
	}

	public void setNoValue(boolean noValue) {
		this.noValue = noValue;
	}

	public boolean isSingleValue() {
		return singleValue;
	}

	public void setSingleValue(boolean singleValue) {
		this.singleValue = singleValue;
	}

	public boolean isBetweenValue() {
		return betweenValue;
	}

	public void setBetweenValue(boolean betweenValue) {
		this.betweenValue = betweenValue;
	}

	public boolean isListValue() {
		return listValue;
	}

	public void setListValue(boolean listValue) {
		this.listValue = listValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
