package com.emperises.monercat.domain;

public class MyInfo extends DomainObject {
	private static final long serialVersionUID = 1L;
	private String nickName; //姓名
	private String gender; //性别
	private String age; //年龄
	private String address; //地址
	private String balance; //余额
	private String telNumber; //电话号码
	private String myLink; //个人链接
	private String myQRCodeLocalPath;//我的二维码,本地地址
	private String myQRCodeUrlPath; //我的二维码在线地址
	private String level;//等级
	private String userId; //用户标识
	private String deviceId; //设备ID
	private String headerResId; //头像资源ID
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String userName) {
		this.nickName = userName;
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
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public int getHeaderResId() {
		//转换为INT类型
		return Integer.parseInt(headerResId);
	}
	public void setHeaderResId(String headerResId) {
		this.headerResId = headerResId;
	}
	
	
	
}
