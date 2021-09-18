package com.mapfinal.converter.proj4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.osgeo.proj4j.*;
import org.osgeo.proj4j.datum.Datum;
import org.osgeo.proj4j.datum.Ellipsoid;
import org.osgeo.proj4j.io.Proj4FileReader;
import org.osgeo.proj4j.parser.DatumParameters;
import org.osgeo.proj4j.parser.Proj4Keyword;
import org.osgeo.proj4j.proj.Projection;
import org.osgeo.proj4j.proj.TransverseMercatorProjection;
import org.osgeo.proj4j.units.Angle;
import org.osgeo.proj4j.units.Unit;
import org.osgeo.proj4j.units.Units;
import org.osgeo.proj4j.util.ProjectionMath;

import com.mapfinal.converter.CRS;
import com.mapfinal.kit.StringKit;

public class Proj4WKTParser {

	private Registry registry;
	private Map<String, String> projs;
	private Map<String, String> datums;
	private Map<String, String> ellips;
	
	public static final Datum ch1903  = new Datum("ch1903", 674.374,15.056,405.346, Ellipsoid.BESSEL, "swiss");
	public static final Datum osni52  = new Datum("osni52", 482.530,-130.596,564.557,-1.042,-0.214,-0.631,8.15, Ellipsoid.AIRY, "Irish National");
	public static final Datum rassadiran  = new Datum("rassadiran", -133.63,-157.5,-158.62, Ellipsoid.INTL, "Rassadiran");
	public static final Datum s_jtsk  = new Datum("s_jtsk", 589,76,480, Ellipsoid.BESSEL, "S-JTSK (Ferro)");
	public static final Datum beduaram  = new Datum("beduaram", -106,-87,188, Ellipsoid.CLARKE_1880, "Beduaram");
	public static final Datum gunung_segara  = new Datum("gunung_segara", -403,684,41, Ellipsoid.BESSEL, "Gunung Segara Jakarta");
	public static final Datum rnb72  = new Datum("rnb72", 106.869,-52.2978,103.724,-0.33657,0.456955,-1.84218,1, Ellipsoid.INTL, "Reseau National Belge 1972");
	public static final Datum china_2000  = new Datum("china_2000", 0, 0, 0, Ellipsoid.GRS80, "china_2000");
	
	public static final Datum[] datumArrays = {
			ch1903,osni52,rassadiran,s_jtsk,beduaram,gunung_segara,rnb72
	};
	
