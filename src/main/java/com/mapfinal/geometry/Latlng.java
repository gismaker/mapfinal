package com.mapfinal.geometry;

import org.locationtech.jts.geom.Coordinate;

import com.mapfinal.converter.ArrayMatrix;
import com.mapfinal.converter.Matrix2D;
import com.mapfinal.converter.Matrix3D;

public class Latlng extends Coordinate {

	private static final long serialVersionUID = 4196912632391641961L;

	public Latlng(double lat, double lng, double z) {
		super(lng, lat, z);
	}

	public Latlng(double lat, double lng) {
		super(lng, lat);
	}

	public Latlng(Latlng latlng) {
		super(latlng);
	}

	public static Latlng create(Coordinate coordinate) {
		if (coordinate instanceof Latlng) {
			return new Latlng((Latlng) coordinate);
		} else {
			return new Latlng(coordinate.y, coordinate.x, coordinate.getZ());
		}
	}
	
	public static Latlng toGcj(Coordinate coordinate) {
		Latlng latlng;
		if (coordinate instanceof Latlng) {
			latlng = new Latlng((Latlng) coordinate);
		} else {
			latlng = new Latlng(coordinate.y, coordinate.x, coordinate.getZ());
		}
		latlng.gcj();
		return latlng;
	}
	
	public void gcj() {
		// a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
		double a = 6378245.0;
		// ee: 椭球的偏心率。
		double ee = 0.00669342162296594323;
		double dLat = transformLat(lng() - 105.0, lat() - 35.0);
		double dLon = transformLon(lng() - 105.0, lat() - 35.0);
		double radLat = lat() / 180.0 * Math.PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
		setLat(lat() + dLat);
		setLng(lng() + dLon);
	}
	
	public void wgs() {
		double initDelta = 0.01;
		double threshold = 0.000000001;
		double dLat = initDelta, dLon = initDelta;
		double mLat = lat() - dLat, mLon = lng() - dLon;
		double pLat = lat() + dLat, pLon = lng() + dLon;
		double wgsLat, wgsLon, i = 0;
		while (true) {
			wgsLat = (mLat + pLat) / 2;
			wgsLon = (mLon + pLon) / 2;
			double[] tmp = wgs2gcj(wgsLat, wgsLon);
			dLat = tmp[0] - lat();
			dLon = tmp[1] - lng();
			if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
				break;

			if (dLat > 0)
				pLat = wgsLat;
			else
				mLat = wgsLat;
			if (dLon > 0)
				pLon = wgsLon;
			else
				mLon = wgsLon;

			if (++i > 10000)
				break;
		}
		setLat(wgsLat);
		setLng(wgsLon);
	}

	public Latlng toGcj() {
		// a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
		double a = 6378245.0;
		// ee: 椭球的偏心率。
		double ee = 0.00669342162296594323;
		double dLat = transformLat(lng() - 105.0, lat() - 35.0);
		double dLon = transformLon(lng() - 105.0, lat() - 35.0);
		double radLat = lat() / 180.0 * Math.PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);

		double mgLat = lat() + dLat;
		double mgLon = lng() + dLon;
		return new Latlng(mgLat, mgLon);
	}

	// GCJ-02 to WGS-84 粗略
	public double[] wgs2gcj(double lat, double lon) {
		// a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
		double a = 6378245.0;
		// ee: 椭球的偏心率。
		double ee = 0.00669342162296594323;
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * Math.PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new double[] { mgLat, mgLon };
	}

	public Latlng toWgs() {
		double initDelta = 0.01;
		double threshold = 0.000000001;
		double dLat = initDelta, dLon = initDelta;
		double mLat = lat() - dLat, mLon = lng() - dLon;
		double pLat = lat() + dLat, pLon = lng() + dLon;
		double wgsLat, wgsLon, i = 0;
		while (true) {
			wgsLat = (mLat + pLat) / 2;
			wgsLon = (mLon + pLon) / 2;
			double[] tmp = wgs2gcj(wgsLat, wgsLon);
			dLat = tmp[0] - lat();
			dLon = tmp[1] - lng();
			if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
				break;

			if (dLat > 0)
				pLat = wgsLat;
			else
				mLat = wgsLat;
			if (dLon > 0)
				pLon = wgsLon;
			else
				mLon = wgsLon;

			if (++i > 10000)
				break;
		}
		return new Latlng(wgsLat, wgsLon);
	}

	public double lat() {
		return y;
	}

	public double lng() {
		return x;
	}
	
	public void setLat(double lat) {
		y = lat;
	}

	public void setLng(double lng) {
		x = lng;
	}
	
	public Latlng transform(Matrix2D m2d) {
		float[][] a = m2d.toArray();
		return new Latlng(a[1][0] * x + a[1][1] * y + a[1][2], a[0][0] * x + a[0][1] * y + a[0][2]);
	}

	public Latlng add(float lat, float lng) {
		return new Latlng(this.lat() + lat, this.lng() + lng);
	}
	
	public Latlng transform(Matrix3D j3d) {
		float[][] v1 = { { (float) x, (float) y, (float) getZ(), 1 } };
		ArrayMatrix m = new ArrayMatrix(v1);
		float[][] v2 = m.times(j3d).toArray();
		if (v2 == null)
			return null;
		return new Latlng(v2[0][1], v2[0][0], v2[0][2]);
	}

	public Latlng add(float lat, float lng, float z) {
		return new Latlng(this.lat() + lat, this.lng() + lng, this.getZ() + z);
	}

	public float norm() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public String toString() {
		return "(lat:" + y + ", lng:" + x + ", altitude:" + getZ() + ")";
	}

	private double transformLat(double lat, double lon) {
		double ret = -100.0 + 2.0 * lat + 3.0 * lon + 0.2 * lon * lon + 0.1 * lat * lon
				+ 0.2 * Math.sqrt(Math.abs(lat));
		ret += (20.0 * Math.sin(6.0 * lat * Math.PI) + 20.0 * Math.sin(2.0 * lat * Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lon * Math.PI) + 40.0 * Math.sin(lon / 3.0 * Math.PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(lon / 12.0 * Math.PI) + 320 * Math.sin(lon * Math.PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLon(double lat, double lon) {
		double ret = 300.0 + lat + 2.0 * lon + 0.1 * lat * lat + 0.1 * lat * lon + 0.1 * Math.sqrt(Math.abs(lat));
		ret += (20.0 * Math.sin(6.0 * lat * Math.PI) + 20.0 * Math.sin(2.0 * lat * Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lat * Math.PI) + 40.0 * Math.sin(lat / 3.0 * Math.PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(lat / 12.0 * Math.PI) + 300.0 * Math.sin(lat / 30.0 * Math.PI)) * 2.0 / 3.0;
		return ret;
	}
}
