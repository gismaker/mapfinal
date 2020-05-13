package com.mapfinal.geometry;

import org.locationtech.jts.geom.Envelope;

public class LatlngBounds {

	private Envelope envelope;

	public LatlngBounds(Latlng latlng1, Latlng latlng2) {
		this.envelope = new Envelope(latlng1, latlng2);
	}

	public LatlngBounds(Envelope envelope) {
		this.envelope = envelope;
	}

	public void extend(Latlng latlng) {
		if(envelope==null) {
			envelope = new Envelope(latlng);
		} else {
			envelope.expandToInclude(latlng);
		}
	}

	public void extend(LatlngBounds latlngBounds) {
		if(envelope==null) {
			envelope = new Envelope(latlngBounds.getEnvelope());
		} else {
			envelope.expandToInclude(latlngBounds.getEnvelope());
		}
	}

	/**
	 * Returns the center point of the bounds.
	 * 
	 * @return
	 */
	public Latlng getCenter() {
		return Latlng.create(envelope.centre());
	}

	/**
	 * Returns the south-west point of the bounds.
	 * 
	 * @return
	 */
	public Latlng getSouthWest() {
		return Latlng.create(envelope.getMinY(), envelope.getMinX());
	}

	/**
	 * Returns the north-east point of the bounds.
	 * 
	 * @return
	 */
	public Latlng getNorthEast() {
		return Latlng.create(envelope.getMaxY(), envelope.getMaxX());
	}

	/**
	 * Returns the north-west point of the bounds.
	 * 
	 * @return
	 */
	public Latlng getNorthWest() {
		return Latlng.create(envelope.getMaxY(), envelope.getMinX());
	}

	/**
	 * Returns the south-east point of the bounds.
	 * 
	 * @return
	 */
	public Latlng getSouthEast() {
		return Latlng.create(envelope.getMinY(), envelope.getMaxX());
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
	public boolean contains(LatlngBounds otherBounds) {
		return isValid() ? false : envelope.contains(otherBounds.getEnvelope());
	}

	/**
	 * Returns true if the rectangle contains the given point.
	 * 
	 * @param latlng
	 * @return
	 */
	public boolean contains(Latlng latlng) {
		return isValid() ? false : envelope.contains(latlng);
	}

	/**
	 * Returns true if the rectangle intersects the given bounds. Two bounds
	 * intersect if they have at least one point in common.
	 * 
	 * @param otherBounds
	 * @return
	 */
	public boolean intersects(LatlngBounds otherBounds) {
		return isValid() ? false : envelope.intersects(otherBounds.getEnvelope());
	}

	/**
	 * Returns true if the rectangle overlaps the given bounds. Two bounds overlap
	 * if their intersection is an area.
	 * 
	 * @return
	 */
	public boolean overlaps(LatlngBounds otherBounds) {
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
