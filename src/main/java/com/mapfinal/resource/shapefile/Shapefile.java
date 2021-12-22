package com.mapfinal.resource.shapefile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

import com.mapfinal.converter.CRS;
import com.mapfinal.converter.ConverterManager;
import com.mapfinal.converter.SpatialReference;
import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.QueryParameter;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.kit.ExchangeUtil;
import com.mapfinal.kit.FileKit;
import com.mapfinal.kit.StringKit;
import com.mapfinal.map.FeatureClass;
import com.mapfinal.map.Field;
import com.mapfinal.map.Field.FieldType;
import com.mapfinal.resource.FeatureResource;
import com.mapfinal.resource.shapefile.dbf.MapField;
import com.mapfinal.resource.shapefile.dbf.MapFields;
import com.mapfinal.resource.shapefile.dbf.MapRecordSet;
import com.mapfinal.resource.shapefile.dbf.MapTableDesc;
import com.mapfinal.resource.shapefile.dbf.RecordStart;
import com.mapfinal.resource.shapefile.shpx.ShpRandomAccess;
import com.mapfinal.resource.shapefile.shpx.ShpType;
import com.mapfinal.resource.shapefile.shpx.ShxRandomAccess;

public class Shapefile extends FeatureResource<Long> {

	/**
	 * Shp文件输入流
	 */
	private ShpRandomAccess shpRandomAccess;
	/**
	 * Shx文件输入流
	 */
	private ShxRandomAccess shxRandomAccess;
	/**
	 * dbf
	 */
	private MapRecordSet recordSet;
	private String charsetName = null;//"utf-8";
	
	private boolean binit = false;
	
	private FeatureClass<Long> featureClass;
	
