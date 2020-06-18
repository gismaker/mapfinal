package com.mapfinal.geometry;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

public class Bounds {

	private Envelope envelope;

	public Bounds(Coordinate coordinate1, Coordinate coordinate2) {
		this.envelope = new Envelope(coordinate1, coordinate2);
	}

	public Bounds(Envelope envelope) {
		this.envelope = envelope;
	}

	public void extend(Coordinate coordinate) {
		if(envelope==null) {
			envelope = new Envelope(coordinate);
		} else {
			envelope.expandToInclude(coordinate);
		}
	}

	public void extend(Bounds coordinateBounds) {
		if(envelope==null) {
			envelope = new Envelope(coordinateBounds.getEnvelope());
		} else {
			envelope.expandToInclude(coordinateBounds.getEnvelope());
		}
	}

	/**
	 * Returns the center point of the bounds.
	 * 
	 * @return
	 */
	public Coordinate getCenter() {
		return new Coordinate(envelope.centre());
	}

	/**
	 * Returns the south-west point of the bounds.
	 * 
	 * @return
	 */
	public Coordinate getSouthWest() {
		return new Coordinate(envelope.getMinY(), envelope.getMinX());
	}

	/**
	 * Returns the north-east point of the bounds.
	 * 
	 * @return
	 */
	public Coordinate getNorthEast() {
		return new Coordinate(envelope.getMaxY(), envelope.getMaxX());
	}

	/**
	 * Returns the north-west point of the bounds.
	 * 
	 * @return
	 */
	public Coordinate getNorthWest() {
		return new Coordinate(envelope.getMaxY(), envelope.getMinX());
	}

	/**
	 * Returns the south-east point of the bounds.
	 * 
	 * @return
	 */
	public Coordinate getSouthEast() {
		return new Coordinate(envelope.getMinY(), envelope.getMaxX());
	}

	/**
	 * Returns the west longitude of the bounds
	 * 
	 * @return
	 */
	public double getWest() {
		return envelope.getMinX();
	}

	/**
	 * Returns the south latitude of the bounds
	 * 
	 * @return
	 */
	public double getSouth() {
		return envelope.getMinY();
	}

	/**
	 * Returns the east longitude of the bounds
	 * 
	 * @return
	 */
	public double getEast() {
		return envelope.getMaxX();
	}

	/**
	 * Returns the north latitude of the bounds
	 * 
	 * @return
	 */
	public double getNorth() {
		return envelope.getMaxY();
	}

	/*
	 * Returns true if the rectangle contains the given one.
	 */
	public boolean contains(Bounds otherBounds) {
		return isValid() ? false : envelope.contains(otherBounds.getEnvelope());
	}

	/**
	 * Returns true if the rectangle contains the given point.
	 * 
	 * @param coordinate
	 * @return
	 */
	public boolean contains(Coordinate coordinate) {
		return isValid() ? false : envelope.contains(coordinate);
	}

	/**
	 * Returns true if the rectangle intersects the given bounds. Two bounds
	 * intersect if they have at least one point in common.
	 * 
	 * @param otherBounds
	 * @return
	 */
	public boolean intersects(Bounds otherBounds) {
		return isValid() ? false : envelope.intersects(otherBounds.getEnvelope());
	}

	/**
	 * Returns true if the rectangle overlaps the given bounds. Two bounds overlap
	 * if their intersection is an area.
	 * 
	 * @return
	 */
	public boolean overlaps(Bounds otherBounds) {
		return isValid() ? false : envelope.overlaps(otherBounds.getEnvelope());
	}

	/**
	 * Returns a string with bounding box coordinates in a
	 * 'southwest_lng,southwest_lat,northeast_lng,northeast_lat' format. Useful for
	 * sending requests to web services that return geo data.
	 * 
	 * @return
	 */
	public String toBBoxString() {
		return isValid() ? ""
				: envelope.getMinX() + "," + envelope.getMinY() + "," + envelope.getMaxX() + "," + envelope.getMaxY();
	}

	/**
	 * Returns true if the rectangle is equivalent (within a small margin of error)
	 * to the given bounds. The margin of error can be overridden by setting
	 * maxMargin to a small number.
	 * 
	 * @param otherBounds
	 * @return
	 * 
	 public boolean equals(LatlngBounds otherBounds) {
	 }
	 */

	/**
	 * Returns true if the bounds are properly initialized.
	 * 
	 * @return
	 */
	public boolean isValid() {
		return envelope == null || envelope.isNull() ? false : true;
	}

	public Envelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}
}
