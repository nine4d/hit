/**
 * 
 */
package com.hanuritien.processor.detection.coordinates;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class LocationVO implements Serializable {
	private static final long serialVersionUID = -6267857736872365308L;

	@Getter @Setter
	private BigDecimal lat;
	
	@Getter @Setter
	private BigDecimal lon;
	
	@Getter @Setter
	private BigDecimal radius;
}
