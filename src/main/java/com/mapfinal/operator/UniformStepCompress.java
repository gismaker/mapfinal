package com.mapfinal.operator;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.CoordinateSequence;

import com.mapfinal.event.Event;

/**
 * 等步长抽稀
 * @author yangyong
 *
 */
public class UniformStepCompress implements GeoCompress {

	@Override
	public List<Integer> excute(Event event, CoordinateSequence points, Double tolerance) {
		// TODO Auto-generated method stub
		List<Integer> result = null;
		if(event.getCallback()==null) {
			result = new ArrayList<Integer>();
		}
		int bc = tolerance==null ? 10 : tolerance.intValue();
		int nPoints = points.size();
		for(int j=0; j<nPoints; j+=bc) {
			if(event.getCallback()!=null){
				event.getCallback().execute(new Event("save", "index", j));
			} else {
				if(result!=null) result.add(j);
			}
		}
		return result;
	}

}
