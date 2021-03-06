package com.emperises.monercat.interfacesandevents;

import com.emperises.monercat.domain.MyInfo;

public interface EditMyInfoInterface {

	void onMyInfoChange(MyInfo info);
	void onAgeEditAfter(String age);
	void onNickNameEditAfter(String nickNmae);
	void onGenderEditAfter(String gender);
	void onAddressEditAfter(String address);
}
