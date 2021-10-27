package com.mapfinal.platform.swing;

import java.awt.Color;

import com.mapfinal.kit.ColorKit;

public class ColorUtil {

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static Color colorFromHexValue(String str) {
		int length = str.length();
		if(length < 7) return null;
		String str1 = str.substring(1, 3);
		String str2 = str.substring(3, 5);
		String str3 = str.substring(5, 7);
		int alpha = Integer.parseInt(str1, 16);
		int red = Integer.parseInt(str2, 16);
		int green = Integer.parseInt(str3, 16);
		if(length==8) {
			String str4 = str.substring(7, 9);
			int blue = Integer.parseInt(str4, 16);
			Color color = new Color(red, green, blue, alpha);
			return color;
		}
		Color color = new Color(alpha, red, green);
		return color;
	}
	
	/**
	 * color 转  #FFFFFF
	 * @param color
	 * @return
	 */
	public static String colorToHexValue(Color color) {
		return "#" + intToHexValue(color.getAlpha()) + intToHexValue(color.getRed()) + intToHexValue(color.getGreen())
				+ intToHexValue(color.getBlue());
	}
	
	/**
	 * color 转  int
	 * @param color
	 * @return
	 */
	public static int colorToInt(Color color) {
		return color.getRGB();
	}
	/**
	 * int 转  color
	 * @param color
	 * @return
	 */
	public static Color intToColor(int color) {
		return new Color(color);
	}
	
	public static Color intToColor(int color, int alpha) {
		color = ColorKit.argb(alpha, color);
		return new Color(color);
	}

	/**
	 * int 转 #FFFFFF
	 * @param color
	 * @return
	 */
	public String convertToHexValue(int color) {
		String red = intToHexValue(red(color));
		String green = intToHexValue(green(color));
		String blue = intToHexValue(blue(color));
		return "#" + intToHexValue(alpha(color)) + red + green + blue;
	}
	
	public int convertToIntValue(String color) {
		return colorFromHexValue(color).getRGB();
	}

	private static String intToHexValue(int number) {
		String result = Integer.toHexString(number & 0xff);
		while (result.length() < 2) {
			result = "0" + result;
		}
		return result.toUpperCase();
	}
	

	private static int alpha(int color) {
		return color >>> 24;
	}

	/**
	 * * Return the red component of a color int. This is the same as saying *
	 * (color >> 16) & 0xFF
	 */

	private static int red(int color) {
		return (color >> 16) & 0xFF;
	}

	/**
	 * * Return the green component of a color int. This is the same as saying *
	 * (color >> 8) & 0xFF
	 */

	private static int green(int color) {
		return (color >> 8) & 0xFF;
	}

	/**
	 * * Return the blue component of a color int. This is the same as saying *
	 * color & 0xFF
	 */

	private static int blue(int color) {
		return color & 0xFF;
	}
}
