package com.mapfinal.converter;

public class ConverterKit {
	//a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
	static double a = 6378245.0;

	//ee: 椭球的偏心率。
	static double ee = 0.00669342162296594323;

	//x_pi: 圆周率转换量。
	public final static double x_pi = Math.PI * 3000.0 / 180.0;

	//transformLat(lat, lon): 转换方法，比较复杂，不必深究了。输入：横纵坐标，输出：转换后的横坐标。
	//transformLon(lat, lon): 转换方法，同样复杂，自行脑补吧。输入：横纵坐标，输出：转换后的纵坐标。
	//wgs2gcj(lat, lon): WGS坐标转换为GCJ坐标。
	//gcj2bd(lat, lon): GCJ坐标转换为百度坐标。


	public static double[] wgs2bd(double lat, double lon) {
	 double[] wgs2gcj = wgs2gcj(lat, lon);
	 double[] gcj2bd = gcj2bd(wgs2gcj[0], wgs2gcj[1]);
	 return gcj2bd;
	}
	
	public static double[] bd2wgs_exact(double lat, double lon) {
		 double[] db2gcj = bd2gcj(lat, lon);
		 double[] gcj2wgs = gcj2wgs_exact(db2gcj[0], db2gcj[1]);
		 return gcj2wgs;
	}

	//GCJ-02 to BD-09
	public static double[] gcj2bd(double lat, double lon) {
	 double x = lon, y = lat;
	 double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
	 double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
	 double bd_lon = z * Math.cos(theta) + 0.0065;
	 double bd_lat = z * Math.sin(theta) + 0.006;
	 return new double[] { bd_lat, bd_lon };
	}

	//BD-09 to GCJ-02
	public static double[] bd2gcj(double lat, double lon) {
	 double x = lon - 0.0065, y = lat - 0.006;
	 double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
	 double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
	 double gg_lon = z * Math.cos(theta);
	 double gg_lat = z * Math.sin(theta);
	 return new double[] { gg_lat, gg_lon };
	}

	//WGS-84 to GCJ-02
	public static double[] wgs2gcj(double lat, double lon) {
		if (outOfChina(lat, lon)) {
			return new double[] { lat, lon };
		}
		double[] dLatLon = delta(lat, lon);
		double mgLat = lat + dLatLon[0];
		double mgLon = lon + dLatLon[1];
		return new double[] { mgLat, mgLon };
	}
	
	//GCJ-02 to WGS-84 粗略
	public static double[] gcj2wgs(double lat, double lon) {
		if (outOfChina(lat, lon)) {
			double[] loc = { lat, lon };
			return loc;
		}
		double[] dLatLon = delta(lat, lon);
		double mgLat = lat - dLatLon[0];
		double mgLon = lon - dLatLon[1];
		return new double[] { mgLat, mgLon };
	}
	
	private static double[] delta(double lat, double lon) {
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		 double dLon = transformLon(lon - 105.0, lat - 35.0);
		 double radLat = lat / 180.0 * Math.PI;
		 double magic = Math.sin(radLat);
		 magic = 1 - ee * magic * magic;
		 double sqrtMagic = Math.sqrt(magic);
		 dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
		 dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
		 return new double[] { dLat, dLon };
    }
     
	private static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

	private static double transformLat(double lat, double lon) {
	 double ret = -100.0 + 2.0 * lat + 3.0 * lon + 0.2 * lon * lon + 0.1 * lat * lon + 0.2 * Math.sqrt(Math.abs(lat));
	 ret += (20.0 * Math.sin(6.0 * lat * Math.PI) + 20.0 * Math.sin(2.0 * lat * Math.PI)) * 2.0 / 3.0;
	 ret += (20.0 * Math.sin(lon * Math.PI) + 40.0 * Math.sin(lon / 3.0 * Math.PI)) * 2.0 / 3.0;
	 ret += (160.0 * Math.sin(lon / 12.0 * Math.PI) + 320 * Math.sin(lon * Math.PI / 30.0)) * 2.0 / 3.0;
	 return ret;
	}

