package com.mapfinal.resource.postgis;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.QueryParameter;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.event.Event;
import com.mapfinal.map.Feature;
import com.mapfinal.map.FeatureClass;
import com.mapfinal.resource.FeatureResource;

/**
 * Postgis矢量数据资源
 * @author yangyong
 *
 * @param <T>
 */
public class PostgisFeatureResource<T> extends FeatureResource<T> {

	@Override
	public Dispatcher connection(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Feature<T> read(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FeatureClass<T> read(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writer(Event event, FeatureClass<T> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpatialReference getSpatialReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FeatureClass<T> queryFeatures(QueryParameter query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

}
