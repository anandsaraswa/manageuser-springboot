package com.usermanagement.boot.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.usermanagement.boot.entity.Users;

@Repository
public class UserHibernateImpl implements UserDAO {
	
	//Define field for it
	private EntityManager entityManager;
	
	//setup constructor injection
	@Autowired
	public UserHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	
	@Override
	public List<Users> findAll() {
		
		//get hibernate session
		Session currentSession =   this.entityManager.unwrap(Session.class);
		
		//create a query
		Query<Users> theQuery =  currentSession.createQuery("from Users", Users.class);
		
		//execute the query and get the result list
		
		List<Users> users =  theQuery.getResultList();
		
		//return the results
	
		return users;
	}


	@Override
	public Users findUserById(int id) {
		
		Session currentSession =  this.entityManager.unwrap(Session.class);
		
		Users theUser =  currentSession.get(Users.class, id);
		
		return theUser;
	}


	@Override
	public void save(Users user) {
		Session currentSession =  this.entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(user);
		
	}


	@Override
	public void deleteById(int id) {
		Session currentSession =  this.entityManager.unwrap(Session.class);
		
		Query query =  currentSession.createQuery("delete from Users where id=:userId");
		query.setParameter("userId", id);
		query.executeUpdate();
	}


	@Override
	public Users findUsernamePassword(String username) {
		Session currentSession =   this.entityManager.unwrap(Session.class);
		
		Query<Users> theQuery =  currentSession.createQuery("from Users where email=:emailId", Users.class);
		
		theQuery.setParameter("emailId", username);
		
		Users user =  theQuery.getSingleResult();

		return user;
	}

}
