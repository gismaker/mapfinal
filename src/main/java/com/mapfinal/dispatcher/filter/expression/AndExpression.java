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

import java.util.ArrayList;
import java.util.List;

import com.mapfinal.dispatcher.filter.Expression;
import com.mapfinal.dispatcher.filter.FilterVisitor;

public class AndExpression implements Expression {

	private List<Expression> expressionList;
	
	public AndExpression() {
		this.expressionList = new ArrayList<Expression>();
	}
	
	public AndExpression add(Expression expression) {
		expressionList.add(expression);
		return this;
	}
	
	public int size() {
		return expressionList.size();
	}
	
	public List<Expression> getExpressionList() {
		return expressionList;
	}

	@Override
	public boolean evaluate(Object object) {
		for (Expression expression : expressionList) {
			boolean flag = expression.evaluate(object);
			if(!flag) return flag;
		}
		return true;
	}

	@Override
	public void accept(FilterVisitor visitor, Object extraData) {
		for (Expression expression : expressionList) {
			expression.accept(visitor, extraData);
		}
	}
	
	/*
	@Override
	public SqlParas getSqlParas(Criteria criteria) {
		// TODO Auto-generated method stub
		SqlParas csql = criterion.getSqlParas(criteria);
		csql.setSql(" not (" + csql.getSql() + ") "); 
		return csql;
	}*/

}
