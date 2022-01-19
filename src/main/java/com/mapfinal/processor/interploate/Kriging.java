package com.mapfinal.processor.interploate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * https://blog.csdn.net/Xuan866/article/details/115555733
 * https://my.oschina.net/umbrellall1/blog/4256500
 * https://gitee.com/bobspace/kriging.js/blob/master/kriging.js
 * @author yangyong
 *
 */
public class Kriging {
	// 格点数据生成方法
	public static Variogram grid(Double[][][] world, Double[] ts, Double[] xs, Double[] ys, Double width) {
		Variogram variogram = train(ts, xs, ys);
		variogram.setMax((double) Collections.max(Arrays.asList(variogram.t)));
		variogram.setMin((double) Collections.min(Arrays.asList(variogram.t)));
		int i, j, k, n = world.length;
		if (n == 0)
			return null;
		// Boundaries of polygons space
		Double[] xlim = { world[0][0][0], world[0][0][0] };
		Double[] ylim = { world[0][0][1], world[0][0][1] };
		for (i = 0; i < n; i++) // Polygons
			for (j = 0; j < world[i].length; j++) { // Vertices
				if (world[i][j][0] < xlim[0])
					xlim[0] = world[i][j][0];
				if (world[i][j][0] > xlim[1])
					xlim[1] = world[i][j][0];
				if (world[i][j][1] < ylim[0])
					ylim[0] = world[i][j][1];
				if (world[i][j][1] > ylim[1])
					ylim[1] = world[i][j][1];
			}

		// Alloc for O(n^2) space
		Double xtarget, ytarget;
		Integer[] a = new Integer[2];
		Integer[] b = new Integer[2];
		Double[] lxlim = new Double[2]; // Local dimensions
		Double[] lylim = new Double[2]; // Local dimensions
		Integer x = (int) Math.ceil((xlim[1] - xlim[0]) / width);
		Integer y = (int) Math.ceil((ylim[1] - ylim[0]) / width);

		Double[][] A = new Double[x + 1][];
		for (i = 0; i <= x; i++)
			A[i] = new Double[y + 1];
		for (i = 0; i < n; i++) {
			// Range for polygons[i]
			lxlim[0] = world[i][0][0];
			lxlim[1] = lxlim[0];
			lylim[0] = world[i][0][1];
			lylim[1] = lylim[0];
			for (j = 1; j < world[i].length; j++) { // Vertices
				if (world[i][j][0] < lxlim[0])
					lxlim[0] = world[i][j][0];
				if (world[i][j][0] > lxlim[1])
					lxlim[1] = world[i][j][0];
				if (world[i][j][1] < lylim[0])
					lylim[0] = world[i][j][1];
				if (world[i][j][1] > lylim[1])
					lylim[1] = world[i][j][1];
			}

			// Loop through polygon subspace
			a[0] = (int) Math.floor(((lxlim[0] - ((lxlim[0] - xlim[0]) % width)) - xlim[0]) / width);
			a[1] = (int) Math.ceil(((lxlim[1] - ((lxlim[1] - xlim[1]) % width)) - xlim[0]) / width);
			b[0] = (int) Math.floor(((lylim[0] - ((lylim[0] - ylim[0]) % width)) - ylim[0]) / width);
			b[1] = (int) Math.ceil(((lylim[1] - ((lylim[1] - ylim[1]) % width)) - ylim[0]) / width);
			for (j = a[0]; j <= a[1]; j++)
				for (k = b[0]; k <= b[1]; k++) {
					xtarget = xlim[0] + j * width;
					ytarget = ylim[0] + k * width;
					if (pip(world[i], xtarget, ytarget)) {
						A[j][k] = predict(xtarget, ytarget, variogram);
						if (A[j][k] > variogram.getMax()) {
							A[j][k] = variogram.getMax();
						}
					}
				}
		}

		Variogram variogram1 = new Variogram();
		variogram1.re_A = A;
		variogram1.re_xlim = xlim;
		variogram1.re_ylim = ylim;
		Double[] zlim1 = { variogram.getMin(), variogram.getMax() };
		variogram1.re_zlim = zlim1;
		variogram1.re_width = width;
		return variogram1;
	}

