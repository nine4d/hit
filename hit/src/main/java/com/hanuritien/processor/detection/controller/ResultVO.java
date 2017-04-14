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

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PUBLIC)
public class ResultVO<T> implements Serializable{
	private static final long serialVersionUID = 2225760532238108192L;

	@Getter @Setter
	Integer draw;
	@Getter @Setter
	int resultCode = -1;
	@Getter @Setter
	String resultMessage = "";
	@Getter @Setter
	List<T> data;
}
