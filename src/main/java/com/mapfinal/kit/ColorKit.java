package com.mapfinal.kit;

import java.util.HashMap;
import java.util.Locale;

public class ColorKit {

    public static final int BLACK = 0xFF000000;
    public static final int DKGRAY = 0xFF444444;
    public static final int GRAY = 0xFF888888;
    public static final int LTGRAY = 0xFFCCCCCC;
    public static final int WHITE = 0xFFFFFFFF;
    public static final int RED = 0xFFFF0000;
    public static final int GREEN = 0xFF00FF00;
    public static final int BLUE = 0xFF0000FF;
    public static final int YELLOW = 0xFFFFFF00;
    public static final int CYAN = 0xFF00FFFF;
    public static final int MAGENTA = 0xFFFF00FF;
    public static final int TRANSPARENT = 0;

    /**
     * Return the alpha component of a color int. This is the same as saying
     * <p>
     * color >>> 24
     */
    public static int alpha(int color) {
        return color >>> 24;
    }

    /**
     * Return the red component of a color int. This is the same as saying
     * <p>
     * (color >> 16) & 0xFF
     */
    public static int red(int color) {
        return (color >> 16) & 0xFF;
    }

    /**
     * Return the green component of a color int. This is the same as saying
     * <p>
     * (color >> 8) & 0xFF
     */
    public static int green(int color) {
        return (color >> 8) & 0xFF;
    }

    /**
     * Return the blue component of a color int. This is the same as saying
     * color & 0xFF
     */
    public static int blue(int color) {
        return color & 0xFF;
    }

    /**
     * Return a color-int from red, green, blue components.
     * The alpha component is implicity 255 (fully opaque).
     * These component values should be [0..255], but there is no
     * range check performed, so if they are out of range, the
     * returned color is undefined.
     *
     * @param red   Red component [0..255] of the color
     * @param green Green component [0..255] of the color
     * @param blue  Blue component [0..255] of the color
     */

    public static int rgb(int red, int green, int blue) {
        return (0xFF << 24) | (red << 16) | (green << 8) | blue;
    }

    /**
     * Return a color-int from alpha, red, green, blue components.
     * <p>
     * These component values should be [0..255], but there is no
     * <p>
     * range check performed, so if they are out of range, the
     * <p>
     * returned color is undefined.
     *
     * @param alpha Alpha component [0..255] of the color
     * @param red   Red component [0..255] of the color
     * @param green Green component [0..255] of the color
     * @param blue  Blue component [0..255] of the color
     */
    public static int argb(int alpha, int red, int green, int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
    
    public static int argb(int alpha, int color) {
        return (alpha << 24) | color;
    }

    /**
     * Returns the hue component of a color int.
     *
     * @return A value between 0.0f and 1.0f
     * @hide Pending API council
     */
    public static float hue(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int V = Math.max(b, Math.max(r, g));
        int temp = Math.min(b, Math.min(r, g));

        float H;

        if (V == temp) {
            H = 0;
        } else {
            final float vtemp = (float) (V - temp);
            final float cr = (V - r) / vtemp;
            final float cg = (V - g) / vtemp;
            final float cb = (V - b) / vtemp;

            if (r == V) {
                H = cb - cg;
            } else if (g == V) {
                H = 2 + cr - cb;
            } else {
                H = 4 + cg - cr;
            }

            H /= 6.f;
            if (H < 0) {
                H++;
            }
        }

        return H;
    }

    /**
     * Returns the saturation component of a color int.
     *
     * @return A value between 0.0f and 1.0f
     * @hide Pending API council
     */
    public static float saturation(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int V = Math.max(b, Math.max(r, g));
        int temp = Math.min(b, Math.min(r, g));

        float S;

        if (V == temp) {
            S = 0;
        } else {
            S = (V - temp) / (float) V;
        }

        return S;
    }

    /**
     * Returns the brightness component of a color int.
     *
     * @return A value between 0.0f and 1.0f
     * @hide Pending API council
     */
    public static float brightness(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int V = Math.max(b, Math.max(r, g));

        return (V / 255.f);
    }

    /**
     * Parse the color string, and return the corresponding color-int.
     * <p>
     * If the string cannot be parsed, throws an IllegalArgumentException
     * <p>
     * exception. Supported formats are:
     * <p>
     * #RRGGBB
     * <p>
     * #AARRGGBB
     * <p>
     * 'red', 'blue', 'green', 'black', 'white', 'gray', 'cyan', 'magenta',
     * <p>
     * 'yellow', 'lightgray', 'darkgray'
     */
    public static int parseColor(String colorString) {
        if (colorString.charAt(0) == '#') {
            // Use a long to avoid rollovers on #ffXXXXXX
            long color = Long.parseLong(colorString.substring(1), 16);
            if (colorString.length() == 7) {
                // Set the alpha value
                color |= 0x00000000ff000000;
            } else if (colorString.length() != 9) {
                throw new IllegalArgumentException("Unknown color");
            }
            return (int) color;
        } else {
            Integer color = sColorNameMap.get(colorString.toLowerCase(Locale.US));
            if (color != null) {
                return color;
            }
        }
        throw new IllegalArgumentException("Unknown color");
    }
    
    /**
	 * int 转 #FFFFFFFF
	 * @param color
	 * @return
	 */
	public static String convertToHexValue(int color) {
		String red = intToHexValue(red(color));
		String green = intToHexValue(green(color));
		String blue = intToHexValue(blue(color));
		return "#" + intToHexValue(alpha(color)) + red + green + blue;
	}
	
	/**
	 * int 转 #FFFFFF
	 * @param color
	 * @return
	 */
	public static String convertToHex(int color) {
		String red = intToHexValue(red(color));
		String green = intToHexValue(green(color));
		String blue = intToHexValue(blue(color));
		return "#" + red + green + blue;
	}
	
	private static String intToHexValue(int number) {
		String result = Integer.toHexString(number & 0xff);
		while (result.length() < 2) {
			result = "0" + result;
		}
		return result.toUpperCase();
	}

    private static final HashMap<String, Integer> sColorNameMap;
    static {
        sColorNameMap = new HashMap<String, Integer>();
        sColorNameMap.put("black", BLACK);
        sColorNameMap.put("darkgray", DKGRAY);
        sColorNameMap.put("gray", GRAY);
        sColorNameMap.put("lightgray", LTGRAY);
        sColorNameMap.put("white", WHITE);
        sColorNameMap.put("red", RED);
        sColorNameMap.put("green", GREEN);
        sColorNameMap.put("blue", BLUE);
        sColorNameMap.put("yellow", YELLOW);
        sColorNameMap.put("cyan", CYAN);
        sColorNameMap.put("magenta", MAGENTA);
    }
}
