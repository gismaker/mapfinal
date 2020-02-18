package com.mapfinal.converter;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Acce1erator
 * @since 1.0
 *
 */
public class ArrayMatrix implements Matrix {

	private float[][] A;

	/**
	 * The number of rows.
	 */
	private final int m;
	/**
	 * The number of columns.
	 */
	private final int n;

	public ArrayMatrix(int m, int n) {
		this.m = m > 0 ? m : 1;
		this.n = n > 0 ? n : 1;
		this.A = new float[this.m][this.n];
	}

	public ArrayMatrix(float[][] array) {
		if (array == null)
			throw new IllegalArgumentException("The init array of Matrix constructer can't be null");
		if (array.length == 0)
			throw new IllegalArgumentException("The init array of Matrix constructer must contain at least one number");
		int m = array.length;
		int n = 1;
		for (int i = 0; i < m; i++)
			if (array[i] != null)
				n = n < array[i].length ? array[i].length : n;
		float[][] A = new float[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < array[i].length; j++)
				A[i][j] = array[i][j];
		this.A = A;
		this.m = m;
		this.n = n;
	}

	@Override
	public int row() {
		// TODO
		return m;
	}

	@Override
	public int column() {
		// TODO
		return n;
	}

	@Override
	public float get(int i, int j) {
		// TODO
		return A[i][j];
	}

	@Override
	public void set(int i, int j, float s) {
		// TODO
		A[i][j] = s;
	}

	@Override
	public Matrix clone() {
		// TODO
		Matrix C = new ArrayMatrix(m, n);
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				C.set(i, j, A[i][j]);
		return C;
	}

	@Override
	public Matrix plus(Matrix B) {
		// TODO
		if (B == null)
			throw new IllegalArgumentException("Matrix B cannot  be null");
		int m = row() < B.row() ? row() : B.row();
		int n = column() < B.column() ? column() : B.column();
		Matrix C = new ArrayMatrix(m, n);
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				C.set(i, j, A[i][j] + B.get(i, j));
		return C;
	}

	@Override
	public Matrix minus(Matrix B) {
		// TODO
		if (B == null)
			throw new IllegalArgumentException("Matrix B cannot  be null");
		int m = row() < B.row() ? row() : B.row();
		int n = column() < B.column() ? column() : B.column();
		Matrix C = new ArrayMatrix(m, n);
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				C.set(i, j, A[i][j] - B.get(i, j));
		return C;
	}

	@Override
	public Matrix times(Matrix B) {
		// TODO
		if (B == null)
			throw new IllegalArgumentException("Matrix B cannot  be null");
		if (column() != B.row())
			throw new IllegalArgumentException("The column number of A and the row number of B do not equal");
		int m = row();
		int n = B.column();
		int o = column();
		Matrix C = new ArrayMatrix(m, n);
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				for (int k = 0; k < o; k++)
					C.set(i, j, C.get(i, j) + A[i][k] * B.get(k, j));
		return C;
	}

	@Override
	public Matrix times(float s) {
		// TODO
		Matrix C = new ArrayMatrix(m, n);
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				C.set(i, j, A[i][j] * s);
		return C;
	}

	@Override
	public Matrix divide(float s) {
		// TODO
		if (s == 0)
			throw new IllegalArgumentException("The divide number can be none zero");
		Matrix C = new ArrayMatrix(m, n);
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				C.set(i, j, A[i][j] / s);
		return C;
	}

	@Override
	public Matrix trans() {
		// TODO
		Matrix C = new ArrayMatrix(n, m);
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				C.set(j, i, A[i][j]);
		return C;
	}

	@Override
	public float det() {
		// TODO
		float det = 0f;
		if (m != n)
			return det;
		int fac = MatrixKit.factorial(n);
		for (int i = 0; i < fac; i++) {
			// 返回一个排列
			int[] order = MatrixKit.order(n, i);
			// 逆序数
			float item = (MatrixKit.inverse(order) & 1) == 0 ? 1 : -1;
			for (int j = 0; j < n; j++)
				item *= A[j][order[j] - 1];
			det += item;
		}
		return det;
	}

	@Override
	public void forEach(Consumer<Float> consumer) {
		// TODO
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				consumer.accept(A[i][j]);
	}

	@Override
	public Matrix map(Function<Float, Float> function) {
		// TODO
		Matrix C = new ArrayMatrix(m, n);
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				C.set(i, j, function.apply(A[i][j]));
		return C;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for (int i = 0; i < m; i++) {
			buffer.append('(');
			for (int j = 0; j < n; j++) {
				buffer.append((int) A[i][j]);
				if (j + 1 < n)
					buffer.append(',');
			}
			buffer.append(')');
			if (i + 1 < m)
				buffer.append(',');
		}
		buffer.append(']');
		return buffer.toString();
	}

	@Override
	/**
	 * Note that the array a reference of the original matrix,that is to say
	 * they point at the same object,use it carefully
	 */
	public float[][] toArray() {
		return A;
	}

	public static void main(String[] args) {
		float[][] a = { { 1, 0, 2 }, { 0, 1.5f, 2 }, { 1, 0, 0 } };
		System.out.println(new ArrayMatrix(a).det());
	}
}
