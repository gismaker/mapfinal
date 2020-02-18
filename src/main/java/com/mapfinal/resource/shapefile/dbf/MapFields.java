package com.mapfinal.resource.shapefile.dbf;

import java.util.ArrayList;

/**
 * 对属性数据的单条记录管理
 * 
 * @author proxyme
 * 
 */
public class MapFields {
	private ArrayList<MapField> mFields = null;

	public MapFields() {
		mFields = new ArrayList<MapField>();
	}

	public MapFields(MapFields fields) {
		mFields = new ArrayList<MapField>();
		int size = fields.getCount();
		for (short i = 0; i < size; i++) {
			mFields.add(new MapField(fields.getField(i)));
		}
	}

	public short getCount() {
		return (short) mFields.size();
	}

	public void add(MapField field) {
		if (field != null) {
			mFields.add(field);
		}
	}

	public void remove(short index) {
		if (index < 0 || index > mFields.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		mFields.remove(index);
	}

	public void insert(short index, MapField field) {
		// 暂时未写
	}

	public MapField getField(short index) {
		if (index < 0 || index > mFields.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		return mFields.get(index);
	}

	public void clear() {
		mFields.clear();
	}
}
