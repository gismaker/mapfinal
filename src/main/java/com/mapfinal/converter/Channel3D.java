package com.mapfinal.converter;

public class Channel3D {
	public static Matrix3D unit() {
		final float[][] trans = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		Matrix3D m = new Matrix3D(trans);
		//Logger.getLogger(Channel3D.class).info("Create an unit matrix");
		return m;
	}

	public static Matrix3D translate(float tx, float ty, float tz) {
		final float[][] trans = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { tx, ty, tz, 1 } };
		Matrix3D m = new Matrix3D(trans);
		//Logger.getLogger(Channel3D.class).info("Create translate matrix:" + m);
		return m;
	}

	public static Matrix3D scale(float sx, float sy, float sz) {
		final float[][] trans = { { sx, 0, 0, 0 }, { 0, sy, 0, 0 }, { 0, 0, sz, 0 }, { 0, 0, 0, 1 } };
		Matrix3D m = new Matrix3D(trans);
		//Logger.getLogger(Channel3D.class).info("Create scale matrix:" + m);
		return m;
	}

	public static Matrix3D rotateX(float arc) {
		float cos = (float) Math.cos(arc);
		float sin = (float) Math.sin(arc);
		final float[][] trans = { { 1, 0, 0, 0 }, { 0, cos, sin, 0 }, { 0, -sin, cos, 0 }, { 0, 0, 0, 1 } };
		Matrix3D m = new Matrix3D(trans);
		//Logger.getLogger(Channel3D.class).info("Create rotateX matrix:" + m);
		return m;
	}

	public static Matrix3D rotateY(float arc) {
		float cos = (float) Math.cos(arc);
		float sin = (float) Math.sin(arc);
		final float[][] trans = { { cos, 0, -sin, 0 }, { 0, 1, 0, 0 }, { sin, 0, cos, 0 }, { 0, 0, 0, 1 } };
		Matrix3D m = new Matrix3D(trans);
		//Logger.getLogger(Channel3D.class).info("Create rotateY matrix:" + m);
		return m;
	}

	public static Matrix3D rotateZ(float arc) {
		float cos = (float) Math.cos(arc);
		float sin = (float) Math.sin(arc);
		final float[][] trans = { { cos, sin, 0, 0 }, { -sin, cos, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		Matrix3D m = new Matrix3D(trans);
		//Logger.getLogger(Channel3D.class).info("Create rotateZ matrix:" + m);
		return m;
	}

	public static Matrix3D perspective(float fov, float aspect, float near, float far) {
		float top = (float) (near * Math.tan(fov / 2));
		float right = top * aspect;
		final float[][] trans = { { near / right, 0, 0, 0 }, { 0, near / top, 0, 0 },
				{ 0, 0, -(far + near) / (far - near), -far * near / (far - near) }, { 0, 0, -1, 0 } };
		Matrix3D m = new Matrix3D(trans);
		//Logger.getLogger(Channel3D.class).info("Create perspective matrix:" + m);
		return m;
	}
}
