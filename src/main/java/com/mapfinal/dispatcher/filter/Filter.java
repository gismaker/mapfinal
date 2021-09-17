package com.mapfinal.dispatcher.filter;

import com.mapfinal.dispatcher.filter.order.OrderBy;

/**
 * 使用 Criterion 设置查询条件。
 * 使用Projection 设置Group查询条件。
 * @author yangyong
 *
 */
public class Filter {

	//查询条件
	private Expression expression;
	//排序
	private OrderBy orderby = null;
	
	public Filter(Expression expression) {
		this.expression = expression;
	}
	
	public boolean evaluate(Object object) {
		return expression.evaluate(object);
	}
	
	public void accept(FilterVisitor visitor, Object extraData) {
		expression.accept(visitor, extraData);
	}
		
}