	public Proj4WKTParser(Registry registry) {
		this.registry = registry;
		projs = new HashMap<>();
		projs.put("Mercator","merc");
		projs.put("longlat","longlat");
		projs.put("Transverse_Mercator","tmerc");
		projs.put("Extended_Transverse_Mercator","etmerc");
		projs.put("Universal Transverse Mercator System","utm");
		projs.put("gauss","gauss");
		projs.put("Stereographic_North_Pole","sterea");
		projs.put("stere","stere");
		projs.put("somerc","somerc");
		projs.put("Hotine_Oblique_Mercator","omerc");
		projs.put("Lambert Tangential Conformal Conic Projection","lcc");
		projs.put("Krovak","krovak");
		projs.put("Cassini","cass");
		projs.put("Lambert Azimuthal Equal Area","laea");
		projs.put("Albers_Conic_Equal_Area","aea");
		projs.put("gnom","gnom");
		projs.put("cea","cea");
		projs.put("Equirectangular","eqc");
		projs.put("Polyconic","poly");
		projs.put("New_Zealand_Map_Grid","nzmg");
		projs.put("Miller_Cylindrical","mill");
		projs.put("Sinusoidal","sinu");
		projs.put("Mollweide","moll");
		projs.put("Equidistant_Conic","eqdc");
		projs.put("Van_der_Grinten_I","vandg");
		projs.put("Azimuthal_Equidistant","aeqd");
		projs.put("ortho","ortho");
		projs.put("Quadrilateralized Spherical Cube","qsc");
		projs.put("Robinson","robin");
		projs.put("Geocentric","geocent");
		projs.put("Popular Visualisation Pseudo Mercator","merc");
		projs.put("identity","longlat");
		projs.put("Transverse Mercator","tmerc");
		projs.put("Extended Transverse Mercator","etmerc");
		projs.put("utm","utm");
		projs.put("Oblique_Stereographic","sterea");
		projs.put("Stereographic_South_Pole","stere");
		projs.put("Hotine Oblique Mercator","omerc");
		projs.put("Lambert_Conformal_Conic","lcc");
		projs.put("krovak","krovak");
		projs.put("Cassini_Soldner","cass");
		projs.put("Lambert_Azimuthal_Equal_Area","laea");
		projs.put("Albers","aea");
		projs.put("Equidistant_Cylindrical","eqc");
		projs.put("poly","poly");
		projs.put("nzmg","nzmg");
		projs.put("mill","mill");
		projs.put("sinu","sinu");
		projs.put("moll","moll");
		projs.put("eqdc","eqdc");
		projs.put("VanDerGrinten","vandg");
		projs.put("aeqd","aeqd");
		projs.put("Quadrilateralized_Spherical_Cube","qsc");
		projs.put("robin","robin");
		projs.put("geocentric","geocent");
		projs.put("Mercator_1SP","merc");
		projs.put("tmerc","tmerc");
		projs.put("etmerc","etmerc");
		projs.put("Polar_Stereographic","sterea");
		projs.put("Polar Stereographic (variant B)","stere");
		projs.put("Hotine_Oblique_Mercator_Azimuth_Natural_Origin","omerc");
		projs.put("Lambert_Conformal_Conic_2SP","lcc");
		projs.put("cass","cass");
		projs.put("laea","laea");
		projs.put("aea","aea");
		projs.put("eqc","eqc");
		projs.put("vandg","vandg");
		projs.put("qsc","qsc");
		projs.put("geocent","geocent");
		projs.put("geocent","geocent");
		projs.put("Mercator_Auxiliary_Sphere","merc");
		projs.put("sterea","sterea");
		projs.put("Hotine_Oblique_Mercator_Azimuth_Center","omerc");
		projs.put("lcc","lcc");
		projs.put("Geocent","geocent");
		projs.put("merc","merc");
		projs.put("Oblique Stereographic Alternative","sterea");
		projs.put("Double_Stereographic","sterea");
		projs.put("omerc","omerc");
		datums = new HashMap<>();
		datums.put("wgs84","WGS84");
		datums.put("swiss","ch1903");
		datums.put("Greek_Geodetic_Reference_System_1987","GGRS87");
		datums.put("North_American_Datum_1983","NAD83");
		datums.put("North_American_Datum_1927","NAD27");
		datums.put("Potsdam Rauenberg 1950 DHDN","potsdam");
		datums.put("Carthage 1934 Tunisia","carthage");
		datums.put("Hermannskogel","hermannskogel");
		datums.put("Irish National","osni52");
		datums.put("Ireland 1965","ire65");
		datums.put("Rassadiran","rassadiran");
		datums.put("New Zealand Geodetic Datum 1949","nzgd49");
		datums.put("Airy 1830","OSGB36");
		datums.put("S-JTSK (Ferro)","s_jtsk");
		datums.put("Beduaram","beduaram");
		datums.put("Gunung Segara Jakarta","gunung_segara");
		datums.put("Reseau National Belge 1972","rnb72");
		datums.put("china_2000","china_2000");
//		datums.put("wgs84","wgs84");
//		datums.put("ch1903","ch1903");
//		datums.put("ggrs87","ggrs87");
//		datums.put("nad83","nad83");
//		datums.put("nad27","nad27");
//		datums.put("potsdam","potsdam");
//		datums.put("carthage","carthage");
//		datums.put("hermannskogel","hermannskogel");
//		datums.put("osni52","osni52");
//		datums.put("ire65","ire65");
//		datums.put("rassadiran","rassadiran");
//		datums.put("nzgd49","nzgd49");
//		datums.put("osgb36","osgb36");
//		datums.put("s_jtsk","s_jtsk");
//		datums.put("beduaram","beduaram");
//		datums.put("gunung_segara","gunung_segara");
//		datums.put("rnb72","rnb72");
		ellips = new HashMap<>();
		ellips.put("MERIT 1983","MERIT");
		ellips.put("Soviet Geodetic System 85","SGS85");
		ellips.put("GRS 1980(IUGG, 1980)","GRS80");
		ellips.put("IAU 1976","IAU76");
		ellips.put("Airy 1830","airy");
		ellips.put("Appl. Physics. 1965","APL4");
		ellips.put("Naval Weapons Lab., 1965","NWL9D");
		ellips.put("Modified Airy","mod_airy");
		ellips.put("Andrae 1876 (Den., Iclnd.)","andrae");
		ellips.put("Australian Natl & S. Amer. 1969","aust_SA");
		ellips.put("GRS 67(IUGG 1967)","GRS67");
		ellips.put("Bessel 1841","bessel");
		ellips.put("Bessel 1841 (Namibia)","bess_nam");
		ellips.put("Clarke 1866","clrk66");
		ellips.put("Clarke 1880 mod.","clrk80");
		ellips.put("Clarke 1858","clrk58");
		ellips.put("Comm. des Poids et Mesures 1799","CPM");
		ellips.put("Delambre 1810 (Belgium)","delmbr");
		ellips.put("Engelis 1985","engelis");
		ellips.put("Everest 1830","evrst30");
		ellips.put("Everest 1948","evrst48");
		ellips.put("Everest 1956","evrst56");
		ellips.put("Everest 1969","evrst69");
		ellips.put("Everest (Sabah & Sarawak)","evrstSS");
		ellips.put("Fischer (Mercury Datum) 1960","fschr60");
		ellips.put("Fischer 1960","fschr60m");
		ellips.put("Fischer 1968","fschr68");
		ellips.put("Helmert 1906","helmert");
		ellips.put("Hough","hough");
		ellips.put("International 1909 (Hayford)","intl");
		ellips.put("Kaula 1961","kaula");
		ellips.put("Lerch 1979","lerch");
		ellips.put("Maupertius 1738","mprts");
		ellips.put("New International 1967","new_intl");
		ellips.put("Plessis 1817 (France)","plessis");
		ellips.put("Krassovsky, 1942","krass");
		ellips.put("Southeast Asia","SEasia");
		ellips.put("Walbeck","walbeck");
		ellips.put("WGS 60","WGS60");
		ellips.put("WGS 66","WGS66");
		ellips.put("WGS 72","WGS7");
		ellips.put("WGS 84","WGS84");
		ellips.put("Normal Sphere (r=6370997)","sphere");
		ellips.put("CGCS2000","GRS80");
	}
	
