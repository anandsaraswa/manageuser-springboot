package com.usermanagement.boot.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.usermanagement.boot.dao.UserDAO;
import com.usermanagement.boot.entity.Users;

@Service
public class UserServiceImpl implements UserService{
	
	private UserDAO userDao;
	
	@Autowired
	public UserServiceImpl(@Qualifier("userHibernateImpl") UserDAO userDao) {
		this.userDao  =  userDao;
		
	}

	@Override
	@Transactional
	public List<Users> findAll() {
		return userDao.findAll();
	}

	@Override
	@Transactional
	public Users findUserById(int id) {
		return userDao.findUserById(id);
	}

	@Override
	@Transactional
	public void save(Users user) {
		userDao.save(user);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		userDao.deleteById(id);
	}

	@Override
	@Transactional
	public Users findUsernamePassword(String username) {
	
		return userDao.findUsernamePassword(username) ;
	}
	
	@Override
	public boolean saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
         
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            
           return true;
        } catch (IOException ioe) {        
        	return false;
        }      
    }
	
	

}
