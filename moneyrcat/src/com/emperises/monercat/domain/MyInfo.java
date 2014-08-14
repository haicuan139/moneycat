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
	private String myQRCodeLocalPath;//我的二维码,本地地址
	private String myQRCodeUrlPath; //我的二维码在线地址
	private String level;
	private String userId;
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMyQRCodeLocalPath() {
		return myQRCodeLocalPath;
	}
	public void setMyQRCodeLocalPath(String myQRCodeLocalPath) {
		this.myQRCodeLocalPath = myQRCodeLocalPath;
	}
	public String getMyQRCodeUrlPath() {
		return myQRCodeUrlPath;
	}
	public void setMyQRCodeUrlPath(String myQRCodeUrlPath) {
		this.myQRCodeUrlPath = myQRCodeUrlPath;
	}
	
	
	
}
