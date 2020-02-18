package com.mapfinal.dispatcher;

import java.util.List;

import com.mapfinal.event.Event;
import org.locationtech.jts.geom.Envelope;

/**
 * 索引器
 * @author yangyong
 *
 */
public interface SpatialIndexer {
	//查询
	List<SpatialIndexObject> query(Event event, Envelope env);
	//查询
	void query(Event event, Envelope env, Dispatcher visitor);
}
