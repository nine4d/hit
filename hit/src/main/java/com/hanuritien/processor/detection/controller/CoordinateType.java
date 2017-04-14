package com.hanuritien.processor.detection.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CoordinateType {
	Circle,
	Line,
	Polygon,
	MultiPolygon;
	
	private static Map<String, CoordinateType> namesMap = new HashMap<String, CoordinateType>();
	
	static {
		namesMap.put("circle", Circle);
		namesMap.put("line", Line);
		namesMap.put("polygon", Polygon);
		namesMap.put("multipolygon", MultiPolygon);
	}	
	
	@JsonCreator
	public static CoordinateType forValue(String val){
		return namesMap.get(StringUtils.lowerCase(val));
	}

	@JsonValue
	public String toValue() {
		for (Entry<String, CoordinateType> entry : namesMap.entrySet()) {
			if (entry.getValue() == this)
				return entry.getKey();
		}
		
		return null;
	}	
}