	public CRS parse(String name, String wkt) {
		if(StringKit.isBlank(wkt)) return null;
		WKTParser.ParamMap lisp = WKTParser.parseString(wkt);
		//lisp.print();
		if(lisp.getType().equals("proj4")) {
			return new CRS(StringKit.isBlank(name) ? lisp.get("name") : name, new String[] {wkt});
		} else if(lisp.getType().equals("epsg")) {
			String crsName = lisp.get("name");
			Proj4FileReader proj4FileReader = new Proj4FileReader();
			String[] param = proj4FileReader.getParameters(crsName);
			return new CRS(crsName, param);
		} else {
			String[] args = parseProj(lisp.getObjs());
			return new CRS(StringKit.isBlank(name) ? lisp.get("name") : name, args, CRS.PROJ4);
		}
	}

	public CoordinateReferenceSystem parse(String name, Map<String, Object> wkt) {
		if (wkt == null)
			return null;
		String[] args = parseProj(wkt);
//		System.out.println("-----------------------------");
//		for (String str : args) {
//			System.out.println(str);
//		}
//		System.out.println("-----------------------------");
		Map params = createParameterMap(args);
		Proj4Keyword.checkUnsupported(params.keySet());
		DatumParameters datumParam = new DatumParameters();
		parseDatum(params, datumParam);
		parseEllipsoid(params, datumParam);
		Datum datum = datumParam.getDatum();
		Ellipsoid ellipsoid = datum.getEllipsoid();
		// TODO: this makes a difference - why?
		// which is better?
		// Ellipsoid ellipsoid = datumParam.getEllipsoid();
		Projection proj = parseProjection(params, ellipsoid);
		return new CoordinateReferenceSystem(name, args, datum, proj);
	}