	public static boolean pip(Double[][] array, Double x, Double y) {
		Integer i, j;
		boolean c = false;
		for (i = 0, j = array.length - 1; i < array.length; j = i++) {
			if (((array[i][1] > y) != (array[j][1] > y))
					&& (x < (array[j][0] - array[i][0]) * (y - array[i][1]) / (array[j][1] - array[i][1])
							+ array[i][0])) {
				c = !c;
			}
		}
		return c;
	}

	public static double predict(Double x, Double y, Variogram variogram) {
		Integer i;
		Double[] k = new Double[variogram.n];
		for (i = 0; i < variogram.n; i++)
			k[i] = variogram.model(Math.pow(Math.pow(x - variogram.x[i], 2) + Math.pow(y - variogram.y[i], 2), 0.5),
					variogram.nugget, variogram.range, variogram.sill, variogram.A);
		return kriging_matrix_multiply(k, variogram.M, 1, variogram.n, 1)[0];
	}

	public static Double[] kriging_matrix_multiply(Double[] X, Double[] Y, Integer n, Integer m, Integer p) {
		Integer i, j, k;
		Double[] Z = new Double[n * p];
		for (i = 0; i < n; i++) {
			for (j = 0; j < p; j++) {
				Z[i * p + j] = 0d;
				for (k = 0; k < m; k++)
					Z[i * p + j] += X[i * m + k] * Y[k * p + j];
			}
		}
		return Z;
	}

	public static Double[] kriging_matrix_transpose(Double[] X, Integer n, Integer m) {
		Integer i, j;
		Double[] Z = new Double[m * n];
		for (i = 0; i < n; i++)
			for (j = 0; j < m; j++)
				Z[j * n + i] = X[i * m + j];
		return Z;
	}

	public static Double[] kriging_matrix_diag(Double c, Integer n) {
		Integer i;
		Double[] Z = rep(new Double[n * n], 0d);
		for (i = 0; i < n; i++)
			Z[i * n + i] = c;
		return Z;
	}

	public static Double[] kriging_matrix_add(Double[] X, Double[] Y, Integer n, Integer m) {
		Integer i, j;
		Double[] Z = new Double[n * m];
		for (i = 0; i < n; i++)
			for (j = 0; j < m; j++)
				Z[i * m + j] = X[i * m + j] + Y[i * m + j];
		return Z;
	}

	public static boolean kriging_matrix_chol(Double[] X, Integer n) {
		Integer i, j, k, sum;
		Double[] p = new Double[n];
		for (i = 0; i < n; i++)
			p[i] = X[i * n + i];
		for (i = 0; i < n; i++) {
			for (j = 0; j < i; j++)
				p[i] -= X[i * n + j] * X[i * n + j];
			if (p[i] <= 0)
				return false;
			p[i] = Math.sqrt(p[i]);
			for (j = i + 1; j < n; j++) {
				for (k = 0; k < i; k++)
					X[j * n + i] -= X[j * n + k] * X[i * n + k];
				X[j * n + i] /= p[i];
			}
		}
		for (i = 0; i < n; i++)
			X[i * n + i] = p[i];
		return true;
	}

	public static void kriging_matrix_chol2inv(Double[] X, Integer n) {
		Integer i, j, k;
		Double sum;
		for (i = 0; i < n; i++) {
			X[i * n + i] = 1 / X[i * n + i];
			for (j = i + 1; j < n; j++) {
				sum = 0d;
				for (k = i; k < j; k++)
					sum -= X[j * n + k] * X[k * n + i];
				X[j * n + i] = sum / X[j * n + j];
			}
		}
		for (i = 0; i < n; i++)
			for (j = i + 1; j < n; j++)
				X[i * n + j] = 0d;
		for (i = 0; i < n; i++) {
			X[i * n + i] *= X[i * n + i];
			for (k = i + 1; k < n; k++)
				X[i * n + i] += X[k * n + i] * X[k * n + i];
			for (j = i + 1; j < n; j++)
				for (k = j; k < n; k++)
					X[i * n + j] += X[k * n + i] * X[k * n + j];
		}
		for (i = 0; i < n; i++)
			for (j = 0; j < i; j++)
				X[i * n + j] = X[j * n + i];
	}

