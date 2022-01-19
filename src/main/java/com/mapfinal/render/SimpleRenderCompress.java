package com.mapfinal.render;

import java.util.List;

import org.locationtech.jts.geom.CoordinateSequence;

import com.mapfinal.event.Event;
import com.mapfinal.map.MapContext;
import com.mapfinal.processor.simplify.DouglasSimplifier;
import com.mapfinal.processor.simplify.GeoSimplifier;
import com.mapfinal.processor.simplify.UniformDistributionSimplifier;
import com.mapfinal.processor.simplify.UniformStepSimplifier;

public class SimpleRenderCompress implements RenderCompress {
	
	private GeoSimplifier.Type type;
	private GeoSimplifier compress = null;
	
	public SimpleRenderCompress(GeoSimplifier.Type type) {
		// TODO Auto-generated constructor stub
		this.setType(type);
	}

	@Override
	public List<Integer> compress(Event event, MapContext context, CoordinateSequence points, Double tolerance) {
		// TODO Auto-generated method stub
		if(compress==null) {
			switch (type) {
			case STEP:
				compress = new UniformStepSimplifier();
				break;
			case DIST:
				compress = new UniformDistributionSimplifier();
				break;
			case DP:
				compress = new DouglasSimplifier();
				break;
			default:
				compress = new UniformStepSimplifier();
				break;
			}
		}
		if(this.type==GeoSimplifier.Type.STEP && tolerance==null) {
			int t = (int) context.getZoom();
			tolerance = (double) (t < 10 ? 10-t : 1);
		}
		return compress.excute(event, points, tolerance);
	}

	public GeoSimplifier.Type getType() {
		return type;
	}

	public void setType(GeoSimplifier.Type type) {
		this.type = type;
	}
}
