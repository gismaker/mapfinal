package com.mapfinal.converter.proj4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.mapfinal.kit.Prop;
import com.mapfinal.kit.StringKit;

public class WKTParser {

	private int NEUTRAL = 1;
	private int KEYWORD = 2;
	private int NUMBER = 3;
	private int QUOTED = 4;
	private int AFTERQUOTE = 5;
	private int ENDED = -1;
	private String whitespace = "\\s";
	private String latin = "[A-Za-z]";
	private String keyword = "[A-Za-z84]";
	private String endThings = "[,\\]]";
	private String digets = "[\\d\\.E\\-\\+]";
	private String text;
	private int level = 0;
	private int place = 0;
	private String root = null;
	private ParamObject stack = new ParamObject(0);
	private ParamObject currentObject = null;
	private int state = NEUTRAL;
	private String word = null;

	public WKTParser(String text) {
		// TODO Auto-generated constructor stub
		this.text = text.trim();
	}

	public static ParamMap parseString(String text) {
		text = text.replaceAll("[\\t\\n\\r]", "");
		text = text.replaceAll(",	", ",").replaceAll(",   ", ",").replaceAll(",  ", ",");
		WKTParser parser = new WKTParser(text);
		if (parser.testWKT(text)) {
			ParamMap out = parser.output();
			// test of spetial case, due to this being a very common and often malformed
			if (parser.checkMercator(out)) {
				out.setName("EPSG:3857");
				out.setType("epsg");
				return out;
			}
			Object maybeProjStr = parser.checkProjStr(out);
			if (maybeProjStr!=null) {
				//System.out.println("maybeProjStr: " + maybeProjStr.toString());
				out.setName(maybeProjStr.toString());
				out.setLevel(1);
				out.setType("proj4");
				return out;
			}
			String epsg = parser.checkEPSG(out);
			if(StringKit.isNotBlank(epsg)) {
				out.setName("EPSG:" + epsg);
				out.setType("epsg");
				return out;
			}
			return out;
		}
		if (parser.testProj(text)) {
			return parser.createProjParamMap(text);
		}
		//return parser.output();
		return null;
	}
	
	public ParamMap createProjParamMap(String text) {
		ParamMap pm = new ParamMap(1, text);
		pm.setType("proj4");
		return pm;
	}
	
	public boolean testProj(String code) {
		return code.startsWith("+") ? true : false;
	}
	
	private final String[] codeWords = {"PROJECTEDCRS", "PROJCRS", "GEOGCS", "GEOCCS", "PROJCS", "LOCAL_CS", "GEODCRS", "GEODETICCRS", "GEODETICDATUM", "ENGCRS", "ENGINEERINGCRS"};
	public boolean testWKT(String code) {
		for (String word : codeWords) {
			if(code.indexOf(word) > -1) {
				return true;
			}
		}
		return false;
	}
	private final String[] codes = {"3857", "900913", "3785", "102113"};
	public boolean checkMercator(ParamMap item) {
		Object auth = match(item, "authority");
		if (auth==null) {
			return false;
		}
		Object code = null;
		if(auth instanceof Map) {
			code = match((Map) auth, "epsg");
		} else if(auth instanceof ParamMap) {
			code = match((ParamMap) auth, "epsg");
		}
		if(code!=null) {
			for (String word : codes) {
				if(word.equals(code)) {
					return true;
				}
			}
		}
		return false;
	}
	public String checkEPSG(ParamMap item) {
		Object epsg = match(item, "epsg");
		if (epsg==null) {
			return null;
		}
		if(epsg instanceof String) {
			return epsg.toString();
		}
		return null;
	}
	public Object checkProjStr(ParamMap item) {
		Object ext = match(item, "extension");
		if (ext==null) {
			return ext;
		}
		if(ext instanceof Map) {
			return match((Map) ext, "proj4");
		} else if(ext instanceof ParamMap) {
			return match((ParamMap) ext, "proj4");
		}
		return null;
	}
	
