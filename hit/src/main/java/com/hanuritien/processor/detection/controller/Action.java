package com.hanuritien.processor.detection.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Action {
	Create,
	Update,
	Delete,
	Export;
	
	private static Map<String, Action> namesMap = new HashMap<String, Action>();
	
	static {
		namesMap.put("create", Create);
		namesMap.put("update", Update);
		namesMap.put("delete", Delete);
		namesMap.put("export", Export);
	}
	
	@JsonCreator
	public static Action forValue(String val){
		return namesMap.get(StringUtils.lowerCase(val));
	}

	@JsonValue
	public String toValue() {
		for (Entry<String, Action> entry : namesMap.entrySet()) {
			if (entry.getValue() == this)
				return entry.getKey();
		}
		
		return null;
	}
}
