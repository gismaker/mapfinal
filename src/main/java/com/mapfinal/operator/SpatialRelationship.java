package com.mapfinal.operator;

public enum SpatialRelationship {
	/**
	 * 包含
	 */
	CONTAINS,
	/**
	 * 穿过
	 */
	CROSSES,
	/**
	 * 不相交
	 */
	DISJOINT,
	/**
	 * 在圆范围内
	 */
	DWITHIN,
	/**
	 * 外包矩形相交
	 */
	ENVELOPE_INTERSECTS,
	/**
	 * 几何图形相等
	 */
	EQUALS,
	/**
	 * 空间索引相交
	 */
	INDEX_INTERSECTS,
	/**
	 * 相交
	 */
	INTERSECTS,
	/**
	 * 重叠
	 */
	OVERLAPS,
	/**
	 * 接触
	 */
	TOUCHES,
	/**
	 * 内部
	 */
	WITHIN

}
