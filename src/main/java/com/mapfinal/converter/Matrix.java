package com.mapfinal.converter;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Provide basic rules for 3D object calculas in java, the method is provided to
 * obtain the basic requirement. So note that this interface is not for math
 * use. To promote effiency also considering the use in 3D area, the basic data
 * type is float instead of double.
 * 
 * @author Acce1erator
 * @since 1.0
 */
public interface Matrix {

	/**
	 * 
	 * @return The rows number of the Matrix
	 */
	int row();

	/**
	 * 
	 * @return The columns number of the Matrix
	 */
	int column();

	/**
	 * Get a single element.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	float get(int i, int j);

	/**
	 * Set a single element.
	 * 
	 * @param i
	 * @param j
	 * @param s
	 */
	void set(int i, int j, float s);

	/**
	 * Clone the Matrix object.
	 * 
	 * @return
	 */
	Matrix clone();

	/**
	 * C = A + B
	 * 
	 * @param B
	 * @return
	 */
	Matrix plus(Matrix B);

	/**
	 * C = A - B
	 * 
	 * @param B
	 * @return
	 */
	Matrix minus(Matrix B);

	/**
	 * Linear algebraic matrix multiplication, A * B.
	 * 
	 * @param B
	 * @return
	 */
	Matrix times(Matrix B);

	/**
	 * Multiply a matrix by a scalar, C = s*A.
	 * 
	 * @param s
	 * @return
	 */
	Matrix times(float s);

	/**
	 * Devide a matrix by a scalar, C = A/s.
	 * 
	 * @param s
	 * @return
	 */
	Matrix divide(float s);

	/**
	 * Matrix transpose.
	 * 
	 * @return
	 */
	Matrix trans();

	/**
	 * Matrix determinant
	 * 
	 * @return
	 */
	float det();

	/**
	 * return an array replacement of the Matrix, see more in
	 * {@link ArrayMatrix#toArray()}.
	 * 
	 * @return
	 */
	float[][] toArray();

	/**
	 * Traverse the matrix and run the given operation.
	 * 
	 * @param consumer
	 */
	void forEach(Consumer<Float> consumer);

	/**
	 * Traverse the matrix and return a new matrix that is the mapper of this
	 * matrix by the given rule.
	 * 
	 * @param funtion
	 * @return
	 */
	Matrix map(Function<Float, Float> function);
}