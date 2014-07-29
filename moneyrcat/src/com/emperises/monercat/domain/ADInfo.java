package com.emperises.monercat.domain;

import java.io.Serializable;

public class ADInfo implements Serializable {
	private String adId;// 广告ID
	private String adImage; // 广告大图图片
	private String adDescription; // 广告内容
	private String adSource;// 广告厂商
	private String adIcon; // 广告的小图标
	private String adAward; // 广告的赏金
	private String adType;// 广告类型 0:默认类型广告 private String ：可以推荐给好友
	private static final long serialVersionUID = 1L;
	private String adTtile; // 广告标题

	public String getAdTtile() {
		return adTtile;
	}

	public void setAdTtile(String adTtile) {
		this.adTtile = adTtile;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdImage() {
		return adImage;
	}

	public void setAdImage(String adImage) {
		this.adImage = adImage;
	}

	public String getAdDescription() {
		return adDescription;
	}

	public void setAdDescription(String adDescription) {
		this.adDescription = adDescription;
	}

	public String getAdSource() {
		return adSource;
	}

	public void setAdSource(String adSource) {
		this.adSource = adSource;
	}

	public String getAdIcon() {
		return adIcon;
	}

	public void setAdIcon(String adIcon) {
		this.adIcon = adIcon;
	}

	public String getAdAward() {
		return adAward;
	}

	public void setAdAward(String adAward) {
		this.adAward = adAward;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	

	@Override
	public String toString() {
		return "ADInfo [adTtile=" + adTtile + ", adId=" + adId + ", adImage="
				+ adImage + ", adDescription=" + adDescription + ", adSource="
				+ adSource + ", adIcon=" + adIcon + ", adAward=" + adAward
				+ ", adType=" + adType + "]";
	}

}
