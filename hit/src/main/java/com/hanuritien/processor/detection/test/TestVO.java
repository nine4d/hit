/**
 * 
 */
package com.hanuritien.processor.detection.test;

import java.io.Serializable;

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
public class TestVO implements Serializable {
	private static final long serialVersionUID = -3542143430542368672L;

	@Getter @Setter
	private String test;
}
