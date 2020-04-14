package com.mapfinal.dispatcher.indexer.jts;

import java.util.List;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.SpatialIndexer;
import com.mapfinal.event.Event;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.ItemVisitor;
import org.locationtech.jts.index.quadtree.Quadtree;

public class QuadtreeSpatialIndexer implements SpatialIndexer {

	private Quadtree quadTree;

	public QuadtreeSpatialIndexer() {
		quadTree = new Quadtree();
	}

	public int depth() {
		// TODO Auto-generated method stub
		return quadTree.depth();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return quadTree.isEmpty();
	}

	public int size() {
		// TODO Auto-generated method stub
		return quadTree.size();
	}

	public void insert(Envelope itemEnv, SpatialIndexObject item) {
		// TODO Auto-generated method stub
		quadTree.insert(itemEnv, item);
	}

	@Override
	public List<SpatialIndexObject> query(Event event, Envelope searchEnv) {
		// TODO Auto-generated method stub
		return quadTree.query(searchEnv);
	}
	
	@Override
	public void query(Event event, Envelope searchEnv, ItemVisitor visitor) {
		// TODO Auto-generated method stub
		quadTree.query(searchEnv, visitor);
	}

	public boolean remove(Envelope itemEnv, SpatialIndexObject item) {
		// TODO Auto-generated method stub
		return quadTree.remove(itemEnv, item);
	}

	public List queryAll() {
		return quadTree.queryAll();
	}

	public Quadtree getQuadTree() {
		return quadTree;
	}

	public void setQuadTree(Quadtree quadTree) {
		this.quadTree = quadTree;
	}

	

}
