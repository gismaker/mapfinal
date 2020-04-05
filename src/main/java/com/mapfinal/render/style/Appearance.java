package com.mapfinal.render.style;

public class Appearance implements Symbol {

	// Whether to draw stroke along the path. Set it to false to disable borders
	// on polygons or circles.
	private boolean stroke = true;
	// Stroke color
	private String color = "#3388ff";
	// Stroke width in pixels
	private int weight = 3;
	// Stroke opacity
	private float opacity = 1.0f;
	// A string that defines shape to be used at the end of the stroke.
	private String lineCap = "round";
	// A string that defines shape to be used at the corners of the stroke.
	private String lineJoin = "round";
	// A string that defines the stroke dash pattern. Doesn"t work on
	// Canvas-powered layers in some old browsers.
	private String dashArray = null;
	// A string that defines the distance into the dash pattern to start the
	// dash. Doesn"t work on Canvas-powered layers in some old browsers.
	private String dashOffset = null;
	// Whether to fill the path with color. Set it to false to disable filling
	// on polygons or circles.
	private boolean fill = true;
	// Fill color. Defaults to the value of the color option
	private String fillColor = "#FFFFFF";
	// Fill opacity.
	private float fillOpacity = 0.2f;
	// A string that defines how the inside of a shape is determined.
	private String fillRule = "evenodd";
	// When true, a mouse event on this path will trigger the same event on the
	// map (unless L.DomEvent.stopPropagation is used).
	private boolean bubblingMouseEvents = true;

	public boolean isStroke() {
		return stroke;
	}

	public void setStroke(boolean stroke) {
		this.stroke = stroke;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public String getLineCap() {
		return lineCap;
	}

	public void setLineCap(String lineCap) {
		this.lineCap = lineCap;
	}

	public String getLineJoin() {
		return lineJoin;
	}

	public void setLineJoin(String lineJoin) {
		this.lineJoin = lineJoin;
	}

	public String getDashArray() {
		return dashArray;
	}

	public void setDashArray(String dashArray) {
		this.dashArray = dashArray;
	}

	public String getDashOffset() {
		return dashOffset;
	}

	public void setDashOffset(String dashOffset) {
		this.dashOffset = dashOffset;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public float getFillOpacity() {
		return fillOpacity;
	}

	public void setFillOpacity(float fillOpacity) {
		this.fillOpacity = fillOpacity;
	}

	public String getFillRule() {
		return fillRule;
	}

	public void setFillRule(String fillRule) {
		this.fillRule = fillRule;
	}

	public boolean isBubblingMouseEvents() {
		return bubblingMouseEvents;
	}

	public void setBubblingMouseEvents(boolean bubblingMouseEvents) {
		this.bubblingMouseEvents = bubblingMouseEvents;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}
