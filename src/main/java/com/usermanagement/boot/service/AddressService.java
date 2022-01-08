package com.usermanagement.boot.service;

import java.util.List;

import com.usermanagement.boot.entity.Address;

public interface AddressService {
	
	public void saveAddress(Address address);
	
	public Address findAddressById(int userId);
	
	public List<Address> findAddressesOfUser(int userId);
	
	public void deleteAddressById(int id);
}
