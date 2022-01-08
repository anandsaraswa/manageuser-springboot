package com.usermanagement.boot.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.usermanagement.boot.entity.Address;
import com.usermanagement.boot.entity.AuthenticateRequest;
import com.usermanagement.boot.entity.AuthenticateResponse;
import com.usermanagement.boot.entity.Users;
import com.usermanagement.boot.exception.AddressNotFoundException;
import com.usermanagement.boot.exception.UserErrorResponse;
import com.usermanagement.boot.exception.UserNotFoundException;
import com.usermanagement.boot.jwt.JwtUtil;
import com.usermanagement.boot.service.AddressService;
import com.usermanagement.boot.service.UserService;

@RestController
@RequestMapping("/api")
public class UserManageRestController {
	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtils;

	private String FILE_PATH_ROOT = "src/main/resources/images/";

	@PostMapping("/authenticate")
	public AuthenticateResponse authenticate(@RequestBody AuthenticateRequest authenticateRequest) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticateRequest.getUsername(), authenticateRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password");

		}

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(),
						authenticateRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		Users users = this.userService.findUsernamePassword(authenticateRequest.getUsername());
		final String jwt = this.jwtUtils.generateToken(authentication);
		AuthenticateResponse authenticateResponse = new AuthenticateResponse(jwt, 200);
		authenticateResponse.setUsers(users);
		return authenticateResponse;

	}

	@GetMapping("/users")
	public List<Users> getAllUsers() {
		return userService.findAll();
	}

	@GetMapping("/user/{userId}")
	public Users getUser(@PathVariable int userId) {
		Users user = userService.findUserById(userId);
		if (user == null) {
			throw new UserNotFoundException("User id not found - " + userId);
		}
		return user;
	}
	
	@GetMapping("/testing")
	public String getTesting()  {
		
		return "Testing working fine.....";
	}

	@GetMapping("/image/{filename}")
	public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) throws IOException {
		Path uploadPath = Paths.get(FILE_PATH_ROOT);
		Path filePath = uploadPath.resolve(filename);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(Files.readAllBytes(filePath));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/user", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },  produces = MediaType.APPLICATION_JSON_VALUE)
	public UserErrorResponse addUser(@RequestPart("file") MultipartFile file, @RequestPart("users") Users user)
			throws IOException {

		if (file != null) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			if(userService.saveFile(FILE_PATH_ROOT, fileName, file)) {
				System.out.print(fileName);
				user.setImage(fileName);
			}
		} 
		
		user.setId(0);
		userService.save(user);
		UserErrorResponse userResponse = new UserErrorResponse(200, "User Added", System.currentTimeMillis());
		userResponse.setId(user.getId());
		 
		return userResponse;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/user")
	public UserErrorResponse updateUser(@RequestBody Users user) {
		userService.save(user);

		UserErrorResponse userResponse = new UserErrorResponse(200, "User Updated", System.currentTimeMillis());
		userResponse.setId(user.getId());
		return userResponse;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/{userId}")
	public UserErrorResponse deleteUser(@PathVariable int userId) {

		Users user = userService.findUserById(userId);
		if (user == null) {
			throw new UserNotFoundException("User id not found - " + userId);
		}

		userService.deleteById(userId);

		return new UserErrorResponse(200, "User Deleted", System.currentTimeMillis());
	}

	@PostMapping("/address")
	public UserErrorResponse addUser(@RequestBody Address address) {

		address.setId(0);

		addressService.saveAddress(address);

		UserErrorResponse userResponse = new UserErrorResponse(200, "Address Added", System.currentTimeMillis());
		userResponse.setId(address.getId());

		return userResponse;
	}

	@PutMapping("/address")
	public UserErrorResponse updateAddress(@RequestBody Address address) {
		addressService.saveAddress(address);

		UserErrorResponse userResponse = new UserErrorResponse(200, "Address Updated", System.currentTimeMillis());
		userResponse.setId(address.getId());
		return userResponse;
	}

	@GetMapping("/user/address/{userId}")
	public List<Address> getUserAddresses(@PathVariable int userId) {

		List<Address> addressList = addressService.findAddressesOfUser(userId);
		/*
		 * if(addressList.size() == 0) { throw new
		 * UserNotFoundException("User id not found - "+userId); }
		 */
		return addressList;
	}

	@DeleteMapping("/user/delete/address/{addressId}")
	public UserErrorResponse deleteAddress(@PathVariable int addressId) {

		Address address = addressService.findAddressById(addressId);
		if (address == null) {
			throw new AddressNotFoundException("Address id not found" + addressId);
		}

		addressService.deleteAddressById(addressId);

		return new UserErrorResponse(200, "Address deleted.", System.currentTimeMillis());
	}

}
