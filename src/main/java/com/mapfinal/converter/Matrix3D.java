package com.mapfinal.converter;

public class Matrix3D extends ArrayMatrix {
	public Matrix3D(float[][] array) {
		super(4, 4);
		if (array == null)
			throw new IllegalArgumentException("The init array of Matrix constructer can't be null");
		if (array.length == 0)
			throw new IllegalArgumentException("The init array of Matrix constructer must contain at least one number");
		for (int i = 0; i < 4 && i < array.length; i++)
			for (int j = 0; j < 4 && j < array[i].length; j++)
				set(i, j, array[i][j]);
	}

	public Matrix3D() {
		super(4, 4);
	}

	public void join(Matrix3D j3d) {
		float[][] A = toArray();
		float[][] B = times(j3d).toArray();
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				A[i][j] = B[i][j];
		//Logger.getLogger(Matrix3D.class).info("J3dMatrix chain with value:" + toString());
	}
}
