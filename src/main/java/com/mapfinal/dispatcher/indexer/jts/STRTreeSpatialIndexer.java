package com.mapfinal.dispatcher.indexer.jts;

import java.util.List;

import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.SpatialIndexer;
import com.mapfinal.event.Event;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.strtree.ItemDistance;
import org.locationtech.jts.index.strtree.STRtree;

public class STRTreeSpatialIndexer implements SpatialIndexer {

	private STRtree strTree;  
	
	public STRTreeSpatialIndexer() {
		// TODO Auto-generated constructor stub
		strTree = new STRtree();  
	}
	
	public STRTreeSpatialIndexer(int nodeCapacity) {
		strTree = new STRtree(nodeCapacity);  
	}
	
	public int depth() {
		// TODO Auto-generated method stub
		return strTree.depth();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return strTree.isEmpty();
	}
	
	public int size() {
		// TODO Auto-generated method stub
		return strTree.size();
	}
	
	public void insert(Envelope itemEnv, SpatialIndexObject item) {
		// TODO Auto-generated method stub
		strTree.insert(itemEnv, item);
	}

	@Override
	public List<SpatialIndexObject> query(Event event, Envelope paramEnvelope) {
		// TODO Auto-generated method stub
		return strTree.query(paramEnvelope);
	}
	
	@Override
	public void query(Event event, Envelope searchEnv, Dispatcher visitor) {
		// TODO Auto-generated method stub
		strTree.query(searchEnv, visitor);
	}

	public boolean remove(Envelope itemEnv, SpatialIndexObject item) {
		// TODO Auto-generated method stub
		return strTree.remove(itemEnv, item);
	}
	
	public Object[] nearestNeighbour(ItemDistance itemDist) {
		return strTree.nearestNeighbour(itemDist);
	}
	
	public Object nearestNeighbour(Envelope env, Object item, ItemDistance itemDist) {
		return strTree.nearestNeighbour(env, item, itemDist);
	}
	
	public Object[] nearestNeighbour(STRtree tree, ItemDistance itemDist) {
		return strTree.nearestNeighbour(tree, itemDist);
	}
	
	public STRtree getStrTree() {
		return strTree;
	}

	public void setStrTree(STRtree strTree) {
		this.strTree = strTree;
	}

	
}
