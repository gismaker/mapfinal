package com.mapfinal.operator;

import com.mapfinal.geometry.GeoKit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.operation.buffer.BufferOp;
import org.locationtech.jts.operation.buffer.BufferParameters;
import org.locationtech.jts.operation.linemerge.LineMerger;
import org.locationtech.jts.operation.overlay.OverlayOp;
import org.locationtech.jts.operation.polygonize.Polygonizer;

/**
 * JTS Geometry合并、裁切、叠加分析等
 * https://blog.csdn.net/kone0611/article/details/83781484
 * 
 * JTS Geometry Operations(二)
 * https://blog.csdn.net/cdl2008sky/article/details/7578886
 */
public class Operator {
	/**
	 * 返回(A)与(B)中距离最近的两个点的距离
	 * @param a
	 * @param b
	 * @return
	 */
	public double distance(Geometry a,Geometry b){
		return a.distance(b);
	}
	
	/**
	 * 两个几何对象的交集
	 * @param a
	 * @param b
	 * @return
	 */
	public Geometry intersection(Geometry a,Geometry b){
		return a.intersection(b);
	}
	
	/**
	 * 几何对象合并
	 * @param a
	 * @param b
	 * @return
	 */
	public Geometry union(Geometry a,Geometry b){
		return a.union(b);
	}
	
	/**
	 * 在A几何对象中有的，但是B几何对象中没有
	 * @param a
	 * @param b
	 * @return
	 */
	public Geometry difference(Geometry a,Geometry b){
		return a.difference(b);
	}
	
	/**
	 * 缓冲区
	 * @param geom
	 * @return
	 */
	public Geometry buffer(Geometry geom) {
		//方式(一)Geometry bg = geom.buffer(2);
		 
		////方式(二) BufferOP
		BufferOp bufOp = new BufferOp(geom);
		bufOp.setEndCapStyle(BufferParameters.CAP_FLAT);
		Geometry bg = bufOp.getResultGeometry(2);
		return bg;
	}
	
	/**
	 * 多边形化是由线条包围区域形成多边形的过程，各线段不能交叉，只能在端点接触，且完全闭合。
	 * @param lines
	 * @return
	 */
	public Collection<Geometry> polygonization(Collection<LineString> lines) {
		Polygonizer polygonizer = new Polygonizer();
		polygonizer.add(lines);
		Collection<Geometry> polys = polygonizer.getPolygons();//面
		//Collection<Geometry> dangles = polygonizer.getDangles();//悬挂线
		//Collection<Geometry> cuts = polygonizer.getCutEdges(); //面和面的连接线
		System.out.println(polys.size()+":"+polys.toString());
		//System.out.println(dangles.size()+":"+dangles.toString());
		//System.out.println(cuts.size()+":"+cuts.toString());
		//2:[POLYGON ((2 2, 4 4, 6 3, 5 1, 2 2)), POLYGON ((6 4, 8 8, 9 5, 7 1, 6 4))]
		//2:[LINESTRING (6 3, 6 10), LINESTRING (0 0, 1 1)]
		//1:[LINESTRING (6 3, 6 4)]
		return polys;
	}
	
	/**
	 * 线路合并，线路之间不能有交点，并且只在线路末尾有公共交点
	 * @param lines
	 */
	public Collection<Geometry> mergerLine(Collection<LineString> lines) {
		LineMerger lineMerger = new LineMerger();
		lineMerger.add(lines);
		Collection<Geometry> mergerLineStrings = lineMerger.getMergedLineStrings();
		return mergerLineStrings;
	}
	
	/**
	 * 线路合并，并且生成交叉点
	 * @param lines
	 */
	public Geometry unionLine(Collection<Geometry> lines) {
		Geometry nodedLine = null;
		int i=0;
		for (Geometry lineString : lines) {
			if(i==0) {
				nodedLine = lineString;
			} else {
				nodedLine = nodedLine.union(lineString);
			}
		}
//		int num = nodedLine.getNumGeometries();
//		for (int j = 0; j < num; j++) {
//			Geometry eachG = nodedLine.getGeometryN(j);
//			System.out.println(eachG.toText());
//		}
		return nodedLine;
	}
	
	/**
	 * 凹壳分析  <br>
	 * - 包含几何形体的所有点的最小凸壳多边形（外包多边形）
	 * @param p
	 * @return
	 */
	public Geometry convexHull(Geometry geom) {
		return geom.convexHull();
	}
	
	/**
	 * 叠加操作 <br>
	 * - 叠加可以用来确定任何几何图形的布尔组合<br>
	 * - 通过对两个数据进行的一系列集合运算，产生新数据的过程。叠加分析的目的就是通过对空间数据的加工或分析，提取用户需要的新的空间几何信息。<br>
	 * - 叠加分析类型包括：<br>
	 * 1.交叉分析（Intersection） 交叉操作就是多边形AB中所有共同点的集合。<br>
	 * 2.联合分析（Union） AB的联合操作就是AB所有点的集合。<br>
	 * 3.差异分析（Difference） AB形状的差异分析就是A里有B里没有的所有点的集合。<br>
	 * 4.对称差异分析（SymDifference） AB形状的对称差异分析就是位于A中或者B中但不同时在AB中的所有点的集合<br>
	 * @param geometry1
	 * @param geometry2
	 * @param overlayOpCode
	 * @return
	 */
	public Geometry overlay(Geometry geometry1, Geometry geometry2, int overlayOpCode) {
		OverlayOp op = new OverlayOp(geometry1, geometry2);
		Geometry g =op.getResultGeometry(OverlayOp.INTERSECTION);
		return g;
	}
	
	public static void main(String[] args){
		Operator op = new Operator();
		//创建一条线
		List<Coordinate> points1 = new ArrayList<Coordinate>();
		points1.add(GeoKit.point(0,0));
		points1.add(GeoKit.point(1,3));
		points1.add(GeoKit.point(2,3));
		LineString line1 = GeoKit.createLine(points1);
		//创建第二条线
		List<Coordinate> points2 = new ArrayList<Coordinate>();
		points2.add(GeoKit.point(3,0));
		points2.add(GeoKit.point(3,3));
		points2.add(GeoKit.point(5,6));
		LineString line2 = GeoKit.createLine(points2);
		System.out.println(op.distance(line1,line2));//out 1.0
		System.out.println(op.intersection(line1,line2));//out GEOMETRYCOLLECTION EMPTY
		System.out.println(op.union(line1,line2)); //out MULTILINESTRING ((0 0, 1 3, 2 3), (3 0, 3 3, 5 6))
		System.out.println(op.difference(line1,line2));//out LINESTRING (0 0, 1 3, 2 3)
	}

}
