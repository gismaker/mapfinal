package com.mapfinal.render;

import java.util.List;

import org.locationtech.jts.geom.CoordinateSequence;

import com.mapfinal.event.Event;
import com.mapfinal.map.MapContext;

/**
 * 渲染压缩算法
 * @author yangyong
 *
 */
public interface RenderCompress {

	public List<Integer> compress(Event event, MapContext context, CoordinateSequence points, Double tolerance);
}