	private static double transformLon(double lat, double lon) {
	 double ret = 300.0 + lat + 2.0 * lon + 0.1 * lat * lat + 0.1 * lat * lon + 0.1 * Math.sqrt(Math.abs(lat));
	 ret += (20.0 * Math.sin(6.0 * lat * Math.PI) + 20.0 * Math.sin(2.0 * lat * Math.PI)) * 2.0 / 3.0;
	 ret += (20.0 * Math.sin(lat * Math.PI) + 40.0 * Math.sin(lat / 3.0 * Math.PI)) * 2.0 / 3.0;
	 ret += (150.0 * Math.sin(lat / 12.0 * Math.PI) + 300.0 * Math.sin(lat / 30.0 * Math.PI)) * 2.0 / 3.0;
	 return ret;
	}

	//GCJ-02 to WGS-84 精确(二分极限法)
	// var threshold = 0.000000001; 目前设置的是精确到小数点后9位，这个值越小，越精确，但是javascript中，浮点运算本身就不太精确，九位在GPS里也偏差不大了
	public static double[] gcj2wgs_exact(double gcjLat, double gcjLon) {
		double initDelta = 0.01;
		double threshold = 0.000000001;
		double dLat = initDelta, dLon = initDelta;
		double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
		double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
		double wgsLat, wgsLon, i = 0;
        while (true) {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;
            double[] tmp = wgs2gcj(wgsLat, wgsLon);
            dLat = tmp[0] - gcjLat;
            dLon = tmp[1] - gcjLon;
            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
                break;
 
            if (dLat > 0) pLat = wgsLat; else mLat = wgsLat;
            if (dLon > 0) pLon = wgsLon; else mLon = wgsLon;
 
            if (++i > 10000) break;
        }
        return new double[] {wgsLat, wgsLon};
	}
	
