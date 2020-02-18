package com.mapfinal.map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.render.Renderer;
import com.mapfinal.render.SceneNode;
import org.locationtech.jts.geom.Envelope;

/**
 * @author yangyong
 *
 */
public interface Layer extends SceneNode {	

	public void addTo(MapView map);
	
	public void setName(String name);
	public Envelope getEnvelope();
	public SpatialReference getSpatialReference();

	public String getTitle();

	public void setTitle(String title);

	public int getMinZoom();

	public void setMinZoom(int minZoom);

	public int getMaxZoom();

	public void setMaxZoom(int maxZoom);

	public Renderer getRenderer();
	
	public void setRenderer(Renderer renderer);
	
	public float getOpacity();
	
	public void setOpacity(float opacity);

}
