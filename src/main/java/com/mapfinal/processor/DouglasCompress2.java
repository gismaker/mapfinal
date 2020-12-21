package com.mapfinal.processor;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;

import com.mapfinal.event.Event;

/**
 * 道格拉斯-普克（Douglas-Peuker)抽稀算法
 * <br>https://my.oschina.net/boonya/blog/3038997
 * <br>https://blog.csdn.net/n009ww/article/details/90669282
 * @author yangyong
 *
 */
public class DouglasCompress2 implements GeoCompress {

	@Override
	public List<Integer> excute(Event event, CoordinateSequence points, Double tolerance) {
		// TODO Auto-generated method stub
		//允许最大误差
		tolerance = tolerance == null ? 1 : tolerance;
		/*
		String type = event.get("type", "polyline");
		Coordinate s = points.getCoordinate(0), e;
		int end = points.size();
		int index = 0;
		if (!"polyline".equals(type)) {
			double maxDis = 0;
			for (int i = 1; i < end - 1; i++) {
				double h = distance(points.getCoordinate(i), s);
				if (h > maxDis) {
					maxDis = h;
					index = i;
				}
			}
			e = points.getCoordinate(index);
		} else {
			e = points.getCoordinate(end - 1);
		}
		// 找到最大阈值点，即操作（1）
		double maxH = 0;
		index = 0;
		*/
		// 找到最大阈值点，即操作（1）
		double maxH = 0;
		int end = points.size();
		int index = 0;
		Coordinate s = points.getCoordinate(0), e = points.getCoordinate(end - 1);
		for (int i = 1; i < end - 1; i++) {
			double h = H(points.getCoordinate(i), s, e);
			if (h > maxH) {
				maxH = h;
				index = i;
			}
		}

		// 如果存在最大阈值点，就进行递归遍历出所有最大阈值点
		List<Integer> result = new ArrayList<>();
		if (maxH > tolerance) {
			List<Integer> leftPoints = new ArrayList<>();// 左曲线
			List<Integer> rightPoints = new ArrayList<>();// 右曲线
			// 分别提取出左曲线和右曲线的坐标点
			for (int i = 0; i < end; i++) {
				if (i <= index) {
					leftPoints.add(i);
					if (i == index)
						rightPoints.add(i);
				} else {
					rightPoints.add(i);
				}
			}

			// 分别保存两边遍历的结果
			List<Integer> leftResult = new ArrayList<>();
			List<Integer> rightResult = new ArrayList<>();
			leftResult = DouglasPeucker(points, leftPoints, tolerance);
			rightResult = DouglasPeucker(points, rightPoints, tolerance);

			// 将两边的结果整合
			rightResult.remove(0);// 移除重复点
			leftResult.addAll(rightResult);
			result = leftResult;
		} else {// 如果不存在最大阈值点则返回当前遍历的子曲线的起始点
			result.add(0);
			result.add(end - 1);
		}
		return result;
	}

	public List<Integer> DouglasPeucker(CoordinateSequence cs, List<Integer> points, Double tolerance) {
		// TODO Auto-generated method stub
		// 找到最大阈值点，即操作（1）
		double maxH = 0;
		int end = points.size();
		int index = 0;
		Coordinate s = cs.getCoordinate(points.get(0)), e = cs.getCoordinate(points.get(end - 1));
		for (int i = 1; i < end - 1; i++) {
			double h = H(cs.getCoordinate(points.get(i)), s, e);
			if (h > maxH) {
				maxH = h;
				index = i;
			}
		}

		// 如果存在最大阈值点，就进行递归遍历出所有最大阈值点
		List<Integer> result = new ArrayList<>();
		if (maxH > tolerance) {
			List<Integer> leftPoints = new ArrayList<>();// 左曲线
			List<Integer> rightPoints = new ArrayList<>();// 右曲线
			// 分别提取出左曲线和右曲线的坐标点
			for (int i = 0; i < end; i++) {
				if (i <= index) {
					leftPoints.add(points.get(i));
					if (i == index)
						rightPoints.add(points.get(i));
				} else {
					rightPoints.add(points.get(i));
				}
			}

			// 分别保存两边遍历的结果
			List<Integer> leftResult = new ArrayList<>();
			List<Integer> rightResult = new ArrayList<>();
			leftResult = DouglasPeucker(cs, leftPoints, tolerance);
			rightResult = DouglasPeucker(cs, rightPoints, tolerance);

			// 将两边的结果整合
			rightResult.remove(0);// 移除重复点
			leftResult.addAll(rightResult);
			result = leftResult;
		} else {// 如果不存在最大阈值点则返回当前遍历的子曲线的起始点
			result.add(points.get(0));
			result.add(points.get(end - 1));
		}
		return result;
	}

	/**
	 * 计算点到直线的距离
	 * 
	 * @param p
	 * @param s
	 * @param e
	 * @return
	 */
	public double H(Coordinate p, Coordinate s, Coordinate e) {
		double AB = distance(s, e);
		double CB = distance(p, s);
		double CA = distance(p, e);

		double S = helen(CB, CA, AB);
		double H = 2.0f * S / AB;

		return H;
	}

	/**
	 * 计算两点之间的距离
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public double distance(Coordinate p1, Coordinate p2) {
		double lng1 = p1.x;
		double lat1 = p1.y;

		double lng2 = p2.x;
		double lat2 = p2.y;

		double radLat1 = lat1 * Math.PI / 180.0;
        double radLat2 = lat2 * Math.PI / 180.0;
        double a = radLat1 - radLat2;
        double b = (lng1 * Math.PI / 180.0) - (lng2 * Math.PI / 180.0);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        return s * 6370996.81;
	}

	/**
	 * 海伦公式，已知三边求三角形面积
	 * 
	 * @param cB
	 * @param cA
	 * @param aB
	 * @return 面积
	 */
	public double helen(double CB, double CA, double AB) {
		double p = (CB + CA + AB) / 2.0;
		double S = Math.sqrt(p * (p - CB) * (p - CA) * (p - AB));
		return S;
	}

}
