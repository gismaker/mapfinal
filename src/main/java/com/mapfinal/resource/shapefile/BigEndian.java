package com.mapfinal.resource.shapefile;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Notice that the methods here is default used for big endian. while some is
 * used for convert little endian to big endian with littleTo prefix.
 * 
 * @author proxyme
 * 
 */
public class BigEndian {
	/**
	 * Convert little endian into big endian.
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] littleToBig(byte[] bytes) {
		if (bytes == null) {
			throw new NullPointerException();
		}
		byte[] temp = new byte[bytes.length];
		for (int i = bytes.length - 1; i >= 0; i--) {
			temp[i] = bytes[bytes.length - 1 - i];
		}
		return temp;
	}

	/**
	 * Convert a byte array that only one element to byte.
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte toByte(byte[] bytes) {
		return bytes[0];
	}

	/**
	 * Convert byte to character.
	 * 
	 * @param b
	 * @return
	 */
	public static char toChar(byte b) {
		return (char) (b & 0xff);
	}

	/**
	 * Convert character to byte array.
	 * 
	 * @param ch
	 * @return
	 */
	public static byte[] toBytes(char ch) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) (ch >> 8 & 0xff);
		bytes[1] = (byte) (ch & 0xff);
		return bytes;
	}

	/**
	 * Convert byte array to character.
	 * 
	 * @param bytes
	 * @return
	 */
	public static char toChar(byte[] bytes) {
		return (char) ((bytes[0] << 8 & 0xff00) | (bytes[1] & 0xff));
	}

	/**
	 * Convert byte array to character array.
	 * 
	 * @param b
	 * @return
	 */
	public static char[] toCharArray(byte b[]) {
		char[] chs = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			chs[i] = toChar(b[i]);
		}
		return chs;
	}

	/**
	 * Convert little endian byte array to big endian char.
	 * 
	 * @param bytes
	 * @return
	 */
	public static char littleToChar(byte b) {
		return (char) (b & 0xff);
	}

	/**
	 * Convert integer to byte array.
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] toBytes(int i) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (i >> 24 & 0xff);
		bytes[1] = (byte) (i >> 16 & 0xff);
		bytes[2] = (byte) (i >> 8 & 0xff);
		bytes[3] = (byte) (i & 0xff);
		return bytes;
	}

	/**
	 * Convert byte array to int.
	 * 
	 * @param bytes
	 * @return
	 */
	public static int toInt(byte[] bytes) {
		int rt = 0;
		rt += (bytes[0] & 0xff) << 24;
		rt += (bytes[1] & 0xff) << 16;
		rt += (bytes[2] & 0xff) << 8;
		rt += (bytes[3] & 0xff);
		return rt;
	}

	/**
	 * Convert byte array to int array.
	 * 
	 * @param bytes
	 * @return
	 */
	public static int[] toIntArray(byte[] bytes) {
		int[] intArray = new int[bytes.length / 4];
		byte[] temp = new byte[4];
		int offset = 0;
		for (int i = 0; i < intArray.length; i++) {
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(bytes, offset, temp, 0, temp.length);
			offset += temp.length;
			intArray[i] = toInt(temp);
		}
		return intArray;
	}

	/**
	 * Convert little endian byte array to big endian int array.
	 * 
	 * @param bytes
	 * @return
	 */
	public static int[] littleToIntArray(byte[] bytes) {
		int[] intArray = new int[bytes.length / 4];
		byte[] temp = new byte[4];
		int offset = 0;
		for (int i = 0; i < intArray.length; i++) {
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(bytes, offset, temp, 0, temp.length);
			offset += temp.length;
			intArray[i] = littleToInt(temp);
		}
		return intArray;
	}

	/**
	 * Convert little endian byte array to big endian integer.
	 * 
	 * @param bytes
	 * @return
	 */
	public static int littleToInt(byte[] bytes) {
		return toInt(littleToBig(bytes));
	}

	/**
	 * Convert short to byte array.
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] toBytes(short s) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) (s >> 8 & 0xff);
		bytes[1] = (byte) (s & 0xff);
		return bytes;
	}

	/**
	 * Convert byte array to short.
	 * 
	 * @param bytes
	 * @return
	 */
	public static short toShort(byte[] bytes) {
		short rt = 0;
		rt += (bytes[0] << 8 & 0xff00);
		rt += (bytes[1] & 0xff);
		return rt;
	}
	/**
	 * Convert little endian unsigned char to Java short.
	 * @param b
	 * @return
	 */
	public static short unsignedToShort(byte b) {
		return (short) (b&0xff);
	}

	/**
	 * Convert byte array to short array.
	 * 
	 * @param bytes
	 * @return
	 */
	public static short[] toShortArray(byte[] bytes) {
		short[] shortArray = new short[bytes.length / 2];
		byte[] temp = new byte[2];
		int offset = 0;
		for (int i = 0; i < shortArray.length; i++) {
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(bytes, offset, temp, 0, temp.length);
			offset += temp.length;
			shortArray[i] = toShort(temp);
		}
		return shortArray;
	}

	/**
	 * Convert little endian byte array to big endian short value.
	 * 
	 * @param bytes
	 * @return
	 */
	public static short littleToShort(byte[] bytes) {
		return toShort(littleToBig(bytes));
	}

	/**
	 * Convert long to byte array.
	 * 
	 * @param l
	 * @return
	 */
	public static byte[] toBytes(long l) {
		byte[] bytes = new byte[8];
		bytes[0] = (byte) (l >> 56 & 0xff);
		bytes[1] = (byte) (l >> 48 & 0xff);
		bytes[2] = (byte) (l >> 40 & 0xff);
		bytes[3] = (byte) (l >> 32 & 0xff);
		bytes[4] = (byte) (l >> 24 & 0xff);
		bytes[5] = (byte) (l >> 16 & 0xff);
		bytes[6] = (byte) (l >> 8 & 0xff);
		bytes[7] = (byte) (l >> 0 & 0xff);
		return bytes;
	}

	/**
	 * Convert byte array to long.
	 * 
	 * @param bytes
	 * @return
	 */
	public static long toLong(byte[] bytes) {
		return (((long) bytes[0] & 0xff) << 56)
				| (((long) bytes[1] & 0xff) << 48)
				| (((long) bytes[2] & 0xff) << 40)
				| (((long) bytes[3] & 0xff) << 32)
				| (((long) bytes[4] & 0xff) << 24)
				| (((long) bytes[5] & 0xff) << 16)
				| (((long) bytes[6] & 0xff) << 8)
				| (((long) bytes[7] & 0xff) << 0);
	}

	/**
	 * Convert byte array to long array.
	 * 
	 * @param bytes
	 * @return
	 */
	public static long[] toLongArray(byte[] bytes) {
		long[] longArray = new long[bytes.length / 8];
		byte[] temp = new byte[8];
		int offset = 0;
		for (int i = 0; i < longArray.length; i++) {
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(bytes, offset, temp, 0, temp.length);
			offset += temp.length;
			longArray[i] = toLong(temp);
		}
		return longArray;
	}

	/**
	 * Convert little endian byte array to big endian long value.
	 * 
	 * @param bytes
	 * @return
	 */
	public static long littleToLong(byte[] bytes) {
		return toLong(littleToBig(bytes));
	}

	/**
	 * Convert float to byte array.
	 * 
	 * @param f
	 * @return
	 */
	public static byte[] toBytes(float f) {
		return toBytes(Float.floatToIntBits(f));
	}

	/**
	 * Convert byte array to float.
	 * 
	 * @param bytes
	 * @return
	 */
	public static float toFloat(byte[] bytes) {
		return Float.intBitsToFloat(toInt(bytes));
	}

	/**
	 * Convert byte array to float array.
	 * 
	 * @param bytes
	 * @return
	 */
	public static float[] toFloatArray(byte[] bytes) {
		float[] floatArray = new float[bytes.length / 4];
		byte[] temp = new byte[4];
		int offset = 0;
		for (int i = 0; i < floatArray.length; i++) {
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(bytes, offset, temp, 0, temp.length);
			offset += temp.length;
			floatArray[i] = toFloat(temp);
		}
		return floatArray;
	}

	/**
	 * Convert little endian byte array to big endian float value.
	 * 
	 * @param bytes
	 * @return
	 */
	public static float littleToFloat(byte[] bytes) {
		return toFloat(littleToBig(bytes));
	}

	/**
	 * Convert double to byte array.
	 * 
	 * @param d
	 * @return
	 */
	public static byte[] toBytes(double d) {
		return toBytes(Double.doubleToLongBits(d));
	}

	/**
	 * Convert byte array to double.
	 * 
	 * @param bytes
	 * @return
	 */
	public static double toDouble(byte[] bytes) {
		return Double.longBitsToDouble(toLong(bytes));
	}

	/**
	 * Convert byte array to double array.
	 * 
	 * @param bytes
	 * @return
	 */
	public static double[] toDoubleArray(byte[] bytes) {
		double[] doubleArray = new double[bytes.length / 8];
		byte[] temp = new byte[8];
		int offset = 0;
		for (int i = 0; i < doubleArray.length; i++) {
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(bytes, offset, temp, 0, temp.length);
			offset += temp.length;
			doubleArray[i] = toDouble(temp);
		}
		return doubleArray;
	}

	/**
	 * Convert little endian byte array to big endian double value.
	 * 
	 * @param bytes
	 * @return
	 */
	public static double littleToDouble(byte[] bytes) {
		return toDouble(littleToBig(bytes));
	}

	/**
	 * Convert a string to byte array with the default charset of your
	 * environment.
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] toBytes(String str) {
		return str.getBytes();
	}

	/**
	 * Convert byte array to a string with the specific charset.
	 * 
	 * @param bytes
	 * @param charsetName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String toString(byte[] bytes, String charsetName)
			throws UnsupportedEncodingException {
		return new String(bytes, charsetName);
	}

	public static void main(String[] arg) {

	}
}
