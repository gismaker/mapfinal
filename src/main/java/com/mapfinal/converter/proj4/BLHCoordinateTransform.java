package com.mapfinal.converter.proj4;

import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.Proj4jException;
import org.osgeo.proj4j.ProjCoordinate;
import org.osgeo.proj4j.datum.GeocentricConverter;

/**
 * 修复proj4 高程没有转换的bug
 * https://blog.csdn.net/qq_16064871/article/details/83117248
 */
public class BLHCoordinateTransform implements CoordinateTransform {
	private CoordinateReferenceSystem srcCRS;
	private CoordinateReferenceSystem tgtCRS;
	private ProjCoordinate geoCoord = new ProjCoordinate(0.0D, 0.0D, 0.0D);
	private boolean doInverseProjection = true;
	private boolean doForwardProjection = true;
	private boolean doDatumTransform = false;
	private boolean transformViaGeocentric = false;
	private GeocentricConverter srcGeoConv;
	private GeocentricConverter tgtGeoConv;

	public BLHCoordinateTransform(CoordinateReferenceSystem srcCRS, CoordinateReferenceSystem tgtCRS) {
		this.srcCRS = srcCRS;
		this.tgtCRS = tgtCRS;
		this.doInverseProjection = srcCRS != null && srcCRS != CoordinateReferenceSystem.CS_GEO;
		this.doForwardProjection = tgtCRS != null && tgtCRS != CoordinateReferenceSystem.CS_GEO;
		this.doDatumTransform = this.doInverseProjection && this.doForwardProjection
				&& srcCRS.getDatum() != tgtCRS.getDatum();
		if (this.doDatumTransform) {
			boolean isEllipsoidEqual = srcCRS.getDatum().getEllipsoid().isEqual(tgtCRS.getDatum().getEllipsoid());
			if (!isEllipsoidEqual) {
				this.transformViaGeocentric = true;
			}

			if (srcCRS.getDatum().hasTransformToWGS84() || tgtCRS.getDatum().hasTransformToWGS84()) {
				this.transformViaGeocentric = true;
			}

			if (this.transformViaGeocentric) {
				this.srcGeoConv = new GeocentricConverter(srcCRS.getDatum().getEllipsoid());
				this.tgtGeoConv = new GeocentricConverter(tgtCRS.getDatum().getEllipsoid());
			}
		}

	}

	public CoordinateReferenceSystem getSourceCRS() {
		return this.srcCRS;
	}

	public CoordinateReferenceSystem getTargetCRS() {
		return this.tgtCRS;
	}

	public ProjCoordinate transform(ProjCoordinate src, ProjCoordinate tgt) throws Proj4jException {
		if (this.doInverseProjection) {
			this.srcCRS.getProjection().inverseProjectRadians(src, this.geoCoord);
			// 高程赋值
			this.geoCoord.z = src.z;
		} else {
			this.geoCoord.setValue(src);
		}

		if (this.doDatumTransform) {
			this.datumTransform(this.geoCoord);
		}

		if (this.doForwardProjection) {
			this.tgtCRS.getProjection().projectRadians(this.geoCoord, tgt);
			// 高程赋值
			tgt.z = this.geoCoord.z;
		} else {
			tgt.setValue(this.geoCoord);
		}

		return tgt;
	}

	// 涉及到七参数 或者 三参数转换
	private void datumTransform(ProjCoordinate pt) {
		// if (this.srcCRS.getDatum().isEqual(this.tgtCRS.getDatum())) {
		if (this.transformViaGeocentric) {
			this.srcGeoConv.convertGeodeticToGeocentric(pt);
			if (this.srcCRS.getDatum().hasTransformToWGS84()) {
				this.srcCRS.getDatum().transformFromGeocentricToWgs84(pt);
			}

			if (this.tgtCRS.getDatum().hasTransformToWGS84()) {
				this.tgtCRS.getDatum().transformToGeocentricFromWgs84(pt);
			}

			this.tgtGeoConv.convertGeocentricToGeodetic(pt);
		}
		// }
	}
}
