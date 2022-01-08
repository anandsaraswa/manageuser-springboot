package com.usermanagement.boot.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.usermanagement.boot.entity.Address;

@Repository
public class AddressHibernateImpl implements AddressDao {
	
	//Define field 
	private EntityManager entityManager;
	
	//setup constructor injection
		@Autowired
		public AddressHibernateImpl(EntityManager theEntityManager) {
			entityManager = theEntityManager;
		}


	@Override
	public void saveAddress(Address address) {
		Session currentSession =  this.entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(address);

	}

	@Override
	public List<Address> findAddressesOfUser(int userId) {
		Session currentSession =   this.entityManager.unwrap(Session.class);
		
		Query<Address> theQuery =  currentSession.createQuery("from Address where user_id=:userId", Address.class);
		
		theQuery.setParameter("userId", userId);
		
		return theQuery.getResultList();
	}

	@Override
	public void deleteAddressById(int id) {
		Session currentSession =  this.entityManager.unwrap(Session.class);
		Query query =  currentSession.createQuery("delete from Address where id=:addressId");
		query.setParameter("addressId", id);
		query.executeUpdate();
	}


	@Override
	public Address findAddressById(int addressId) {
		Session currentSession =  this.entityManager.unwrap(Session.class);
		
		Address theAddress =  currentSession.get(Address.class, addressId);
		
		return theAddress;
	}

}
