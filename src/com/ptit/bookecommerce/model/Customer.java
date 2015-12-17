package com.ptit.bookecommerce.model;

public class Customer {
	private int id;
	private String userName;
	private String passWord;
	private String fullName;
	private String email;
	private String phone;
	private String city;
	private String district;
	private String ward;
	private String streetNumber;
	private String postalCode;

	public Customer() {
		super();
	}

	public Customer(int id, String userName, String passWord, String fullName,
			String email, String phone, String city, String district,
			String ward, String streetNumber, String postalCode) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.city = city;
		this.district = district;
		this.ward = ward;
		this.streetNumber = streetNumber;
		this.postalCode = postalCode;
	}

	public Customer(int id, String fullName, String email, String phone,
			String city, String district, String ward, String streetNumber,
			String postalCode) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.city = city;
		this.district = district;
		this.ward = ward;
		this.streetNumber = streetNumber;
		this.postalCode = postalCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public static Customer customerLogin = null;

}