	String ignoredChar = "[\\s_\\-\\/\\(\\)]/g";
	public Object match(ParamMap obj, String key) {
		if (obj.get(key)!=null) {
			return obj.get(key);
		}
		String lkey = key.toLowerCase().replace(ignoredChar, "");
		String processedKey;
		for (String testkey : obj.getObjs().keySet()) {
			processedKey = testkey.toLowerCase().replace(ignoredChar, "");
			if (processedKey.equalsIgnoreCase(lkey)) {
				return obj.get(testkey);
			}
		}
		for (String testkey : obj.getSubObjs().keySet()) {
			processedKey = testkey.toLowerCase().replace(ignoredChar, "");
			if (processedKey.equalsIgnoreCase(lkey)) {
				return obj.getSubObj(testkey);
			}
		}
		return null;
	}
	
	public Object match(Map<String, Object> obj, String key) {
		if (obj.get(key)!=null) {
			return obj.get(key);
		}
		String lkey = key.toLowerCase().replace(ignoredChar, "");
		String processedKey;
		for (String testkey : obj.keySet()) {
			processedKey = testkey.toLowerCase().replace(ignoredChar, "");
			if (processedKey == lkey) {
				return obj.get(testkey);
			}
		}
		return null;
	}

	

	private void pushCurrentObject(String obj) {
		currentObject.push(obj);
		//System.out.println("currentObject: " + currentObject.toString() + ", add: " + obj);
	}

	private void readCharicter() {
		while (place < text.length()) {
			int p = place;
			place++;
			String c = text.substring(p, place);
			if (state != QUOTED) {
				while (Pattern.matches(whitespace, c)) {
					if (place >= text.length()) {
						return;
					}
					place++;
					c = text.substring(p, place);
				}
			}
			c = c.trim();
			if (this.state == NEUTRAL) {
				this.neutral(c);
			} else if (this.state == KEYWORD) {
				this.keyword(c);
			} else if (this.state == QUOTED) {
				this.quoted(c);
			} else if (this.state == AFTERQUOTE) {
				this.afterquote(c);
			} else if (this.state == NUMBER) {
				this.number(c);
			} else if (this.state == ENDED) {
			}
		}
	}

	private void afterquote(String c) {
		if (c.equals("\"")) {
			this.word += "\"";
			this.state = QUOTED;
			return;
		}
		if (Pattern.matches(endThings, c)) {
			this.word = this.word.trim();
			this.afterItem(c);
			return;
		}
		//// throw new Error("havn\"t handled "" +c + "" in afterquote yet,
		//// index " + this.place);
	}

	private void afterItem(String c) {
		if (c.equals(",")) {
			if (this.word != null) {
				this.pushCurrentObject(this.word);
			}
			this.word = null;
			this.state = NEUTRAL;
			return;
		}
		if (c.equals("]")) {
			this.level--;
			if (this.word != null) {
				this.pushCurrentObject(this.word);
				this.word = null;
			}
			this.state = NEUTRAL;
			//this.currentObject = new ParamObject(this.level);
			//this.currentObject.push(this.stack.pop());
			if(level==0) {//if (this.currentObject != null) {
				//currentObject.print();
				this.stack.pushSub(this.currentObject.getLevel(), this.currentObject);
				this.state = ENDED;
			}

			return;
		}
	}

	private void number(String c) {
		if (Pattern.matches(digets, c)) {
			this.word += c;
			return;
		}
		if (Pattern.matches(endThings, c)) {
			// this.word = parseFloat(this.word);
			this.afterItem(c);
			return;
		}
		// throw new Error("havn\"t handled "" +c + "" in number yet, index " +
		// this.place);
	}

	private void quoted(String c) {
		if (c.equals("\"")) {
			this.state = AFTERQUOTE;
			return;
		}
		this.word += c;
		return;
	}

	private void keyword(String c) {
		if (Pattern.matches(keyword, c)) {
			this.word += c;
			return;
		}
		if (c.equals("[")) {
			String newObjects = this.word;
			//System.out.println("newObjects: " + newObjects);
			this.level++;
			if (this.root == null) {
				this.root = newObjects;//GEOGCS or PROJCS
			} else {
				this.pushCurrentObject(newObjects);
			}
			if(currentObject!=null) {
				currentObject.pop();
				//currentObject.print();
				this.stack.pushSub(this.currentObject.getLevel(), this.currentObject);
			}
			currentObject=null;
			currentObject = new ParamObject(this.level-1);
			this.pushCurrentObject(newObjects);
			this.state = NEUTRAL;
			return;
		}
		if (Pattern.matches(endThings, c)) {
			this.afterItem(c);
			return;
		}
		// throw new Error("havn\"t handled "" +c + "" in keyword yet, index " +
		// this.place);
	}