	public static boolean kriging_matrix_solve(Double[] X, Integer n) {
		Integer m = n;
		Double[] b = new Double[n * n];
		Integer[] indxc = new Integer[n];
		Integer[] indxr = new Integer[n];
		Integer[] ipiv = new Integer[n];
		Integer i = 0, icol = 0, irow = 0, j, k, l, ll;
		Double big, dum, pivinv, temp;

		for (i = 0; i < n; i++)
			for (j = 0; j < n; j++) {
				if (i == j)
					b[i * n + j] = 1d;
				else
					b[i * n + j] = 0d;
			}
		for (j = 0; j < n; j++)
			ipiv[j] = 0;
		for (i = 0; i < n; i++) {
			big = 0d;
			for (j = 0; j < n; j++) {
				if (ipiv[j] != 1) {
					for (k = 0; k < n; k++) {
						if (ipiv[k] == 0) {
							if (Math.abs(X[j * n + k]) >= big) {
								big = Math.abs(X[j * n + k]);
								irow = j;
								icol = k;
							}
						}
					}
				}
			}
			++(ipiv[icol]);

			if (irow != icol) {
				for (l = 0; l < n; l++) {
					temp = X[irow * n + l];
					X[irow * n + l] = X[icol * n + l];
					X[icol * n + l] = temp;
				}
				for (l = 0; l < m; l++) {
					temp = b[irow * n + l];
					b[irow * n + l] = b[icol * n + l];
					b[icol * n + l] = temp;
				}
			}
			indxr[i] = irow;
			indxc[i] = icol;

			if (X[icol * n + icol] == 0)
				return false; // Singular

			pivinv = 1 / X[icol * n + icol];
			X[icol * n + icol] = 1d;
			for (l = 0; l < n; l++)
				X[icol * n + l] *= pivinv;
			for (l = 0; l < m; l++)
				b[icol * n + l] *= pivinv;

			for (ll = 0; ll < n; ll++) {
				if (ll != icol) {
					dum = X[ll * n + icol];
					X[ll * n + icol] = 0d;
					for (l = 0; l < n; l++)
						X[ll * n + l] -= X[icol * n + l] * dum;
					for (l = 0; l < m; l++)
						b[ll * n + l] -= b[icol * n + l] * dum;
				}
			}
		}
		for (l = (n - 1); l >= 0; l--)
			if (indxr[l] != indxc[l]) {
				for (k = 0; k < n; k++) {
					temp = X[k * n + indxr[l]];
					X[k * n + indxr[l]] = X[k * n + indxc[l]];
					X[k * n + indxc[l]] = temp;
				}
			}

		return true;
	}

	public static Double[] rep(Double[] X, Double init) {
		for (int i = 0; i < X.length; i++) {
			X[i] = init;
		}
		return X;
	}

	public static Variogram train(Double[] t, Double[] x, Double[] y) {
		Variogram variogram = new Variogram();
		variogram.t = t;
		variogram.x = x;
		variogram.y = y;
		variogram.nugget = 0.0;
		variogram.range = 0.0;
		variogram.sill = 0.0;
		variogram.A = 1d / 3;
		variogram.n = 0;
		String model = "spherical";
		Double sigma2 = 0d;
		Double alpha = 0.01d;
		// Lag distance/semivariance
		Integer i, j, k, l, n = t.length;
		Double[][] distance = new Double[(n * n - n) / 2][];
		for (i = 0, k = 0; i < n; i++)
			for (j = 0; j < i; j++, k++) {
				distance[k] = new Double[2];
				distance[k][0] = Math.pow(Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2), 0.5);
				distance[k][1] = Math.abs(t[i] - t[j]);
			}
		Arrays.sort(distance, new Comparator<Double[]>() {
			@Override
			public int compare(Double[] o1, Double[] o2) {

				if ((o1[0] - o2[0]) > 0d) {
					return 1;
				}
				if ((o1[0] - o2[0]) < 0d) {
					return -1;
				}
				return 0;
			}
		});
		variogram.range = distance[(n * n - n) / 2 - 1][0];

