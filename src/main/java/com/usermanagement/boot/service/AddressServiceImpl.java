package com.usermanagement.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usermanagement.boot.dao.AddressDao;
import com.usermanagement.boot.entity.Address;

@Service
public class AddressServiceImpl implements AddressService{
	
	private AddressDao addressDao;
	
	@Autowired
	public AddressServiceImpl(@Qualifier("addressHibernateImpl") AddressDao addressDao) {
		this.addressDao  =  addressDao;
		
	}

	@Override
	@Transactional
	public void saveAddress(Address address) {
		addressDao.saveAddress(address);
		
	}

	@Override
	@Transactional
	public List<Address> findAddressesOfUser(int addressId) {
		return addressDao.findAddressesOfUser(addressId);
	}

	@Override
	@Transactional
	public void deleteAddressById(int id) {
		addressDao.deleteAddressById(id);
		
	}

	@Override
	@Transactional
	public Address findAddressById(int addressId) {
		return addressDao.findAddressById(addressId);
	}

	

}