	private void neutral(String c) {
		if (Pattern.matches(latin, c)) {
			this.word = c;
			this.state = KEYWORD;
			return;
		}
		if (c.equals("\"")) {
			this.word = "";
			this.state = QUOTED;
			return;
		}
		if (Pattern.matches(digets, c)) {
			this.word = c;
			this.state = NUMBER;
			return;
		}
		if (Pattern.matches(endThings, c)) {
			this.afterItem(c);
			return;
		}
		// throw new Error("havn\"t handled "" +c + "" in neutral yet, index " +
		// this.place);
	}

	public ParamMap output() {
		//System.out.println(text);
		System.out.println("--------start-----------");
		while (this.place < this.text.length()) {
			this.readCharicter();
		}
		if (this.state == ENDED) {
			//currentObject.print();
			//System.out.println("--------current-----------");
			//stack.print();
			System.out.println("--------stack-----------");
			ParamMap pm = obj2Map(stack);
			//pm.print();
			//System.out.println("---------param----------");
			cleanWKT(pm);
			//pm.print();
			return pm;
		} else {
			return null;
		}

		// throw new Error("unable to parse string "" +this.text + "". State is
		// " + this.state);
	}
	
	class ParamObject {
		private List<String> objs = new ArrayList<>();
		private List<ParamObject> subObjs;
		/**
		 * 层级
		 */
		private int level = 0;
		
		public ParamObject(int level) {
			// TODO Auto-generated constructor stub
			this.level = level;
		}
		
		public ParamObject(ParamObject pobj) {
			// TODO Auto-generated constructor stub
			copy(pobj);
		}
		
		public String pop() {
			// TODO Auto-generated method stub
			if(objs.size() < 1) return null;
			String p = new String(objs.get(objs.size()-1));
			objs.remove(objs.size()-1);
			return p;
		}

		public void push(String obj) {
			objs.add(obj);
		}
		
		public void push(int i, String pobj) {
			if(level > i) return;
			if(level == i) {
				push(pobj);
			} else {
				if(subObjs==null) {
					subObjs = new ArrayList<>();
				}
				if(subObjs.size() < 1) {
					ParamObject obj = new ParamObject(this.level + 1);
					obj.push(i, pobj);
				} else {
					ParamObject obj = subObjs.get(subObjs.size()-1);
					obj.push(i, pobj);
				}
			}
		}
		
		public void pushSub(ParamObject pobj) {
			if(subObjs==null) {
				subObjs = new ArrayList<>();
			}
			subObjs.add(pobj);
		}
		
		public void copy(ParamObject pobj) {
			this.level = pobj.getLevel();
			for (String str : pobj.getObjs()) {
				push(str);
			}
			if(pobj.getSubObjs()==null) return;
			for (ParamObject obj : pobj.getSubObjs()) {
				pushSub(obj);
			}
		}
		
		public void pushSub(int i, ParamObject pobj) {
			if(level==0) i = i > 0 ? i-1 : i;//加入到父级ParamObject中
			//System.out.println("level: " + level + ", i: " + i);
			if(level > i) return;
			if(level == i) {
				if(objs.size()<1) {
					copy(pobj);
				} else {
					pushSub(pobj);
				}
			} else {
				if(subObjs==null) {
					subObjs = new ArrayList<>();
				}
				if(subObjs.size() < 1) {
					ParamObject obj = new ParamObject(this.level + 1);
					subObjs.add(obj);
					obj.pushSub(i, pobj);
				} else {
					ParamObject obj = subObjs.get(subObjs.size()-1);
					obj.pushSub(i, pobj);
				}
			}
		}
		
		public void print() {
			for(int i=0; i<level; i++) {
				System.out.print("-");
			}
			System.out.print("[");
			for (String str : objs) {
				System.out.print(str);
				System.out.print(",");
			}
			System.out.print("]");
			System.out.println();
			if(subObjs==null) return;
			for (ParamObject pobj : subObjs) {
				pobj.print();
			}
		}

		public List<String> getObjs() {
			return objs;
		}

		public void setObjs(List<String> objs) {
			this.objs = objs;
		}

		public List<ParamObject> getSubObjs() {
			return subObjs;
		}

