package com.mapfinal.converter;

public class Channel2D {

	/**
	 * 旋转
	 * 
	 * @param theta
	 *            绕原点逆时针旋转角度，弧度制
	 * @return
	 */
	public static Matrix2D rotate(float theta) {
		float cos = (float) Math.cos(theta);
		float sin = (float) Math.sin(theta);
		final float[][] a = { { cos, sin, 0f }, { -sin, cos, 0f }, { 0f, 0f, 1f } };
		return new Matrix2D(a);
	}

	/**
	 * 平移
	 * 
	 * @param tx
	 *            x方向平移距离
	 * @param ty
	 *            y方向平移距离
	 * @return
	 */
	public static Matrix2D translate(float tx, float ty) {
		final float[][] a = { { 1f, 0f, tx }, { 0f, 1f, ty }, { 0f, 0f, 1f } };
		return new Matrix2D(a);
	}

	/**
	 * 缩放
	 * 
	 * @param sx
	 *            x方向缩放比例
	 * @param sy
	 *            y方向缩放比例
	 * @return
	 */
	public static Matrix2D scale(float sx, float sy) {
		final float[][] a = { { sx, 0f, 0f }, { 0f, sy, 0f }, { 0f, 0f, 1f } };
		return new Matrix2D(a);
	}

	/**
	 * 切变
	 * 
	 * @param kx
	 *            平行于x轴的切变分量
	 * @param ky
	 *            平行于y轴的切变分量
	 * @return
	 */
	public static Matrix2D shear(float kx, float ky) {
		final float[][] a = { { 1f, kx, 0f }, { ky, 1f, 0f }, { 0f, 0f, 1f } };
		return new Matrix2D(a);
	}

	/**
	 * 反射
	 * 
	 * @param theta
	 *            经过原点反射平面倾角
	 * @return
	 */
	public static Matrix2D reflect(float theta) {
		float ux = (float) Math.cos(theta);
		float uy = (float) Math.sin(theta);
		final float[][] a = { { 2 * ux * ux - 1, 2 * ux * uy, 0f }, { 2 * ux * uy, 2 * uy * uy - 1, 0f }, { 0, 0, 1 } };
		return new Matrix2D(a);
	}

	/**
	 * 投影
	 * 
	 * @param theta
	 *            经过原点投影平面倾角
	 * @return
	 */
	public static Matrix2D project(float theta) {
		float ux = (float) Math.cos(theta);
		float uy = (float) Math.sin(theta);
		final float[][] a = { { ux * ux, ux * uy, 0f }, { ux * uy, uy * uy, 0f }, { 0f, 0f, 1f } };
		return new Matrix2D(a);
	}
}
