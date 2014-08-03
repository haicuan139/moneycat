package com.emperises.monercat.domain;

import java.io.Serializable;

public class ADInfo implements Serializable {
	private String adId;// 
	private String adImage; // 
	private String adDescription; //
	private String adSource;// 
	private String adIcon; // 
	private String adAward; // 
	private String adType;//
	private static final long serialVersionUID = 1L;
	private String adTtile; // 

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
