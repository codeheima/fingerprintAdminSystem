package org.ma.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BaiduMapUtils {
	/**
	 * 判断点位是否在视野或者指定范围之内
	 * 
	 * @param lon
	 * @param lat
	 * @param area
	 * @return
	 */
	public static boolean checkPointInPolygon(String lon, String lat, String area) {
		String geometry = area.replace("POLYGON((", "").replace("))", "");
		List<Point2D.Double> polygon = new ArrayList<Point2D.Double>();
		for (String ss : geometry.split(",")) {
			double lonDb = Double.parseDouble(ss.split(" ")[0]);
			double latDb = Double.parseDouble(ss.split(" ")[1]);
			Point2D.Double point = new Point2D.Double(lonDb, latDb);
			polygon.add(point);
		}
		double lond = Double.parseDouble(lon);
		double latd = Double.parseDouble(lat);
		Point2D.Double point = new Point2D.Double(lond, latd);
		boolean flag = checkWithJdkGeneralPath(point, polygon);
		return flag;
	}

	/**
	 * 判断一个点是否在一个多边形区域内
	 * 
	 * @param point
	 * @param polygon
	 * @return
	 */
	public static boolean checkWithJdkGeneralPath(Point2D.Double point,
			List<Point2D.Double> polygon) {
		java.awt.geom.GeneralPath p = new java.awt.geom.GeneralPath();
		Point2D.Double first = polygon.get(0);
		p.moveTo(first.x, first.y);
		for (Point2D.Double d : polygon) {
			p.lineTo(d.x, d.y);
		}
		p.lineTo(first.x, first.y);
		p.closePath();
		return p.contains(point);
	}
}
