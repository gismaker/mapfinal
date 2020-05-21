package com.mapfinal.resource.tile;

import com.mapfinal.map.GeoElement;
import com.mapfinal.resource.ResourceDispatcher;

public abstract class TileResourceDispatcher<G extends GeoElement> implements ResourceDispatcher<G> {

	public abstract int getTmsType();
	
	public abstract void setCurrentTileNumberOnScreen(int numTile);
}
