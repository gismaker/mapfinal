package com.mapfinal.dispatcher.indexer;

import java.util.List;

import com.mapfinal.converter.ConverterManager;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.converter.scene.SphericalMercatorProjection;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.SpatialIndexer;
import com.mapfinal.event.Event;
import com.mapfinal.map.MapContext;
import com.mapfinal.map.Tile;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.ItemVisitor;

public class TileMercatorIndexer implements SpatialIndexer {

	@Override
	public List<SpatialIndexObject> query(Event event, Envelope env) {
		// TODO Auto-generated method stub
		ArrayListDispatcherVisitor visitor = new ArrayListDispatcherVisitor();
		query(event, env, visitor);
		return visitor.getItems();
	}

	@Override
	public void query(Event event, Envelope env, ItemVisitor visitor) {
		// TODO Auto-generated method stub
		if(event==null) {
			return;
		}
		MapContext context = event.get("map");
		ScenePoint spt = context.getCenterPoint();
		System.out.println("sceneCenter: " + spt.toString());
		System.out.println("envelope: " + env.toString());
		int tmsType =  event.get("type", Tile.TMS_LT);
		String name = event.get("name");
		int decimalZoom = event.get("zoom",(int) context.getZoom());
		if(decimalZoom==0) {
			Tile tile = new Tile(name);
			SpatialIndexObject sio = new SpatialIndexObject(tile.getId(), "image", "tile", tile.getEnvelope());
			sio.setOption("tile", tile);
			visitor.visitItem(sio);
		}
		//Envelope sceneEnvelope = context.getSceneEnvelope();
		Coordinate latlng1 = new Coordinate(env.getMinX(), env.getMinY());
		Coordinate latlng2 = new Coordinate(env.getMaxX(), env.getMaxY());
		//System.out.println("[TileDispatcher] latlng1: " + latlng1.toString());
		//System.out.println("[TileDispatcher] latlng2: " + latlng2.toString());
		int lngNum1 = (int) Math.ceil(latlng1.x / 180);
		int lngNum2 = (int) Math.ceil(latlng2.x / 180);
		latlng1.x = latlng1.x - (lngNum1 * 180);
		latlng2.x = latlng2.x - (lngNum2 * 180);
		//System.out.println("[TileDispatcher] lngNum1: " + lngNum1);
		//System.out.println("[TileDispatcher] lngNum2: " + lngNum2);
		Coordinate c1 = ConverterManager.me().wgs84ToMercator(latlng1);
		Coordinate c2 = ConverterManager.me().wgs84ToMercator(latlng2);
		
		//支持>180和<-180的都可以tile
		c1.x += lngNum1*SphericalMercatorProjection.MAX_DIMENSION;
		c2.x += lngNum2*SphericalMercatorProjection.MAX_DIMENSION;
		//System.out.println("[TileDispatcher] c1: " + c1.toString());
		//System.out.println("[TileDispatcher] c2: " + c2.toString());
		Envelope bounds = new Envelope(c1, c2);
		//System.out.println("[TileDispatcher] bounds: " + bounds.toString());
		//1,4,16,64,...
		int tilesInMapOneDimension = 1 << decimalZoom;
		//0.5,2,8,32,...
        double halfTilesInMapOneDimension = tilesInMapOneDimension * 0.5;
        double tilesSizeOneDimension = SphericalMercatorProjection.MAX_DIMENSION / halfTilesInMapOneDimension;
        int begX = (int) Math.floor(bounds.getMinX() / tilesSizeOneDimension + halfTilesInMapOneDimension);
        int begY = (int) Math.floor(bounds.getMinY() / tilesSizeOneDimension + halfTilesInMapOneDimension);
        int endX = (int) Math.ceil(bounds.getMaxX() / tilesSizeOneDimension + halfTilesInMapOneDimension);
        int endY = (int) Math.ceil(bounds.getMaxY() / tilesSizeOneDimension + halfTilesInMapOneDimension);
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
        /* this block unlimited X scroll of the map
        if (begX < 0) {
            begX = 0;
        }
        if (endX > tilesInMapOneDimension) {
            endX = tilesInMapOneDimension;
        }
        */
        // normal fill from left bottom corner
        int realX, realY;
        double fullBoundsMinX = -SphericalMercatorProjection.MAX_DIMENSION;
        double fullBoundsMinY = -SphericalMercatorProjection.MAX_DIMENSION;
        
        //System.out.println("[TileDispatcher] bend: [" + endX + ", " + endY + "]");
        for (int x = begX; x < endX; x++) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            for (int y = begY; y < endY; y++) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                realX = x;
                while(realX < 0 || realX >= tilesInMapOneDimension) {
                	if (realX < 0) {
                        realX += tilesInMapOneDimension;
                    } else if (realX >= tilesInMapOneDimension) {
                        realX -= tilesInMapOneDimension;
                    }
                }

                realY = y;
                
                if (tmsType == Tile.TMS_LT) {
                    realY = tilesInMapOneDimension - y - 1;
                }
                //System.out.println("[TileDispatcher] real1: [" + x + ", " + y + "]");
                //System.out.println("[TileDispatcher] real2: [" + realX + "-" + x + ", " + realY  + "-" + y + "]");
                
                if (realY < 0 || realY >= tilesInMapOneDimension) {
                    continue;
                }

                double minX = fullBoundsMinX + x * tilesSizeOneDimension;
                double minY = fullBoundsMinY + y * tilesSizeOneDimension;
                //System.out.println("[TileDispatcher] min: [" + minX + ", " + minY + "]");
                final Envelope tileEnv = new Envelope(
                        minX,
                        minX + tilesSizeOneDimension,
                        minY,
                        minY + tilesSizeOneDimension);
                Tile tile = new Tile(name, realX, realY, decimalZoom, tileEnv);
                SpatialIndexObject sio = new SpatialIndexObject(tile.getId(), "image", "tile", tileEnv);
    			sio.setOption("tile", tile);
    			System.out.println("[TileDispatcher] query tile: " + tile.getId() + ", " + env.toString());
    			visitor.visitItem(sio);
            }
        }
	}

}
