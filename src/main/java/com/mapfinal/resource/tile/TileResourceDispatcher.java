package com.mapfinal.resource.tile;

import com.mapfinal.map.Graphic;
import com.mapfinal.resource.ResourceDispatcher;

public abstract class TileResourceDispatcher<G extends Graphic> implements ResourceDispatcher<G> {

	public abstract int getTmsType();
	
	public abstract void setCurrentTileNumberOnScreen(int numTile);
}