		public void setSubObjs(List<ParamObject> subObjs) {
			this.subObjs = subObjs;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}
		
		public String arrayShift() {
			String a =  objs.get(0);
			objs.remove(0);
			return a;
		}
		
		public int length() {
			int s = objs.size();
			s += subObjs!=null ? subObjs.size() : 0;
			return s;
		}
		
		public boolean isArray(int index) {
			if(index < objs.size()) return false;
			return true;
		}
		
		public String getStr(int i) {
			return objs.get(i);
		}
		
		public ParamObject getObject(int i) {
			return subObjs.get(i);
		}
	}
	
	public ParamMap obj2Map(ParamObject pobj) {
		if(pobj.getLevel()!=0) return null;
		ParamMap pm = new ParamMap(0, pobj.getStr(0));
		pm.setLevel(pobj.getLevel());
		
		//PROJCS or GEOGCS
		pm.put("type", pobj.getStr(0));
		
		String s0 = pobj.getStr(1);
		int dhxs0 = s0.indexOf("=");
		if(dhxs0 > 0) {
			s0 = s0.substring(dhxs0 + 1).trim();
		}
		pm.put("name", s0);
		if(pobj.getStr(0).equalsIgnoreCase("PROJCS")) {
			pm.put("projcs", s0);
			Prop prop = new Prop("epsg.txt");
			String code = prop.get(s0);
			if(StringKit.isNotBlank(code)) pm.put("epsg", code);
		}
		
		for (ParamObject sub : pobj.getSubObjs()) {
			//String key = sub.getStr(0);
			//ParamMap npm = new ParamMap(sub.getLevel(), key);
			//pm.put(key, npm);
			sExpr(sub, pm);
		}
		return pm;
	}
	
	public void sExpr(ParamObject po, ParamMap pm) {
		String key = po.arrayShift();
		if ("PARAMETER".equals(key)) {
			key = po.arrayShift();
		}
		if (po.length() == 1) {
			if (po.isArray(0)) {
				ParamMap npm = pm.newSub(po.getLevel(), key);
				sExpr(po.getObject(0), npm);
				return;
			}
			String s0 = po.getStr(0);
			int dhxs0 = s0.indexOf("=");
			if(dhxs0 > 0) {
				s0 = s0.substring(dhxs0 + 1).trim();
			}
			pm.put(key, s0);
			return;
		}
//		if (!v.length) {
//			obj[key] = true;
//			return;
//		}
		if ("TOWGS84".equals(key)) {
//			obj[key] = v;
			pm.putPo(key, po);
			return;
		}
//		if (!Array.isArray(key)) {
//			obj[key] = {};
//		}
		
		ParamMap npm = pm.newSub(po.getLevel(), key);

		switch (key) {
		case "UNIT":
		case "PRIMEM":
		case "VERT_DATUM":
			npm.put("name", po.getStr(0));
			npm.put("convert", po.getStr(1));
			if (po.length() == 3) {
				sExpr(po.getObject(0), npm);
			}
			return;
		case "SPHEROID":
		case "ELLIPSOID":
			String t0 = po.getStr(0);
			int dhxt0 = t0.indexOf("=");
			if(dhxt0 > 0) {
				t0 = t0.substring(dhxt0 + 1).trim();
			}
			t0 = t0.trim();
			npm.put("name", t0);
			npm.put("a", po.getStr(1));
			npm.put("rf", po.getStr(2));
			if (po.length() == 4) {
				sExpr(po.getObject(0), npm);
			}
			return;
		case "PROJECTEDCRS":
		case "PROJCRS":
		case "GEOGCS":
		case "GEOCCS":
		case "PROJCS":
			//v[0] = ["name", v[0]];
//			String prj0 = po.getStr(0);
//			int dhx = prj0.indexOf("=");
//			if(dhx > 0) {
//				prj0 = prj0.substring(dhx + 1);
//			}
//			prj0 = prj0.trim();
//			npm.put("PROJCS", prj0);
//			break;
		case "LOCAL_CS":
		case "GEODCRS":
		case "GEODETICCRS":
		case "GEODETICDATUM":
		case "EDATUM":
		case "ENGINEERINGDATUM":
		case "VERT_CS":
		case "VERTCRS":
		case "VERTICALCRS":
		case "COMPD_CS":
		case "COMPOUNDCRS":
		case "ENGINEERINGCRS":
		case "ENGCRS":
		case "FITTED_CS":
		case "LOCAL_DATUM":
		case "DATUM":
			//v[0] = ["name", v[0]];
			String datum0 = po.getStr(0);
			int dhxd = datum0.indexOf("=");
			if(dhxd > 0) {
				datum0 = datum0.substring(dhxd + 1);
			}
			datum0 = datum0.trim();
			npm.put("name", datum0);
			//mapit(pm, key, po);
//			if(po.getSubObjs()!=null) {
//				for (ParamObject subobj : po.getSubObjs()) {
//					sExpr(subobj, npm);
//				}	
//			}
//			return;
			break;
		default:
			String s = "";
			for (int i = 1; i< po.getObjs().size(); i++) {
				s += po.getStr(i);
			}
			String s0 = po.getStr(0);
			int dhxs0 = s0.indexOf("=");
			if(dhxs0 > 0) {
				s0 = s0.substring(dhxs0 + 1).trim();
			}
			npm.put(s0, s);
			//mapit(pm, key, po);
		}
		if(po.getSubObjs()!=null) {
			for (ParamObject subobj : po.getSubObjs()) {
				sExpr(subobj, npm);
			}	
		}
	}
	
