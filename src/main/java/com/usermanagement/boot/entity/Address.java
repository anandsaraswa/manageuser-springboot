package com.usermanagement.boot.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="address")
public class Address {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="user_id")
	private int userId;
	

	@Column(name="address1")
	private String address1;
	
	@Column(name="pincode")
	private int pincode;
	
	@Column(name="address_type")
	private String addressType;
	
	@Column(name="city")
	private String city;
	
	@Column(name="created_on")
	private Date created_on;
	
	public Address() {
		
	}
	
	

	public Address(String address1,int userId, int pincode, String addressType, String city, Date created_on) {
		this.userId =  userId;
		this.address1 = address1;
		this.pincode = pincode;
		this.addressType = addressType;
		this.city = city;
		this.created_on = created_on;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}



	public Date getCreated_on() {
		return created_on;
	}



	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	

}
