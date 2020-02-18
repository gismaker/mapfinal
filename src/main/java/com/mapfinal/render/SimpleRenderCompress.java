package com.mapfinal.render;

import java.util.List;

import org.locationtech.jts.geom.CoordinateSequence;

import com.mapfinal.event.Event;
import com.mapfinal.map.MapContext;
import com.mapfinal.operator.DouglasCompress;
import com.mapfinal.operator.GeoCompress;
import com.mapfinal.operator.UniformDistributionCompress;
import com.mapfinal.operator.UniformStepCompress;

public class SimpleRenderCompress implements RenderCompress {
	
	private GeoCompress.Type type;
	private GeoCompress compress = null;
	
	public SimpleRenderCompress(GeoCompress.Type type) {
		// TODO Auto-generated constructor stub
		this.setType(type);
	}

	@Override
	public List<Integer> compress(Event event, MapContext context, CoordinateSequence points, Double tolerance) {
		// TODO Auto-generated method stub
		if(compress==null) {
			switch (type) {
			case STEP:
				compress = new UniformStepCompress();
				break;
			case DIST:
				compress = new UniformDistributionCompress();
				break;
			case DP:
				compress = new DouglasCompress();
				break;
			default:
				compress = new UniformStepCompress();
				break;
			}
		}
		if(this.type==GeoCompress.Type.STEP && tolerance==null) {
			int t = (int) context.getZoom();
			tolerance = (double) (t < 10 ? 10-t : 1);
		}
		return compress.excute(event, points, tolerance);
	}

	public GeoCompress.Type getType() {
		return type;
	}

	public void setType(GeoCompress.Type type) {
		this.type = type;
	}
}