	public class ParamMap {
		private Map<String, Object> objs = new HashMap<String, Object>();
		private Map<String, ParamMap> subObjs;
		private int level;
		private String name;
		private String type = "wkt";
		
		public ParamMap(int level, String name) {
			this.level = level;
			this.setName(name);
		}
		
		private ParamMap newSub(int ilevel, String key) {
			ParamMap pm = new ParamMap(ilevel, key);
			if(subObjs==null) subObjs = new  HashMap<String, ParamMap>();
			subObjs.put(key, pm);
			return pm;
		}
		
		public void put(String key, Object value) {
			objs.put(key, value);
		}
		
		public void putPo(String key, ParamObject po) {
			objs.put(key, po.getObjs());
		}
		
		public void put(String key, ParamMap pm) {
			if(subObjs==null) subObjs = new  HashMap<String, ParamMap>();
			subObjs.put(key, pm);
		}
		
		public Map<String, Object> getObjs() {
			return objs;
		}

		public void setObjs(Map<String, Object> objs) {
			this.objs = objs;
		}

		public Map<String, ParamMap> getSubObjs() {
			return subObjs;
		}

		public void setSubObjs(Map<String, ParamMap> subObjs) {
			this.subObjs = subObjs;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}
		
		public void print() {
			System.out.print("[");
			if(level == 0) System.out.println();
			for (Entry<String, Object> obj : objs.entrySet()) {
				System.out.print("{" + obj.getKey() + ": ");
				if(obj.getValue() instanceof List) {
					List l = (List) obj;
					System.out.print("[");
					for (Object o : l) {
						System.out.print(o.toString());
						System.out.print(",");
					}
					System.out.print("]");
				} else {
					System.out.print(obj.getValue().toString());
				}
				if(level > 0) {
					System.out.print("},");
				} else {
					System.out.println("}");
				}
			}
			if(level > 0) System.out.println();
			if(subObjs!=null) {
				for (Entry<String, ParamMap> pobj : subObjs.entrySet()) {
					for(int i=0; i<pobj.getValue().getLevel(); i++) {
						System.out.print("-");
					}
					System.out.print(pobj.getKey() + ": ");
					pobj.getValue().print();
					//System.out.print("]");
					//System.out.println();
				}
			}
			for(int i=0; i<getLevel(); i++) {
				System.out.print("-");
			}
			//System.out.print("]//" + this.name + "-" + this.level);
			System.out.print("]");
			System.out.println();
		}
		
		public Object getObj(String key) {
			return objs.get(key);
		}
		public String get(String key) {
			Object obj = objs.get(key);
			if(obj instanceof String) {
				return (String) obj;
			} else {
				return null;
			}
		}
		