	private String[] parseProj(Map<String, Object> params) {
		String[] args = new String[37];
		int length = 0;
		String s = (String) params.get("projName");
		String ts = this.projs.get(s);
		if(ts==null) {
			for (Entry<String, String> kv : this.projs.entrySet()) {
				if(kv.getKey().equalsIgnoreCase(s)) {
					ts = kv.getValue();
					break;
				}
			}
		}
		s = ts!=null ? ts : s;
		args[length] = "+" + Proj4Keyword.proj + "=" + s;
		length++;
		String dc = (String) params.get("datumCode");
		String tc = this.datums.get(dc);
		if(tc==null) {
			for (Entry<String, String> kv : this.datums.entrySet()) {
				if(kv.getKey().equalsIgnoreCase(dc)) {
					tc = kv.getValue();
					break;
				}
			}
		}
		dc = tc!=null ? tc : dc;
		args[length] = "+" + Proj4Keyword.datum + "=" + dc;
		length++;
		if (params.containsKey("ellps")) {
			String e = (String) params.get("ellps");
			String te = this.ellips.get(e);
			if(te==null) {
				for (Entry<String, String> kv : this.ellips.entrySet()) {
					if(kv.getKey().equalsIgnoreCase(e)) {
						te = kv.getValue();
						break;
					} else if(kv.getKey().startsWith(e)) {// && !kv.getKey().startsWith(e+" ")
						te = kv.getValue();
						break;
					}
				}
			}
			e = te!=null ? te : e;
			args[length] = "+" + Proj4Keyword.ellps + "=" + e;
			length++;
		}
		if (params.containsKey("rf")) {
			args[length] = "+" + Proj4Keyword.rf + "=" + params.get("rf");
			length++;
		}
		if (params.containsKey("lat0")) {
			args[length] = "+" + Proj4Keyword.lat_0 + "=" + params.get("lat0");
			length++;
		}
		if (params.containsKey("lat1")) {
			args[length] = "+" + Proj4Keyword.lat_1 + "=" + params.get("lat1");
			length++;
		}
		if (params.containsKey("lat2")) {
			args[length] = "+" + Proj4Keyword.lat_2 + "=" + params.get("lat2");
			length++;
		}
		if (params.containsKey("lat_ts")) {
			args[length] = "+" + Proj4Keyword.lat_ts + "=" + params.get("lat_ts");
			length++;
		}
		if (params.containsKey("long0")) {
			args[length] = "+" + Proj4Keyword.lon_0 + "=" + params.get("long0");
			length++;
		}
		if (params.containsKey("longc")) {
			args[length] = "+" + Proj4Keyword.lonc + "=" + params.get("longc");
			length++;
		}
		if (params.containsKey("alpha")) {
			args[length] = "+" + Proj4Keyword.alpha + "=" + params.get("alpha");
			length++;
		}
		if (params.containsKey("x0")) {
			args[length] = "+" + Proj4Keyword.x_0 + "=" + params.get("x0");
			length++;
		}
		if (params.containsKey("y0")) {
			args[length] = "+" + Proj4Keyword.y_0 + "=" + params.get("y0");
			length++;
		}
		if (params.containsKey("k0")) {
			args[length] = "+" + Proj4Keyword.k + "=" + params.get("k0");
			length++;
		}
		if (params.containsKey("a")) {
			args[length] = "+" + Proj4Keyword.a + "=" + params.get("a");
			length++;
		}
		if (params.containsKey("b")) {
			args[length] = "+" + Proj4Keyword.b + "=" + params.get("b");
			length++;
		}
		if (params.containsKey("R_A")) {
			args[length] = "+" + Proj4Keyword.R_A + "=" + params.get("R_A");
			length++;
		}
		if (params.containsKey("zone")) {
			args[length] = "+" + Proj4Keyword.zone + "=" + params.get("zone");
			length++;
		}
		if (params.containsKey("south")) {
			args[length] = "+" + Proj4Keyword.south + "=" + params.get("utmSouth");
			length++;
		}
		if (params.containsKey("towgs84")) {
			args[length] = "+" + Proj4Keyword.towgs84 + "=" + params.get("towgs84").toString();
			length++;
		}
		if (params.containsKey("to_meter")) {
			args[length] = "+" + Proj4Keyword.to_meter + "=" + params.get("to_meter");
			length++;
		}
		if (params.containsKey("units")) {
			args[length] = "+" + Proj4Keyword.units + "=" + params.get("units");
			length++;
		}
		if (params.containsKey("from_greenwich")) {
			args[length] = "+" + Proj4Keyword.f + "=" + params.get("from_greenwich");
			// args[0] = "+" + Proj4Keyword.pm + "=" +
			// params.get("from_greenwich");
			length++;
		}
		return Arrays.copyOf(args, length);
	}

