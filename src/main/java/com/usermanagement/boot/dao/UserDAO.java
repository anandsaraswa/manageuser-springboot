package com.usermanagement.boot.dao;

import java.util.List;

import com.usermanagement.boot.entity.Users;

public interface UserDAO {
	
	//use dao methods-----------------------------------------
	public List<Users> findAll();
	
	public Users findUserById(int id);
	
	public void save(Users user);
	
	public void deleteById(int id);
	
	public Users findUsernamePassword(String username);
	


}
