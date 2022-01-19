package com.mapfinal.processor.simplify;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.locationtech.jts.geom.CoordinateSequence;

import com.mapfinal.event.Event;

/**
 * 对密集的点抽稀，保持点的均匀分布。
 * <br>适用于点集合
 * <br>https://blog.csdn.net/cdl2008sky/article/details/8281316
 * 
 * @author yangyong
 *
 */
public class UniformDistributionSimplifier implements GeoSimplifier {

	/**
	 * 6378137赤道半径，一度对应赤道上的一米
	 */
	private double degToMeter = Math.PI * 6378137 / 180.0;
	/**
	 * 1公里对应多少度
	 */
	private int tolerance = (int) (1000 * 1.0e7 / degToMeter);

	@Override
	public List<Integer> excute(Event event, CoordinateSequence points, Double tolerance) {
		// TODO Auto-generated method stub
		List<Integer> result = null;
		if(event.getCallback()==null) {
			result = new ArrayList<Integer>();
		}
		if (points == null)
			return result;
		if(tolerance!=null) {
			this.tolerance = tolerance.intValue();
		}
//		if(params!=null && params.get("tolerance")!=null) {
//			tolerance = (int) params.get("tolerance");
//		}
		List<Pos> spector = new LinkedList<Pos>();
		for (int i = 0; i < points.size(); i++) {
			double cx = points.getX(i);
			double cy = points.getY(i);
			int x = (int) (cx * 1e7);
			int y = (int) (cy * 1e7);
			Pos cur = new Pos(x, y);
			cur.setBuffer(this.tolerance);
			if (spector.contains(cur)) {
				// 删除点
				if(event.getCallback()!=null){
					event.getCallback().execute(new Event("delete", "index", i));
				} else {
					if(result!=null) result.add(i);
				}
			} else {
				spector.add(cur);
				// 保留点
				if(event.getCallback()!=null){
					event.getCallback().execute(new Event("save", "index", i));
				} else {
					if(result!=null) result.add(i);
				}
			}
		}
		return result;
	}

	class Pos {
		public int x;
		public int y;

		private int buf;

		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void setBuffer(int buf) {
			this.buf = buf;
		}

		public boolean equals(Object pt) {
			if (pt instanceof Pos)
				return (Math.abs(this.x - ((Pos) pt).x) <= buf && Math.abs(this.y - ((Pos) pt).y) <= buf);
			return false;
		}

		public int hashCode() {
			return Integer.valueOf(x + "" + y);
		}

	}
}
