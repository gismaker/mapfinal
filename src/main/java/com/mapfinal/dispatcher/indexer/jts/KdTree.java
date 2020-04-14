package com.mapfinal.dispatcher.indexer.jts;

import java.util.ArrayList;
import java.util.List;

import com.mapfinal.dispatcher.SpatialIndexObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.ItemVisitor;

public class KdTree {
	private KdNode root = null;
	private KdNode last = null;
	private long numberOfNodes;
	private double tolerance;

	public KdTree() {
		this(0.0D);
	}

	public KdTree(double tolerance) {
		this.tolerance = tolerance;
	}

	public boolean isEmpty() {
		if (root == null)
			return true;
		return false;
	}

	public KdNode insert(Coordinate p) {
		return insert(p, null);
	}

	public KdNode insert(Coordinate p, Object data) {
		if (root == null) {
			root = new KdNode(p, data);
			return root;
		}

		KdNode currentNode = root;
		KdNode leafNode = root;
		boolean isOddLevel = true;
		boolean isLessThan = true;

		while (currentNode != last) {
			if (currentNode != null) {
				boolean isInTolerance = p.distance(currentNode.getCoordinate()) <= tolerance;

				if (isInTolerance) {
					currentNode.increment();
					return currentNode;
				}
			}
			
			if (isOddLevel) {
				isLessThan = p.x < currentNode.getX();
			} else {
				isLessThan = p.y < currentNode.getY();
			}
			leafNode = currentNode;
			if (isLessThan) {
				currentNode = currentNode.getLeft();
			} else {
				currentNode = currentNode.getRight();
			}

			isOddLevel = !isOddLevel;
		}

		numberOfNodes += 1L;
		KdNode node = new KdNode(p, data);
		node.setLeft(last);
		node.setRight(last);
		if (isLessThan) {
			leafNode.setLeft(node);
		} else {
			leafNode.setRight(node);
		}
		return node;
	}

	private void queryNode(KdNode currentNode, KdNode bottomNode, Envelope queryEnv, boolean odd, List<KdNode> result) {
		if (currentNode == bottomNode)
			return;
		double discriminant;
		double min;
		double max;
		if (odd) {
			min = queryEnv.getMinX();
			max = queryEnv.getMaxX();
			discriminant = currentNode.getX();
		} else {
			min = queryEnv.getMinY();
			max = queryEnv.getMaxY();
			discriminant = currentNode.getY();
		}
		boolean searchLeft = min < discriminant;
		boolean searchRight = discriminant <= max;

		if (searchLeft) {
			queryNode(currentNode.getLeft(), bottomNode, queryEnv, !odd, result);
		}
		if (queryEnv.contains(currentNode.getCoordinate())) {
			result.add(currentNode);
		}
		if (searchRight) {
			queryNode(currentNode.getRight(), bottomNode, queryEnv, !odd, result);
		}
	}
	
	private void querySIO(KdNode currentNode, KdNode bottomNode, Envelope queryEnv, boolean odd, List<SpatialIndexObject> result) {
		if (currentNode == bottomNode)
			return;
		double discriminant;
		double min;
		double max;
		if (odd) {
			min = queryEnv.getMinX();
			max = queryEnv.getMaxX();
			discriminant = currentNode.getX();
		} else {
			min = queryEnv.getMinY();
			max = queryEnv.getMaxY();
			discriminant = currentNode.getY();
		}
		boolean searchLeft = min < discriminant;
		boolean searchRight = discriminant <= max;

		if (searchLeft) {
			querySIO(currentNode.getLeft(), bottomNode, queryEnv, !odd, result);
		}
		if (queryEnv.contains(currentNode.getCoordinate())) {
			SpatialIndexObject obj = (SpatialIndexObject) currentNode.getData();
			obj.setOption("kdtree_node_coordinate", currentNode.getCoordinate());
			obj.setOption("kdtree_node_count", currentNode.getCount());
			obj.setOption("kdtree_node_left", currentNode.getLeft());
			obj.setOption("kdtree_node_right", currentNode.getRight());
			result.add(obj);
		}
		if (searchRight) {
			querySIO(currentNode.getRight(), bottomNode, queryEnv, !odd, result);
		}
	}
	
	private void queryVisitor(KdNode currentNode, KdNode bottomNode, Envelope queryEnv, boolean odd, ItemVisitor result) {
		if (currentNode == bottomNode)
			return;
		double discriminant;
		double min;
		double max;
		if (odd) {
			min = queryEnv.getMinX();
			max = queryEnv.getMaxX();
			discriminant = currentNode.getX();
		} else {
			min = queryEnv.getMinY();
			max = queryEnv.getMaxY();
			discriminant = currentNode.getY();
		}
		boolean searchLeft = min < discriminant;
		boolean searchRight = discriminant <= max;

		if (searchLeft) {
			queryVisitor(currentNode.getLeft(), bottomNode, queryEnv, !odd, result);
		}
		if (queryEnv.contains(currentNode.getCoordinate())) {
			SpatialIndexObject obj = (SpatialIndexObject) currentNode.getData();
			obj.setOption("kdtree_node_coordinate", currentNode.getCoordinate());
			obj.setOption("kdtree_node_count", currentNode.getCount());
			obj.setOption("kdtree_node_left", currentNode.getLeft());
			obj.setOption("kdtree_node_right", currentNode.getRight());
			result.visitItem(obj);
		}
		if (searchRight) {
			queryVisitor(currentNode.getRight(), bottomNode, queryEnv, !odd, result);
		}
	}

	public List<KdNode> query(Envelope queryEnv) {
		List<KdNode> result = new ArrayList<KdNode>();
		queryNode(root, last, queryEnv, true, result);
		return result;
	}
	
	public void query(Envelope queryEnv, List<KdNode> result) {
		queryNode(root, last, queryEnv, true, result);
	}
	
	public List<SpatialIndexObject> querySIO(Envelope queryEnv) {
		List<SpatialIndexObject> result = new ArrayList<SpatialIndexObject>();
		querySIO(root, last, queryEnv, true, result);
		return result;
	}
	
	public void querySIO(Envelope queryEnv, List<SpatialIndexObject> result) {
		querySIO(root, last, queryEnv, true, result);
	}
	
	public void query(Envelope queryEnv, ItemVisitor result) {
		queryVisitor(root, last, queryEnv, true, result);
	}
	
	public long getNumberOfNodes() {
		return numberOfNodes;
	}

	
}
