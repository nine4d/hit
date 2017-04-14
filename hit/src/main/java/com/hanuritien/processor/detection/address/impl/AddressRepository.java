package com.hanuritien.processor.detection.address.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.hanuritien.processor.detection.address.Address;

public interface AddressRepository extends Repository<Address, String> {
	Page<Address> findAll(Pageable pageable);
	
	Address findByCode(String code);
}