	/**
	 * Creates a {@link Projection} initialized from a PROJ.4 argument list.
	 */
	private Projection parseProjection(Map params, Ellipsoid ellipsoid) {
		Projection projection = null;

		String s;
		s = (String) params.get(Proj4Keyword.proj);
		if (s != null) {
			projection = registry.getProjection(s);
			if (projection == null)
				throw new InvalidValueException("Unknown projection: " + s);
		}

		projection.setEllipsoid(ellipsoid);

		// TODO: better error handling for things like bad number syntax.
		// Should be able to report the original param string in the error
		// message
		// Should the exception be lib-specific? (e.g. ParseException)

		s = (String) params.get(Proj4Keyword.alpha);
		if (s != null)
			projection.setAlphaDegrees(Double.parseDouble(s));

		s = (String) params.get(Proj4Keyword.lonc);
		if (s != null)
			projection.setLonCDegrees(Double.parseDouble(s));

		s = (String) params.get(Proj4Keyword.lat_0);
		if (s != null)
			projection.setProjectionLatitudeDegrees(parseAngle(s));

		s = (String) params.get(Proj4Keyword.lon_0);
		if (s != null)
			projection.setProjectionLongitudeDegrees(parseAngle(s));

		s = (String) params.get(Proj4Keyword.lat_1);
		if (s != null)
			projection.setProjectionLatitude1Degrees(parseAngle(s));

		s = (String) params.get(Proj4Keyword.lat_2);
		if (s != null)
			projection.setProjectionLatitude2Degrees(parseAngle(s));

		s = (String) params.get(Proj4Keyword.lat_ts);
		if (s != null)
			projection.setTrueScaleLatitudeDegrees(parseAngle(s));

		s = (String) params.get(Proj4Keyword.x_0);
		if (s != null)
			projection.setFalseEasting(Double.parseDouble(s));

		s = (String) params.get(Proj4Keyword.y_0);
		if (s != null)
			projection.setFalseNorthing(Double.parseDouble(s));

		s = (String) params.get(Proj4Keyword.k_0);
		if (s == null)
			s = (String) params.get(Proj4Keyword.k);
		if (s != null)
			projection.setScaleFactor(Double.parseDouble(s));

		s = (String) params.get(Proj4Keyword.units);
		if (s != null) {
			Unit unit = Units.findUnits(s);
			// TODO: report unknown units name as error
			if (unit != null) {
				projection.setFromMetres(1.0 / unit.value);
				projection.setUnits(unit);
			}
		}

		s = (String) params.get(Proj4Keyword.to_meter);
		if (s != null)
			projection.setFromMetres(1.0 / Double.parseDouble(s));

		if (params.containsKey(Proj4Keyword.south))
			projection.setSouthernHemisphere(true);

		// TODO: implement some of these parameters ?

		// this must be done last, since behaviour depends on other params being
		// set (eg +south)
		if (projection instanceof TransverseMercatorProjection) {
			s = (String) params.get(Proj4Keyword.zone);
			if (s != null)
				((TransverseMercatorProjection) projection).setUTMZone(Integer.parseInt(s));
		}

		projection.initialize();

		return projection;
	}

