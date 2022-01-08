package com.usermanagement.boot.entity;

public class AuthenticateResponse {
	
	private int status;
	private String jwt;
	private Users users;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public AuthenticateResponse(String jwt, int status) {
		this.status = status;
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}
