/**
 * 
 */
package com.hanuritien.processor.detection.address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author changu
 *
 */
public interface AddressService {
	Page<Address> findAll(Pageable pageable);
	Address findByCode(String code);
}
