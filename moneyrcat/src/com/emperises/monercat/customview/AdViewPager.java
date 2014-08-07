package com.emperises.monercat.customview;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class AdViewPager extends ViewPager{

	public AdViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public AdViewPager(Context context) {
		super(context);
	}
    PointF downPoint = new PointF();  
    OnSingleTouchListener onSingleTouchListener;  
  
    @Override  
    public boolean onTouchEvent(MotionEvent evt) {  
        switch (evt.getAction()) {  
        case MotionEvent.ACTION_DOWN:  
            downPoint.x = evt.getX();  
            downPoint.y = evt.getY();  
            if (this.getChildCount() > 1) { //�����ݣ�����1��ʱ  
                // ֪ͨ�丸�ؼ������ڽ��е��Ǳ��ؼ��Ĳ���������������  
                getParent().requestDisallowInterceptTouchEvent(true);  
            }  
            break;  
        case MotionEvent.ACTION_MOVE:  
            if (this.getChildCount() > 1) {   
                getParent().requestDisallowInterceptTouchEvent(true);  
            }  
            break;  
        case MotionEvent.ACTION_UP:  
            if (PointF.length(evt.getX() - downPoint.x, evt.getY()  
                    - downPoint.y) < (float) 5.0) {  
                onSingleTouch(this);  
                return true;  
            }  
            break;  
        }  
        return super.onTouchEvent(evt);  
    }  
  
    public void onSingleTouch(View v) {  
        if (onSingleTouchListener != null) {  
            onSingleTouchListener.onSingleTouch(v);  
        }  
    }  
  
    public interface OnSingleTouchListener {  
        public void onSingleTouch(View v);  
    }  
  
    public void setOnSingleTouchListener(  
            OnSingleTouchListener onSingleTouchListener) {  
        this.onSingleTouchListener = onSingleTouchListener;  
    }

}
