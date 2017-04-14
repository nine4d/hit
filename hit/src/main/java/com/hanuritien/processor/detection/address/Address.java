/**
 * 
 */
package com.hanuritien.processor.detection.address;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * @author changu
 *
 */
@Entity
public class Address implements Serializable {
	private static final long serialVersionUID = 6935226865936406570L;

	@Id
	@Getter @Setter
	private String code;
	@Getter @Setter
	private String address;
	
	protected Address() {
		
	}
	
	public Address(String c, String a) {
		this.code = c;
		this.address = a;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
