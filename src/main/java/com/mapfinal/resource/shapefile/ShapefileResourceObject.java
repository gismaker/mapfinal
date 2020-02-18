package com.mapfinal.resource.shapefile;

import java.io.File;
import java.io.FileNotFoundException;

import com.mapfinal.converter.CRS;
import com.mapfinal.converter.ConverterManager;
import com.mapfinal.converter.SpatialReference;
import com.mapfinal.kit.FileKit;
import com.mapfinal.resource.FeatureResourceObject;
import com.mapfinal.resource.Resource;

public class ShapefileResourceObject extends FeatureResourceObject {

	private ShapefileReaderFactory readerFactory;
	
	public ShapefileResourceObject(String filepath) {
		super(FileKit.getFileNameNoEx(filepath), filepath, Resource.Type.shp);
		// TODO Auto-generated constructor stub
		try {
			isShapefileExists(filepath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.setReader(new ShapefileReader(this));
		this.readerFactory = new DefaultShapefileReaderFactory();
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
	public SpatialReference getSpatialReference() {
		// TODO Auto-generated method stub
		SpatialReference sr = super.getSpatialReference();
		if (sr==null) {
			CRS crs = getReader().getCRS();
			String crsName = ConverterManager.me().registCRS(crs.getName(), crs);
			setSpatialReference(new SpatialReference(crsName));
		}
		return sr;
	}

	public ShapefileReaderFactory getReaderFactory() {
		return readerFactory;
	}

	public void setReaderFactory(ShapefileReaderFactory readerFactory) {
		this.readerFactory = readerFactory;
	}
}
