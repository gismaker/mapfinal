package com.mapfinal.dispatcher.indexer;

import java.util.List;

import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.SpatialIndexer;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.MapContext;
import com.mapfinal.map.Tile;
import org.locationtech.jts.geom.Envelope;

public class TileWGS84Indexer implements SpatialIndexer {

	@Override
	public List<SpatialIndexObject> query(Event event, Envelope env) {
		// TODO Auto-generated method stub
		ArrayListDispatcherVisitor visitor = new ArrayListDispatcherVisitor();
		query(event, env, visitor);
		return visitor.getItems();
	}

	@Override
	public void query(Event event, Envelope env, Dispatcher visitor) {
		// TODO Auto-generated method stub
		if(event==null || event.get("map")==null) {
			return;
		}
		MapContext context = event.get("map");
		int tmsType =  event.get("type", Tile.TMS_LT);
		String name = event.get("name");
		int decimalZoom = (int) context.getZoom();
		if(decimalZoom==0) {
			Tile tile = new Tile(name);
			SpatialIndexObject sio = new SpatialIndexObject(tile.getId(), "image", "tile", null);
			sio.setOption("tile", tile);
			visitor.visitItem(sio);
		}
		Envelope sceneEnvelope = context.getSceneEnvelope();
		Latlng latlng1 = new Latlng(sceneEnvelope.getMaxX(), sceneEnvelope.getMinY());
		Latlng latlng2 = new Latlng(sceneEnvelope.getMinX(), sceneEnvelope.getMaxY());
		//System.out.println("[TileDispatcher] latlng1: " + latlng1.toString());
		//System.out.println("[TileDispatcher] latlng2: " + latlng2.toString());
		int tilesInMapOneDimension = 1 << decimalZoom;
		//System.out.println("[TileDispatcher] tilesInMapOneDimension: " + tilesInMapOneDimension);
		double tilesSizeLatDimension = 180 / tilesInMapOneDimension;
		double tilesSizeLngDimension = 360 / tilesInMapOneDimension;
		//System.out.println("[TileDispatcher] tilesSizeLatDimension: " + tilesSizeLatDimension);
		//System.out.println("[TileDispatcher] tilesSizeLngDimension: " + tilesSizeLngDimension);
		double lat1 = (latlng1.x + 90);
		double lat2 = (latlng2.x + 90);
		double lng1 = (latlng1.y + 180);
		double lng2 = (latlng2.y + 180);
		double tx1 = 180 - lat1;
		double tx2 = 180 - lat2;
		double ty1 = lng1;
		double ty2 = lng2;
		int begX = (int) Math.floor( ty1 / tilesSizeLngDimension);
		int endX = (int) Math.ceil( ty2 / tilesSizeLngDimension);
		int begY = (int) Math.floor( tx1 / tilesSizeLatDimension);
		int endY = (int) Math.floor( tx2 / tilesSizeLatDimension);
		//System.out.println("begX: " + begX + ", endX: " + endX + ", begY: " + begY + ", endY: " + endY);
		if(begY == endY)
            endY++;
        if(begX == endX)
            endX++;

        if (begY < 0) {
            begY = 0;
        }
        if (endY > tilesInMapOneDimension) {
            endY = tilesInMapOneDimension;
        }
        //System.out.println("begX: " + begX + ", endX: " + endX + ", begY: " + begY + ", endY: " + endY);
        int realX, realY;
        for (int rx = begX; rx <= endX; rx++) {
            for (int ry = begY; ry <= endY; ry++) {
            	realX = rx;
                if (realX < 0) {
                    realX += tilesInMapOneDimension;
                } else if (realX >= tilesInMapOneDimension) {
                    realX -= tilesInMapOneDimension;
                }
                
                realY = ry;
                if (tmsType == Tile.TMS_LT) {
                    realY = tilesInMapOneDimension - ry - 1;
                }
                
                //System.out.println("realX: " + realX + ", realY: " + realY + ", rx: " + rx + ", ry: " + ry);
                if (realY < 0 || realY >= tilesInMapOneDimension) {
                    continue;
                }
            	
            	double x0 = 90 - tilesSizeLatDimension*ry;
            	double x1 = x0 - tilesSizeLatDimension;
            	double y0 = tilesSizeLngDimension*rx - 180;
            	double y1 = y0 + tilesSizeLngDimension;
            	Envelope tileEnv = new Envelope(x0, x1, y0 ,y1);
            	Tile tile = new Tile(name, realX, realY, decimalZoom, tileEnv);
            	SpatialIndexObject sio = new SpatialIndexObject(tile.getId(), "image", "tile", tileEnv);
    			sio.setOption("tile", tile);
    			//System.out.println("[TileDispatcher] query tile: " + tile.getId() + ", " + env.toString());
    			visitor.visitItem(sio);
            }
        }
	}

}