		public ParamMap getSubObj(String key) {
			return subObjs==null ? null : subObjs.get(key);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
	
	public void cleanWKT(ParamMap wkt) {
		String type = wkt.get("type");
		if ("GEOGCS".equals(type)) {
			wkt.put("projName", "longlat");
		} else if ("LOCAL_CS".equals(type)) {
			wkt.put("projName", "identity");
			wkt.put("local", true);
		} else {
			ParamMap prj = wkt.getSubObj("PROJECTION");
			if(prj!=null) {
//				if(prj.getObj("name") instanceof String) {
//					wkt.put("projName", wkt.get("PROJECTION"));
//				} else {
//					List ol = (List) wkt.getObj("PROJECTION");
//					wkt.put("projName", ol.get(0));
//				}
				wkt.put("projName", prj.get("name"));
			} else {
				wkt.put("projName", wkt.get("PROJECTION"));
			}
		}
		if (wkt.getSubObj("UNIT")!=null) {
			ParamMap unit = wkt.getSubObj("UNIT");
			String units = unit.get("name").toLowerCase();
			wkt.put("units", units);
			if ("metre".equals(units)) {
				wkt.put("units", "meter");
			}
			String convert = unit.get("convert");
			if (convert!=null) {
				if ("GEOGCS".equals(type)) {
					if (wkt.getSubObj("DATUM")!=null && wkt.getSubObj("DATUM").getSubObj("SPHEROID")!=null) {
						String a = wkt.getSubObj("DATUM").getSubObj("SPHEROID").get("a");
						wkt.put("to_meter", Double.valueOf(convert) * Double.valueOf(a));
					}
				} else {
					wkt.put("to_meter", Double.valueOf(convert));
				}
			}
		}
		ParamMap geogcs = wkt.getSubObj("GEOGCS");
		if ("GEOGCS".equals(type)) {
			geogcs = wkt;
		}
		if (geogcs!=null) {
			//if(wkt.GEOGCS.PRIMEM&&wkt.GEOGCS.PRIMEM.convert){
			//  wkt.from_greenwich=wkt.GEOGCS.PRIMEM.convert*D2R;
			//}
			ParamMap DATUM = geogcs.getSubObj("DATUM");
			if (DATUM!=null) {
				wkt.put("datumCode", DATUM.get("name").toLowerCase());
			} else {
				wkt.put("datumCode", geogcs.get("name").toLowerCase());
			}
			if ("d_".equals(wkt.get("datumCode").substring(0, 2))) {
				wkt.put("datumCode", wkt.get("datumCode").substring(2));
			}
			if ("new_zealand_geodetic_datum_1949".equals(wkt.get("datumCode")) 
					|| "new_zealand_1949".equals(wkt.get("datumCode"))) {
				wkt.put("datumCode", "nzgd49");
			}
			if ("wgs_1984".equals(wkt.get("datumCode"))) {
				if ("Mercator_Auxiliary_Sphere".equals(wkt.get("PROJECTION"))) {
					wkt.put("sphere", true);
				}
				wkt.put("datumCode", "wgs84");
			}
			String datumCode = wkt.get("datumCode");
			int dclens = datumCode.length();
			if (dclens > 5 && "_ferro".equals(datumCode.substring(dclens-6, dclens))) {//最后6个字符
				wkt.put("datumCode", datumCode.substring(dclens-6));
			}
			if (dclens > 7 && "_jakarta".equals(datumCode.substring(dclens-8, dclens))) {
				wkt.put("datumCode", datumCode.substring(dclens-8));
			}
			if (datumCode.indexOf("belge") > -1) {
				wkt.put("datumCode", "rnb72");
			}
			if (DATUM!=null && DATUM.getSubObj("SPHEROID")!=null) {
				String ellps = DATUM.getSubObj("SPHEROID").get("name").replace("_19", "").replace("[Cc]larke\\_18", "clrk");
				wkt.put("ellps", ellps);
				String el = ellps.toLowerCase();
				if (el.length() > 12 && "international".equals(el.substring(0, 13))) {
					wkt.put("ellps", "intl");
				}
				wkt.put("a", Double.valueOf(DATUM.getSubObj("SPHEROID").get("a")));
				wkt.put("rf", Double.valueOf(DATUM.getSubObj("SPHEROID").get("rf")));//保留10位mn 
			}

			if (DATUM!=null && DATUM.getObj("TOWGS84")!=null) {
				wkt.put("datum_params", DATUM.getObj("TOWGS84"));
			}
			if (datumCode.indexOf("osgb_1936") > -1) {
				wkt.put("datumCode", "osgb36");
			}
			if (datumCode.indexOf("osni_1952") > -1) {
				wkt.put("datumCode", "osni52");
			}
			if (datumCode.indexOf("tm65") > -1
				 || datumCode.indexOf("geodetic_datum_of_1965") > -1) {
				wkt.put("datumCode", "ire65");
			}
			if ("ch1903+".equals(datumCode)) {
				wkt.put("datumCode", "ch1903");
			}
			if (datumCode.indexOf("israel") > -1) {
				wkt.put("datumCode", "isr93");
			}
		}
		
		Object b = wkt.getObj("b");
		if (b!=null && Double.valueOf(b.toString())!=Double.MAX_VALUE) {
			wkt.put("b", wkt.getObj("a"));
		}
		String[][] list = {
		    		{"standard_parallel_1", "Standard_Parallel_1"},
		    		{"standard_parallel_2", "Standard_Parallel_2"},
		    		{"false_easting", "False_Easting"},
		    		{"false_northing", "False_Northing"},
		    		{"central_meridian", "Central_Meridian"},
		    		{"latitude_of_origin", "Latitude_Of_Origin"},
		    		{"latitude_of_origin", "Central_Parallel"},
		    		{"scale_factor", "Scale_Factor"},
		    		{"k0", "scale_factor"},
		    		{"latitude_of_center", "Latitude_Of_Center"},
		    		{"latitude_of_center", "Latitude_of_center"},
		    		{"lat0", "latitude_of_center", "d2r"},
		    		{"longitude_of_center", "Longitude_Of_Center"},
		    		{"longitude_of_center", "Longitude_of_center"},
		    		{"longc", "longitude_of_center", "d2r"},
		    		{"x0", "false_easting", "toMeter"},
		    		{"y0", "false_northing", "toMeter"},
		    		{"long0", "central_meridian", "d2r"},
		    		{"lat0", "latitude_of_origin", "d2r"},
		    		{"lat0", "standard_parallel_1", "d2r"},
		    		{"lat1", "standard_parallel_1", "d2r"},
		    		{"lat2", "standard_parallel_2", "d2r"},
		    		{"azimuth", "Azimuth"},
		    		{"alpha", "azimuth", "d2r"},
		    		{"srsCode", "name"}
		    	};
		
		for (int i = 0; i < list.length; i++) {
			rename(wkt, list[i]);
		}
		String projName = wkt.get("projName");
		if (wkt.getObj("long0")==null && wkt.getObj("longc")!=null && ("Albers_Conic_Equal_Area".equals(projName) || "Lambert_Azimuthal_Equal_Area".equals(projName))) {
			wkt.put("long0", wkt.getObj("longc"));
		}
		if (wkt.getObj("lat_ts")==null && wkt.getObj("lat1")!=null && ("Stereographic_South_Pole".equals(projName) || "Polar Stereographic (variant B)".equals(projName))) {
			double lat1 = Double.valueOf(wkt.getObj("lat1").toString());
			wkt.put("lat0", d2r(lat1 > 0 ? 90 : -90));
			wkt.put("lat_ts", wkt.getObj("lat1"));
		}
//		if(wkt.getObj("central_meridian")==null && wkt.getObj("Central_Meridian")!=null) {
//			
//		}
	}
	
	double D2R$1 = 0.01745329251994329577;
	public void rename(ParamMap obj, String[] params) {
		String outName = params[0];
		String inName = params[1];
		if (!obj.getObjs().containsKey(outName) && obj.getObjs().containsKey(inName)) {
			obj.put(outName, obj.getObj(inName));
			if (params.length == 3) {
				String mn = params[2];
				if("d2r".equals(mn)) {
					obj.put(outName, Double.valueOf(obj.getObj(outName).toString()));
					//obj.put(outName, d2r(Double.valueOf(obj.getObj(outName).toString())));
				} else if("toMeter".equals(mn)) {
					obj.put(outName, Double.valueOf(obj.getObj(outName).toString()));
					//obj.put(outName, toMeter(obj, Double.valueOf(obj.getObj(outName).toString())));
				}
			}
		}
	}

	public double d2r(double input) {
		return input * D2R$1;
	}
	
	public double toMeter(ParamMap wkt, double input) {
		Object to_meter = wkt.getObj("to_meter");
		double ratio = to_meter == null ? 1 : Double.valueOf(to_meter.toString());
		return input * ratio;
	}
}
