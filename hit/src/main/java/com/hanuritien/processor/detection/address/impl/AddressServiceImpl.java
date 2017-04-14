package com.hanuritien.processor.detection.address.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.hanuritien.processor.detection.address.Address;
import com.hanuritien.processor.detection.address.AddressService;

@Component("addressService")
public class AddressServiceImpl implements AddressService {

	private final AddressRepository addressRepository;
	
	public AddressServiceImpl(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}
	
	@Override
	public Page<Address> findAll(Pageable pageable) {
		return addressRepository.findAll(pageable);
	}

	@Override
	public Address findByCode(String code) {
		return addressRepository.findByCode(code);
	}

}
