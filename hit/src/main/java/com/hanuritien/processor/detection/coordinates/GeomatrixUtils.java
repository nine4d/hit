package com.hanuritien.processor.detection.coordinates;

import java.util.ArrayList;
import java.util.List;

import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Rectangle;
import com.hanuritien.processor.detection.controller.CoordinateType;
import com.esri.core.geometry.Polygon;

public class GeomatrixUtils {
	public static Polygon getPolygon(List<LocationVO> arg) {
		Polygon ret = new Polygon();
		
		boolean first = true;
		for (LocationVO tmp : arg) {
			if (!first) {
				ret.lineTo(tmp.getLon().doubleValue(), tmp.getLat().doubleValue());
			} else {
				ret.startPath(tmp.getLon().doubleValue(), tmp.getLat().doubleValue());
				first = false;
			}
		}
		
		return ret;
	}
	
	public static List<Rectangle> getBounds(CoordinatesVO arg) {
		List<Rectangle> ret = new ArrayList<>();
		
		if (arg.getType() == CoordinateType.Polygon) {
			float top, left, right, bottom;
			float add = 200;
			
			LocationVO first = arg.getLocations().get(0);
			top = first.getLat().floatValue() + add;
			left = first.getLon().floatValue() + add;
			right = first.getLon().floatValue() + add;
			bottom = first.getLat().floatValue() + add;
			
			for (LocationVO tmp : arg.getLocations()) {
				float x = tmp.getLon().floatValue() + add;
				float y = tmp.getLat().floatValue() + add;
				
				if (top > y) {
					top = y;
				}
				if (left > x) {
					left = x;
				}
				if (right < x) {
					right = x;
				}
				if (bottom < y) {
					bottom = y;
				}
			}
			ret.add(Geometries.rectangle(left - add, top - add, right - add, bottom - add));
		} else if (arg.getType() == CoordinateType.MultiPolygon) {
			float top, left, right, bottom;
			float add = 200;
				
			for (List<LocationVO> arr : arg.getMultiLocations()) {
				LocationVO first = arr.get(0);
				top = first.getLat().floatValue() + add;
				left = first.getLon().floatValue() + add;
				right = first.getLon().floatValue() + add;
				bottom = first.getLat().floatValue() + add;		
				
				for (LocationVO tmp : arr) {
					float x = tmp.getLon().floatValue() + add;
					float y = tmp.getLat().floatValue() + add;
					
					if (top > y) {
						top = y;
					}
					if (left > x) {
						left = x;
					}
					if (right < x) {
						right = x;
					}
					if (bottom < y) {
						bottom = y;
					}					
				}
				ret.add(Geometries.rectangle(left - add, top - add, right - add, bottom - add));
			}
		}
		
		return ret;
	}
	
	public static Rectangle getBound(CoordinatesVO arg) {
		Rectangle ret = null;
		
		if (arg.getType() == CoordinateType.Polygon) {
			float top, left, right, bottom;
			float add = 200;
			
			LocationVO first = arg.getLocations().get(0);
			top = first.getLat().floatValue() + add;
			left = first.getLon().floatValue() + add;
			right = first.getLon().floatValue() + add;
			bottom = first.getLat().floatValue() + add;
			
			for (LocationVO tmp : arg.getLocations()) {
				float x = tmp.getLon().floatValue() + add;
				float y = tmp.getLat().floatValue() + add;
				
				if (top > y) {
					top = y;
				}
				if (left > x) {
					left = x;
				}
				if (right < x) {
					right = x;
				}
				if (bottom < y) {
					bottom = y;
				}
			}
			ret = Geometries.rectangle(left - add, top - add, right - add, bottom - add);
		} else if (arg.getType() == CoordinateType.MultiPolygon) {
			float top, left, right, bottom;
			float add = 200;
			
			LocationVO first = arg.getMultiLocations().get(0).get(0);
			top = first.getLat().floatValue() + add;
			left = first.getLon().floatValue() + add;
			right = first.getLon().floatValue() + add;
			bottom = first.getLat().floatValue() + add;			
			
			for (List<LocationVO> arr : arg.getMultiLocations()) {
				for (LocationVO tmp : arr) {
					float x = tmp.getLon().floatValue() + add;
					float y = tmp.getLat().floatValue() + add;
					
					if (top > y) {
						top = y;
					}
					if (left > x) {
						left = x;
					}
					if (right < x) {
						right = x;
					}
					if (bottom < y) {
						bottom = y;
					}					
				}
			}
			ret = Geometries.rectangle(left - add, top - add, right - add, bottom - add);
		}
		
		return ret;
	}
}