		// Bin lag distance
		Integer lags = ((n * n - n) / 2) > 30 ? 30 : (n * n - n) / 2;
		Double tolerance = variogram.range / lags;
		Double[] lag = rep(new Double[lags], 0d);
		Double[] semi = rep(new Double[lags], 0d);
		if (lags < 30) {
			for (l = 0; l < lags; l++) {
				lag[l] = distance[l][0];
				semi[l] = distance[l][1];
			}
		} else {
			for (i = 0, j = 0, k = 0, l = 0; i < lags && j < ((n * n - n) / 2); i++, k = 0) {
				while (distance[j][0] <= ((i + 1) * tolerance)) {
					lag[l] += distance[j][0];
					semi[l] += distance[j][1];
					j++;
					k++;
					if (j >= ((n * n - n) / 2))
						break;
				}
				if (k > 0) {
					lag[l] /= k;
					semi[l] /= k;
					l++;
				}
			}
			if (l < 2)
				return variogram; // Error: Not enough points
		}

		// Feature transformation
		n = l;
		variogram.range = lag[n - 1] - lag[0];
		Double[] X = rep(new Double[2 * n], 1d);
		Double[] Y = new Double[n];
		Double A = variogram.A;
		for (i = 0; i < n; i++) {
			if (model.equals("gaussian")) {

				X[i * 2 + 1] = 1.0 - Math.exp(-(1.0 / A) * Math.pow(lag[i] / variogram.range, 2));
			} else if (model.equals("exponential")) {

				X[i * 2 + 1] = 1.0 - Math.exp(-(1.0 / A) * lag[i] / variogram.range);

			} else if (model.equals("spherical")) {
				X[i * 2 + 1] = 1.5 * (lag[i] / variogram.range) - 0.5 * Math.pow(lag[i] / variogram.range, 3);

			}
			;
			Y[i] = semi[i];
		}

		// Least squares
		Double[] Xt = kriging_matrix_transpose(X, n, 2);
		Double[] Z = kriging_matrix_multiply(Xt, X, 2, n, 2);
		Z = kriging_matrix_add(Z, kriging_matrix_diag(1 / alpha, 2), 2, 2);
		Double[] cloneZ = Z.clone();
		if (kriging_matrix_chol(Z, 2))
			kriging_matrix_chol2inv(Z, 2);
		else {
			kriging_matrix_solve(cloneZ, 2);
			Z = cloneZ;
		}
		Double[] W = kriging_matrix_multiply(kriging_matrix_multiply(Z, Xt, 2, 2, n), Y, 2, n, 1);

		// Variogram parameters
		variogram.nugget = W[0];
		variogram.sill = W[1] * variogram.range + variogram.nugget;
		variogram.n = x.length;

		// Gram matrix with prior
		n = x.length;
		Double[] K = new Double[n * n];
		for (i = 0; i < n; i++) {
			for (j = 0; j < i; j++) {
				K[i * n + j] = variogram.model(Math.pow(Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2), 0.5),
						variogram.nugget, variogram.range, variogram.sill, variogram.A);
				K[j * n + i] = K[i * n + j];
			}
			K[i * n + i] = variogram.model(0d, variogram.nugget, variogram.range, variogram.sill, variogram.A);
		}

		// Inverse penalized Gram matrix projected to target vector
		Double[] C = kriging_matrix_add(K, kriging_matrix_diag(sigma2, n), n, n);
		Double[] cloneC = C.clone();
		if (kriging_matrix_chol(C, n))
			kriging_matrix_chol2inv(C, n);
		else {
			kriging_matrix_solve(cloneC, n);
			C = cloneC;
		}

		// Copy unprojected inverted matrix as K
		K = C.clone();
		Double[] M = kriging_matrix_multiply(C, t, n, n, 1);
		variogram.K = K;
		variogram.M = M;

		return variogram;
	};

}