	//WGS-84 to Web mercator
    //mercatorLat -> y mercatorLon -> x
    public static double[] wgs2mercator(double wgsLat, double wgsLon) {
    	double x = wgsLon * 20037508.34 / 180.;
    	double y = Math.log(Math.tan((90.0 + wgsLat) * Math.PI / 360.0)) / (Math.PI / 180.0);
        y = y * 20037508.34 / 180.0;
        return new double[] {y, x};
        /*
        if ((Math.abs(wgsLon) > 180 || Math.abs(wgsLat) > 90))
            return null;
        var x = 6378137.0 * wgsLon * 0.017453292519943295;
        var a = wgsLat * 0.017453292519943295;
        var y = 3189068.5 * Math.log((1.0 + Math.sin(a)) / (1.0 - Math.sin(a)));
        return {'lat' : y, 'lon' : x};
        //*/
    }
    // Web mercator to WGS-84
    // mercatorLat -> y mercatorLon -> x
    public static double[] mercator2wgs(double mercatorLat, double mercatorLon) {
    	double x = mercatorLon / 20037508.34 * 180.;
    	double y = mercatorLat / 20037508.34 * 180.;
        y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180.)) - Math.PI / 2);
        return new double[] {y, x};
        /*
        if (Math.abs(mercatorLon) < 180 && Math.abs(mercatorLat) < 90)
            return null;
        if ((Math.abs(mercatorLon) > 20037508.3427892) || (Math.abs(mercatorLat) > 20037508.3427892))
            return null;
        var a = mercatorLon / 6378137.0 * 57.295779513082323;
        var x = a - (Math.floor(((a + 180.0) / 360.0)) * 360.0);
        var y = (1.5707963267948966 - (2.0 * Math.atan(Math.exp((-1.0 * mercatorLat) / 6378137.0)))) * 57.295779513082323;
        return {'lat' : y, 'lon' : x};
        //*/
    }
    // two point's distance
    public static double distance(double latA, double lonA, double latB, double lonB) {
        double earthR = 6371000.0;
        double x = Math.cos(latA * Math.PI / 180.0) * Math.cos(latB * Math.PI / 180.0) * Math.cos((lonA - lonB) * Math.PI / 180.0);
        double y = Math.sin(latA * Math.PI / 180.0) * Math.sin(latB * Math.PI / 180.0);
        double s = x + y;
        if (s > 1) s = 1;
        if (s < -1) s = -1;
        double alpha = Math.acos(s);
        double distance = alpha * earthR;
        return distance;
    }
    
    /**
	 * 十进制度转化为度分秒
	 * 
	 * @param str
	 * @return
	 */
	public final static double[] convertToSexagesimal(String str) {
		double[] dt = new double[3];
		dt[0] = Double.valueOf(str.substring(0, str.indexOf('.')));
		String str1 = str.substring(str.indexOf(".") + 1);
		str1 = "0." + str1;
		String str2 = String.valueOf(Double.valueOf(str1) * 60);
		dt[1] = Double.valueOf(str2.substring(0, str2.indexOf(".")));
		String str3 = str2.substring(str2.indexOf(".") + 1);
		str3 = "0." + str3;
		dt[2] = Double.valueOf(str3) * 60;
		return dt;
	}
	
	/**
	 * 十进制度转化为度分秒
	 * 
	 * @param str
	 * @return
	 */
	public final static String[] convertToDFM(String str) {
		String[] dt = new String[3];
		dt[0] = str.substring(0, str.indexOf('.'));
		String str1 = str.substring(str.indexOf(".") + 1);
		str1 = "0." + str1;
		String str2 = String.valueOf(Double.valueOf(str1) * 60);
		dt[1] = str2.substring(0, str2.indexOf("."));
		String str3 = str2.substring(str2.indexOf(".") + 1);
		str3 = "0." + str3;
		dt[2] = String.valueOf(Double.valueOf(str3) * 60);
		return dt;
	}

	/**
	 * 经纬度度分秒转换为小数
	 * 
	 * @param du
	 * @param fen
	 * @param miao
	 * @return
	 */
	public final static double convertToDecimal(double du, double fen, double miao) {
		if (du < 0)
			return -(Math.abs(du) + (Math.abs(fen) + (Math.abs(miao) / 60)) / 60);
		return Math.abs(du) + (Math.abs(fen) + (Math.abs(miao) / 60)) / 60;
	}
    
    public static void main(String[] args) {
    	double lon = 116.32715863448607;
        double lat = 39.990912172420714;
        double[] bd = wgs2bd(lat, lon);
        System.out.println("lat:" + bd[0] + ", lon:" + bd[1]);
        double[] wgs = bd2wgs_exact(bd[0], bd[1]);
        System.out.println("lat:" + wgs[0] + ", lon:" + wgs[1]);
        System.out.println("lat:" + lat + ", lon:" + lon);
        
        double[] wgs2 = bd2wgs_exact(39.997909857257, 116.33993631438);
        System.out.println("lat:" + wgs2[0] + ", lon:" + wgs2[1]);
        
        System.out.println("bd distance:" + distance(bd[0], bd[1], 39.997909857257, 116.33993631438));
        System.out.println("wgs distance:" + distance(lat, lon, wgs[0], wgs[1]));
        System.out.println("bd2wgs distance:" + distance(lat, lon, wgs2[0], wgs2[1]));
        
        //gcj to baidu 的转换与百度代码转换的差别较大
        //1原始wgs坐标      lat:39.990912172420714, lon:116.32715863448607
        //2原始百度坐标    lat:39.997909857257000, lon:116.33993631438000
        //3原始wgs转百度  lat:39.997903976430734, lon:116.33993249456921
        //4百度转回wgs   lat:39.99091297862144, lon:116.32715831970444
        //5原始百度转wgs lat:39.990918876030975, lon:116.32716212245498
        
        //2和3比较distance:0.7353656559791406
        //1和4比较distance:0.09493529796600342从wgs>baidu>wgs
        //1和5比较distance:0.8055527155887182
        
        
        String[] dfm = ConverterKit.convertToDFM("116.3456211");
		System.out.println(dfm[0]+"度" + dfm[1]+"分"+dfm[2]+"秒");
		double[] dfms = ConverterKit.convertToSexagesimal("116.3456211");
		double du = ConverterKit.convertToDecimal(dfms[0], dfms[1], dfms[2]);
		System.out.println(du+"度");
		System.out.println("116.3456211");
	}
}
