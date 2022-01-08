package com.usermanagement.boot.dao;

import java.util.List;

import com.usermanagement.boot.entity.Address;

public interface AddressDao {
	//address dao methods--------------------------------------
	public void saveAddress(Address address);
	
	public List<Address> findAddressesOfUser(int userId);
	
	public void deleteAddressById(int id);

	public Address findAddressById(int addressId);
}
