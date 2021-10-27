package com.mapfinal.render;

import java.util.List;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.indexer.jts.STRTreeSpatialIndexer;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.map.MapContext;

/**
 * 标注引擎
 * @author yangyong
 *
 */
public class LabelSTRTreeEngine implements LabelEngine {
	
	private STRTreeSpatialIndexer indexer;
	
	public LabelSTRTreeEngine() {
		// TODO Auto-generated constructor stub
		indexer = new STRTreeSpatialIndexer();
	}

	public boolean renderable(MapContext context, RenderEngine engine, Label label) {
		double zoom = context.getZoom();
		ScenePoint sp = context.latLngToPoint(label.getPosition(), zoom);
		ScreenPoint pt = engine.getLableBox(label);
		int fx = label.getSymbol()!=null ? label.getSymbol().getOffsetX() : 0;
		int fy = label.getSymbol()!=null ? label.getSymbol().getOffsetY() : 0;
		int padding = label.getSymbol()!=null ? label.getSymbol().getPadding() : 0;
		
		Envelope env = new Envelope(
				sp.getSx() + fx, 
				sp.getSx() + pt.x + fx + padding * 2, 
				sp.getSy() + fy - pt.y - padding * 2, 
				sp.getSy() + fy);
		List<SpatialIndexObject> sis = indexer.query(null, env);
		if(sis==null || sis.size() < 1) {
			indexer.insert(env, new SpatialIndexObject(label.getText(), "label", "label", env));
			return true;
		}
		label.setVisible(false);
		return false;		
	}
	
	public void clear() {
		indexer = null;
		indexer = new STRTreeSpatialIndexer();
	}
}