	private void parseDatum(Map params, DatumParameters datumParam) {
		String towgs84 = (String) params.get(Proj4Keyword.towgs84);
		if (towgs84 != null) {
			double[] datumConvParams = parseToWGS84(towgs84);
			datumParam.setDatumTransform(datumConvParams);
		}

		String code = (String) params.get(Proj4Keyword.datum);
		if (code != null) {
			Datum datum = registry.getDatum(code);
			if(datum==null) {
				for (int i = 0; i < datumArrays.length; i++) {
					if (datumArrays[i].getCode().equals(code)) {
						datum = datumArrays[i];
						break;
					}
				}
			}
//			if (datum == null)
//				throw new InvalidValueException("Unknown datum: " + code);
			//yangyong修改，某些wkt使用自定义datum，而proj4不识别的情况
			if(datum == null && !params.containsKey("ellps")) {
				throw new InvalidValueException("Unknown datum: " + code);
			}
			if(datum!=null) datumParam.setDatum(datum);
		}

	}

	private double[] parseToWGS84(String paramList) {
		String[] numStr = paramList.split(",");

		if (!(numStr.length == 3 || numStr.length == 7)) {
			throw new InvalidValueException("Invalid number of values (must be 3 or 7) in +towgs84: " + paramList);
		}
		double[] param = new double[numStr.length];
		for (int i = 0; i < numStr.length; i++) {
			// TODO: better error reporting
			param[i] = Double.parseDouble(numStr[i]);
		}
		if (param.length > 3) {
			// optimization to detect 3-parameter transform
			if (param[3] == 0.0 && param[4] == 0.0 && param[5] == 0.0 && param[6] == 0.0) {
				param = new double[] { param[0], param[1], param[2] };
			}
		}

		/**
		 * PROJ4 towgs84 7-parameter transform uses units of arc-seconds for the
		 * rotation factors, and parts-per-million for the scale factor. These
		 * need to be converted to radians and a scale factor.
		 */
		if (param.length > 3) {
			param[3] *= ProjectionMath.SECONDS_TO_RAD;
			param[4] *= ProjectionMath.SECONDS_TO_RAD;
			param[5] *= ProjectionMath.SECONDS_TO_RAD;
			param[6] = (param[6] / ProjectionMath.MILLION) + 1;
		}

		return param;
	}

