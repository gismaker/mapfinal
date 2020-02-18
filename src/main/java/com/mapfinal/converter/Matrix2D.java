package com.mapfinal.converter;

public class Matrix2D extends ArrayMatrix {
	public Matrix2D() {
		super(3, 3);
	}

	public Matrix2D(float[][] a) {
		super(3, 3);
		float[][] A = toArray();
		for (int i = 0; i < 3 && i < a.length; i++)
			for (int j = 0; j < 3 && j < a[i].length; j++)
				A[i][j] = a[i][j];
	}

	public void join(Matrix2D j2d) {
		float[][] A = toArray();
		float[][] B = times(j2d).toArray();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				A[i][j] = B[i][j];
	}
}
