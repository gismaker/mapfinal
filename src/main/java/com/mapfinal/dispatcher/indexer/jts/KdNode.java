package com.mapfinal.dispatcher.indexer.jts;

import org.locationtech.jts.geom.Coordinate;

public class KdNode {
	private Coordinate p = null;

	private Object data;

	private KdNode left;

	private KdNode right;

	private int count;

	public KdNode(double _x, double _y, Object data) {
		p = new Coordinate(_x, _y);
		left = null;
		right = null;
		count = 1;
		this.data = data;
	}

	public KdNode(Coordinate p, Object data) {
		this.p = new Coordinate(p);
		left = null;
		right = null;
		count = 1;
		this.data = data;
	}

	public double getX() {
		return p.x;
	}

	public double getY() {
		return p.y;
	}

	public Coordinate getCoordinate() {
		return p;
	}

	public Object getData() {
		return data;
	}

	public KdNode getLeft() {
		return left;
	}

	public KdNode getRight() {
		return right;
	}

	void increment() {
		count += 1;
	}

	public int getCount() {
		return count;
	}

	public boolean isRepeated() {
		return count > 1;
	}

	void setLeft(KdNode _left) {
		left = _left;
	}

	void setRight(KdNode _right) {
		right = _right;
	}
}
