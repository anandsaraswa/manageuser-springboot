package com.usermanagement.boot.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.usermanagement.boot.entity.Users;

public interface UserService {
	
	public List<Users> findAll();
	
	public Users findUserById(int id);
	
	public void save(Users user);
	
	public void deleteById(int id);
	
	public Users findUsernamePassword(String username);

	public boolean saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException;
}
