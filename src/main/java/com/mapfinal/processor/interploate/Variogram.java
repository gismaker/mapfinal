package com.mapfinal.processor.interploate;

public class Variogram {
	public Double A;
	public Double[] K;
	public Double[] M;
	public Integer n;
	public Double nugget;
	public Double range;
	public Double sill;
	public Double[] t;
	public Double[] x;
	public Double[] y;
	
	public Double[][] re_A;
	public Double[] re_xlim;
	public Double[] re_ylim;
	public Double[] re_zlim;
	public Double re_width;
	
	public Double[][][] world;
	public Double width;
	public Double max;
	public Double min;
	
	public static Double model(Double h,Double nugget,Double range,Double sill,Double A) {
		if(h>range) return nugget + (sill-nugget)/range;
	    return nugget + ((sill-nugget)/range)*
	      ( 1.5*(h/range) - 0.5*Math.pow(h/range, 3) );
	}
	
	public void setMax(Double max) {
		this.max = max;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	
	public Double getMax() {
		return max;
	}

	public Double getMin() {
		return min;
	}

	public Double getA() {
		return A;
	}
	public void setA(Double a) {
		A = a;
	}
	public Double[] getK() {
		return K;
	}
	public void setK(Double[] k) {
		K = k;
	}
	public Double[] getM() {
		return M;
	}
	public void setM(Double[] m) {
		M = m;
	}
	public Integer getN() {
		return n;
	}
	public void setN(Integer n) {
		this.n = n;
	}
	public Double getNugget() {
		return nugget;
	}
	public void setNugget(Double nugget) {
		this.nugget = nugget;
	}
	public Double getRange() {
		return range;
	}
	public void setRange(Double range) {
		this.range = range;
	}
	public Double getSill() {
		return sill;
	}
	public void setSill(Double sill) {
		this.sill = sill;
	}
	public Double[] getT() {
		return t;
	}
	public void setT(Double[] t) {
		this.t = t;
	}
	public Double[] getX() {
		return x;
	}
	public void setX(Double[] x) {
		this.x = x;
	}
	public Double[] getY() {
		return y;
	}
	public void setY(Double[] y) {
		this.y = y;
	}
	public Double[][] getRe_A() {
		return re_A;
	}
	public void setRe_A(Double[][] re_A) {
		this.re_A = re_A;
	}
	public Double[] getRe_xlim() {
		return re_xlim;
	}
	public void setRe_xlim(Double[] re_xlim) {
		this.re_xlim = re_xlim;
	}
	public Double[] getRe_ylim() {
		return re_ylim;
	}
	public void setRe_ylim(Double[] re_ylim) {
		this.re_ylim = re_ylim;
	}
	public Double[] getRe_zlim() {
		return re_zlim;
	}
	public void setRe_zlim(Double[] re_zlim) {
		this.re_zlim = re_zlim;
	}
	public Double getRe_width() {
		return re_width;
	}
	public void setRe_width(Double re_width) {
		this.re_width = re_width;
	}
	public Double[][][] getWorld() {
		return world;
	}
	public void setWorld(Double[][][] world) {
		this.world = world;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
}
