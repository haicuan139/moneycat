package com.emperises.monercat.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.emperises.monercat.domain.MyInfo;

public class EditMyInfoEvent {

	private static EditMyInfoEvent mEditMyInfoEvent;
	private List<EditMyInfoInterface> mListeners =  new ArrayList<EditMyInfoInterface>();
	private EditMyInfoEvent() {
	}
	public static EditMyInfoEvent getInstance(){
		if(mEditMyInfoEvent == null){
			mEditMyInfoEvent = new EditMyInfoEvent();
		}
		return mEditMyInfoEvent;
	}
	public void addEditInfoListener(EditMyInfoInterface listener){
		mListeners.add(listener);
	}
	public void fireEditInfoEvent(MyInfo info){
		for(EditMyInfoInterface listener : mListeners){
			if(listener != null){
				listener.onInfoEditAfter(info);
			}
		}
	}
	public void fireAgeEditEvent(String age){
		for(EditMyInfoInterface listener : mListeners){
			if(listener != null){
				listener.onAgeEditAfter(age);
			}
		}
	}
	public void fireNickNameEditEvent(String nickNmae){
		for(EditMyInfoInterface listener : mListeners){
			if(listener != null){
				listener.onNickNameEditAfter(nickNmae);
			}
		}
	}
	public void fireGenderEditEvent(String gender){
		for(EditMyInfoInterface listener : mListeners){
			if(listener != null){
				listener.onGenderEditAfter(gender);
			}
		}
	}
	public void fireAddressEditEvent(String address){
		for(EditMyInfoInterface listener : mListeners){
			if(listener != null){
				listener.onAddressEditAfter(address);
			}
		}
	}
}
