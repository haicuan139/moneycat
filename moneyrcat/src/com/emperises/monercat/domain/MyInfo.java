package com.emperises.monercat.domain;

public class MyInfo extends DomainObject {
	private static final long serialVersionUID = 1L;
	private String userName;
	private String gender;
	private String age;
	private String address;
	private String balance; //余额
	private String telNumber;
	private String myLink;
	private String myQRCodePath;
	private String level;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getMyLink() {
		return myLink;
	}
	public void setMyLink(String myLink) {
		this.myLink = myLink;
	}
	public String getMyQRCodePath() {
		return myQRCodePath;
	}
	public void setMyQRCodePath(String myQRCodePath) {
		this.myQRCodePath = myQRCodePath;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	
	
}
