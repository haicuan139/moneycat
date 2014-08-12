package com.emperises.monercat.interfaces;

import com.emperises.monercat.domain.MyInfo;

public interface EditMyInfoInterface {

	void onInfoEditAfter(MyInfo info);
	void onAgeEditAfter(String age);
	void onNickNameEditAfter(String nickNmae);
	void onGenderEditAfter(String gender);
	void onAddressEditAfter(String address);
}
