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
	/**
	 * 返回List的查询
	 * @param event
	 * @param env
	 * @return
	 */
	List<SpatialIndexObject> query(Event event, Envelope env);
	/**
	 * 访问者模式的查询
	 * @param event
	 * @param env
	 * @param visitor
	 */
	void query(Event event, Envelope env, Dispatcher visitor);
}
