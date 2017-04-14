package com.hanuritien.processor.detection.controller;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PUBLIC)
public class RequestListVO<T> implements Serializable {
	private static final long serialVersionUID = -3190905010689463126L;

	@Getter @Setter
	Integer draw;
	@Getter @Setter
	Action action;
	@Getter @Setter
	List<T> data;
}
