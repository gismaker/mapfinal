package com.mapfinal.map;

public class Field {

	public enum FieldType {
		/**
		 * 二进制数据
		 */
		BLOB,
		/**
		 * 布尔数据类型
		 */
		BOOLEAN,
		/**
		 * 字节类型
		 */
		BYTE,
		/**
		 * 字符型
		 */
		CHAR, CLOB,
		/**
		 * 日期型
		 */
		DATE,
		/**
		 * 双精度浮点型
		 */
		DOUBLE,
		/**
		 * 单精度浮点型
		 */
		FLOAT,
		/**
		 * 整型
		 */
		INTEGER,
		/**
		 * 长整型
		 */
		LONG, REAL,
		/**
		 * 字符串型
		 */
		STRING,
		/**
		 * 时间型
		 */
		TIME,
		/**
		 * 时间
		 */
		TIMESTAMP
	}

	public static enum Orderby {
		/**
		 * 升序
		 */
		ASC,
		/**
		 * 降序
		 */
		DESC
	}

	private String name;
	private Field.FieldType type;
	private Object defaultValue;
	private int length;
	private boolean nullable;
	private int precision;

	public Field(String name, Field.FieldType type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * 获取要素对象
	 * 
	 * @return
	 */
	public java.lang.Object getDefaultValue() {
		return defaultValue;
	}

	/**
	 * 获取字段的类型
	 * 
	 * @return
	 */
	public Field.FieldType getFieldType() {
		return type;
	}

	/**
	 * 获取字段长度
	 * 
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * 获取字段的名称
	 * 
	 * @return
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * 字段值是否可以为空
	 * 
	 * @return
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * 设置要素对象
	 * 
	 * @param defaultValue
	 */
	public void setDefaultValue(java.lang.Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * 设置字段值的长度
	 * 
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * 设置字段值是否可以为空
	 * 
	 * @param nullable
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}
}
