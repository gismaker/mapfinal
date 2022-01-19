package com.mapfinal.processor.simplify;

import java.util.List;

import org.locationtech.jts.geom.CoordinateSequence;

import com.mapfinal.event.Event;

/**
 * 使用JTS的TopologyPreservingSimplifier
 * @author yangyong
 *
 */
public class TopoSimplifier implements GeoSimplifier {

	@Override
	public List<Integer> excute(Event event, CoordinateSequence points, Double tolerance) {
		// TODO Auto-generated method stub
		return null;
	}

}
