package com.sir.app.test;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhuyinan on 2019/6/26.
 */
public class StampView extends View {

    public StampView(Context context) {
        this(context, null);
    }

    public StampView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StampView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        int actionIndex = event.getActionIndex();
        int pointerCount = event.getPointerCount();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                Log.d("TAG", "第1触点按下");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("TAG", "第1触点抬起");
                break;
            //多个触电响应下面事件
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d("TAG", "第" + (actionIndex + 1) + "触点按下");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d("TAG", "第" + (actionIndex + 1) + "触点抬起");
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {


    }
}
