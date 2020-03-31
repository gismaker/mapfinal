package com.mapfinal.resource.shapefile.dbf;

import java.util.ArrayList;

import com.mapfinal.resource.shapefile.shpx.BigEndian;

/**
 * 对属性数据的表结构的管理
 * 
 * @author proxyme
 * 
 */
public class MapTableDesc {
	private ArrayList<FieldElement> mFieldsDesc;

	public MapTableDesc() {
		mFieldsDesc = new ArrayList<FieldElement>();
	}

	public MapTableDesc(MapTableDesc tableDesc) {
		short count = tableDesc.getFieldCount();
		for (short i = 0; i < count; i++) {
			FieldElement des = new FieldElement();
			FieldElement src = tableDesc.getDesc(i);
			System.arraycopy(src.szFiledName, 0, des.szFiledName, 0,
					des.szFiledName.length);
			des.fieldType = src.fieldType;
			des.fieldDecimal = src.fieldDecimal;
			des.fieldLength = src.fieldLength;
			des.offset = src.offset;
			des.dbaseiv_id = src.dbaseiv_id;
			System.arraycopy(src.reserved1, 0, des.reserved1, 0,
					des.reserved1.length);
			System.arraycopy(src.reserved2, 0, des.reserved2, 0,
					des.reserved2.length);
			des.productionIndex = src.productionIndex;
			mFieldsDesc.add(des);
		}
	}

	public short getFieldCount() {
		return (short) mFieldsDesc.size();
	}

	public void setFieldCount(short count) {

	}

	public String getFieldName(short index) {
		if (index < 0 || index > mFieldsDesc.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		return new String(mFieldsDesc.get(index).szFiledName);
	}

	public void setFieldName(short index, String fieldName) {
		if (index < 0 || index > mFieldsDesc.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		FieldElement des = mFieldsDesc.get(index);
		System.arraycopy(fieldName.getBytes(), 0, des.szFiledName, 0,
				des.szFiledName.length);
	}

	public long getFieldType(short index) {
		if (index < 0 || index > mFieldsDesc.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		FieldElement fe = mFieldsDesc.get(index);
		return (long) fe.fieldType & 0xff;
	}

	public void setFieldType(short index, long fieldType) {
		if (index < 0 || index > mFieldsDesc.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		FieldElement fe = mFieldsDesc.get(index);
		fe.fieldType = BigEndian.toChar((byte)(fieldType & 0xff));
	}

	public short getFieldPrecision(short index) {
		if (index < 0 || index > mFieldsDesc.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		return mFieldsDesc.get(index).fieldDecimal;
	}

	public void setFieldPrecision(short index, short fieldPrecision) {
		if (index < 0 || index > mFieldsDesc.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		mFieldsDesc.get(index).fieldDecimal = (byte) (fieldPrecision & 0xff);
	}

	public short getFieldLength(short index) {
		if (index < 0 || index > mFieldsDesc.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		return mFieldsDesc.get(index).fieldLength;
	}

	public void setFieldLength(short index, short fieldLength) {
		if (index < 0 || index > mFieldsDesc.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		mFieldsDesc.get(index).fieldLength = (byte) (fieldLength & 0xff);
	}

	// public short getFieldScale(short index) {
	//
	// }

	// public void setFieldScale(short index, short fieldScale) {
	//
	// }

	public FieldElement getDesc(short index) {
		if (index < 0 || index > mFieldsDesc.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		return mFieldsDesc.get(index);
	}

	public void add(FieldElement fieldElement) {
		if (fieldElement != null) {
			mFieldsDesc.add(fieldElement);
		}
	}

	public void remove(short index) {
		if (index < 0 || index > mFieldsDesc.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		mFieldsDesc.remove(index);
	}

	public void insert(short index, FieldElement fieldElement) {

	}

	public void clear() {
		mFieldsDesc.clear();
	}
}