	private void parseEllipsoid(Map params, DatumParameters datumParam) {
		double b = 0;
		String s;

		/*
		 * // not supported by PROJ4 s = (String) params.get(Proj4Param.R); if
		 * (s != null) a = Double.parseDouble(s);
		 */

		String code = (String) params.get(Proj4Keyword.ellps);
		if (code != null) {
			Ellipsoid ellipsoid = registry.getEllipsoid(code);
			if (ellipsoid == null)
				throw new InvalidValueException("Unknown ellipsoid: " + code);
			datumParam.setEllipsoid(ellipsoid);
		}

		/*
		 * Explicit parameters override ellps and datum settings
		 */
		s = (String) params.get(Proj4Keyword.a);
		if (s != null) {
			double a = Double.parseDouble(s);
			datumParam.setA(a);
		}

		s = (String) params.get(Proj4Keyword.es);
		if (s != null) {
			double es = Double.parseDouble(s);
			datumParam.setES(es);
		}

		s = (String) params.get(Proj4Keyword.rf);
		if (s != null) {
			double rf = Double.parseDouble(s);
			datumParam.setRF(rf);
		}

		s = (String) params.get(Proj4Keyword.f);
		if (s != null) {
			double f = Double.parseDouble(s);
			datumParam.setF(f);
		}

		s = (String) params.get(Proj4Keyword.b);
		if (s != null) {
			b = Double.parseDouble(s);
			datumParam.setB(b);
		}

		if (b == 0) {
			b = datumParam.getA() * Math.sqrt(1. - datumParam.getES());
		}

		parseEllipsoidModifiers(params, datumParam);

		/*
		 * // None of these appear to be supported by PROJ4 ??
		 * 
		 * s = (String) params.get(Proj4Param.R_A); if (s != null &&
		 * Boolean.getBoolean(s)) { a *= 1. - es * (SIXTH + es * (RA4 + es *
		 * RA6)); } else { s = (String) params.get(Proj4Param.R_V); if (s !=
		 * null && Boolean.getBoolean(s)) { a *= 1. - es * (SIXTH + es * (RV4 +
		 * es * RV6)); } else { s = (String) params.get(Proj4Param.R_a); if (s
		 * != null && Boolean.getBoolean(s)) { a = .5 * (a + b); } else { s =
		 * (String) params.get(Proj4Param.R_g); if (s != null &&
		 * Boolean.getBoolean(s)) { a = Math.sqrt(a * b); } else { s = (String)
		 * params.get(Proj4Param.R_h); if (s != null && Boolean.getBoolean(s)) {
		 * a = 2. * a * b / (a + b); es = 0.; } else { s = (String)
		 * params.get(Proj4Param.R_lat_a); if (s != null) { double tmp =
		 * Math.sin(parseAngle(s)); if (Math.abs(tmp) > MapMath.HALFPI) throw
		 * new ProjectionException("-11"); tmp = 1. - es * tmp * tmp; a *= .5 *
		 * (1. - es + tmp) / (tmp * Math.sqrt(tmp)); es = 0.; } else { s =
		 * (String) params.get(Proj4Param.R_lat_g); if (s != null) { double tmp
		 * = Math.sin(parseAngle(s)); if (Math.abs(tmp) > MapMath.HALFPI) throw
		 * new ProjectionException("-11"); tmp = 1. - es * tmp * tmp; a *=
		 * Math.sqrt(1. - es) / tmp; es = 0.; } } } } } } } }
		 */
	}

	/**
	 * Parse ellipsoid modifiers.
	 * 
	 * @param params
	 * @param datumParam
	 */
	private void parseEllipsoidModifiers(Map params, DatumParameters datumParam) {
		/**
		 * Modifiers are mutually exclusive, so when one is detected method
		 * returns
		 */
		if (params.containsKey(Proj4Keyword.R_A)) {
			datumParam.setR_A();
			return;
		}

	}

	private Map createParameterMap(String[] args) {
		Map params = new HashMap();
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			// strip leading "+" if any
			if (arg.startsWith("+")) {
				arg = arg.substring(1);
			}
			int index = arg.indexOf('=');
			if (index != -1) {
				// param of form pppp=vvvv
				String key = arg.substring(0, index);
				String value = arg.substring(index + 1);
				params.put(key, value);
			} else {
				// param of form ppppp
				// String key = arg.substring(1);
				params.put(arg, null);
			}
		}
		return params;
	}

	private static double parseAngle(String s) {
		return Angle.parse(s);
	}
}