	public Shapefile(String url, String charsetName) {
		this.charsetName = charsetName;
		setUrl(url);
		setName(FileKit.getFileNameNoEx(getUrl()));
		try {
			isShapefileExists(getUrl());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		recordSet = new MapRecordSet();
		ShapefileManager.me().addResource(url, this);
	}
	
	public Shapefile(String url) {
		// TODO Auto-generated constructor stub
		setUrl(url);
		setName(FileKit.getFileNameNoEx(getUrl()));
		try {
			isShapefileExists(getUrl());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		recordSet = new MapRecordSet();
		ShapefileManager.me().addResource(url, this);
	}
	
	private void isShapefileExists(String filepath) throws FileNotFoundException {
		// 判断shp文件是否存在
		File fShp = new File(filepath);
		if (!fShp.exists()) {
			// logger.error(fileName + " doesn't exist.");
			throw new FileNotFoundException(filepath + " doesn't exist.");
		}
		// shp文件所在目录
		String shpFilePath = filepath.substring(0, filepath.lastIndexOf('.'));
		// 判断shx文件是否存在
		String shxFileName = shpFilePath + ".shx";
		File fShx = new File(shxFileName);
		if (!fShx.exists()) {
			// logger.error(shxFileName + " doesn't exist.");
			throw new FileNotFoundException(shxFileName + " doesn't exist.");
		}
		// 判断prj文件是否存在
		String prjFileName = shpFilePath + ".prj";
		File fPrj = new File(prjFileName);
		if (!fPrj.exists()) {
			// logger.error(shxFileName + " doesn't exist.");
			throw new FileNotFoundException(prjFileName + " doesn't exist.");
		}
		// 判断dbf文件是否存在
		String dbfFilePath = shpFilePath + ".dbf";
		if (!new File(dbfFilePath).exists()) {
			// logger.error(dbfFilePath + " doesn't exist.");
			throw new FileNotFoundException(dbfFilePath + " doesn't exist.");
		}
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "shp";
	}
	
	@Override
	public void prepare(Event event) {
		// TODO Auto-generated method stub
		if(binit) return;
		long start = System.currentTimeMillis();
		try {
			if (getUrl() == null) {
				throw new NullPointerException();
			}
			// 判断shp文件是否存在
			File fShp = new File(getUrl());
			if (!fShp.exists()) {
				//logger.error(fileName + " doesn't exist.");
				throw new FileNotFoundException(getUrl() + " doesn't exist.");
			}
			// shp文件所在目录
			String shpFilePath = getUrl().substring(0, getUrl().lastIndexOf('.'));
			// 判断shx文件是否存在
			String shxFileName = shpFilePath + ".shx";
			File fShx = new File(shxFileName);
			if (!fShx.exists()) {
				//logger.error(shxFileName + " doesn't exist.");
				throw new FileNotFoundException(shxFileName + " doesn't exist.");
			}
			// 判断dbf文件是否存在
			String dbfFilePath = shpFilePath + ".dbf";
			if (!new File(dbfFilePath).exists()) {
				//logger.error(dbfFilePath + " doesn't exist.");
				throw new FileNotFoundException(dbfFilePath + " doesn't exist.");
			}
			shxRandomAccess = new ShxRandomAccess(fShx);
			shpRandomAccess = new ShpRandomAccess(fShp, shxRandomAccess);
			if (!readDBF(dbfFilePath)) {
				setFeatureClass(new FeatureClass<>(null));
			} else {
				setFeatureClass(new FeatureClass<>(getFields()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		featureClass.setEnvelope(shpRandomAccess.getExtent());
		featureClass.setName(getName());
		String geometryType = ShpType.shpTypeName(shxRandomAccess.getShpType());
		featureClass.setGeometryType(geometryType);
		binit = true;
		System.out.println("[Shapefile] readInit times: " + (System.currentTimeMillis() - start));
	}
	
	/**
	 * 读入DBF文件格式
	 * 
	 * @param dbfFile
	 *            dbf文件绝对路径
	 * @return 读取成功返回true,否则返回false
	 * @throws IOException
	 */
	protected boolean readDBF(String dbfFile) throws IOException { /* 已测试 */
		if(this.recordSet==null) this.recordSet = new MapRecordSet();
		if(StringKit.isBlank(charsetName)) {
			charsetName = getCharset(dbfFile);
		}
		this.recordSet.setCharsetName(charsetName);
		return recordSet.openDBF(dbfFile);
	}
	
	protected String getCharset(String dbfFile) {
		ExchangeUtil s = new ExchangeUtil();
		String charset = ExchangeUtil.javaname[s.detectEncoding(new File(dbfFile))];
		return charset;
	}

	@Override
	public Dispatcher connection(Event event) {
		// TODO Auto-generated method stub
		try {
			prepare(event);
			return shpRandomAccess.buildDispatcher(this, shxRandomAccess);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ShapefileFeature read(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		if(!binit) return null;
		Long id = Long.valueOf(sio.getId());
		if(id == null || id < 0 || id > shxRandomAccess.getRecordCount()) {
			return null;
		}
		ShapefileFeature feature = (ShapefileFeature) getFeatureClass().getFeature(id);
		if(feature==null) {
			try {
				feature = readRecord(id.intValue(), sio);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(feature!=null) {
				getFeatureClass().putFeature(feature.getId(), feature);
			}
		}
		return feature;
	}
	
	public void close() {
		// TODO Auto-generated method stub
		try {
			shpRandomAccess.close();
			shxRandomAccess.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		recordSet = null;
		recordSet = new MapRecordSet();
	}
	
	/**
	 * 读入Shp对象坐标,polyline和polygon读取方法差不多
	 * 
	 * @return 成功返回true,否则返回false
	 * @throws IOException
	 */
	protected ShapefileFeature readRecord(int i, SpatialIndexObject obj) throws IOException {
		switch (shxRandomAccess.getShpType()) {
		case ShpType.NULL_SHAPE:
			break;
		case ShpType.POINT: /* 已测试 */
			return readRecordPoint(i, obj);
		case ShpType.POLYLINE: /* 已测试 */
			return readRecordPolyline(i, obj);
		case ShpType.POLYGON:/* 已测试 */
			return readRecordPolygon(i, obj);
		case ShpType.MULTIPOINT:
			break;
		case ShpType.POINT_Z:
			break;
		case ShpType.POLYLINE_Z:
			break;
		case ShpType.POLYGONZ:
			break;
		case ShpType.MULTIPOINT_Z:
			break;
		case ShpType.POINT_M:
			break;
		case ShpType.POLYLINE_M:
			break;
		case ShpType.POLYGON_M:
			break;
		case ShpType.MULTIPOINT_M:
			break;
		case ShpType.MULTIPATCH:
			break;
		}
		return null;
	}
	
	private ShapefileFeature readRecordPoint(int i, SpatialIndexObject obj) throws IOException {
		Point point = null;
		if(obj.getEnvelope()==null) {
			point = shpRandomAccess.readRecordPoint(shxRandomAccess.getRecordPosition(i+1));
		} else {
			point = GeoKit.getGeometryFactory().createPoint(obj.getEnvelope().centre());
		}
		point.setUserData(i);
		ShapefileFeature feature = new ShapefileFeature(obj.getId(), obj, point, shxRandomAccess.getShpType());
		feature.setEnvelope(point.getEnvelopeInternal());
		//属性
		this.recordSet.move(i, RecordStart.BookmarkFirst);
		MapFields mFields = this.recordSet.getFields(0);
		for (short k = 0; k < mFields.getCount(); k++) {
			MapField mField = mFields.getField(k);
			feature.putAttr(mField.getName(), mField.getValue());
		}
		return feature;		
	}
	
	private ShapefileFeature readRecordPolyline(int i, SpatialIndexObject obj) throws IOException {
		MultiLineString line = shpRandomAccess.readRecordPolyline(shxRandomAccess.getRecordPosition(i+1));
		line.setUserData(i);
		ShapefileFeature feature = new ShapefileFeature(obj.getId(), obj, line, shxRandomAccess.getShpType());
		feature.setEnvelope(line.getEnvelopeInternal());
		//属性
		this.recordSet.move(i, RecordStart.BookmarkFirst);
		MapFields mFields = this.recordSet.getFields(0);
		for (short k = 0; k < mFields.getCount(); k++) {
			MapField mField = mFields.getField(k);
			feature.putAttr(mField.getName(), mField.getValue());
		}
		return feature;		
	}
	
	private ShapefileFeature readRecordPolygon(int i, SpatialIndexObject obj) throws IOException {
		MultiPolygon mpolygon = shpRandomAccess.readRecordPolygon(shxRandomAccess.getRecordPosition(i+1));
		mpolygon.setUserData(i);
		//ShapefileFeatureRender renderer = factory.build(mpolygon, shxRandomAccess.getShpType());
		//System.out.println("read shp polygon times: " + (System.currentTimeMillis() - start));
		//return new ShapefileFeature(obj.getId(), obj, renderer, mpolygon.getEnvelopeInternal());
		mpolygon.setUserData(i - 1);
		ShapefileFeature feature = new ShapefileFeature(obj.getId(), obj, mpolygon, shxRandomAccess.getShpType());
		feature.setEnvelope(mpolygon.getEnvelopeInternal());
		
		//属性
		this.recordSet.move(i, RecordStart.BookmarkFirst);
		MapFields mFields = this.recordSet.getFields(0);
		for (short k = 0; k < mFields.getCount(); k++) {
			MapField mField = mFields.getField(k);
			feature.putAttr(mField.getName(), mField.getValue());
			//System.out.println("[Shp-Random-Reader] Feature " + mField.getName() + ": " + str);
		}
		//System.out.println("[readRecordPolygon] id: " + feature.getId());
		return feature;		
	}

	public ShpRandomAccess getShpRandomAccess() {
		return shpRandomAccess;
	}

	public ShxRandomAccess getShxRandomAccess() {
		return shxRandomAccess;
	}
	

	/**
	 * 获得当前地图文件最大矩形范围
	 * 
	 * @return 地图矩形范围
	 */
	public Envelope getExtent() {
		return this.shpRandomAccess.getExtent();
	}

	public int getShpFileLength() {
		return this.shpRandomAccess.getShpFileLength();
	}
	
	public int getRecordCount() {
		return shxRandomAccess.getRecordCount();
	}
	
	public int getShpType() {
		return shxRandomAccess.getShpType();
	}
	
	public SpatialReference getSpatialReference() {
		if (featureClass!=null && featureClass.getSpatialReference()==null) {
			CRS crs = getCRS();
			if(crs!=null && StringKit.notBlank(crs.getName())) {
				String crsName = ConverterManager.me().registCRS(crs.getName(), crs);
				featureClass.setSpatialReference(new SpatialReference(crsName));
			} else {
				featureClass.setSpatialReference(SpatialReference.wgs84());
			}
			return featureClass.getSpatialReference();
		}
		return null;
	}

	public CRS getCRS() {
		// TODO Auto-generated method stub
		// shp文件所在目录
		String shpFilePath = getUrl().substring(0, getUrl().lastIndexOf('.'));
		// 判断shx文件是否存在
		String prjFileName = shpFilePath + ".prj";
		File fprj = new File(prjFileName);
		if (!fprj.exists()) {
			//logger.error(shxFileName + " doesn't exist.");
			return null;
		}
		BufferedReader reader = null;  
		String prjWkt = "";
        try {  
            //System.out.println("以行为单位读取文件内容，一次读一整行：");  
            reader = new BufferedReader(new FileReader(fprj));  
            String tempString = null;  
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                // 显示行号  
                //System.out.println("line " + line + ": " + tempString);  
                prjWkt += tempString;
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }
        CRS crs = ConverterManager.me().getFactory().parseWKT(null, prjWkt);
        if(crs==null) {
        	String[] param = new String[]{prjWkt};
        	crs = new CRS(FileKit.getFileNameNoEx(shpFilePath), param, CRS.OGRCS);
        }
        return crs;
	}
	
	
	/**
	 * dbf record
	 * @return
	 */
	public MapRecordSet getRecordSet() {
		return this.recordSet;
	}
	
	/**
	 * 获取某一条记录
	 * @param i
	 * @return
	 */
	public MapFields getRecord(int i) {
		try {
			this.recordSet.move(i, RecordStart.BookmarkFirst);
			MapFields mFields = this.recordSet.getFields(0);
			return mFields;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Field> getFields() {
		MapTableDesc desc = this.recordSet.getTableDesc();
		short cnt = desc.getFieldCount();
		List<Field> myfields = new ArrayList<Field>();
		for (short i = 0; i < cnt; i++) {
			String name = desc.getFieldName(i);
			//System.out.print("name：" + name.trim());
			long fieldType = desc.getFieldType(i);
			//System.out.print(", type：" + fieldType);
			short length = desc.getFieldLength(i);
			//System.out.print(", length：" + length);
			short fieldDecimal = desc.getFieldPrecision(i);
			//System.out.println(", precision：" + fieldDecimal);
			Field.FieldType ftype = FieldType.CHAR;
			if (fieldType == 'N' || fieldType == 'F') {
				if (fieldDecimal == 0) {
					// 1表示整形
					ftype = FieldType.INTEGER;
				} else {
					// 2表示double
					ftype = FieldType.FLOAT;
				}
			} else if (fieldType == 'C') {
				// 0表示string
				ftype = FieldType.STRING;
			} else if (fieldType == 'L') {
				ftype = FieldType.BOOLEAN;
			} else if (fieldType == 'B') {
				ftype = FieldType.BLOB;
			} else if (fieldType == 'D') {
				ftype = FieldType.DATE;
			} else {
				// 3表示Invaild
				ftype = FieldType.BYTE;
			}
			Field fld = new Field(name.trim(), ftype);
			fld.setLength(length);
			fld.setPrecision(fieldDecimal);
			myfields.add(fld);
		}
		return myfields;
	}

	@Override
	public FeatureClass<Long> read(Event event) {
		// TODO Auto-generated method stub
		if(!binit) return null;
		FeatureClass<Long> resultFeature = featureClass.clone();
		for(int i = 0; i < shxRandomAccess.getRecordCount(); i++) {
			Long id = Long.valueOf(i);
			ShapefileFeature feature = (ShapefileFeature) getFeatureClass().getFeature(id);
			if(feature==null) {
				try {
					String sid = String.valueOf(i);
					String dataType = "shp";
					String geometryType = ShpType.shpTypeName(shxRandomAccess.getShpType());
					SpatialIndexObject sio = new SpatialIndexObject(sid, dataType, geometryType, null);
					feature = readRecord(id.intValue(), sio);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(feature!=null) {
					getFeatureClass().putFeature(feature.getId(), feature);
				}
			}
		}
		return resultFeature;		
	}

	@Override
	public void writer(Event event, FeatureClass<Long> data) {
		// TODO Auto-generated method stub
		//undo
	}

	public FeatureClass<Long> getFeatureClass() {
		return featureClass;
	}

	public void setFeatureClass(FeatureClass<Long> featureClass) {
		this.featureClass = featureClass;
	}
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return featureClass!=null ? featureClass.getEnvelope() : null;
	}

	@Override
	public FeatureClass<Long> queryFeatures(QueryParameter query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEvent(Event event) {
		// TODO Auto-generated method stub
		
	}
}
