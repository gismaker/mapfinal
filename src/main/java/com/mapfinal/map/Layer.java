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

	public void addTo(LayerGroup layerGroup);
	
	/**
	 * 名称，主键
	 */
	public String getName();
	
	public void setName(String name);
	
	/**
	 * Envelope
	 * @return
	 */
	public Envelope getEnvelope();
	
	/**
	 * SpatialReference
	 * @return
	 */
	public SpatialReference getSpatialReference();

	/**
     * set layer parent
     * @param layer Layer parent object
     */
    void setParent(Layer layer);

    /**
     * @return Layer parent object
     */
    Layer getParent();
    
	/**
	 * 标题
	 * @return
	 */
	public String getTitle();

	public void setTitle(String title);

	/**
	 * min zoom for layer
	 * @return
	 */
	public float getMinZoom();

	public void setMinZoom(float minZoom);

	/**
	 * max zoom for layer
	 * @return
	 */
	public float getMaxZoom();

	public void setMaxZoom(float maxZoom);

	/**
	 * renderer
	 * @return
	 */
	public Renderer getRenderer();
	
	public void setRenderer(Renderer renderer);
	
	/**
	 * 透明度
	 * @return
	 */
	public float getOpacity();
	
	public void setOpacity(float opacity);
	
	/**
	 * 
	 * @param id The layer identificator
     * @param percent The draw progress percent
     */
	public void drawFinished(int id, float percent);

}
