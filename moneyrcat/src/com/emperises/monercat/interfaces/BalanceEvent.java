package com.emperises.monercat.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.emperises.monercat.utils.Logger;

public class BalanceEvent {

	private static BalanceEvent mBalanceEvent;
	private List<BalanceInterface> mListeners =  new ArrayList<BalanceInterface>();
	private BalanceEvent() {
	}
	public static BalanceEvent getInstance(){
		if(mBalanceEvent == null){
			mBalanceEvent = new BalanceEvent();
		}
		return mBalanceEvent;
	}
	public void removeListener(BalanceInterface listener){
		if(listener != null){
			mListeners.remove(listener);
			
		}
	}
	public void removeAllListener(){
		mListeners.clear();
	}
	public void addBalanceListener(BalanceInterface listener){
		mListeners.add(listener);
	}
	public void fireBalanceAddEvent(float balance){
		Logger.i("BALANCE", "余额增加接口数量:"+mListeners.size());
		for(BalanceInterface listener : mListeners){
			if(listener != null){
				listener.onBalanceAddAfter(balance);
			}
		}
	}
	public void fireBalanceDecEvent(float balance){
		Logger.i("BALANCE", "余额减少接口数量:"+mListeners.size());
		for(BalanceInterface listener : mListeners){
			if(listener != null){
				listener.onBalanceDecAfter(balance);
			}
		}
	}
}
