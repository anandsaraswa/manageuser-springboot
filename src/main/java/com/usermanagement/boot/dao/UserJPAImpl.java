package com.usermanagement.boot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.usermanagement.boot.entity.Users;

@Repository
public class UserJPAImpl implements UserDAO {
	
	//Define field for it
	private EntityManager entityManager;
	
	//setup constructor injection
	@Autowired
	public UserJPAImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	
	@Override
	@Transactional
	public List<Users> findAll() {
		
		//create a query
		
		Query theQuery =  this.entityManager.createQuery("from User", Users.class);
		
		//execute the query and get the result list
		
		List<Users> users =  theQuery.getResultList();
		
		//return the results
	
		return users;
	}


	@Override
	public Users findUserById(int id) {
		
	//	Session currentSession =  this.entityManager.unwrap(Session.class);
		
		Users theUser =  this.entityManager.find(Users.class, id);
		
		return theUser;
	}


	@Override
	public void save(Users user) {
		//Session currentSession =  this.entityManager.unwrap(Session.class);
		
	Users  theUser =	this.entityManager.merge(user);
	user.setId(theUser.getId());
		
	}


	@Override
	public void deleteById(int id) {
		Query query =  this.entityManager.createQuery("delete from User where id=:userId");
		query.setParameter("userId", id);
		query.executeUpdate();
	}


	@Override
	public Users findUsernamePassword(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
