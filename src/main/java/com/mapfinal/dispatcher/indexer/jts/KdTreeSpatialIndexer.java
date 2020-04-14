package com.mapfinal.dispatcher.indexer.jts;

import java.util.List;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.SpatialIndexer;
import com.mapfinal.event.Event;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.ItemVisitor;

public class KdTreeSpatialIndexer implements SpatialIndexer {

	private KdTree kdTree;
	
	public KdTreeSpatialIndexer() {
		kdTree = new KdTree();  
	}
	
	public KdTreeSpatialIndexer(double tolerance) {
		kdTree = new KdTree(tolerance);  
	}	
	
	public boolean isEmpty() {
		return kdTree.isEmpty();
	}
	
	public KdNode insert(Coordinate p) {
		return kdTree.insert(p);
	}
	
	public KdNode insert(Coordinate p, SpatialIndexObject data) {
		return kdTree.insert(p, data);
	}
	
	@Override
	public List<SpatialIndexObject> query(Event event, Envelope env) {
		// TODO Auto-generated method stub
		return kdTree.querySIO(env);
	}
	
	@Override
	public void query(Event event, Envelope env, ItemVisitor visitor) {
		// TODO Auto-generated method stub
		kdTree.query(env, visitor);
	}
	
	public List<KdNode> queryNode(Envelope queryEnv) {
		return kdTree.query(queryEnv);
	}
	
	public KdTree getKdTree() {
		return kdTree;
	}

	public void setKdTree(KdTree kdTree) {
		this.kdTree = kdTree;
	}
}
