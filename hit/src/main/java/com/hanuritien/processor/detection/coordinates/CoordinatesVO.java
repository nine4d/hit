/**
 * 
 */
package com.hanuritien.processor.detection.coordinates;

import java.io.Serializable;
import java.util.List;

import com.hanuritien.processor.detection.controller.CoordinateType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author changu
 *
 */
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PUBLIC)
public class CoordinatesVO implements Serializable {
	private static final long serialVersionUID = -3284579764688512781L;

	@Getter @Setter
	private String id;
	@Getter @Setter
	private CoordinateType type;
	@Getter @Setter
	private String code;	
	@Getter @Setter
	private String name;
	@Getter @Setter
	private List<LocationVO> locations;
	@Getter @Setter
	private List<List<LocationVO>> multiLocations;
}
