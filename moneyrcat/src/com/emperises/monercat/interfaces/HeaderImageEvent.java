package com.emperises.monercat.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.emperises.monercat.utils.Logger;

public class HeaderImageEvent {

	private static HeaderImageEvent event;
	
	private List<HeaderImageChangeInterface> mHeaderImageChangeInterfaces = new ArrayList<HeaderImageChangeInterface>(); 
	private HeaderImageEvent() {
	}
	public static HeaderImageEvent getInstance(){
		if(event == null){
			event = new HeaderImageEvent();
		}
		return event;
	}
	public void fireHeaderChangeImageEvent(int resId){
		for (int i = 0; i < mHeaderImageChangeInterfaces.size(); i++) {
			if(mHeaderImageChangeInterfaces.get(i) != null){
				mHeaderImageChangeInterfaces.get(i).onHeaderImageChange(resId);
			}
		}
		Logger.i("HEADERIMAGE", "子类个数:"+mHeaderImageChangeInterfaces.size());
	}
	public void addHeaderImageListener(HeaderImageChangeInterface listener){
		mHeaderImageChangeInterfaces.add(listener);
	}
}
