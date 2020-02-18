package com.mapfinal.dispatcher;

import java.util.Map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.map.Field;
import com.mapfinal.map.Field.Orderby;
import com.mapfinal.operator.SpatialRelationship;

import org.locationtech.jts.geom.Geometry;

public abstract class QueryParameter {

	public abstract Geometry getGeometry();

	public abstract SpatialReference getInSpatialReference();

	public abstract int getMaxFeatures();

	public abstract java.util.Map<String, Field.Orderby> getOrderByFields();

	public abstract String[] getOutFields();

	public abstract SpatialReference getOutSpatialReference();

	public abstract SpatialRelationship getSpatialRelationship();

	public abstract String getWhere();

	public abstract void setGeometry(Geometry geometry);

	public abstract void setInSpatialReference(SpatialReference inSR);

	public abstract void setMaxFeatures(int maxFeatures);

	public abstract void setOrderByFields(Map<String, Field.Orderby> orderByFields);

	public abstract void setOutFields(String[] outFields);

	public abstract void setOutSpatialReference(SpatialReference outSR);

	public abstract void setSpatialRelationship(SpatialRelationship spatialRelationship);

	public abstract void setWhere(String where);
}
