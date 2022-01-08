package com.usermanagement.boot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.usermanagement.boot.entity.CustomUser;
import com.usermanagement.boot.entity.Users;


@Service
public class MyUserDetailService implements UserDetailsService {
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userService.findUsernamePassword(username);
		
		  if (user == null) {
	            throw new UsernameNotFoundException("Could not find user");
	        }
		return new CustomUser(user);
	}

}
