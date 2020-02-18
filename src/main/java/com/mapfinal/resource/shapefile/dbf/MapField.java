package com.mapfinal.resource.shapefile.dbf;

enum DbfFieldType {
	fdString, // 字符串类型
	fdInteger, // 整形
	fdDouble, // 浮点型
	fdInvaild// 未知类型
};

class VarValue {
	public static int fdString = 0x00;
	public static int fdInteger = 0x01;
	public static int fdDouble = 0x02;
	public static int fdInvaild = 0x02;
}

/**
 * 对属性数据的字段的管理
 * 
 * @author proxyme
 * 
 */
public class MapField {
	private String fieldName;
	private long fieldType;
	private Object varValue;
	//private String strValue;

	public MapField() {

	}

	public MapField(MapField field) {

	}

	public String getName() {
		return this.fieldName;
	}

	public void setName(String name) {
		this.fieldName = name;
	}

	public long getType() {
		return fieldType;
	}

	public void setType(long type) {
		this.fieldType = type;
	}

	public String getStringValue() {
		return String.valueOf(this.varValue);
	}

//	public void setStringValue(String value) {
//		this.strValue = value;
//	}

	public Object getValue() {
		return this.varValue;
	}

	public void setValue(Object value) {
		this.varValue = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name: " + fieldName + ", type: " + fieldType + ", value: " + varValue;
	}
}
