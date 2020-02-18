package com.mapfinal.converter;

public class MatrixKit {

	/**
	 * Inverse number
	 * 
	 * @param s
	 * @return
	 */
	public static int inverse(int[] s) {
		int t = 0;
		for (int i = 0; i < s.length - 1; i++)
			for (int j = i + 1; j < s.length; j++)
				if (s[i] > s[j])
					t++;
		return t;
	}

	public static int factorial(int n) {
		return n == 0 ? 1 : n * factorial(n - 1);
	}

	/**
	 * 
	 * @param n
	 *            the serial number size
	 * @param index
	 *            index of all arrangements
	 * @return A possible order from 1 to n by the given index that is from 0 to
	 *         n! - 1
	 */
	public static int[] order(int n, int index) {
		if (n < 1)
			throw new IllegalArgumentException("The size of number array could not less than 1");
		if (index >= factorial(n) || index < 0)
			throw new IllegalArgumentException("The index could not be reached");
		int[] nums = new int[n];
		fillArray(n, nums, index);
		return nums;
	}

	private static void fillArray(int n, int[] nums, int index) {
		if (n == 0)
			return;
		int fac = factorial(n - 1);
		int p = index / fac + 1;
		int i = -1;
		while (p > 0) {
			if (nums[++i] == 0)
				p--;
		}
		nums[i] = n;
		fillArray(n - 1, nums, index % fac);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++)
				System.out.print(order(3, i)[j]);
			System.out.println();
		}
	}
}
